package tcs.familytree.services;

import javafx.beans.property.SimpleObjectProperty;
import org.jooq.Result;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.place.PlaceType;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.RealDatabaseConnection;

import java.util.List;

public class RealGraphProvider implements GraphProvider{
    SimpleObjectProperty<FamilyGraph> graphProperty;
    RealDatabaseConnection connection;

    public RealGraphProvider() {
        connection = new RealDatabaseConnection();
        graphProperty = new SimpleObjectProperty<>(this, "RealGraphProperty",
                new SimpleGraph());
        updateGraph();
    }

    @Override
    public void updateGraph() {
             graphProperty.set(new SimpleGraph(connection.getAllPersons()));
    }

    @Override
    public SimpleObjectProperty<FamilyGraph> getGraphProperty() {
        return graphProperty;
    }

    @Override
    public List<Place> getAllPlaces() {
        return connection.getAllPlaces();
    }

    @Override
    public List<PlaceType> getAllPlacesType() {
        return connection.getAllPlaceType();
    }

    @Override
    public Person createNewPerson() {
        return connection.createNewPerson();
    }
    @Override
    public Result<org.jooq.Record> sendQuery(String query) {
        return connection.sendQuery(query);
    }

}
