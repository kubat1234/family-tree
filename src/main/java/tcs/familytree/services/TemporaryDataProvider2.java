package tcs.familytree.services;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.DatabaseConnectionWithoutDatabase;
import tcs.familytree.services.database.DatabaseFactory;
import tcs.familytree.services.database.DatabaseFactorySimpleInput1;

/**
 * Very dangerous class, please refactor code asap so it's no longer needed.
 * Connects {@code DatabaseFactorySimpleInput1}, {@code SimpleGraph} and {@code HardcodedSingleTreeViewModel}
 */
public class TemporaryDataProvider2 implements GraphProvider {

    SimpleObjectProperty<FamilyGraph> graphProperty;
    DatabaseConnectionWithoutDatabase databaseConnection;

    public TemporaryDataProvider2() {
        graphProperty = new SimpleObjectProperty<>(TemporaryDataProvider2.this, "SimpleGraphProperty", new SimpleGraph());
        databaseConnection = new DatabaseConnectionWithoutDatabase();
        databaseConnection.load(new DatabaseFactorySimpleInput1(databaseConnection));
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    private void updateGraph2() {

        DatabaseFactory factory = new DatabaseFactorySimpleInput1();
        graphProperty.set(new SimpleGraph(databaseConnection.getAllPersons()));
    }

    @Override
    public void updateGraph() {
        /*
        new Thread(() -> {
            try {
                Thread.sleep(200);
                updateGraph2();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
         */
        updateGraph2();
    }

    /**
     * Obsolete, use getGraphProperty() instead
     */
    public SimpleObjectProperty<FamilyGraph> provideTemporaryDataAsProperty() {
        return graphProperty;
    }

    @Override
    public SimpleObjectProperty<FamilyGraph> getGraphProperty() {
        return provideTemporaryDataAsProperty();
    }
}
