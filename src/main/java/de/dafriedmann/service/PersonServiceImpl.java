package de.dafriedmann.service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
	SimplePersistenceManager spm;

	@Override
	public void addPerson(Person person) {
		person.setId(getNewId());
		spm.getRoot().addPerson(person);
		// Store persons list since a new person was added
		spm.store(spm.getRoot().getPersons());
	}

	@Override
	public Collection<Person> getPersons() {
		return this.spm.getRoot().getPersons();
	}
	
	@Override
	public void addPersons(Collection<Person> persons) {
		Collection<Person> personsInStorage = this.spm.getRoot().getPersons();
		persons.stream().forEach(p -> {
			p.setId(getNewId());
			personsInStorage.add(p);
		});
		// Store persons list since new persons were added
		this.spm.store(personsInStorage);
	}

	@Override
	public void removePerson(Person person) {
		boolean isRemoved = spm.getRoot().getPersons().removeIf(p -> p.equals(person));
		if(!isRemoved){
			throw new NoSuchElementException("Person not found in storage");
		}
		// Store persons back to storage
		spm.store(spm.getRoot().getPersons());
	}

	@Override
	public void updatePerson(Person person) {
		Optional<Person> personToBeUpdated = this.spm.getRoot().getPersons().stream().filter(p -> p.getId() == person.getId()).findFirst();
		if(!personToBeUpdated.isPresent()){
			throw new NoSuchElementException("Person not found in storage");
		}

		Person refPerson = personToBeUpdated.get();
		refPerson.setName(person.getName());
		refPerson.setPrename(person.getPrename());
		refPerson.setAddress(person.getAddress());
		refPerson.setDateOfBirth(person.getDateOfBirth());

		this.spm.store(refPerson);
	}

	@Override
	public Collection<Person> findPersonByName(String name) {
		return spm.getRoot().getPersons().stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
	}

	private long getNewId(){
		long lastId = spm.getRoot().getPersons().stream().mapToLong(p->p.getId()).max().orElse(0l);
		return ++lastId;
	}

}
