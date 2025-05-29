package tcs.familytree.core.relation;

import tcs.familytree.core.AbstractConnectionData;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relationtype.RelationType;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.core.NotImplemented;

public class SimpleConnectionRelation extends AbstractConnectionData<Relation> implements Relation{
    public SimpleConnectionRelation(int id, DatabaseConnection connection){
        super(id, connection);
    }
    public SimpleConnectionRelation(Relation relation, DatabaseConnection connection){
        super(relation, connection);
    }

    @Override
    public void load() {
        throw new NotImplemented(); // TODO
    }

    @Override
    public RelationType getType() {
        if(isUnloaded()) load();
        return data.getType();
    }
    @Override
    public Person getFirstPerson() {
        if(isUnloaded()) load();
        return data.getFirstPerson();
    }
    @Override
    public Person getSecondPerson() {
        if(isUnloaded()) load();
        return data.getSecondPerson();
    }
    @Override
    public Place getPlace() {
        if(isUnloaded()) load();
        return data.getPlace();
    }
    @Override
    public Date getDate(){
        if(isUnloaded()) load();
        return data.getDate();
    }

    @Override
    public boolean isSymetric() {
        if(isUnloaded()) load();
        return data.isSymetric();
    }
}
