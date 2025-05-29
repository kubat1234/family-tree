package tcs.familytree.services.database;

import tcs.familytree.core.*;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relation.Relation;
import tcs.familytree.core.toanihilate.RelationMarriage;

import java.util.List;

public interface DatabaseConnection {

//    void load(DatabaseFactory databaseFactory);

//    void unload();

    default ConnectionDataUpdater getUpdater(){
        throw new NotImplemented();
    }

    List<Person> getAllPersons();

    default Person getPerson(int id){
        return getAllPersons().stream().filter(p -> p.getId() == id).findAny().orElse(null);
    }

    default boolean checkIfPersonExist(int id){
        return getPerson(id) != null;
    }

    default boolean checkIfPersonExist(Person person){
        return checkIfPersonExist(person.getId());
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

    default boolean checkIfPlaceExist(int id){throw new NotImplemented();}

    default boolean updatePlace(Place place){throw new NotImplemented();}

    default boolean addPlace(Place place){throw new NotImplemented();}
    // More Connection
}
