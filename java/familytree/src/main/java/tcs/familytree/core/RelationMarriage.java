package tcs.familytree.core;

public class RelationMarriage implements Relation {
    final private Person firstPerson;
    final private Person secondPerson;

    RelationMarriage(Person firstPerson, Person secondPerson){
        this.firstPerson = firstPerson;
        this.secondPerson = secondPerson;
    }

    @Override
    public Person getFirstPerson() {
        return firstPerson;
    }

    @Override
    public Person getSecondPerson() {
        return secondPerson;
    }
}
