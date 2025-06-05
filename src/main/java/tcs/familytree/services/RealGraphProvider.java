package tcs.familytree.services;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.RealDatabaseConnection;

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

    /**
     * Don't use it plz
     */
    public DatabaseConnection getDatabase(String password) {
        if(!password.equals("admin")) {
            throw new IllegalStateException();
        }
        return connection;
    }
}
