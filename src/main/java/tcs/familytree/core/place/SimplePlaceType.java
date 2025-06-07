package tcs.familytree.core.place;

public class SimplePlaceType implements PlaceType{
    int id;
    String name;
    PlaceType superPlaceType;

    public SimplePlaceType(int id, String name, PlaceType superPlaceType){
        this.id = id;
        this.name = name;
        this.superPlaceType = superPlaceType;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlaceType getSuperPlaceType() {
        return superPlaceType;
    }

    @Override
    public int getId() {
        return id;
    }
}
