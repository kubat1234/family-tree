package tcs.familytree.core;

import java.util.LinkedList;
import java.util.List;

public interface Relation {

    Person getFirstPerson();
    Person getSecondPerson();
    default List<Person> getBothPerson(){
        List<Person> list = new LinkedList<>();
        list.add(getFirstPerson());
        list.add(getSecondPerson());
        return list;
    }



}
