package de.dafriedmann.data;

import io.quarkus.arc.Lock;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Lock
@ApplicationScoped
public class PersonRepository {

    @Inject
    SimplePersistenceManager spm;

    public void storePerson(Person person) {
        person.setId(getId());
        spm.getRoot().getPersons().add(person);
        spm.store(spm.getRoot().getPersons());
    }

    public void storePersons(List<Person> persons) {
        long startId = getId();
        for (Person p : persons) {
            p.setId(startId++);
        }
        spm.getRoot().getPersons().addAll(persons);
        spm.store(spm.getRoot().getPersons());
    }

    public boolean removePerson(Person person) {
        boolean isRemoved = spm.getRoot().getPersons().remove(person);
        spm.store(spm.getRoot().getPersons());
        return isRemoved;
    }

    public boolean removePersonById(long id) {
        boolean isRemoved = spm.getRoot().getPersons().removeIf(p -> p.getId() == id);
        spm.store(spm.getRoot().getPersons());
        return isRemoved;
    }

    public List<Person> getPersons() {
        return spm.getRoot().getPersons();
    }

    public Optional<Person> updatePerson(Person person) {
        Optional<Person> foundPersonOptional = spm.getRoot().getPersons().stream().filter(p -> p.getId().equals(person.getId())).findAny();
        Person foundPerson = foundPersonOptional.get();

        // copy values to update ref.
        foundPerson.setPrename(person.getPrename());
        foundPerson.setName(person.getName());
        foundPerson.setAddress(person.getAddress());
        foundPerson.setDateOfBirth(person.getDateOfBirth());

        // store back ref
        spm.store(foundPerson);

        return foundPersonOptional;
    }

    public Optional<Person> getPersonById(long id) {
        return spm.getRoot().getPersons().stream()
                .filter(p -> p.getId() == id)
                .findAny();
    }

    public List<Person> findPersonByName(String name) {
        return spm.getRoot().getPersons().stream()
                .filter(p -> p.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Person> findPersonsLivingInCity(String city) {
        return spm.getRoot().getPersons().stream()
                .filter(p -> p.getAddress() != null && p.getAddress().getCity() != null)
                .filter(p -> p.getAddress().getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    private synchronized long getId() {
        long lastId = spm.getRoot().getPersons().stream()
                .map(Person::getId)
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L);
        return ++lastId;
    }

}
