package de.dafriedmann.endpoints;

import de.dafriedmann.data.Person;
import de.dafriedmann.testsupport.AbstractMicrostreamTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(PersonResource.class)
class PersonResourceIntegrationTest extends AbstractMicrostreamTest {

    @Test
    void addPersonShouldResultInAddedPerson() throws Exception {
        Person person = createSimplePerson("Max", "Mustermann");
        given().contentType(ContentType.JSON).body(person).when().post("/add").then().statusCode(200);
        assertEquals(1, spm.getRoot().getPersons().size());
    }

    @Test
    void removePersonShouldRemovePerson() {
        Person personToBeDeleted = createAndStoreSimplePerson("Max", "Mustermann");
        given().contentType(ContentType.JSON).body(personToBeDeleted).when().delete("/delete").then().statusCode(204);
        assertTrue(spm.getRoot().getPersons().isEmpty());
    }

    @Test
    void updatePersonShouldUpdatePerson() {
        Person person = createAndStoreSimplePerson("Max", "Mustermann");
        person.setName("Doe");
        given().contentType(ContentType.JSON).body(person).when().post("/update").then().statusCode(200);
        assertEquals("Doe", spm.getRoot().getPersonAt(0).getName());
    }

    @Test
    void getPersonsShouldReturnAllPersons() {
        createAndStoreSimplePerson("Max", "Mustermann");
        createAndStoreSimplePerson("Jane", "Doe");
        Person[] persons = given().contentType(ContentType.JSON).get("/all").as(Person[].class);
        assertArrayEquals(persons, spm.getRoot().getPersons().toArray());
    }

    @Test
    void findPersonByNameShouldReturnPerson() {
        createAndStoreSimplePerson("Max", "Mustermann");
        createAndStoreSimplePerson("Jane", "Doe");
        Person[] persons = given().contentType(ContentType.JSON).get("/findbyname?name=Doe")
                .as(Person[].class);
        assertEquals(1, persons.length);
        assertEquals("Doe", persons[0].getName());
    }

}