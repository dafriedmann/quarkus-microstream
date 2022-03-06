package de.dafriedmann.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Preconditions;
import de.dafriedmann.analytics.AnalyticsRecorder;
import de.dafriedmann.data.Person;
import de.dafriedmann.data.PersonRepository;
import io.quarkus.arc.Lock;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Lock
@ApplicationScoped
public class PersonServiceImpl implements PersonService {

    @Inject
    PersonRepository personRepository;

    @Inject
    AnalyticsRecorder analyticsRecorder;

    @Override
    public void addPerson(Person person) {
        personRepository.storePerson(person);
        analyticsRecorder.recordPerson(person.getId(), person.getAddress());
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
    public boolean deletePerson(Person person) {
        return personRepository.removePerson(person);
    }

    @Override
    public boolean deletePersonById(long id) {
        boolean isDeleted = personRepository.removePersonById(id);
        if (isDeleted) {
            analyticsRecorder.deletePerson(id);
        }
        return isDeleted;
    }

    @Override
    public Optional<Person> updatePerson(Person person) {
        Preconditions.checkArgument(person != null, "Person to be updated should not be null");
        return personRepository.updatePerson(person);
    }

    @Override
    public List<Person> findPersonByName(String name) {
        Preconditions.checkArgument(name != null, "Name should not be null");
        return personRepository.findPersonByName(name);
    }

    @Override
    public List<Person> findPersonLivingInCity(String city) {
        Preconditions.checkArgument(city != null, "City should not be null");
        return personRepository.findPersonsLivingInCity(city);
    }

    @Override
    public Optional<Person> getPersonById(long id) {
        return personRepository.getPersonById(id);
    }

    @Override
    public List<Person> importDemoPersons() {
        try {
            // simple import of demo data
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            List<Person> persons = mapper.readValue(this.getClass().getClassLoader().getResourceAsStream("demodata.json").readAllBytes(), new TypeReference<List<Person>>() {
            });

            addPersons(persons);
            return persons;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // should not happen in this demo
        throw new RuntimeException("Import of demo data failed.");
    }
}
