package de.dafriedmann.endpoints;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.dafriedmann.data.Address;
import de.dafriedmann.data.DataRoot;
import de.dafriedmann.data.Person;
import de.dafriedmann.data.SimplePersistenceManager;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(PersonResource.class)
public class PersonResourceIntegrationTest {

	@Inject
	private SimplePersistenceManager spm;

	@BeforeEach
	public void resetMicrostreamDB() {
		this.spm.setRoot(new DataRoot());
		this.spm.storeRoot();
	}

	@Test
	public void testAddPerson() throws Exception {
		Person me = new Person();
		me.setName("Test");
		me.setPrename("Test");
		me.setDateOfBirth(LocalDate.now());

		given().contentType(ContentType.JSON).body(me).when().post("/add").then().statusCode(200);
		assertEquals(1, spm.getRoot().getPersons().size());
		assertEquals(me, spm.getRoot().getPersonAt(0));
	}

	@Test
	public void testGetPersons() {
		createDummyPersons(3).stream().forEach(p -> {
			spm.getRoot().addPerson(p);
		});
		spm.store(spm.getRoot().getPersons());

		Person[] givenPersons = given().contentType(ContentType.JSON).get("/all").as(Person[].class);
		assertArrayEquals(givenPersons, spm.getRoot().getPersons().toArray());
	}

	private List<Person> createDummyPersons(int count) {
		List<Person> persons = new ArrayList<>();
		Address address = new Address("Teststrasse", "10", "Teststadt", "0123456789");
		for (int i = 0; i < count; i++) {
			persons.add(new Person("Max", "Mustermann", LocalDate.now(), address));
		}
		return persons;
	}

}