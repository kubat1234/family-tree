package tcs.familytree.core.place;

public class SimplePlace implements Place{
    int id;
    String name;
    Place superPlace;
    PlaceType placeType;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Place getSuperPlace() {
        return superPlace;
    }

    @Override
    public PlaceType getPlaceType() {
        return placeType;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSuperPlace(Place superPlace) {
        this.superPlace = superPlace;
    }

    @Override
    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    @Override
    public int getId() {
        return id;
    }
}
