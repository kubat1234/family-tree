package tcs.familytree.core.place;

import tcs.familytree.core.Identifiable;

public interface Place extends Identifiable {
    String getName();
    Place getSuperPlace();
    PlaceType getPlaceType();

    void setName(String name);
    void setSuperPlace(Place superPlace);
    void setPlaceType(PlaceType placeType);
}
