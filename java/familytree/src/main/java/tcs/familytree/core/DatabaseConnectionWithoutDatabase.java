package tcs.familytree.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DatabaseConnectionWithoutDatabase implements DatabaseConection {
    Map<Integer, Person> allPeople = new HashMap<>();
    Map<Integer, List<Relation>> allRelation;

    DatabaseConnectionWithoutDatabase(DatabaseFactory databaseFactory){
        while(databaseFactory.personAvailable()){
            Person pr = databaseFactory.getPerson();
            if(pr == null){
                throw new NullPointerException();
            }
            allPeople.put(pr.getId(), pr);
        }
    }

    @Override
    public Person getPerson(int id) {
        if(checkIfPersonExist(id)){
            throw new DatabaseError("DATABASE " + this + " do not have person on id: " + id);
        }
        return allPeople.get(id).copy();
    }

    @Override
    public List<Person> getAllPersons() {
        return List.copyOf(allPeople.values());
    }

    @Override
    public boolean checkIfPersonExist(int id) {
        return allPeople.containsKey(id);
    }

    @Override
    public boolean checkIfPersonExist(Person person) {
        return allPeople.containsValue(person);
    }

    @Override
    public List<Relation> getAllRelation() {
        List<Relation> allRelationList = new LinkedList<>();
        for(List<Relation> list: allRelation.values()){
            allRelationList.addAll(list);
        }
        return allRelationList;
    }

    @Override
    public List<RelationMarriage> getMarriage(Person person) {
        if(person == null){
            throw new NullPointerException();
        }
        return getMarriage(person.getId());
    }

    @Override
    public List<RelationMarriage> getMarriage(int id) {
        if(allPeople.containsKey(id)){
            throw new IllegalArgumentException("Nie ma osoby o id = " + id);
        }
        List<RelationMarriage> relationMarriagesList = new LinkedList<>();
        for(Relation relation: allRelation.get(id)){
            if(relation instanceof RelationMarriage){
                relationMarriagesList.add((RelationMarriage) relation);
            }
        }
        return relationMarriagesList;
    }


    @Override
    public List<Person> getChildren(Person person) {
        if(!allPeople.containsValue(person)){
            throw new IllegalArgumentException("Person " + person + " not exist in database.");
        }
        List<Person> children = new LinkedList<>();
        for(Person localPerson: allPeople.values()){
            if(localPerson.getMother().getId() == person.getId() || localPerson.getFather().getId() == person.getId() ){
                children.add(localPerson);
            }
        }
        return children;
    }

    @Override
    public List<Person> getChildren(int id) {
        if(!allPeople.containsKey(id)){
            throw new IllegalArgumentException("Person with id = " + id + " not exist in database.");
        }
        return getChildren(allPeople.get(id));
    }
}

