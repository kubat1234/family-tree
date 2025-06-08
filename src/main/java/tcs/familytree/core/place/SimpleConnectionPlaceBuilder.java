package tcs.familytree.core.place;

import tcs.familytree.services.database.DatabaseConnection;

public class SimpleConnectionPlaceBuilder{
    DatabaseConnection connection;
    SimplePlaceBuilder builder;
    public SimpleConnectionPlaceBuilder(DatabaseConnection connection){
        this.connection = connection;
        builder = new SimplePlaceBuilder(connection);
    }


    public SimpleConnectionPlaceBuilder setPlace(Place place){
        builder.setPlace(place);
        return this;
    }



    public SimpleConnectionPlaceBuilder setId(int id){
        builder.setId(id);
        return this;
    }

    public SimpleConnectionPlaceBuilder setName(String name){
        builder.setName(name);
        return this;
    }

    public SimpleConnectionPlaceBuilder setSuperPlace(Place superPlace){
        builder.setSuperPlace(superPlace);
        return this;
    }

    public SimpleConnectionPlaceBuilder setSuperPlace(Integer superPlace){
        builder.setSuperPlace(superPlace);
        return this;
    }

    public SimpleConnectionPlaceBuilder setPlaceType(PlaceType placeType){
        builder.setPlaceType(placeType);
        return this;
    }

    public Place build(){
        return new SimpleConnectionPlace(builder.build(), connection);
    }

}
