package de.dafriedmann.service;

import de.dafriedmann.data.Person;
import de.dafriedmann.testsupport.AbstractMicrostreamTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class PersonServiceIntegrationTest extends AbstractMicrostreamTest {

    @Inject
    PersonService service;

    @Test
    void addPersonShouldAddPersonToStorage() {
        // given
        Person person = new Person("Max", "Mustermann", LocalDate.now());
        // when
        service.addPerson(person);
        // then
        assertEquals(spm.getRoot().getPersons().size(), 1);
        Person storedPerson = spm.getRoot().getPersonAt(0);
        assertEquals(person, storedPerson);
        assertEquals(1L, storedPerson.getPersonId());
    }

    @Test
    void addPersonsShouldAddPersonsToStorage() {
        // given
        Person firstPerson = createSimplePerson("Max", "Mustermann");
        Person anotherPerson = createSimplePerson("Jane", "Doe");
        // when
        service.addPersons(List.of(firstPerson, anotherPerson));
        // then
        assertTrue(spm.getRoot().getPersons().containsAll(List.of(firstPerson, anotherPerson)));
        assertEquals(1L, spm.getRoot().getPersonAt(0).getPersonId());
        assertEquals(2L, spm.getRoot().getPersonAt(1).getPersonId());
    }

    @Test
    void removePersonShouldRemovePersonFromStorage() {
        // given
        Person firstPerson = createSimplePerson("Max", "Mustermann");
        storePersons(firstPerson);
        // when
        service.removePerson(firstPerson);
        // then
        assertTrue(spm.getRoot().getPersons().isEmpty());
    }

    @Test
    void removePersonByIdShouldRemovePersonByIdFromStorage() {
        // given
        Person firstPerson = createSimplePerson("Max", "Mustermann");
        Person anotherPerson = createSimplePerson("Max", "Mustermann");
        firstPerson.setId(1L);
        anotherPerson.setId(2L);
        storePersons(firstPerson, anotherPerson);
        // when
        service.removePersonById(1L);
        // then
        assertEquals(1, spm.getRoot().getPersons().size());
        assertEquals(anotherPerson, spm.getRoot().getPersonAt(0));
    }

    @Test
    void updatePersonShouldUpdatePerson() {
        // given
        Person firstPerson = createSimplePerson("Max", "Mustermann");
        storePersons(firstPerson);
        Person personToBeUpdated = spm.getRoot().getPersonAt(0);
        // when
        personToBeUpdated.setName("Doe");
        service.updatePerson(firstPerson);
        // then
        assertEquals("Doe", spm.getRoot().getPersonAt(0).getName());
    }

    @Test
    void getPersonsShouldReturnAllPersonsFromStorage() {
        // given
        Person firstPerson = createSimplePerson("Max", "Mustermann");
        Person anotherPerson = createSimplePerson("Jane", "Doe");
        storePersons(firstPerson, anotherPerson);
        // when
        Collection<Person> storedPersons = service.getPersons();
        // then
        assertTrue(storedPersons.containsAll(List.of(firstPerson, anotherPerson)));
    }

    @Test
    void getPersonByIdShouldReturnPersonByIdFromStorage() {
        // given
        Person firstPerson = createSimplePerson("Max", "Mustermann");
        Person anotherPerson = createSimplePerson("Jane", "Doe");
        firstPerson.setId(1L);
        anotherPerson.setId(2L);
        storePersons(firstPerson, anotherPerson);
        // when
        Optional<Person> foundPerson = service.getPersonById(1L);
        // then
        assertTrue(foundPerson.isPresent());
        assertEquals(firstPerson, foundPerson.get());
    }

    @Test
    void findPersonByNameShouldReturnPersonWithMatchingNameFromStorage() {
        // given
        Person firstPerson = createSimplePerson("Max", "Mustermann");
        Person anotherPerson = createSimplePerson("Jane", "Doe");
        firstPerson.setId(1L);
        anotherPerson.setId(2L);
        storePersons(firstPerson, anotherPerson);
        // when
        Collection<Person> persons = service.findPersonByName("Mustermann");
        // then
        assertEquals(1, persons.size());
        assertEquals(firstPerson, persons.stream().findFirst().get());
    }

}