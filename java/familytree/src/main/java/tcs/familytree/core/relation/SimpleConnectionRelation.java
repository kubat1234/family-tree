package tcs.familytree.core.relation;

import tcs.familytree.services.database.DatabaseConection;

public class SimpleConnectionRelation extends AbstractConnectionRelation{
    DatabaseConection connection;

    SimpleConnectionRelation(int id,DatabaseConection connection){
        super(id);
        this.connection = connection;
    }
    SimpleConnectionRelation(Relation relation,DatabaseConection connection){
        super(relation);
        this.id = relation.getId();
        this.connection = connection;
    }

    @Override
    void load(){
        //TODO
    }
}
