package tcs.familytree.services;

import tcs.familytree.core.Person;
import tcs.familytree.core.RandomPersonsProvider;

import java.util.List;

/**
 * Very dangerous class, please refactor code asap so it's no longer needed.
 * Connects {@code RandomPersonsProvider}, {@code SimpleGraph} and {@code DummyViewModel}
 */
public class TemporaryDataProvider {
    public FamilyGraph provideTemporaryData() {
        RandomPersonsProvider provider = new RandomPersonsProvider();
        return new SimpleGraph(provider.getStaticData());
    }
}
