package tcs.familytree.core;

import java.util.List;

public class SimplePerson implements Person {
    int id;
    String firstName;
    List<String> names;
    String familyName;
    List<String> surnames;
    Person mother;
    Person father;
    Date birthDate;
    Date deathDate;
    Place birthPlace;
    Place deathPlace;
    boolean alive;
    boolean gender;

    public SimplePerson(int id, String firstName, List<String> names, String familyName, List<String> surnames, Person mother, Person father, Date birthDate, Date deathDate, Place birthPlace, Place deathPlace, boolean alive, boolean gender){
        this.id = id;
        this.firstName = firstName;
        this.names = names;
        this.familyName = familyName;
        this.surnames = surnames;
        this.mother = mother;
        this.father = father;
    }

    public SimplePerson(Person person){
        this(person.getId(), person.getName(), person.getAllName(), person.getFamilySurname(), person.getSurnamesAsList(), person.getMother(), person.getFather(), person.getDateOfBirth(), person.getDateOfDeath(), person.getPlaceOfBirth(), person.getPlaceOfDeath(), person.isAlive(), person.getGender());
    }

    public Person copy(){
        return new SimplePerson(this);
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return firstName;
    }

    public List<String> getAllName(){
        return names;
    }

    public String getSurname(){
        return familyName;
    }

    public String getFamilySurname(){
        return familyName;
    }

    public String getSurname(int numberOfSurname){
        return numberOfSurname == 0 ? familyName : surnames.get(numberOfSurname - 1);
    }

    public List<String> getSurnamesAsList(){
        return surnames;
    }

    public Person getMother(){
        return mother;
    }

    public Person getFather(){
        return father;
    }

    public Date getDateOfBirth(){
        return birthDate;
    }

    public Date getDateOfDeath(){
        return deathDate;
    }

    public Place getPlaceOfBirth(){
        return birthPlace;
    }

    public Place getPlaceOfDeath(){
        return deathPlace;
    }

    public boolean isAlive(){
        return alive;
    }

    public boolean getGender(){
        return gender;
    }
}
