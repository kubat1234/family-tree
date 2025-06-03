package tcs.familytree.viewmodels;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.MainController;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.GraphProvider;
import tcs.familytree.views.SimplePersonDescription;

public class GraphOrientedViewModel implements GraphViewModel {
    Person centralPerson;
    Integer x_mod;
    Integer y_mod;
    GraphProvider provider;
    SimpleObjectProperty<FamilyGraph> graphProperty;
    MainController mainController;
    SimplePersonDescription simplePersonDescription;
    Refresher refresher = new Refresher(this);

    public GraphOrientedViewModel(Person centralPerson, GraphProvider provider, MainController mainController){
        this.provider = provider;
        this.centralPerson = centralPerson;
        x_mod = 0;
        y_mod = 0;
        graphProperty = provider.getGraphProperty();
        this.mainController = mainController;
        updateGraph();

    }

    @Override
    public void updateGraph() {
        provider.updateGraph();
    }

    @Override
    public SimpleObjectProperty<FamilyGraph> getGraphProperty() {
        return graphProperty;
    }

    @Override
    public Integer x() {
        return x_mod;
    }

    @Override
    public Integer y() {
        return y_mod;
    }

    @Override
    public Person central() {
        return centralPerson;
    }

    @Override
    public void updateCentral(Person person) {
        if(graphProperty.get().findPerson(person)){
            centralPerson = person;
            refresh().centeredPersonChange();
        }
    }

    @Override
    public void changeMod(Integer x, Integer y) {
        x_mod += x;
        y_mod += y;
    }

    @Override
    public Refresher refresh() {
        return refresher;
    }

    @Override
    public void setSimplePersonDescription(SimplePersonDescription simplePersonDescription) {
        this.simplePersonDescription = simplePersonDescription;
    }

    @Override
    public SimplePersonDescription getSimplePersonDescription() {
        return simplePersonDescription;
    }

    @Override
    public MainController getMainController() {
        return mainController;
    }


}
