package de.dafriedmann.data;

import java.util.Objects;

public class Address {

	private String street;
	private String houseNumber;
	private String city;
	private String phone;
	
	public Address(String street, String houseNumber, String city, String phone) {
		super();
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.phone = phone;
	}
	
	public Address() {
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, houseNumber, phone, street);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(city, other.city) && Objects.equals(houseNumber, other.houseNumber)
				&& Objects.equals(phone, other.phone) && Objects.equals(street, other.street);
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", houseNumber=" + houseNumber + ", city=" + city + ", phone=" + phone
				+ "]";
	}
	
}
