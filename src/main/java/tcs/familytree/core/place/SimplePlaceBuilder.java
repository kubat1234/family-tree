package tcs.familytree.core.place;

import tcs.familytree.services.database.DatabaseConnection;

public class SimplePlaceBuilder {
    DatabaseConnection connection;
    int id;
    String name;
    Place superPlace;
    PlaceType placeType;

    SimplePlaceBuilder(DatabaseConnection databaseConnection){
        connection = databaseConnection;
    }

    SimplePlaceBuilder(DatabaseConnection connection, Place place){
        this(connection);
        setPlace(place);
    }

    public SimplePlaceBuilder setPlace(Place place){
        id = place.getId();
        name = place.getName();
        superPlace = place.getSuperPlace();
        placeType = place.getPlaceType();
        return this;
    }


    public SimplePlaceBuilder setId(int id){
        this.id = id;
        return this;
    }

    public SimplePlaceBuilder setName(String name){
        this.name = name;
        return this;
    }

    public SimplePlaceBuilder setSuperPlace(Place superPlace){
        this.superPlace = superPlace;
        return this;
    }

    public SimplePlaceBuilder setSuperPlace(Integer superPlace){
        return setSuperPlace(superPlace == null ? null : new SimpleConnectionPlace(superPlace, connection));
    }

    public SimplePlaceBuilder setPlaceType(PlaceType placeType){
        this.placeType = placeType;
        return this;
    }

    public Place build(){
        return new SimplePlace(id, name, superPlace, placeType);
    }
}
