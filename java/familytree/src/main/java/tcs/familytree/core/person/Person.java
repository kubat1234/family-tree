package tcs.familytree.core.person;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.NotImplemented;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relation.Relation;

import java.util.ArrayList;
import java.util.List;

public interface Person {
    // Interface to Person

    default public List<Object> getAllData(){
        throw new NotImplemented();
    }

    Person copy();

    // Names getters

    int getId();

    default String getName(){
        throw new NotImplemented();
    }

    default List<String> getAllName(){
        throw new NotImplemented();
    }

    default void setName(String name){
        throw new NotImplemented();
    }

    default void addName(){
        throw new NotImplemented();
    }

    default String getSurname(){
        throw new NotImplemented();
    }

    default String getSurname(int numberOfSurname){
        throw new NotImplemented();
    }

    default void addSurname(String surname, int numberSurname){
        throw new NotImplemented();
    }

    default void addSurname(String surname){
        throw new NotImplemented();
    }

    default String getFamilySurname(){
        throw new NotImplemented();
    }

    default List<String> getSurnamesAsList(){
        throw new NotImplemented();
    }

    // Family getters;
    // TODO family setters

    default Person getMother(){
        throw new NotImplemented();
    }

    default Person getFather(){
        throw new NotImplemented();
    }

    default List<Person> getParents(){
        List<Person> list = new ArrayList<>();
        list.add(getFather());
        list.add(getMother());
        return list;
    }

    default List<Person> getChildren(){
        throw new NotImplemented();
    }

    default List<Person> getPartners(){ throw new NotImplemented();}

    default List<Relation> getRelations(){ throw new NotImplemented();}

    // TODO relations getters

    default Date getDateOfBirth(){throw new NotImplemented();}

    default Date getDateOfDeath(){throw new NotImplemented();}

    default boolean isAlive(){throw new NotImplemented();}

    default boolean isDead(){throw new NotImplemented();}

    default Gender getGender(){throw new NotImplemented();}

    default boolean isMale(){
        return getGender()==Gender.MALE;
    }

    default boolean isFemale(){
        return getGender()==Gender.FEMALE;
    }

    default Place getPlaceOfBirth(){throw new NotImplemented();}

    default Place getPlaceOfDeath(){throw new NotImplemented();}

}
