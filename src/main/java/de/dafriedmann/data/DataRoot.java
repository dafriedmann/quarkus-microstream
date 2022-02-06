package de.dafriedmann.data;

import java.util.ArrayList;
import java.util.List;

public class DataRoot {

    private final List<Person> persons = new ArrayList<>();

    public DataRoot() {
    }

    public void addPerson(Person p) {
        this.persons.add(p);
    }

    public List<Person> getPersons() {
        // must return the reference
        // in order to make it work with MicroStream
        return persons;
    }

    public Person getPersonAt(int index) {
        return persons.get(index);
    }

}