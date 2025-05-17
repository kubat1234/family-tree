package tcs.familytree.services;

import tcs.familytree.core.DummyCore;

public class DummyService {
    public String getDummyData() {
        return new DummyCore().getDummyData() + " and Services!";
    }
}

