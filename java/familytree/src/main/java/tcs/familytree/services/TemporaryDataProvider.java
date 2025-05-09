package tcs.familytree.services;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.core.toanihilate.RandomPersonsProvider;

/**
 * Very dangerous class, please refactor code asap so it's no longer needed.
 * Connects {@code RandomPersonsProvider}, {@code SimpleGraph} and {@code DummyViewModel}
 */
public class TemporaryDataProvider {

    SimpleObjectProperty<FamilyGraph> graphProperty;

    public TemporaryDataProvider() {
        graphProperty = new SimpleObjectProperty<>(TemporaryDataProvider.this, "SimpleGraphProperty", new SimpleGraph());
    }

    private void updateGraph2() {
        RandomPersonsProvider provider = new RandomPersonsProvider();
        graphProperty.set(new SimpleGraph(provider.getStaticData()));
    }

    public void updateGraph() {
        /*
        new Thread(() -> {
            try {
                Thread.sleep(200);
                updateGraph2();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
         */
        updateGraph2();
    }

    public SimpleObjectProperty<FamilyGraph> provideTemporaryDataAsProperty() {
        return graphProperty;
    }
}
