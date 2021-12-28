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
	 * Add and persist new persons
	 * 
	 * @param person
	 */
	public void addPersons(Collection<Person> persons);

	/**
	 * Remove person from storage
	 */
	public void removePerson(Person person);

	/**
	 * Remove a person from storage by a given id
	 * @param id
	 */
	public void removePersonById(long id);

	/**
	 * Update a person
	 * @param person
	 */
	public void updatePerson(Person person);

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
