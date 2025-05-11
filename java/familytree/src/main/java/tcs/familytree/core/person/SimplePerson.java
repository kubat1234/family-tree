package tcs.familytree.core.person;

import jdk.jshell.spi.ExecutionControl;
import tcs.familytree.core.NotImplemented;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relation.Relation;

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
        this.names = person.getAllNames();
        this.familyName = person.getFamilySurname();
        this.surnames = person.getAllSurnames();
        this.mother = person.getMother();
        this.father = person.getFather();
    }
    @Override
    public Person copy(){
        return new SimplePerson(this);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName(){
        return firstName;
    }

    @Override
    public List<String> getAllNames(){
        return names;
    }

    @Override
    public String getFamilySurname(){
        return familyName;
    }

    @Override
    public String getSurname(int numberOfSurname){
        return numberOfSurname == 0 ? familyName : surnames.get(numberOfSurname - 1);
    }

    @Override
    public List<String> getAllSurnames(){
        return surnames;
    }

    @Override
    public Person getMother(){
        return mother;
    }

    @Override
    public Person getFather(){
        return father;
    }

    @Override
    public List<Person> getChildren() {
        throw new NotImplemented();
    }

    @Override
    public List<Person> getPartners() {
        throw new NotImplemented();
    }

    @Override
    public List<Relation> getRelations() {
        throw new NotImplemented();
    }

    @Override
    public Date getDateOfBirth(){
        return birthDate;
    }

    @Override
    public Date getDateOfDeath(){
        return deathDate;
    }

    @Override
    public Place getPlaceOfBirth(){
        return birthPlace;
    }

    @Override
    public Place getPlaceOfDeath(){
        return deathPlace;
    }

    @Override
    public boolean isAlive(){
        return alive;
    }

    @Override
    public boolean isDead() {
        return !alive;
    }

    @Override
    public Gender getGender(){
        return gender;
    }

    @Override
    public void setName(String name) {
        this.firstName = name;
    }

    @Override
    public void addName(String name) {
        this.names.add(name);
    }

    @Override
    public void setAllNames(String... names) {
        this.names = List.of(names);
    }

    @Override
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public void addSurname(String surname) {
        this.surnames.add(surname);
    }

    @Override
    public void addSurname(String surname, int numberOfSurname) {
        this.surnames.add(numberOfSurname - 1, surname);
    }

    @Override
    public void setAllSurnames(String... surnames) {
        this.surnames = List.of(surnames);
    }

    @Override
    public void setMother(Person mother) {
        this.mother = mother;
    }

    @Override
    public void setFather(Person father) {
        this.father = father;
    }

    @Override
    public void setDateOfBirth(Date dateOfBirth) {
        this.birthDate = dateOfBirth;
    }

    @Override
    public void setDateOfDeath(Date dateOfDeath) {
        this.deathDate = dateOfDeath;
    }

    @Override
    public void setPlaceOfBirth(Place placeOfBirth) {
        this.birthPlace = placeOfBirth;
    }

    @Override
    public void setPlaceOfDeath(Place placeOfDeath) {
        this.deathPlace = placeOfDeath;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Person person) {
            return person.getId() == id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
