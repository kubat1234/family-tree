package tcs.familytree.viewmodels;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.TemporaryDataProvider;

public class SingleTreeViewModel {

    TemporaryDataProvider provider;
    SimpleObjectProperty<FamilyGraph> graphProperty;

    public SingleTreeViewModel() {
        provider = new TemporaryDataProvider();
        graphProperty = provider.provideTemporaryDataAsProperty();
    }

    public void updateGraph() {
        provider.updateGraph();
    }

    public SimpleObjectProperty<FamilyGraph> getGraphProperty() {
        return graphProperty;
    }
}
