package tcs.familytree.views.plane;


import java.util.List;

public class SimpleGraphOnPlane implements GraphOnPlane {

    final List<PersonOnPlane> personsOnPlane;

    public SimpleGraphOnPlane(List<PersonOnPlane> personsOnPlane) {
        this.personsOnPlane = personsOnPlane;
    }
    @Override
    public List<PersonOnPlane> getPersons() {
        return personsOnPlane;
    }
}
