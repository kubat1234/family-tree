package tcs.familytree.core.relation;

import tcs.familytree.services.database.DatabaseConnection;

public class SimpleConnectionRelation extends AbstractConnectionRelation{
    DatabaseConnection connection;

    SimpleConnectionRelation(int id, DatabaseConnection connection){
        super(id);
        this.connection = connection;
    }
    SimpleConnectionRelation(Relation relation, DatabaseConnection connection){
        super(relation);
        this.id = relation.getId();
        this.connection = connection;
    }

    @Override
    public void load(){
        //TODO
    }
}
