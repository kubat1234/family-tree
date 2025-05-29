package tcs.familytree.core.person;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.place.Place;
import tcs.familytree.services.database.DatabaseConnection;

public class SimpleConnectionPersonBuilder implements PersonBuilder{
    DatabaseConnection connection;
    PersonBuilder builder;

    public SimpleConnectionPersonBuilder(DatabaseConnection connection){
        if(connection == null)throw new IllegalArgumentException("Connection must not be null");
        this.connection = connection;
        builder = new SimplePersonBuilder(connection);
    }

    public SimpleConnectionPersonBuilder(DatabaseConnection connection, Person person){
        if(connection == null)throw new IllegalArgumentException("Connection must not be null");
        if(person == null)throw new IllegalArgumentException("Person must not be null");
        this.connection = connection;
        builder = new SimplePersonBuilder(connection, person);
    }

    public SimpleConnectionPersonBuilder(DatabaseConnection connection, PersonBuilder builder){
        if(connection == null)throw new IllegalArgumentException("Connection must not be null");
        if(builder == null)throw new IllegalArgumentException("Builder must not be null");
        this.connection = connection;
        this.builder = builder;
    }

    @Override
    public Person build() {
        return new SimpleConnectionPerson(builder.build(),connection);
    }

    @Override
    public PersonBuilder setPerson(Person person) {
        builder.setPerson(person);
        return this;
    }

    @Override
    public PersonBuilder setId(int id) {
        builder.setId(id);
        return this;
    }

    @Override
    public PersonBuilder setName(String name) {
        builder.setName(name);
        return this;
    }

    @Override
    public PersonBuilder setNames(String... names) {
        builder.setNames(names);
        return this;
    }

    @Override
    public PersonBuilder setFamilyName(String familyName) {
        builder.setFamilyName(familyName);
        return this;
    }

    @Override
    public PersonBuilder setSurnames(String... surnames) {
        builder.setSurnames(surnames);
        return this;
    }

    @Override
    public PersonBuilder addSurname(String surname) {
        builder.addSurname(surname);
        return this;
    }

    @Override
    public PersonBuilder setMother(Person mother) {
        builder.setMother(mother);
        return this;
    }

    @Override
    public PersonBuilder setMother(Integer motherId) {
        builder.setMother(motherId);
        return this;
    }

    @Override
    public PersonBuilder setFather(Person father) {
        builder.setFather(father);
        return this;
    }

    @Override
    public PersonBuilder setFather(Integer fatherId) {
        builder.setFather(fatherId);
        return this;
    }

    @Override
    public PersonBuilder setAlive(boolean alive) {
        builder.setAlive(alive);
        return this;
    }

    @Override
    public PersonBuilder setPlaceOfBirth(Place placeOfBirth) {
        builder.setPlaceOfBirth(placeOfBirth);
        return this;
    }

    @Override
    public PersonBuilder setPlaceOfBirth(Integer placeOfBirthId) {
        return null;
    }

    @Override
    public PersonBuilder setPlaceOfDeath(Place placeOfDeath) {
        builder.setPlaceOfDeath(placeOfDeath);
        return this;
    }

    @Override
    public PersonBuilder setPlaceOfDeath(Integer placeOfDeathId) {
        builder.setPlaceOfDeath(placeOfDeathId);
        return this;
    }

    @Override
    public PersonBuilder setDateOfBirth(Date dateOfBirth) {
        builder.setDateOfBirth(dateOfBirth);
        return this;
    }

    @Override
    public PersonBuilder setDateOfBirth(Integer dateOfBirthId) {
        builder.setDateOfBirth(dateOfBirthId);
        return this;
    }

    @Override
    public PersonBuilder setDateOfDeath(Date dateOfDeath) {
        builder.setDateOfDeath(dateOfDeath);
        return this;
    }

    @Override
    public PersonBuilder setDateOfDeath(Integer dateOfDeathId) {
        builder.setDateOfDeath(dateOfDeathId);
        return this;
    }

    @Override
    public PersonBuilder setGender(Gender gender) {
        builder.setGender(gender);
        return this;
    }
}
