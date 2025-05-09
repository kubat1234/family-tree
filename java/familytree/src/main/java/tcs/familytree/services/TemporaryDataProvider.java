package tcs.familytree.services;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.core.Person;
import tcs.familytree.core.RandomPersonsProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Very dangerous class, please refactor code asap so it's no longer needed.
 * Connects {@code RandomPersonsProvider}, {@code SimpleGraph} and {@code DummyViewModel}
 */
public class TemporaryDataProvider {

    SimpleGraph graph = new SimpleGraph();

    public FamilyGraph provideTemporaryData() {
        RandomPersonsProvider provider = new RandomPersonsProvider();
        graph.setData(provider.getStaticData());
        return graph;
    }

    public ReadOnlyProperty<FamilyGraph> provideTemporaryDataAsProperty() {
        return new SimpleObjectProperty<>(TemporaryDataProvider.this, "SimpleGraphProperty", graph);
    }
}
