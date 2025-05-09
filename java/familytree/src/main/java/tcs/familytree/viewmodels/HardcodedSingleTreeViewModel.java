package tcs.familytree.viewmodels;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.TemporaryDataProvider;

public class HardcodedSingleTreeViewModel implements SingleTreeViewModel{

    TemporaryDataProvider provider;
    SimpleObjectProperty<FamilyGraph> graphProperty;
    static HardcodedSingleTreeViewModel model = new HardcodedSingleTreeViewModel();

    public HardcodedSingleTreeViewModel() {
        provider = new TemporaryDataProvider();
        graphProperty = provider.provideTemporaryDataAsProperty();
    }

    public void updateGraph() {
        provider.updateGraph();
    }

    public SimpleObjectProperty<FamilyGraph> getGraphProperty() {
        return graphProperty;
    }

    public static HardcodedSingleTreeViewModel getModel() {
        return model;
    }
}
