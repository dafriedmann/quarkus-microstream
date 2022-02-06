package de.dafriedmann.service;

import de.dafriedmann.data.Person;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PersonService {

    /**
     * Add and persist a new person
     *
     * @param person
     */
    void addPerson(Person person);

    /**
     * Add and persist new persons
     *
     * @param persons
     */
    void addPersons(List<Person> persons);

    /**
     * Remove person from storage
     */
    void removePerson(Person person);

    /**
     * Remove a person from storage by a given id
     *
     * @param id
     */
    void removePersonById(long id);

    /**
     * Update a person
     *
     * @param person
     */
    Optional<Person> updatePerson(Person person);

    /**
     * Get all stored persons
     *
     * @return - a person collection
     */
    List<Person> getPersons();

    /**
     * Find person by name
     *
     * @param name
     * @return a person collection filtered by name
     */
    Collection<Person> findPersonByName(String name);

    /**
     * Find a person by id
     *
     * @param id
     * @return Optional of person
     */
    Optional<Person> getPersonById(long id);

}
