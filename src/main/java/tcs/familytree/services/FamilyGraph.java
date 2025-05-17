package tcs.familytree.services;

import tcs.familytree.core.person.Person;

import java.util.Collection;

public interface FamilyGraph {
    Collection<Person> getAllPersons();
    Collection<Person> getChildren(Person person);
    Collection<Person> getChildren(Integer personId);

    Collection<Person> getParents(Person person);
    Collection<Person> getParents(Integer personId);

    Collection<Person> getPartners(Person person);
    Collection<Person> getPartners(Integer personId);

    default int getSize() {
        return getAllPersons().size();
    }
}
