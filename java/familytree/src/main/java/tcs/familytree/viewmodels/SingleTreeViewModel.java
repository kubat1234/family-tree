package tcs.familytree.viewmodels;

import javafx.beans.property.ReadOnlyProperty;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.SimpleGraph;
import tcs.familytree.services.TemporaryDataProvider;

public class SingleTreeViewModel {

    TemporaryDataProvider provider;
    ReadOnlyProperty<FamilyGraph> graphProperty;

    public SingleTreeViewModel() {
        provider = new TemporaryDataProvider();
        graphProperty = provider.provideTemporaryDataAsProperty();
    }

    public ReadOnlyProperty<FamilyGraph> getGraphProperty() {
        return graphProperty;
    }
}
