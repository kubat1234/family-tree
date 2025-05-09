package tcs.familytree.core.toanihilate;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonFactory;
import tcs.familytree.core.place.Place;

public class PersonFactoryDummyPerson implements PersonFactory {
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
    public PersonFactory setId(int id) {
        this.id = id;
        ifId = true;
        return this;
    }

    @Override
    public PersonFactory setFirstName(String name) {
        this.name.set(name);
        return this;
    }

    @Override
    public PersonFactory addAdditionalName(String name) {
        return this;
    }

    @Override
    public PersonFactory setFamilyName(String name) {
        return this;
    }

    @Override
    public PersonFactory addSurname(String surName) {
        return this;
    }

    @Override
    public PersonFactory setMother(Person mother) {
        return this;
    }

    @Override
    public PersonFactory setFather(Person mother) {
        return this;
    }

    @Override
    public PersonFactory setBirthDate(Date date) {
        return this;
    }

    @Override
    public PersonFactory setDeathDate(Date date) {
        return this;
    }

    @Override
    public PersonFactory setBirthPlace(Place date) {
        return this;
    }

    @Override
    public PersonFactory setDeathPlace(Place place) {
        return this;
    }

    @Override
    public PersonFactory setAlive(boolean place) {
        return this;
    }

    @Override
    public PersonFactory setGender(Boolean place) {
        return this;
    }


}
