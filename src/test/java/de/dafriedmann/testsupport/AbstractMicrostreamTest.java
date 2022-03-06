package de.dafriedmann.testsupport;

import de.dafriedmann.data.Address;
import de.dafriedmann.data.DataRoot;
import de.dafriedmann.data.Person;
import de.dafriedmann.data.SimplePersistenceManager;
import org.junit.jupiter.api.AfterEach;

import javax.inject.Inject;
import java.time.LocalDate;

public abstract class AbstractMicrostreamTest {

    @Inject
    protected SimplePersistenceManager spm;

    @AfterEach
    void resetMicroStreamDB() {
        this.spm.setRoot(new DataRoot());
        this.spm.storeRoot();
    }

    protected Person createAndStoreSimplePerson(long id, String prename, String name) {
        Person person = createSimplePerson(prename, name);
        person.setId(id);
        spm.getRoot().getPersons().add(person);
        spm.store(spm.getRoot().getPersons());
        return person;
    }

    protected Person createAndStoreSimplePersonWithAddress(long id, String prename, String name, Address address) {
        Person person = new Person(prename, name, LocalDate.now());
        person.setAddress(address);
        person.setId(id);
        spm.getRoot().getPersons().add(person);
        spm.store(spm.getRoot().getPersons());
        return person;
    }

    protected Person createSimplePerson(String prename, String name) {
        Person person = new Person(prename, name, LocalDate.now());
        Address a = new Address("Teststrasse", "23a", "Berlin", "0123456789");
        person.setAddress(new Address());
        return person;
    }

}
