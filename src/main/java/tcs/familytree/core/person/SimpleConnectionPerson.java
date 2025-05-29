package tcs.familytree.core.person;

import tcs.familytree.core.AbstractConnectionData;
import tcs.familytree.core.Identifiable;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relation.Relation;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.DatabaseError;

import java.util.List;

public class SimpleConnectionPerson extends AbstractConnectionData<Person> implements Person{
    public SimpleConnectionPerson(int id, DatabaseConnection connection){
        super(id,connection);
    }

    public SimpleConnectionPerson(Person person, DatabaseConnection connection){
        super(person,connection);
    }

    @Override
    public void load() {
        if(isUnloaded()){
            if(!connection.checkIfPersonExist(id)){
                throw new DatabaseError("Person with id: " + id + "cannot load from database: " + connection + ".");
            }
            data = connection.getPerson(id);
        }
    }

    @Override
    public Gender getGender() {
        if(isUnloaded()) load();
        return data.getGender();
    }

    @Override
    public boolean isAlive() {
        if(isUnloaded()) load();
        return data.isAlive();
    }

    @Override
    public boolean isDead() {
        if(isUnloaded()) load();
        return data.isDead();
    }

    @Override
    public Person copy() {
        if(isUnloaded()) load();
        return data.copy();
    }

    @Override
    public boolean isFemale() {
        if(isUnloaded()) load();
        return data.isFemale();
    }

    @Override
    public boolean isMale() {
        if(isUnloaded()) load();
        return data.isMale();
    }

    @Override
    public Date getDateOfBirth() {
        if(isUnloaded()) load();
        return data.getDateOfBirth();
    }

    @Override
    public Date getDateOfDeath() {
        if(isUnloaded()) load();
        return data.getDateOfDeath();
    }

    @Override
    public List<Object> getAllData() {
        if(isUnloaded()) load();
        return data.getAllData();
    }

    @Override
    public List<Person> getChildren() {
        if(isUnloaded()) load();
        return data.getChildren();
    }

    @Override
    public List<Person> getParents() {
        if(isUnloaded()) load();
        return data.getParents();
    }

    @Override
    public List<Person> getPartners() {
        if(isUnloaded()) load();
        return data.getPartners();
    }

    @Override
    public Person getFather() {
        if(isUnloaded()) load();
        return data.getFather();
    }

    @Override
    public Person getMother() {
        if(isUnloaded()) load();
        return data.getMother();
    }

    @Override
    public List<String> getAllNames() {
        if(isUnloaded()) load();
        return data.getAllNames();
    }

    @Override
    public List<String> getAllSurnames() {
        if(isUnloaded()) load();
        return data.getAllSurnames();
    }

    @Override
    public Place getPlaceOfBirth() {
        if(isUnloaded()) load();
        return data.getPlaceOfBirth();
    }

    @Override
    public Place getPlaceOfDeath() {
        if(isUnloaded()) load();
        return data.getPlaceOfDeath();
    }

    @Override
    public String getFamilySurname() {
        if(isUnloaded()) load();
        return data.getFamilySurname();
    }

    @Override
    public String getName() {
        if(isUnloaded()) load();
        return data.getName();
    }

    @Override
    public String getSurname(int numberOfSurname) {
        if(isUnloaded()) load();
        return data.getSurname(numberOfSurname);
    }

    @Override
    public void setPlaceOfDeath(Place placeOfDeath) {
        if(isUnloaded()) load();
        data.setPlaceOfDeath(placeOfDeath);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setPlaceOfBirth(Place placeOfBirth) {
        if(isUnloaded()) load();
        data.setPlaceOfBirth(placeOfBirth);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setDateOfDeath(Date dateOfDeath) {
        if(isUnloaded()) load();
        data.setDateOfDeath(dateOfDeath);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setName(String name) {
        if(isUnloaded()) load();
        data.setName(name);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setAlive(boolean alive) {
        if(isUnloaded()) load();
        data.setAlive(alive);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setAllNames(String... names) {
        if(isUnloaded()) load();
        data.setAllNames(names);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void addName(String name) {
        if(isUnloaded()) load();
        data.addName(name);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public List<Relation> getRelations() {
        if(isUnloaded()) load();
        return data.getRelations();
    }

    @Override
    public void addSurname(String surname) {
        if(isUnloaded()) load();
        data.addSurname(surname);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void addSurname(String surname, int numberSurname) {
        if(isUnloaded()) load();
        data.addSurname(surname, numberSurname);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setAllSurnames(String... surnames) {
        if(isUnloaded()) load();
        data.setAllSurnames(surnames);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setDateOfBirth(Date dateOfBirth) {
        if(isUnloaded()) load();
        data.setDateOfBirth(dateOfBirth);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setFamilyName(String familyName) {
        if(isUnloaded()) load();
        data.setFamilyName(familyName);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setFather(Person father) {
        if(isUnloaded()) load();
        data.setFather(father);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setGender(Gender gender) {
        if(isUnloaded()) load();
        data.setGender(gender);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setMother(Person mother) {
        if(isUnloaded()) load();
        data.setMother(mother);
        if(!connection.updatePerson(data)){
            unload();
            throw new DatabaseError("Person with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Person p)) {
            return false;
        }
        return p.getId() == id;
    }

    @Override
    public String toString(){
        if(isUnloaded()) load();
        return data.toString();
    }

    @Override
    public Class<? extends Identifiable> getDataClass(){
        return Person.class;
    }
}
