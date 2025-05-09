package tcs.familytree.core;

import java.util.List;

public abstract class AbstractConnectionPerson implements Person{
    int id;
    Person person;

    AbstractConnectionPerson(int id){
        this.id = id;
    }

    AbstractConnectionPerson(Person person){
        this.person = person;
        this.id = person.getId();
    }

    abstract void load();

    void unload(){
        person = null;
    }

    public int getId(){
        return id;
    }

    boolean isUnloaded(){
        return person == null;
    }

    @Override
    public boolean getGender() {
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
    public List<String> getAllName() {
        if(isUnloaded()) load();
        return person.getAllName();
    }

    @Override
    public List<String> getSurnamesAsList() {
        if(isUnloaded()) load();
        return person.getSurnamesAsList();
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
    public String getSurname() {
        if(isUnloaded()) load();
        return person.getSurname();
    }

    @Override
    public String getSurname(int numberOfSurname) {
        if(isUnloaded()) load();
        return person.getSurname(numberOfSurname);
    }

}
