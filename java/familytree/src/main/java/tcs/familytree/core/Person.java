package tcs.familytree.core;

import java.util.ArrayList;
import java.util.List;

public interface Person {
    // Interface to Person

    default  public List<Data> getAllData(){
        throw new NotImplemented();
    }

    // Names getters

    Integer getId();

    default Data getName(){
        throw new NotImplemented();
    }

    default List<Data> getAllName(){
        throw new NotImplemented();
    }

    default void setName(Data name){
        throw new NotImplemented();
    }

    default void addName(){
        throw new NotImplemented();
    }

    default Data getSurname(){
        throw new NotImplemented();
    }

    default Data getSurname(int numberOfSurname){
        throw new NotImplemented();
    }

    default void addSurname(){
        throw new NotImplemented();
    }

    default void addSurname(int numberSurname){
        throw new NotImplemented();
    }

    default Data getFamilySurname(){
        throw new NotImplemented();
    }

    default List<Data> getSurnamesAsList(){
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
