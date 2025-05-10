package tcs.familytree.viewmodels;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.TemporaryDataProvider;
import tcs.familytree.services.TemporaryDataProvider2;

public class HardcodedSingleTreeViewModel implements SingleTreeViewModel{

    TemporaryDataProvider2 provider;
    SimpleObjectProperty<FamilyGraph> graphProperty;
    static HardcodedSingleTreeViewModel model = new HardcodedSingleTreeViewModel();

    private HardcodedSingleTreeViewModel() {
        provider = new TemporaryDataProvider2();
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
