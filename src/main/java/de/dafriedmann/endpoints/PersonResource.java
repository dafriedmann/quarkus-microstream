package de.dafriedmann.endpoints;

import de.dafriedmann.data.Person;
import de.dafriedmann.service.PersonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;

@Path("/persons")
public class PersonResource {

    @Inject
    PersonService personService;

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Person> getAllPersons() {
        return this.personService.getPersons();
    }

    @GET
    @Path("/findby")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Person> findPersonsByName(@QueryParam("name") String name) {
        return personService.findPersonByName(name);
    }

    @GET
    @Path("/findby")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Person> findPersonsLivingInCity(@QueryParam("city") String city) {
        return personService.findPersonLivingInCity(city);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person addPerson(Person person) {
        personService.addPerson(person);
        return person;
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person person) {
        if (personService.updatePerson(person).isPresent()) {
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }

    @POST
    @Path("/add/batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> addPerson(List<Person> persons) {
        personService.addPersons(persons);
        return persons;
    }

    @GET
    @Path("/import/demodata")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> importDemo() {
        return personService.importDemoPersons();
    }

    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void removePerson(Person person) {
        this.personService.deletePerson(person);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response removePersonById(@PathParam("id") long id) {
        personService.deletePersonById(id);
        return Response.status(200).build();
    }

}