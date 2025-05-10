package tcs.familytree.core.person;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.place.Place;

import java.util.List;
import java.util.Objects;

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
    Gender gender;

    public SimplePerson(int id, String firstName, List<String> names, String familyName, List<String> surnames, Person mother, Person father, Date birthDate, Date deathDate, Place birthPlace, Place deathPlace, boolean alive, Gender gender){
        this.id = id;
        this.firstName = firstName;
        this.names = names;
        this.familyName = familyName;
        this.surnames = surnames;
        this.mother = mother;
        this.father = father;
    }

    public SimplePerson(Person person){
        if(person == null)throw new NullPointerException();
        this.id = person.getId();
        this.firstName = person.getName();
        this.names = person.getAllName();
        this.familyName = person.getSurname();
        this.surnames = person.getSurnamesAsList();
        this.mother = person.getMother();
        this.father = person.getFather();
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

    public Gender getGender(){
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Person person)) {
            return false;
        }
        return person.getId() == id;
    }
}
