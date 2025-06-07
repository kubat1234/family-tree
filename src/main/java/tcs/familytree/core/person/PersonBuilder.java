package tcs.familytree.core.person;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.place.Place;

public interface PersonBuilder {

    Person build();
    PersonBuilder setPerson(Person person);
    PersonBuilder setId(int id);
    PersonBuilder setName(String name);
    PersonBuilder setNames(String... names);
    PersonBuilder setFamilyName(String familyName);
    PersonBuilder setSurnames(String... surnames);
    PersonBuilder addSurname(String surname);
    PersonBuilder setMother(Person mother);
    PersonBuilder setMother(Integer motherId);
    PersonBuilder setFather(Person father);
    PersonBuilder setFather(Integer fatherId);
    PersonBuilder setAlive(boolean alive);
    PersonBuilder setPlaceOfBirth(Place placeOfBirth);
    PersonBuilder setPlaceOfBirth(Integer placeOfBirthId);
    PersonBuilder setPlaceOfDeath(Place placeOfDeath);
    PersonBuilder setPlaceOfDeath(Integer placeOfDeathId);
    PersonBuilder setDateOfBirth(Date dateOfBirth);
    PersonBuilder setDateOfDeath(Date dateOfDeath);
    PersonBuilder setGender(Gender gender);
    PersonBuilder addPartner(Person partner);
    PersonBuilder addPartner(Integer partnerId);
}
