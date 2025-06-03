package tcs.familytree.core.person;

import tcs.familytree.core.date.Date;
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
    List<Person> partners = new ArrayList<>();

    public SimplePersonBuilder(DatabaseConnection connection){
        this.connection = connection;
    }

    public SimplePersonBuilder(DatabaseConnection connection, Person person){
        this(connection);
        setPerson(person);
    }

    @Override
    public PersonBuilder setPerson(Person person) {
        if(person == null)throw new NullPointerException("Person cannot be null");
        this.id = person.getId();
        this.firstName = person.getName();
        this.names = person.getAllNames();
        this.familyName = person.getFamilySurname();
        this.surnames = person.getAllSurnames();
        this.mother = person.getMother();
        this.father = person.getFather();
        this.birthDate = person.getDateOfBirth();
        this.deathDate = person.getDateOfDeath();
        this.birthPlace = person.getPlaceOfBirth();
        this.deathPlace = person.getPlaceOfDeath();
        this.alive = person.isAlive();
        this.gender = person.getGender();
        return this;
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
        this.names = names == null ? new ArrayList<>() : Arrays.asList(names);
        return this;
    }

    @Override
    public PersonBuilder setFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    @Override
    public PersonBuilder setSurnames(String... surnames) {
        this.surnames = surnames == null ? new ArrayList<>() : Arrays.asList(surnames);
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
    public PersonBuilder setMother(Integer motherId) {
        return setMother(motherId == null ? null : new SimpleConnectionPerson(motherId,connection));
    }

    @Override
    public PersonBuilder setFather(Person father) {
        this.father = father;
        return this;
    }

    @Override
    public PersonBuilder setFather(Integer fatherId) {
        return setFather(fatherId == null ? null : new SimpleConnectionPerson(fatherId,connection));
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
    public PersonBuilder setPlaceOfBirth(Integer placeOfBirthId) {
        // TODO
        return this;
    }

    @Override
    public PersonBuilder setPlaceOfDeath(Place placeOfDeath) {
        this.deathPlace = placeOfDeath;
        return this;
    }

    @Override
    public PersonBuilder setPlaceOfDeath(Integer placeOfDeathId) {
        //TODO
        return this;
    }

    @Override
    public PersonBuilder setDateOfBirth(Date dateOfBirth) {
        this.birthDate = dateOfBirth;
        return this;
    }

    @Override
    public PersonBuilder setDateOfBirth(Integer dateOfBirthId) {
        return this;
//        return setDateOfBirth(dateOfBirthId == null ? null : new SimpleConnectionDate(dateOfBirthId,connection));
    }

    @Override
    public PersonBuilder setDateOfDeath(Date dateOfDeath) {
        this.deathDate = dateOfDeath;
        return this;
    }

    @Override
    public PersonBuilder setDateOfDeath(Integer dateOfDeathId) {
        return this;
//        return setDateOfDeath(dateOfDeathId == null ? null : new SimpleConnectionDate(dateOfDeathId,connection));
    }

    @Override
    public PersonBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public PersonBuilder addPartner(Person partner) {
        partners.add(partner);
        return this;
    }

    @Override
    public PersonBuilder addPartner(Integer partnerId) {
        return addPartner(partnerId == null ? null : new SimpleConnectionPerson(partnerId, connection));
    }

    @Override
    public Person build() {
        if(gender == null)throw new IllegalStateException("Gender must be set");
        return new SimplePerson(id, firstName, names, familyName, surnames, 
                              mother, father, birthDate, deathDate, 
                              birthPlace, deathPlace, alive, gender, partners);
    }
}