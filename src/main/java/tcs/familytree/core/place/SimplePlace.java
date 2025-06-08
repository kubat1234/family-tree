package tcs.familytree.core.place;

public class SimplePlace implements Place{
    int id;
    String name;
    Place superPlace;
    PlaceType placeType;

    public SimplePlace(int id, String name, Place superPlace, PlaceType placeType){
        this.id = id;
        this.name = name;
        this.superPlace = superPlace;
        this.placeType = placeType;
    }

    public SimplePlace(Place place){
        this.id = place.getId();
        this.name = place.getName();
        this.superPlace = place.getSuperPlace();
        this.placeType = place.getPlaceType();
    }

    @Override
    public void setPlace(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.superPlace = place.getSuperPlace();
        this.placeType = place.getPlaceType();
    }

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

    @Override
    public String toString() {
        if(superPlace == null){
            return name;
        }else{
            return superPlace + " " + name;
        }
    }
}
