package tcs.familytree.views;

import tcs.familytree.viewmodels.DummyViewModel;

public class DummyView{
    public String getDummyData() {
        return new DummyViewModel().getDummyData() + " and Views!";
    }
}
