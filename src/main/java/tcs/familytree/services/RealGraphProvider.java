package tcs.familytree.services;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.services.database.RealDatabaseConnection;

public class RealGraphProvider implements GraphProvider{
    SimpleObjectProperty<FamilyGraph> graphProperty;
    RealDatabaseConnection connection;

    public RealGraphProvider() {
        connection = new RealDatabaseConnection();
        graphProperty = new SimpleObjectProperty<>(this, "RealGraphProperty",
                new SimpleGraph());
        updateGraph();
        graphProperty.set(new SimpleGraph(connection.getAllPersons()));

//        updateGraph();
    }

    @Override
    public void updateGraph() {
        new Thread(() -> {
             graphProperty.set(new SimpleGraph(connection.getAllPersons()));
        }).start();
    }

    @Override
    public SimpleObjectProperty<FamilyGraph> getGraphProperty() {
        return graphProperty;
    }
}
