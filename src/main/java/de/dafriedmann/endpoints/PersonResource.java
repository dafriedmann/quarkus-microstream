package de.dafriedmann.endpoints;

import java.util.Collection;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.dafriedmann.data.Person;
import de.dafriedmann.service.PersonService;

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
		try{
			this.personService.updatePerson(person);
		}
		catch (NoSuchElementException e){
			return Response.status(404).entity(person).build();
		}
		return Response.status(200).entity(person).build();
	}

	@POST
	@Path("/add/batch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Person> addPerson(Collection<Person> persons) {
		this.personService.addPersons(persons);
		return persons;
	}

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void removePerson(Person person) {
		this.personService.removePerson(person);
	}

}