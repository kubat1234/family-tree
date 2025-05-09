package tcs.familytree.core.relation;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.date.SimpleConnectionDate;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.SimpleConnectionPerson;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relationtype.RelationType;
import tcs.familytree.core.relationtype.SimpleConnectionRelationType;
import tcs.familytree.core.relationtype.SimpleRelationType;
import tcs.familytree.services.database.DatabaseConection;

public class SimpleRelationBuilder implements RelationBuilder{
    DatabaseConection connection;
    int id;
    RelationType relationType;
    Person person1;
    Person person2;
    Date date;
    Place place;
    boolean isSymetric;

    SimpleRelationBuilder(DatabaseConection connection){
        this.connection = connection;
    }
    @Override
    public RelationBuilder setId(int id) {
        this.id = id;
        return this;
    }
    @Override
    public RelationBuilder setType(RelationType relationType) {
        this.relationType = relationType;
        return this;
    }
    @Override
    public RelationBuilder setType(int relationTypeId) {
        return setType(new SimpleConnectionRelationType(relationTypeId,connection));
    }
    @Override
    public RelationBuilder setPerson1(Person person1) {
        this.person1 = person1;
        return this;
    }

    @Override
    public RelationBuilder setPerson1(int person1Id) {
        return setPerson1(new SimpleConnectionPerson(person1Id,connection));
    }
    @Override
    public RelationBuilder setPerson2(Person person2) {
        this.person2 = person2;
        return this;
    }

    @Override
    public RelationBuilder setPerson2(int person2Id) {
        return setPerson2(new SimpleConnectionPerson(person2Id,connection));
    }

    @Override
    public RelationBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public RelationBuilder setDate(int dateId) {
        return setDate(new SimpleConnectionDate(dateId,connection));
    }
    @Override
    public RelationBuilder setPlace(Place place) {
        this.place = place;
        return this;
    }

    @Override
    public RelationBuilder setPlace(int placeId) {
//         TODO
//        return setPlace(new SimpleConnectionPlace(placeId,connection));
        return this;
    }

    @Override
    public RelationBuilder setSymetric(boolean symetric) {
        this.isSymetric = symetric;
        return this;
    }

    @Override
    public Relation build() {
        return new SimpleRelation(id, relationType, person1, person2, date, place, isSymetric);
    }
}
