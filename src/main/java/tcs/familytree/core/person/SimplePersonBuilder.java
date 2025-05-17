package tcs.familytree.core.person;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.date.SimpleConnectionDate;
import tcs.familytree.core.place.Place;
import tcs.familytree.services.database.DatabaseConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimplePersonBuilder implements PersonBuilder {
    DatabaseConnection connection;
    int id;
    String firstName;
    List<String> names;
    String familyName;
    List<String> surnames;
    Person mother;
    Person father;
    Date birthDate;
    Date deathDate;
    Place birthPlace;
    Place deathPlace;
    boolean alive;
    Gender gender;

    public SimplePersonBuilder(DatabaseConnection connection){
        this.connection = connection;
    }
    
    @Override
    public PersonBuilder setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public PersonBuilder setName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public PersonBuilder setNames(String... names) {
        this.names = Arrays.asList(names);
        return this;
    }

    @Override
    public PersonBuilder setFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    @Override
    public PersonBuilder setSurnames(String... surnames) {
        this.surnames = Arrays.asList(surnames);
        return this;
    }

    @Override
    public PersonBuilder addSurname(String surname) {
        if (this.surnames == null) {
            this.surnames = new ArrayList<>();
        }
        this.surnames.add(surname);
        return this;
    }

    @Override
    public PersonBuilder setMother(Person mother) {
        this.mother = mother;
        return this;
    }

    @Override
    public PersonBuilder setMother(int motherId) {
        return setMother(new SimpleConnectionPerson(motherId,connection));
    }

    @Override
    public PersonBuilder setFather(Person father) {
        this.father = father;
        return this;
    }

    @Override
    public PersonBuilder setFather(int fatherId) {
        return setFather(new SimpleConnectionPerson(fatherId,connection));
    }

    @Override
    public PersonBuilder setAlive(boolean alive) {
        this.alive = alive;
        return this;
    }

    @Override
    public PersonBuilder setPlaceOfBirth(Place placeOfBirth) {
        this.birthPlace = placeOfBirth;
        return this;
    }

    @Override
    public PersonBuilder setPlaceOfBirth(int placeOfBirthId) {
        // TODO
        return this;
    }

    @Override
    public PersonBuilder setPlaceOfDeath(Place placeOfDeath) {
        this.deathPlace = placeOfDeath;
        return this;
    }

    @Override
    public PersonBuilder setPlaceOfDeath(int placeOfDeathId) {
        //TODO
        return this;
    }

    @Override
    public PersonBuilder setDateOfBirth(Date dateOfBirth) {
        this.birthDate = dateOfBirth;
        return this;
    }

    @Override
    public PersonBuilder setDateOfBirth(int dateOfBirthId) {
        return setDateOfBirth(new SimpleConnectionDate(dateOfBirthId,connection));
    }

    @Override
    public PersonBuilder setDateOfDeath(Date dateOfDeath) {
        this.deathDate = dateOfDeath;
        return this;
    }

    @Override
    public PersonBuilder setDateOfDeath(int dateOfDeathId) {
        return setDateOfDeath(new SimpleConnectionDate(dateOfDeathId,connection));
    }

    @Override
    public PersonBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public Person build() {
        if(gender == null)throw new IllegalStateException("Gender must be set");
        return new SimplePerson(id, firstName, names, familyName, surnames, 
                              mother, father, birthDate, deathDate, 
                              birthPlace, deathPlace, alive, gender);
    }
}