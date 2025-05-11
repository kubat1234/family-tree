package tcs.familytree.core.person;

import tcs.familytree.core.ConnectionData;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relation.Relation;

import java.util.List;

public abstract class AbstractConnectionPerson implements Person, ConnectionData {
    int id;
    Person person;

    public AbstractConnectionPerson(int id){
        this.id = id;
    }

    public AbstractConnectionPerson(Person person){
        this.person = person;
        this.id = person.getId();
    }

    public abstract void load();

    public void unload(){
        person = null;
    }

    public int getId(){
        return id;
    }

    public boolean isUnloaded(){
        return person == null;
    }

    public boolean isLoaded(){
        return person != null;
    }

    @Override
    public Gender getGender() {
        if(isUnloaded()) load();
        return person.getGender();
    }

    @Override
    public boolean isAlive() {
        if(isUnloaded()) load();
        return person.isAlive();
    }

    @Override
    public boolean isDead() {
        if(isUnloaded()) load();
        return person.isDead();
    }

    @Override
    public Person copy() {
        if(isUnloaded()) load();
        return person.copy();
    }

    @Override
    public boolean isFemale() {
        if(isUnloaded()) load();
        return person.isFemale();
    }

    @Override
    public boolean isMale() {
        if(isUnloaded()) load();
        return person.isMale();
    }

    @Override
    public Date getDateOfBirth() {
        if(isUnloaded()) load();
        return person.getDateOfBirth();
    }

    @Override
    public Date getDateOfDeath() {
        if(isUnloaded()) load();
        return person.getDateOfDeath();
    }

    @Override
    public List<Object> getAllData() {
        if(isUnloaded()) load();
        return person.getAllData();
    }

    @Override
    public List<Person> getChildren() {
        if(isUnloaded()) load();
        return person.getChildren();
    }

    @Override
    public List<Person> getParents() {
        if(isUnloaded()) load();
        return person.getParents();
    }

    @Override
    public List<Person> getPartners() {
        if(isUnloaded()) load();
        return person.getPartners();
    }

    @Override
    public Person getFather() {
        if(isUnloaded()) load();
        return person.getFather();
    }

    @Override
    public Person getMother() {
        if(isUnloaded()) load();
        return person.getMother();
    }

    @Override
    public List<String> getAllNames() {
        if(isUnloaded()) load();
        return person.getAllNames();
    }

    @Override
    public List<String> getAllSurnames() {
        if(isUnloaded()) load();
        return person.getAllSurnames();
    }

    @Override
    public Place getPlaceOfBirth() {
        if(isUnloaded()) load();
        return person.getPlaceOfBirth();
    }

    @Override
    public Place getPlaceOfDeath() {
        if(isUnloaded()) load();
        return person.getPlaceOfDeath();
    }

    @Override
    public String getFamilySurname() {
        if(isUnloaded()) load();
        return person.getFamilySurname();
    }

    @Override
    public String getName() {
        if(isUnloaded()) load();
        return person.getName();
    }

    @Override
    public String getSurname(int numberOfSurname) {
        if(isUnloaded()) load();
        return person.getSurname(numberOfSurname);
    }

    @Override
    public void setPlaceOfDeath(Place placeOfDeath) {
        if(isUnloaded()) load();
        person.setPlaceOfDeath(placeOfDeath);
    }

    @Override
    public void setPlaceOfBirth(Place placeOfBirth) {
        if(isUnloaded()) load();
        person.setPlaceOfBirth(placeOfBirth);
    }

    @Override
    public void setDateOfDeath(Date dateOfDeath) {
        if(isUnloaded()) load();
        person.setDateOfDeath(dateOfDeath);
    }

    @Override
    public void setName(String name) {
        if(isUnloaded()) load();
        person.setName(name);
    }

    @Override
    public void setAlive(boolean alive) {
        if(isUnloaded()) load();
        person.setAlive(alive);
    }

    @Override
    public void setAllNames(String... names) {
        if(isUnloaded()) load();
        person.setAllNames(names);
    }

    @Override
    public void addName(String name) {
        if(isUnloaded()) load();
        person.addName(name);
    }

    @Override
    public List<Relation> getRelations() {
        if(isUnloaded()) load();
        return person.getRelations();
    }

    @Override
    public void addSurname(String surname) {
        if(isUnloaded()) load();
        person.addSurname(surname);
    }

    @Override
    public void addSurname(String surname, int numberSurname) {
        if(isUnloaded()) load();
        person.addSurname(surname, numberSurname);
    }

    @Override
    public void setAllSurnames(String... surnames) {
        if(isUnloaded()) load();
        person.setAllSurnames(surnames);
    }

    @Override
    public void setDateOfBirth(Date dateOfBirth) {
        if(isUnloaded()) load();
        person.setDateOfBirth(dateOfBirth);
    }

    @Override
    public void setFamilyName(String familyName) {
        if(isUnloaded()) load();
        person.setFamilyName(familyName);
    }

    @Override
    public void setFather(Person father) {
        if(isUnloaded()) load();
        person.setFather(father);
    }

    @Override
    public void setGender(Gender gender) {
        if(isUnloaded()) load();
        person.setGender(gender);
    }

    @Override
    public void setMother(Person mother) {
        if(isUnloaded()) load();
        person.setMother(mother);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Person p)) {
            return false;
        }
        return p.getId() == id;
    }
}
