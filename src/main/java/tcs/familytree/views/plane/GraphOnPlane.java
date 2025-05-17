package tcs.familytree.views.plane;

import java.util.List;

public interface GraphOnPlane {
    List<PersonOnPlane> getPersons();
    default List<ParentLineOnPlane> getParents() {
        return null;
    }
    default List<SymmetricLineOnPlane> getMarriages() {
        return null;
    }
}
