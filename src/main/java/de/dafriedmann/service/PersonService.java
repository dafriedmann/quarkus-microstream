package de.dafriedmann.service;

import java.util.Collection;

import de.dafriedmann.data.Person;

public interface PersonService {

	/**
	 * Add and persist a new person
	 * 
	 * @param person
	 */
	public void addPerson(Person person);

	/**
	 * Remove person from storage
	 */
	public void removePerson(Person person);

	/**
	 * Get all stored persons
	 * 
	 * @return - a person collection
	 */
	public Collection<Person> getPersons();

	/**
	 * Find person by name
	 * 
	 * @param name
	 * @return a person collection filtered by name
	 */
	public Collection<Person> findPersonByName(String name);

}
