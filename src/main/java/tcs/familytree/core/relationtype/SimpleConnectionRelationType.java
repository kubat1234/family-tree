package tcs.familytree.core.relationtype;

import tcs.familytree.core.AbstractConnectionData;
import tcs.familytree.core.Identifiable;
import tcs.familytree.core.NotImplemented;
import tcs.familytree.services.database.DatabaseConnection;

public class SimpleConnectionRelationType  extends AbstractConnectionData<RelationType> implements RelationType{
    DatabaseConnection connection;
    public SimpleConnectionRelationType(int id, DatabaseConnection connection){
        super(id, connection);
        connection.getUpdater().registerRelationType(this);
    }
    public SimpleConnectionRelationType(RelationType relationType, DatabaseConnection connection){
        super(relationType, connection);
        connection.getUpdater().registerRelationType(this);
    }
    @Override
    public void load() {
        throw new NotImplemented("Load in SimpleConnectionRelationType");
    }

    @Override
    public String getName(){
        if(isUnloaded()) load();
        return data.getName();
    }
    @Override
    public RelationType getSuper(){
        if(isUnloaded()) load();
        return data;
    }

    @Override
    public Class<? extends Identifiable> getDataClass(){
        return RelationType.class;
    }
}
