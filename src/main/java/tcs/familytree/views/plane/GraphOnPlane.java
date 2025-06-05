package tcs.familytree.views.plane;

import java.util.List;

public interface GraphOnPlane {
    List<PersonOnPlane> getPersons();
    default List<LineOnPlane> getLines() {
        return null;
    }
}
