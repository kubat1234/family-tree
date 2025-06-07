package tcs.familytree.core.place;

import tcs.familytree.core.Identifiable;

public interface PlaceType extends Identifiable {
    String getName();
    PlaceType getSuperPlaceType();
}
