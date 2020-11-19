package de.sane.personservice.models;

import java.time.LocalDate;
import java.util.Date;

public class PersonBuilder {

    private final String firstName;

    private final String lastName;

    private Integer size;

    private Float weight;

    private Gender gender;

    private LocalDate birthday;

    public PersonBuilder(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonBuilder size(Integer size) {
        this.size = size;
        return this;
    }

    public PersonBuilder weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public PersonBuilder gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PersonBuilder birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public Person build() {
        final var person = new Person();
        person.setFirstName(this.firstName);
        person.setLastName(this.lastName);
        person.setGender(this.gender);
        person.setSize(this.size);
        person.setWeight(this.weight);
        person.setBirthday(this.birthday);
        return person;
    }

}
