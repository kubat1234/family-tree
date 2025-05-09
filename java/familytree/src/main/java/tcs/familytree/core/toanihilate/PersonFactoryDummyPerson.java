package tcs.familytree.core.toanihilate;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Gender;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonBuilder;
import tcs.familytree.core.place.Place;

public class PersonFactoryDummyPerson implements PersonBuilder {
    Integer id;
    boolean ifId = false;
    Data name = new DataString();

    @Override
    public Person build() {
        if(!ifId){
            throw new RuntimeException("Brak id!!!");
        }
        return new DummyPerson(id, (String) name.get());
    }

    @Override
    public PersonBuilder setId(int id) {
        this.id = id;
        ifId = true;
        return this;
    }

    @Override
    public PersonBuilder setName(String name) {
        this.name.set(name);
        return this;
    }

    @Override
    public PersonBuilder setNames(String... names) {
        return null;
    }

    @Override
    public PersonBuilder setFamilyName(String familyName) {
        return null;
    }

    @Override
    public PersonBuilder setSurnames(String... surnames) {
        return null;
    }

    @Override
    public PersonBuilder addSurname(String surname) {
        return null;
    }

    @Override
    public PersonBuilder setMother(Person mother) {
        return null;
    }

    @Override
    public PersonBuilder setMother(int motherId) {
        return null;
    }

    @Override
    public PersonBuilder setFather(Person father) {
        return null;
    }

    @Override
    public PersonBuilder setFather(int fatherId) {
        return null;
    }

    @Override
    public PersonBuilder setAlive(boolean alive) {
        return null;
    }

    @Override
    public PersonBuilder setPlaceOfBirth(Place placeOfBirth) {
        return null;
    }

    @Override
    public PersonBuilder setPlaceOfBirth(int placeOfBirthId) {
        return null;
    }

    @Override
    public PersonBuilder setPlaceOfDeath(Place placeOfDeath) {
        return null;
    }

    @Override
    public PersonBuilder setPlaceOfDeath(int placeOfDeathId) {
        return null;
    }

    @Override
    public PersonBuilder setDateOfBirth(Date dateOfBirth) {
        return null;
    }

    @Override
    public PersonBuilder setDateOfBirth(int dateOfBirthId) {
        return null;
    }

    @Override
    public PersonBuilder setDateOfDeath(Date dateOfDeath) {
        return null;
    }

    @Override
    public PersonBuilder setDateOfDeath(int dateOfDeathId) {
        return null;
    }

    @Override
    public PersonBuilder setGender(Gender gender) {
        return null;
    }


}
