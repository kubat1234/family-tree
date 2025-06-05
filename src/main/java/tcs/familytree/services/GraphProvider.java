package tcs.familytree.services;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.core.person.Person;

public interface GraphProvider {
//    FamilyGraph getGraph();
    void updateGraph();
    SimpleObjectProperty<FamilyGraph> getGraphProperty();

    Person createNewPerson();
}