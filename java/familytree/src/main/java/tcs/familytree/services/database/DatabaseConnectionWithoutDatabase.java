package tcs.familytree.services.database;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.relation.Relation;
import tcs.familytree.core.toanihilate.RelationMarriage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DatabaseConnectionWithoutDatabase implements DatabaseConnection {
    Map<Integer, Person> allPeople = new HashMap<>();
    Map<Integer, List<Relation>> allRelation = new HashMap<>();
    Map<Integer, Date> allDates = new HashMap<>();
    boolean loaded = false;


    public void load(DatabaseFactory databaseFactory){
        unload();
        while(databaseFactory.personAvailable()){
            Person pr = databaseFactory.getPerson();
            if(pr == null){
                throw new NullPointerException();
            }
            allPeople.put(pr.getId(), pr);
        }
        while (databaseFactory.dateAvailable()){
            Date date = databaseFactory.getDate();
            if(date == null){
                throw new NullPointerException();
            }
            allDates.put(date.getId(), date);
        }
        while (databaseFactory.relationAvailable()){
            Relation relation = databaseFactory.getRelation();
            if(relation == null){
                throw new NullPointerException();
            }
            Person pr = relation.getFirstPerson();
            if(!allRelation.containsKey(pr.getId())){
                allRelation.put(pr.getId(), new LinkedList<>());
            }
            allRelation.get(pr.getId()).add(relation);
            pr = relation.getSecondPerson();
            if(!allRelation.containsKey(pr.getId())){
                allRelation.put(pr.getId(), new LinkedList<>());
            }
            allRelation.get(pr.getId()).add(relation);
        }
        loaded = true;
    }

    @Override
    public void unload() {
        allPeople.clear();
        allRelation.clear();
        allDates.clear();
    }

    public DatabaseConnectionWithoutDatabase(){
        loaded = false;
    }

    DatabaseConnectionWithoutDatabase(DatabaseFactory databaseFactory){
        while(databaseFactory.personAvailable()){
            Person pr = databaseFactory.getPerson();
            if(pr == null){
                throw new NullPointerException();
            }
            allPeople.put(pr.getId(), pr);
        }
        while (databaseFactory.dateAvailable()){
            Date date = databaseFactory.getDate();
            if(date == null){
                throw new NullPointerException();
            }
            allDates.put(date.getId(), date);
        }
        while (databaseFactory.relationAvailable()){
            Relation relation = databaseFactory.getRelation();
            if(relation == null){
                throw new NullPointerException();
            }
            Person pr = relation.getFirstPerson();
            if(!allRelation.containsKey(pr.getId())){
                allRelation.put(pr.getId(), new LinkedList<>());
            }
            allRelation.get(pr.getId()).add(relation);
            pr = relation.getSecondPerson();
            if(!allRelation.containsKey(pr.getId())){
                allRelation.put(pr.getId(), new LinkedList<>());
            }
            allRelation.get(pr.getId()).add(relation);
        }
        loaded = true;

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
    public boolean updatePerson(Person person) {
        if(!allPeople.containsKey(person.getId())){
            return false;
        }
        allPeople.remove(person.getId());
        allPeople.put(person.getId(), person);
        return true;
    }

    @Override
    public boolean addPerson(Person person) {
        if(allPeople.containsKey(person.getId())){
            return false;
        }
        allPeople.put(person.getId(), person);
        return true;
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

    @Override
    public boolean checkIfDateExist(int id) {
        return allDates.containsKey(id);
    }

    @Override
    public Date getDate(int id) {
        if(!checkIfDateExist(id)){
            throw new IllegalArgumentException("Date with id = " + id + " not exist in database.");
        }
        return allDates.get(id);
    }

    @Override
    public boolean updateDate(Date date) {
        if(!allDates.containsKey(date.getId())){
            return false;
        }
        allDates.remove(date.getId(), date);
        allDates.put(date.getId(), date);
        return true;
    }

    @Override
    public boolean addDate(Date date) {
        if(allDates.containsKey(date.getId())){
            return false;
        }
        allDates.put(date.getId(), date);
        return true;
    }
}

