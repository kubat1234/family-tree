package tcs.familytree.core;

import java.util.List;

public interface DatabaseConection {
    
    default List<Person> getAllPersons(){
        throw new NotImplemented();
    }

    default Person getPerson(int id){
        throw new NotImplemented();
    }

    default boolean checkIfPersonExist(int id){
        throw new NotImplemented();
    }

    default boolean checkIfPersonExist(Person person){
        throw new NotImplemented();
    }

    default List<Relation> getAllRelation(){
        throw new NotImplemented();
    }

    default List<RelationMarriage> getMarriage(Person person){
        throw new NotImplemented();
    }

    default List<RelationMarriage> getMarriage(int id){
        throw new NotImplemented();
    }

    default List<Person> getChildren(Person person){
        throw new NotImplemented();
    }

    default List<Person> getChildren(int id){
        throw new NotImplemented();
    }


    // More Connection
}
