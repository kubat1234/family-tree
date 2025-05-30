package tcs.familytree.viewmodels;

import tcs.familytree.core.person.Person;

public interface GraphViewModel extends SingleTreeViewModel {
    Integer x();
    Integer y();
    Person central();
    void updateCentral(Person person);
    void changeMod(Integer x, Integer y);
    void refresh();
}
