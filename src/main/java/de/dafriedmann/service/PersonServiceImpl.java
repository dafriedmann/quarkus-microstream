package de.dafriedmann.service;

import com.google.common.base.Preconditions;
import de.dafriedmann.data.Person;
import de.dafriedmann.data.PersonRepository;
import io.quarkus.arc.Lock;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Lock
@ApplicationScoped
public class PersonServiceImpl implements PersonService {

    @Inject
    PersonRepository personRepository;

    @Override
    public void addPerson(Person person) {
        personRepository.storePerson(person);
    }

    @Override
    public List<Person> getPersons() {
        return personRepository.getPersons();
    }

    @Override
    public void addPersons(List<Person> persons) {
        personRepository.storePersons(persons);
    }

    @Override
    public void removePerson(Person person) {
        personRepository.removePerson(person);
    }

    @Override
    public void removePersonById(long id) {
        boolean isRemoved = personRepository.removePersonById(id);
        if (!isRemoved) {
            throw new NoSuchElementException("Person not found in storage");
        }
    }

    @Override
    public Optional<Person> updatePerson(Person person) {
        Preconditions.checkArgument(person != null, "Person to be updated should not be null");
        return personRepository.updatePerson(person);
    }

    @Override
    public Collection<Person> findPersonByName(String name) {
        Preconditions.checkArgument(name != null, "Name should not be null");
        return personRepository.findPersonByName(name);
    }

    @Override
    public Optional<Person> getPersonById(long id) {
        return personRepository.getPersonById(id);
    }

}
