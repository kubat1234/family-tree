package tcs.familytree.core.relation;

import tcs.familytree.core.relationtype.RelationType;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public interface Relation {

    int getId();

    Person getFirstPerson();
    Person getSecondPerson();

    default List<Person> getBothPerson(){
        List<Person> list = new LinkedList<>();
        list.add(getFirstPerson());
        list.add(getSecondPerson());
        return list;
    }

    Place getPlace();
    Date getDate();

    RelationType getType();
    boolean isSymetric();
}
