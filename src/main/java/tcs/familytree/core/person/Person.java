package tcs.familytree.core.person;

import tcs.familytree.core.Identifiable;
import tcs.familytree.core.NotImplemented;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relation.Relation;

import java.util.ArrayList;
import java.util.List;

public interface Person extends Identifiable {
    // Interface to Person

    default List<Object> getAllData(){
        throw new NotImplemented();
    }

    Person copy();

    // Names getters

    String getName();

    List<String> getAllNames();

    String getSurname(int numberOfSurname);

    String getFamilySurname();

    List<String> getAllSurnames();

    // Family getters;
    // TODO family setters

    Person getMother();

    Person getFather();

    default List<Person> getParents(){
        List<Person> list = new ArrayList<>();
        list.add(getFather());
        list.add(getMother());
        return list;
    }

    List<Person> getChildren();

    List<Person> getPartners();

    List<Relation> getRelations();

    // TODO relations getters

    Date getDateOfBirth();

    Date getDateOfDeath();

    boolean isAlive();

    boolean isDead();

    Gender getGender();

    default boolean isMale(){
        return getGender()==Gender.MALE;
    }

    default boolean isFemale(){
        return getGender()==Gender.FEMALE;
    }

    Place getPlaceOfBirth();

    Place getPlaceOfDeath();

    void setName(String name);

    void addName(String name);

    void setAllNames(String... names);

    void addSurname(String surname, int numberSurname);

    void addSurname(String surname);

    void setAllSurnames(String... surnames);

    void setFamilyName(String familyName);

    void setMother(Person mother);

    void setFather(Person father);

    void setDateOfBirth(Date dateOfBirth);

    void setDateOfDeath(Date dateOfDeath);

    void setAlive(boolean alive);

    void setGender(Gender gender);

    void setPlaceOfBirth(Place placeOfBirth);

    void setPlaceOfDeath(Place placeOfDeath);

    boolean equals(Object o);

    int hashCode();
}
