package tcs.familytree.services;

import javafx.beans.property.SimpleObjectProperty;

public interface GraphProvider {
//    FamilyGraph getGraph();
    void updateGraph();
    SimpleObjectProperty<FamilyGraph> getGraphProperty();
}