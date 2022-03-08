package de.dafriedmann.endpoints;

import de.dafriedmann.data.Address;
import de.dafriedmann.data.Person;
import de.dafriedmann.testsupport.AbstractMicrostreamTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
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
    }

    @Test
    void deletePersonShouldRemovePerson() {
        Person personToBeDeleted = createAndStoreSimplePerson(1L, "Max", "Mustermann");
        given().contentType(ContentType.JSON).body(personToBeDeleted).when().delete("/delete").then().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void updatePersonShouldUpdatePerson() {
        Person person = createAndStoreSimplePerson(1L, "Max", "Mustermann");
        person.setName("Doe");
        given().contentType(ContentType.JSON).body(person).when().post("/update").then().statusCode(HttpStatus.SC_OK);
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
    void importDemoDataShouldImportData() {
        given().get("/import/demodata");
        assertTrue(spm.getRoot().getPersons().size() > 0);
    }

}