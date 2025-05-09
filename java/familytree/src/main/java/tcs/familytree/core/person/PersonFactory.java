package tcs.familytree.core.person;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.place.Place;

public interface PersonFactory {

    Person build();
    PersonFactory setId(int id);
    PersonFactory setFirstName(String name);
    PersonFactory addAdditionalName(String name);
    PersonFactory setFamilyName(String name);
    PersonFactory addSurname(String surName);
    PersonFactory setMother(Person mother);
    PersonFactory setFather(Person mother);
    PersonFactory setBirthDate(Date date);
    PersonFactory setDeathDate(Date date);
    PersonFactory setBirthPlace(Place date);
    PersonFactory setDeathPlace(Place place);
    PersonFactory setAlive(boolean place);
    PersonFactory setGender(Boolean place);
}
