package tcs.familytree.services;

import tcs.familytree.core.person.Person;

import java.util.*;

/**
 * Answers queries in O(input) time, while it is supposed to answer in O(output).
 * This could be greatly improved.
 */
public class SimpleGraph implements FamilyGraph{
    List<Person> persons;

    SimpleGraph(List<Person> persons) {
        this.persons = persons;
    };

    SimpleGraph() {
        persons = Collections.emptyList();
    }

    public void setData(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public List<Person> getAllPersons() {
        return persons;
    }

    @Override
    public List<Person> getChildren(Person person) {
        return persons.stream().filter(p -> person.equals(p.getMother()) || person.equals(p.getFather())).toList();
    }

    @Override
    public List<Person> getChildren(Integer personId) {
        Optional<Person> person = persons.stream().filter(p -> Objects.equals(p.getId(), personId)).findFirst();
        if(person.isPresent()) {
            return getChildren(person.get());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<Person> getParents(Person person) {
        if(person == null) {
            throw new NullPointerException();
        }
        List<Person> parents = new ArrayList<>();
        if(person.getMother() != null) {
            parents.add(person.getMother());
        }
        if(person.getFather() != null) {
            parents.add(person.getFather());
        }
        return parents;
    }

    @Override
    public List<Person> getParents(Integer personId) {
        Optional<Person> person = persons.stream().filter(p -> Objects.equals(p.getId(), personId)).findFirst();
        if(person.isPresent()) {
            return getParents(person.get());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<Person> getPartners(Person person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Person> getPartners(Integer personId) {
        Optional<Person> person = persons.stream().filter(p -> Objects.equals(p.getId(), personId)).findFirst();
        if(person.isPresent()) {
            return getPartners(person.get());
        }
        throw new IllegalArgumentException();
    }
}
