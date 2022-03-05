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
    public Collection<Person> getAllPersons() {
        return this.personService.getPersons();
    }

    @GET
    @Path("/findbyname")
    public Collection<Person> findPersonsByName(@QueryParam("name") String name) {
        return this.personService.findPersonByName(name);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person addPerson(Person person) {
        this.personService.addPerson(person);
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
        this.personService.addPersons(persons);
        return persons;
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
        this.personService.deletePersonById(id);
        return Response.status(200).build();
    }

}