package de.dafriedmann.endpoints;

import de.dafriedmann.data.Person;
import de.dafriedmann.testsupport.AbstractMicrostreamTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestHTTPEndpoint(PersonResource.class)
class PersonResourceIntegrationTest extends AbstractMicrostreamTest {

    @Test
    void addPersonShouldResultInAddedPerson() throws Exception {
        Person person = createSimplePerson("Max", "Mustermann");
        given().contentType(ContentType.JSON).body(person).when().post("/add").then().statusCode(200);
    }

    @Test
    void removePersonShouldRemovePerson() {
        Person personToBeDeleted = createAndStoreSimplePerson(1L, "Max", "Mustermann");
        given().contentType(ContentType.JSON).body(personToBeDeleted).when().delete("/delete").then().statusCode(204);
    }

    @Test
    void updatePersonShouldUpdatePerson() {
        Person person = createAndStoreSimplePerson(1L, "Max", "Mustermann");
        person.setName("Doe");
        given().contentType(ContentType.JSON).body(person).when().post("/update").then().statusCode(200);
    }

    @Test
    void getPersonsShouldReturnAllPersons() {
        createAndStoreSimplePerson(1L, "Max", "Mustermann");
        createAndStoreSimplePerson(2L, "Jane", "Doe");
        Person[] persons = given().contentType(ContentType.JSON).get("/all").as(Person[].class);
        assertArrayEquals(persons, spm.getRoot().getPersons().toArray());
    }

    @Test
    void findPersonByNameShouldReturnPerson() {
        createAndStoreSimplePerson(1L, "Max", "Mustermann");
        createAndStoreSimplePerson(2L, "Jane", "Doe");
        Person[] persons = given().contentType(ContentType.JSON).get("/findbyname?name=Doe")
                .as(Person[].class);
        assertEquals(1, persons.length);
        assertEquals("Doe", persons[0].getName());
    }

}