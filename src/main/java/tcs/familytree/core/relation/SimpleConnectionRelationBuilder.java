package tcs.familytree.core.relation;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relationtype.RelationType;
import tcs.familytree.services.database.DatabaseConnection;

public class SimpleConnectionRelationBuilder implements RelationBuilder {
    DatabaseConnection connection;
    RelationBuilder builder;

    public SimpleConnectionRelationBuilder(DatabaseConnection connection) {
        this.connection = connection;
        this.builder = new SimpleRelationBuilder(connection);
    }

    @Override
    public Relation build() {
        return builder.build();
    }

    @Override
    public RelationBuilder setId(int id) {
        builder.setId(id);
        return this;
    }

    @Override
    public RelationBuilder setType(RelationType relationType) {
        builder.setType(relationType);
        return this;
    }

    @Override
    public RelationBuilder setType(int relationTypeId) {
        builder.setType(relationTypeId);
        return this;
    }

    @Override
    public RelationBuilder setPerson1(Person person1) {
        builder.setPerson1(person1);
        return this;
    }

    @Override
    public RelationBuilder setPerson1(int person1Id) {
        builder.setPerson1(person1Id);
        return this;
    }

    @Override
    public RelationBuilder setPerson2(Person person2) {
        builder.setPerson2(person2);
        return this;
    }

    @Override
    public RelationBuilder setPerson2(int person2Id) {
        builder.setPerson2(person2Id);
        return this;
    }

    @Override
    public RelationBuilder setDate(Date date) {
        builder.setDate(date);
        return this;
    }

    @Override
    public RelationBuilder setDate(Integer dateId) {
        builder.setDate(dateId);
        return this;
    }

    @Override
    public RelationBuilder setPlace(Place place) {
        builder.setPlace(place);
        return this;
    }

    @Override
    public RelationBuilder setPlace(Integer placeId) {
        builder.setPlace(placeId);
        return this;
    }

    @Override
    public RelationBuilder setSymmetric(boolean symmetric) {
        builder.setSymmetric(symmetric);
        return this;
    }
}
