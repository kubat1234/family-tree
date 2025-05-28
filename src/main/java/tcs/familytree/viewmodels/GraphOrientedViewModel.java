package tcs.familytree.viewmodels;

import javafx.beans.property.SimpleObjectProperty;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.TemporaryDataProvider2;

public class GraphOrientedViewModel implements GraphViewModel {
    Person centralPerson;
    Integer x_mod;
    Integer y_mod;
    TemporaryDataProvider2 provider;
    SimpleObjectProperty<FamilyGraph> graphProperty;

    public GraphOrientedViewModel(Person centralPerson, TemporaryDataProvider2 provider){
        this.provider = provider;
        this.centralPerson = centralPerson;
        x_mod = 0;
        y_mod = 0;
        graphProperty = provider.provideTemporaryDataAsProperty();
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
        if(!graphProperty.get().findPerson(person)){
            centralPerson = person;
        }
    }

    @Override
    public void changeMod(Integer x, Integer y) {
        x_mod += x;
        y_mod += y;
    }


}
