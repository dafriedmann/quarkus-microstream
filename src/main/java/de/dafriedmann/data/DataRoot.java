package de.dafriedmann.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataRoot
{

	private List<Person> persons = new ArrayList<>();
	
	public DataRoot() {
	}

	public void addPerson(Person p) {
		this.persons.add(p);
	}
	
	public Collection<Person> getPersons() {
		return persons;
	}
	
	public Person getPersonAt(int index) {
		return persons.get(index);
	}
	
}