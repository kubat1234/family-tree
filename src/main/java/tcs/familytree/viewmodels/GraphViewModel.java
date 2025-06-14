package tcs.familytree.viewmodels;

import org.jooq.Result;
import tcs.familytree.MainController;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.place.PlaceType;
import tcs.familytree.views.SimplePersonDescription;

import java.util.List;

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
    Result<org.jooq.Record> sendQuery(String query);
    List<Place> getAllPlaces();
    List<PlaceType> getAllPlaceTypes();
}
