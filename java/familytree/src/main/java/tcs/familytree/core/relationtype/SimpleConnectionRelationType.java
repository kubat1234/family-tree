package tcs.familytree.core.relationtype;

import tcs.familytree.core.relation.RelationBuilder;
import tcs.familytree.services.database.DatabaseConection;

public class SimpleConnectionRelationType  extends AbstractConnectionRelationType{
    DatabaseConection connection;
    public SimpleConnectionRelationType(int id, DatabaseConection connection){
        super(id);
        this.connection = connection;
    }
    public SimpleConnectionRelationType(RelationType relationType, DatabaseConection connection){
        super(relationType);
        this.id = relationType.getId();
        this.connection = connection;
    }
    @Override
    public void load() {
        //TODO
    }
}
