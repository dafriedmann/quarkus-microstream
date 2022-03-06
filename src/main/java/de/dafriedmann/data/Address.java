package de.dafriedmann.data;

import java.util.Objects;

public class Address {

    private String street;
    private String houseNumber;
    private String city;
    private String zipcode;
    private String phone;

    public Address(String street, String houseNumber, String city, String phone) {
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber)
                && Objects.equals(city, address.city) && Objects.equals(zipcode, address.zipcode)
                && Objects.equals(phone, address.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, city, zipcode, phone);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
