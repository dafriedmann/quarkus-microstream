package de.dafriedmann.analytics;

import de.dafriedmann.data.Address;
import io.quarkus.arc.Lock;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Values;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Simple noe4j analytics recorder
 * One could use OGM however this is just a small demo ;)
 */
@Lock
@ApplicationScoped
public class AnalyticsRecorder {

    @Inject
    Driver driver;

    public void recordPerson(long personId, Address address) {
        String city = null;
        if (address != null) {
            city = address.getCity();
        }

        try (Transaction tx = driver.session().beginTransaction()) {
            if (city != null) {
                tx.run("MERGE (p:Person {id: $id})" +
                                "MERGE(c:City {name:$city})" +
                                "MERGE (p)-[:RESIDESIN]->(c)",
                        Values.parameters(
                                "id", personId,
                                "city", city)
                );
            } else {
                tx.run("CREATE (f:Person {id: $id}) RETURN f", Values.parameters("id", personId));
            }
            tx.commit();
        }
    }

    public void deletePerson(long personId) {
        try (Transaction tx = driver.session().beginTransaction()) {
            tx.run("MATCH (p:Person {id: $id}) DETACH DELETE p",
                    Values.parameters("id", personId)
            );
            tx.commit();
        }
    }

}
