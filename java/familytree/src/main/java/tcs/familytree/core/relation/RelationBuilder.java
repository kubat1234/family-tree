package tcs.familytree.core.relation;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relationtype.RelationType;

public interface RelationBuilder {
    Relation build();
    RelationBuilder setId(int id);
    RelationBuilder setType(RelationType relationType);
    RelationBuilder setType(int relationTypeId);
    RelationBuilder setPerson1(Person person1);
    RelationBuilder setPerson1(int person1Id);
    RelationBuilder setPerson2(Person person2);
    RelationBuilder setPerson2(int person2Id);
    RelationBuilder setDate(Date date);
    RelationBuilder setDate(int dateId);
    RelationBuilder setPlace(Place place);
    RelationBuilder setPlace(int placeId);
    RelationBuilder setSymetric(boolean symetric);
    default RelationBuilder setPersons(Person person1, Person person2){
        return setPerson1(person1).setPerson2(person2);
    }
    default RelationBuilder setPersons(int person1Id, int person2Id){
        return setPerson1(person1Id).setPerson2(person2Id);
    }
}
