package de.dafriedmann.endpoints;

import de.dafriedmann.data.Address;
import de.dafriedmann.data.Person;
import de.dafriedmann.testsupport.AbstractMicrostreamTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(PersonResource.class)
class PersonResourceIntegrationTest extends AbstractMicrostreamTest {

    @Test
    void addPersonShouldResultInAddedPerson() {
        Person person = createSimplePerson("Max", "Mustermann");
        given().contentType(ContentType.JSON).body(person).post("/add").then().statusCode(200);
    }

    @Test
    void addPersonsShouldResultInAddedPersons() {
        List<Person> personsToBeStored = List.of(
                createSimplePerson("Max", "Mustermann"),
                createSimplePerson("John", "Doe"),
                createSimplePerson("Jane", "Doe")
        );

        given().contentType(ContentType.JSON).body(personsToBeStored).post("/add/batch").then().statusCode(200);

        assertEquals(3, spm.getRoot().getPersons().size());
    }

    @Test
    void deletePersonShouldDeletePerson() {
        Person personToBeDeleted = createAndStoreSimplePerson(1L, "Max", "Mustermann");
        given().contentType(ContentType.JSON).body(personToBeDeleted).delete("/delete").then().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void deletePersonByIdShouldDeletePersonWithId() {
        createAndStoreSimplePerson(1L, "Max", "Mustermann");
        createAndStoreSimplePerson(2L, "Jane", "Doe");

        given().contentType(ContentType.JSON).delete("/delete/2").then().assertThat().statusCode(HttpStatus.SC_OK);

        assertEquals(1, spm.getRoot().getPersons().size());
        assertEquals(1L, spm.getRoot().getPersons().get(0).getId());
    }

    @Test
    void updatePersonShouldUpdatePerson() {
        Person person = createAndStoreSimplePerson(1L, "Max", "Mustermann");
        person.setName("Doe");
        given().contentType(ContentType.JSON).body(person).post("/update").then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getAllPersonsShouldReturnAllPersons() {
        createAndStoreSimplePerson(1L, "Max", "Mustermann");
        createAndStoreSimplePerson(2L, "Jane", "Doe");
        Person[] persons = given().contentType(ContentType.JSON).get("/all").then()
                .assertThat().statusCode(HttpStatus.SC_OK).extract().as(Person[].class);
        assertArrayEquals(persons, spm.getRoot().getPersons().toArray());
    }

    @Test
    void findPersonByNameShouldReturnPerson() {
        createAndStoreSimplePerson(1L, "Max", "Mustermann");
        createAndStoreSimplePerson(2L, "Jane", "Doe");
        Person[] persons = given().contentType(ContentType.JSON).get("/findbyname/Doe").then()
                .assertThat().statusCode(HttpStatus.SC_OK).extract().as(Person[].class);

        assertEquals(1, persons.length);
        assertEquals("Doe", persons[0].getName());
    }

    @Test
    void findPersonByCityShouldReturnPersons() {
        Address munich = new Address();
        munich.setCity("Munich");

        createAndStoreSimplePersonWithAddress(1L, "Max", "Mustermann", munich);
        createAndStoreSimplePersonWithAddress(2L, "Jane", "Doe", munich);
        createAndStoreSimplePersonWithAddress(3L, "John", "Doe", new Address());

        Person[] persons = given().contentType(ContentType.JSON).get("/findbycity/Munich").then()
                .assertThat().statusCode(HttpStatus.SC_OK).extract().as(Person[].class);
        assertEquals(2, persons.length);
    }

    @Test
    void getPersonByIdShouldReturnPersonWithId() {
        createAndStoreSimplePerson(1L, "Max", "Mustermann");
        createAndStoreSimplePerson(2L, "Jane", "Doe");

        Person person = given().contentType(ContentType.JSON).get("/get/2").then()
                .assertThat().statusCode(HttpStatus.SC_OK).extract().as(Person.class);

        assertEquals(2L, person.getId());
    }

    @Test
    void importDemoDataShouldImportData() {
        given().get("/import/demodata");
        assertTrue(spm.getRoot().getPersons().size() > 0);
    }

}