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
    public Response getAllPersons() {
        Collection<Person> persons = this.personService.getPersons();
        return Response.ok(persons).build();
    }

    @GET
    @Path("/findbyname/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPersonsByName(@PathParam("name") String name) {
        List<Person> persons = personService.findPersonByName(name);
        if (persons.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(persons).build();
    }

    @GET
    @Path("/findbycity/{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPersonsByArg(@PathParam("city") String city) {
        List<Person> persons = personService.findPersonLivingInCity(city);
        if (persons.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(persons).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {
        personService.addPerson(person);
        return Response.ok(person).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(Person person) {
        if (personService.updatePerson(person).isPresent()) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/add/batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(List<Person> persons) {
        personService.addPersons(persons);
        return Response.ok().build();
    }

    @GET
    @Path("/import/demodata")
    @Produces(MediaType.APPLICATION_JSON)
    public Response importDemo() {
        List<Person> persons = personService.importDemoPersons();
        if (persons.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(personService.importDemoPersons()).build();
    }

    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void removePerson(Person person) {
        // todo return status
        this.personService.deletePerson(person);
    }

    @DELETE
    @Path("/delete/{id}")
    public Response removePersonById(@PathParam("id") long id) {
        boolean isRemoved = personService.deletePersonById(id);
        if (isRemoved) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}