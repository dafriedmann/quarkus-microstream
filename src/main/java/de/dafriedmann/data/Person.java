package de.dafriedmann.data;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

	private String prename;
	private String name;
	private LocalDate dateOfBirth;
	private Address address;
	
	public Person() {
	}

	public Person(String prename, String name, LocalDate dateOfBirth, Address address) {
		super();
		this.prename = prename;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}

	public String getPrename() {
		return prename;
	}
	public void setPrename(String prename) {
		this.prename = prename;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, dateOfBirth, name, prename);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(address, other.address) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(name, other.name) && Objects.equals(prename, other.prename);
	}

	@Override
	public String toString() {
		return "Person [prename=" + prename + ", name=" + name + ", dateOfBirth=" + dateOfBirth + ", address=" + address
				+ "]";
	}
	
}
