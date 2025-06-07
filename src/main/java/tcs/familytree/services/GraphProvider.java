package tcs.familytree.services;

import javafx.beans.property.SimpleObjectProperty;
import org.jooq.Record;
import org.jooq.Result;
import tcs.familytree.core.person.Person;

public interface GraphProvider {
//    FamilyGraph getGraph();
    void updateGraph();
    SimpleObjectProperty<FamilyGraph> getGraphProperty();

    Person createNewPerson();
    Result<org.jooq.Record> sendQuery(String query);
}