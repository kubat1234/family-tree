package tcs.familytree.core.place;

import tcs.familytree.core.AbstractConnectionData;
import tcs.familytree.core.Identifiable;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.DatabaseError;

public class SimpleConnectionPlaceType extends AbstractConnectionData<PlaceType> implements PlaceType {

    public SimpleConnectionPlaceType(int id, DatabaseConnection connection) {
        super(id, connection);
    }

    public SimpleConnectionPlaceType(PlaceType placeType, DatabaseConnection connection) {
        super(placeType, connection);
    }

    @Override
    public void load() {
        if(isUnloaded()){
            if(!connection.checkIfPlaceTypeExist(id)){
                throw new DatabaseError("Place Type with id: " + id + "cannot load from database: " + connection + ".");
            }
            data = connection.getPlaceType(id);
        }
    }

    @Override
    public Class<? extends Identifiable> getDataClass() {
        return PlaceType.class;
    }

    @Override
    public String getName() {
        return data.getName();
    }

    @Override
    public PlaceType getSuperPlaceType() {
        return data.getSuperPlaceType();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
