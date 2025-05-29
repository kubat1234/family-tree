package tcs.familytree.viewmodels;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.GraphProvider;
import tcs.familytree.services.RealGraphProvider;

public class SingleDatabaseViewModel implements SingleTreeViewModel {
    GraphProvider provider;
    SimpleObjectProperty<FamilyGraph> graphProperty;

    public SingleDatabaseViewModel() {
        provider = new RealGraphProvider();
        graphProperty = provider.getGraphProperty();
    }

    @Override
    public SimpleObjectProperty<FamilyGraph> getGraphProperty() {
        return graphProperty;
    }

    @Override
    public void updateGraph() {
        provider.updateGraph();
    }
}
