package tcs.familytree.viewmodels;


import tcs.familytree.services.DummyService;

public class DummyViewModel {
    public String getDummyData() {
        return new DummyService().getDummyData() + " and ViewModels!";
    }
}
