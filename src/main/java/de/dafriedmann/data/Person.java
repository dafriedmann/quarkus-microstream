package de.dafriedmann.data;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

    private Long id;
    private String prename;
    private String name;
    private LocalDate dateOfBirth;
    private Address address;

    public Person() {
    }

    public Person(String prename, String name, LocalDate dateOfBirth) {
        this. id = id;
        this.prename = prename;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(String prename, String name, LocalDate dateOfBirth, Address address) {
        this.id = id;
        this.prename = prename;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(prename, person.prename) && Objects.equals(name, person.name) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prename, name, dateOfBirth, address);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", prename='" + prename + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                '}';
    }
}
