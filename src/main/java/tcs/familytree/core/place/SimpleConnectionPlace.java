package tcs.familytree.core.place;

import tcs.familytree.core.AbstractConnectionData;
import tcs.familytree.core.Identifiable;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.DatabaseError;

public class SimpleConnectionPlace extends AbstractConnectionData<Place> implements Place {
    public SimpleConnectionPlace(int id, DatabaseConnection connection) {
        super(id, connection);
        connection.getUpdater().registerPlace(this);
    }

    public SimpleConnectionPlace(Place place, DatabaseConnection connection) {
        super(place, connection);
        connection.getUpdater().registerPlace(this);
    }

    @Override
    public void load() {
        if(isUnloaded()){
            if(!connection.checkIfPlaceExist(id)){
                throw new DatabaseError("Person with id: " + id + "cannot load from database: " + connection + ".");
            }
            data = connection.getPlace(id);
        }
    }

    @Override
    public void setPlace(Place place) {
        if(isUnloaded()) load();
        data.setPlace(place);
        if(!connection.updatePlace(data)){
            unload();
            throw new DatabaseError("Place with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public String getName() {
        return data.getName();
    }

    @Override
    public Place getSuperPlace() {
        return data.getSuperPlace();
    }

    @Override
    public PlaceType getPlaceType() {
        return data.getPlaceType();
    }

    @Override
    public void setName(String name) {
        if(isUnloaded()) load();
        data.setName(name);
        if(!connection.updatePlace(data)){
            unload();
            throw new DatabaseError("Place with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setSuperPlace(Place superPlace) {
        if(isUnloaded()) load();
        data.setSuperPlace(superPlace);
        if(!connection.updatePlace(data)){
            unload();
            throw new DatabaseError("Place with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setPlaceType(PlaceType placeType) {

        if(isUnloaded()) load();
        data.setPlaceType(placeType);
        if(!connection.updatePlace(data)){
            unload();
            throw new DatabaseError("Place with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public Class<? extends Identifiable> getDataClass(){
        return Place.class;
    }

    @Override
    public String toString() {
        if(data == null){
            load();
        }
        if(data == null){
            return "";
        }
        return data.toString();
    }
}
