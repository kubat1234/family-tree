package tcs.familytree.core.relationtype;

import tcs.familytree.services.database.DatabaseConnection;

public class SimpleConnectionRelationType  extends AbstractConnectionRelationType{
    DatabaseConnection connection;
    public SimpleConnectionRelationType(int id, DatabaseConnection connection){
        super(id);
        this.connection = connection;
    }
    public SimpleConnectionRelationType(RelationType relationType, DatabaseConnection connection){
        super(relationType);
        this.id = relationType.getId();
        this.connection = connection;
    }
    @Override
    public void load() {
        //TODO
    }
}
