package tcs.familytree.core.relation;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relationtype.RelationType;

public class SimpleRelation implements Relation{
    int id;
    RelationType relationType;
    Person person1;
    Person person2;
    Date date;
    Place place;
    boolean isSymmetric;

    SimpleRelation(int id, RelationType relationType, Person person1, Person person2, Date date, Place place, boolean isSymmetric){
        this.id = id;
        this.relationType = relationType;
        this.person1 = person1;
        this.person2 = person2;
        this.date = date;
        this.place = place;
        this.isSymmetric = isSymmetric;
    }
    SimpleRelation(Relation relation){
        this.id = relation.getId();
        this.relationType = relation.getType();
        this.person1 = relation.getFirstPerson();
        this.person2 = relation.getSecondPerson();
        this.date = relation.getDate();
        this.place = relation.getPlace();
        this.isSymmetric = relation.isSymmetric();
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public RelationType getType() {
        return relationType;
    }
    @Override
    public Person getFirstPerson() {
        return person1;
    }
    @Override
    public Person getSecondPerson() {
        return person2;
    }
    @Override
    public Date getDate() {
        return date;
    }
    @Override
    public Place getPlace() {
        return place;
    }
    @Override
    public boolean isSymmetric() {
        return isSymmetric;
    }
}
