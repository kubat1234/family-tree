package tcs.familytree.core;

import java.util.ArrayList;
import java.util.List;

public interface Person {
    // Interface to Person

    default  public List<Object> getAllData(){
        throw new NotImplemented();
    }

    Person copy();

    // Names getters

    Integer getId();

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


    // place/date getters
    // TODO



}
