package tcs.familytree.core.relation;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relationtype.RelationType;

public abstract class AbstractConnectionRelation implements Relation{
    int id;
    Relation relation;

    AbstractConnectionRelation(int id){
        this.id = id;
    }
    AbstractConnectionRelation(Relation relation){
        this.relation = relation;
        this.id = relation.getId();
    }

    abstract void load();
    void unload(){
        relation = null;
    }
    public boolean isUnloaded(){
        return relation == null;
    }
    @Override
    public int getId(){
        return id;
    }
    @Override
    public RelationType getType() {
        if(isUnloaded()) load();
        return relation.getType();
    }
    @Override
    public Person getFirstPerson() {
        if(isUnloaded()) load();
        return relation.getFirstPerson();
    }
    @Override
    public Person getSecondPerson() {
        if(isUnloaded()) load();
        return relation.getSecondPerson();
    }
    @Override
    public Place getPlace() {
        if(isUnloaded()) load();
        return relation.getPlace();
    }
    @Override
    public Date getDate(){
        if(isUnloaded()) load();
        return relation.getDate();
    }

    @Override
    public boolean isSymetric() {
        if(isUnloaded()) load();
        return relation.isSymetric();
    }
}
