package tcs.familytree.services.database;

import tcs.familytree.core.*;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relation.Relation;

import java.util.List;

public interface DatabaseConnection {

    Updater getUpdater();

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

    boolean updatePerson(Person person);

    default boolean addPerson(Person person){throw new NotImplemented();}

    default List<Relation> getAllRelation(){
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
