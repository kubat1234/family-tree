package tcs.familytree.core.toanihilate;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relation.Relation;
import tcs.familytree.core.relationtype.RelationType;

public class RelationMarriage implements Relation {
    final private Person firstPerson;
    final private Person secondPerson;
    Place place;
    Date date;

    RelationMarriage(Person firstPerson, Person secondPerson, Place place, Date date){
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
        this.place = place;
        this.date = date;
    }

    @Override
    public Person getFirstPerson() {
        return firstPerson;
    }

    @Override
    public Person getSecondPerson() {
        return secondPerson;
    }

    @Override
    public Place getPlace() {
        return place;
    }

    @Override
    public Date getDate(){
        return date;
    }

    @Override
    public boolean isSymetric() {
        return true;
    }

    @Override
    public RelationType getType() {
        return null;
    }
}
