package tcs.familytree.services.database;

import tcs.familytree.core.*;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.relation.Relation;
import tcs.familytree.core.toanihilate.RelationMarriage;

import java.util.List;

public interface DatabaseConnection {

    void load(DatabaseFactory databaseFactory);

    void unload();

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

    default boolean updatePerson(Person person){throw new NotImplemented();}

    default boolean addPerson(Person person){throw new NotImplemented();}

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

    default boolean checkIfDateExist(int id){throw new NotImplemented();}

    default Date getDate(int id){throw new NotImplemented();}

    default boolean updateDate(Date date){throw new NotImplemented();}

    default boolean addDate(Date date){throw new NotImplemented();}
    // More Connection
}
