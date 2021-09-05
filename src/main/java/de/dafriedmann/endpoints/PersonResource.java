package de.dafriedmann.endpoints;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.dafriedmann.data.Person;
import de.dafriedmann.data.SimplePersistenceManager;

@Path("/persons")
public class PersonResource {

	private SimplePersistenceManager spm;

	@Inject
	public PersonResource(SimplePersistenceManager spm) {
		this.spm = spm;
	}
	
    @GET
    @Path("/all")
    public Collection<Person> getAllPersons(){
    	return spm.getRoot().getPersons();
    }
    
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person addPerson(Person person) {
    	spm.getRoot().addPerson(person);
    	// Store persons list since a new person was added
    	spm.store(spm.getRoot().getPersons());
    	return person;
    }
    
}