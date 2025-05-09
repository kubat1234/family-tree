package tcs.familytree.viewmodels;


import tcs.familytree.core.Person;
import tcs.familytree.services.DummyService;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.TemporaryDataProvider;

import java.util.List;


public class DummyViewModel {
    public String getDummyData() {
        return new DummyService().getDummyData() + " and ViewModels!";
    }

    /**
     * Some ugly code to get our GUI working asap.
     * @return few people's data
     */
    public FamilyGraph provideTemporaryData() {
        TemporaryDataProvider provider = new TemporaryDataProvider();
        return provider.provideTemporaryData();
    }
}
