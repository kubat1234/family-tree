package tcs.familytree.services;

import org.jooq.impl.QOM;
import tcs.familytree.core.Identifiable;
import tcs.familytree.core.person.Person;

import java.util.Collection;
import java.util.List;

public interface FamilyGraph {
    Collection<Person> getAllPersons();
    Collection<Person> getChildren(Person person);
    Collection<Person> getChildren(Integer personId);

    Collection<Person> getParents(Person person);
    Collection<Person> getParents(Integer personId);

    Collection<Person> getPartners(Person person);
    Collection<Person> getPartners(Integer personId);

    boolean findPerson(Person person);

    default int getSize() {
        return getAllPersons().size();
    }

    default Person getPerson(int personId) {
        return getAllPersons().stream().filter(p -> p.getId() == personId).findAny().orElse(null);
    }

    default int getWidthDown(int personId) {
        Collection<Integer> people = List.of(personId);
        int maxWidth = 1;
        while(!people.isEmpty()) {
            people.stream()
                    .forEachOrdered(p -> {
                        System.out.print(p + " ");
                        getChildren(p).stream()
                                .map(Identifiable::getId).forEachOrdered(System.out::print);
                        System.out.println();
                    });
            people = people.stream().flatMap(p -> getChildren(p).stream().map(Identifiable::getId)).toList();
            maxWidth = Math.max(maxWidth, people.size());
        }

        return maxWidth;
    }
}
