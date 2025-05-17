package tcs.familytree.viewmodels;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.core.NotImplemented;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.database.DatabaseConnection;

public interface SingleTreeViewModel {
    /**
     * Temporary method, but it's more convenient to have it declared in the interface, not in the implementation
     */
    default void updateGraph() {
        throw new UnsupportedOperationException();
    }
    SimpleObjectProperty<FamilyGraph> getGraphProperty();
    default DatabaseConnection getDatabaseConnection(){
        throw new NotImplemented();
    }
}
