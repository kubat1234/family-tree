package tcs.familytree.services;

import tcs.familytree.core.person.Person;

import java.util.Collection;
import java.util.Optional;

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

    default Person getPerson(int personId) {
        return getAllPersons().stream().filter(p -> p.getId() == personId).findAny().orElse(null);
    }
}
