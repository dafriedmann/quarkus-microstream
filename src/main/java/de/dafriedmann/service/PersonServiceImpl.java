package de.dafriedmann.service;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.dafriedmann.data.Person;
import de.dafriedmann.data.SimplePersistenceManager;
import io.quarkus.arc.Lock;

@Lock
@ApplicationScoped
public class PersonServiceImpl implements PersonService {

	@Inject
	private SimplePersistenceManager spm;

	@Override
	public void addPerson(Person person) {
		spm.getRoot().addPerson(person);
		// Store persons list since a new person was added
		spm.store(spm.getRoot().getPersons());
	}

	@Override
	public Collection<Person> getPersons() {
		return this.spm.getRoot().getPersons();
	}

	@Override
	public Collection<Person> findPersonByName(String name) {
		return spm.getRoot().getPersons().stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
	}

}
