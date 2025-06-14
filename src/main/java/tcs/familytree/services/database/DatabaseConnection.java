package tcs.familytree.services.database;

import org.jooq.Result;
import tcs.familytree.core.*;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.place.PlaceType;
import tcs.familytree.core.relation.Relation;

import java.util.List;

public interface DatabaseConnection {

    Updater getUpdater();

    Result<org.jooq.Record> sendQuery(String query);

    List<Person> getAllPersons();
    Person getPerson(int id);
    default boolean checkIfPersonExist(int id){
        return getPerson(id) != null;
    }
    default boolean checkIfPersonExist(Person person){
        return checkIfPersonExist(person.getId());
    }
    boolean updatePerson(Person person);
    boolean addPerson(Person person);
    Person createNewPerson();
    boolean deletePerson(int id);
    default boolean deletePerson(Person person){
        return deletePerson(person.getId());
    }

    List<Person> getChildren(Person person);
    List<Person> getChildren(int id);

    /*
    List<Date> getAllDates();
    Date getDate(int id);
    default boolean checkIfDateExist(int id){
        return getDate(id) != null;
    }
    default boolean checkIfDateExist(Date date){
        return checkIfDateExist(date.getId());
    }
    boolean updateDate(Date date);
    boolean addDate(Date date);
    boolean deleteDate(int id);
    default boolean deleteDate(Date date){
        return deleteDate(date.getId());
    }
    */

    List<Place> getAllPlaces();
    Place getPlace(int id);
    default boolean checkIfPlaceExist(int id){
        return getPlace(id) != null;
    }
    default boolean checkIfPlaceExist(Place place){
        return checkIfPlaceExist(place.getId());
    }
    boolean updatePlace(Place place);
    boolean addPlace(Place place);
    boolean deletePlace(int id);
    default boolean deletePlace(Place place){
        return deletePlace(place.getId());
    }

    PlaceType getPlaceType(int id);
    List<PlaceType> getAllPlaceType();
    default boolean checkIfPlaceTypeExist(int id){
        return getPlaceType(id) != null;
    }
    default boolean checkIfPlaceTYpeExist(PlaceType place){
        return checkIfPlaceTypeExist(place.getId());
    }
    boolean updatePlaceType(PlaceType place);
    boolean addPlaceType(PlaceType place);
    boolean deletePlaceType(int id);
    default boolean deletePlaceType(PlaceType place){
        return deletePlaceType(place.getId());
    }


    List<Relation> getAllRelations();
    Relation getRelation(int id);
    default boolean checkIfRelationExist(int id){
        return getRelation(id) != null;
    }
    default boolean checkIfRelationExist(Relation relation){
        return checkIfRelationExist(relation.getId());
    }
    boolean updateRelation(Relation relation);
    boolean addRelation(Relation relation);
    boolean deleteRelation(int id);
    default boolean deleteRelation(Relation relation){
        return deleteRelation(relation.getId());
    }
}
