package de.dafriemdann.endpoints;

import java.time.LocalDate;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.dafriedmann.data.Address;
import de.dafriedmann.data.Person;
import de.dafriedmann.data.SimplePersistenceManager;

@Path("/persons")
public class PersonResource {

	private SimplePersistenceManager spm;

	@Inject
	public PersonResource(SimplePersistenceManager spm) {
		this.spm = spm;
	}
	
	/**
	 * TODO: Remove dummy crud operation once more operations are implemented.
	 * @return
	 */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/crud")
    public String test() {
    	// Some data
    	Person p1 = new Person("Max", "Mustermann", LocalDate.now(), new Address());
    	
    	// Hook into object graph
    	spm.getRoot().addPerson(p1);
    	System.out.println(spm.getRoot());

    	// Add new person 
    	spm.getRoot().addPerson(new Person("Jan", "Mustermann", LocalDate.now(), new Address()));
    	
    	// Update name
    	p1 = spm.getRoot().getPersonAt(0);
    	p1.setName("Mutermann2");
    	
    	spm.store(p1);
    	spm.getStorageManager().storeRoot();

    	return ">> "+ spm.getRoot();        
    }
    
    @GET
    public Collection<Person> getAllPersons(){
    	return spm.getRoot().getPersons();
    }
    
}