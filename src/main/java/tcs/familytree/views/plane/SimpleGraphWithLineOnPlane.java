package tcs.familytree.views.plane;

import java.util.List;

public class SimpleGraphWithLineOnPlane implements GraphOnPlane {

    final List<PersonOnPlane> personsOnPlane;
    final List<LineOnPlane> lineOnPlane;

    public SimpleGraphWithLineOnPlane(List<PersonOnPlane> personsOnPlane, List<LineOnPlane> lineOnPlane) {
        this.personsOnPlane = personsOnPlane;
        this.lineOnPlane = lineOnPlane;
    }
    @Override
    public List<PersonOnPlane> getPersons() {
        return personsOnPlane;
    }

    @Override
    public List<LineOnPlane> getLines() {
        return lineOnPlane;
    }
}
