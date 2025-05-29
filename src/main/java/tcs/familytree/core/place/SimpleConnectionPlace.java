package tcs.familytree.core.place;

import tcs.familytree.core.AbstractConnectionData;
import tcs.familytree.core.Identifiable;
import tcs.familytree.core.date.Date;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.DatabaseError;

public class SimpleConnectionPlace extends AbstractConnectionData<Place> implements Place {
    public SimpleConnectionPlace(int id, DatabaseConnection connection) {
        super(id, connection);
    }

    @Override
    public void load() {

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
}
