package tcs.familytree.viewmodels;

import tcs.familytree.MainController;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.views.SimplePersonDescription;

public interface GraphViewModel extends SingleTreeViewModel {
    Integer x();
    Integer y();
    Person central();
    void updateCentral(Person person);
    void changeMod(Integer x, Integer y);
    Refresher refresh();
    void setSimplePersonDescription(SimplePersonDescription simplePersonDescription);
    SimplePersonDescription getSimplePersonDescription();
    MainController getMainController();
    Person createNewPerson();
}
