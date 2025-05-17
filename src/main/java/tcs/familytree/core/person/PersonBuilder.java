package tcs.familytree.core.person;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.place.Place;

public interface PersonBuilder {

    Person build();
    PersonBuilder setId(int id);
    PersonBuilder setName(String name);
    PersonBuilder setNames(String... names);
    PersonBuilder setFamilyName(String familyName);
    PersonBuilder setSurnames(String... surnames);
    PersonBuilder addSurname(String surname);
    PersonBuilder setMother(Person mother);
    PersonBuilder setMother(int motherId);
    PersonBuilder setFather(Person father);
    PersonBuilder setFather(int fatherId);
    PersonBuilder setAlive(boolean alive);
    PersonBuilder setPlaceOfBirth(Place placeOfBirth);
    PersonBuilder setPlaceOfBirth(int placeOfBirthId);
    PersonBuilder setPlaceOfDeath(Place placeOfDeath);
    PersonBuilder setPlaceOfDeath(int placeOfDeathId);
    PersonBuilder setDateOfBirth(Date dateOfBirth);
    PersonBuilder setDateOfBirth(int dateOfBirthId);
    PersonBuilder setDateOfDeath(Date dateOfDeath);
    PersonBuilder setDateOfDeath(int dateOfDeathId);
    PersonBuilder setGender(Gender gender);
}
