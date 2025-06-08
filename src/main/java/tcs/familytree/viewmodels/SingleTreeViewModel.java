package tcs.familytree.viewmodels;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.services.FamilyGraph;

public interface SingleTreeViewModel {
    void updateGraph();
    SimpleObjectProperty<FamilyGraph> getGraphProperty();
}
