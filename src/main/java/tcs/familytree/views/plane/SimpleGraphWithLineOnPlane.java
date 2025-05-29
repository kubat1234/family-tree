package tcs.familytree.views.plane;

import java.util.List;

public class SimpleGraphWithLineOnPlane implements GraphOnPlane {

    final List<PersonOnPlane> personsOnPlane;
    final List<ParentLineOnPlane> lineOnPlane;

    public SimpleGraphWithLineOnPlane(List<PersonOnPlane> personsOnPlane, List<ParentLineOnPlane> lineOnPlane) {
        this.personsOnPlane = personsOnPlane;
        this.lineOnPlane = lineOnPlane;
    }
    @Override
    public List<PersonOnPlane> getPersons() {
        return personsOnPlane;
    }

    @Override
    public List<ParentLineOnPlane> getParents() {
        return lineOnPlane;
    }
}
