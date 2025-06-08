package tcs.familytree.services;

import javafx.beans.property.SimpleObjectProperty;
import org.jooq.Record;
import org.jooq.Result;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.place.PlaceType;

import java.util.List;

public interface GraphProvider {
//    FamilyGraph getGraph();
    void updateGraph();
    SimpleObjectProperty<FamilyGraph> getGraphProperty();
    List<Place> getAllPlaces();
    List<PlaceType> getAllPlacesType();

    Person createNewPerson();

    Result<org.jooq.Record> sendQuery(String query);
    void updateAll();

}