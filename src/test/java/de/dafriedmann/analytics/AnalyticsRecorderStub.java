package de.dafriedmann.analytics;

import de.dafriedmann.data.Address;
import io.quarkus.arc.Priority;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.Map;

/**
 * Stub for Analytics: Disable in tests
 */
@Alternative
@Priority(1)
@ApplicationScoped
public class AnalyticsRecorderStub extends AnalyticsRecorder {

    @Override
    public void recordPerson(long personId, Address address) {
        // nothing to do in test here
    }

    @Override
    public void deletePerson(long personId) {
        // nothing to do in test here
    }

    @Override
    public void recordPersons(Map<Long, Address> personsWithAddress) {
        // nothing to do in test here
    }
}
