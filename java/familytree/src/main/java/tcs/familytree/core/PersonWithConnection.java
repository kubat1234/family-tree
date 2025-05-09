package tcs.familytree.core;

public class PersonWithConnection implements Person{
    Person innerPerson;
    DatabaseConection connection;
    Integer id;
    boolean ifLoaded = false;

    PersonWithConnection(Integer id, DatabaseConection connection){
        this.id = id;
        this.connection = connection;
    }

    void load(){
        if(ifLoaded)return;
        if(!connection.checkIfPersonExist(id)){
            throw new DatabaseError("Person with id: " + id + "cannot load from database: " + connection + ".");
        }
        innerPerson = connection.getPerson(id);
        ifLoaded = true;
    }

    void unload(){
        ifLoaded = false;
        innerPerson = null;
    }

    @Override
    public Person copy() {
        return new PersonWithConnection(id, connection);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Data getName() {
        load();
        return innerPerson.getName();
    }

    @Override
    public String getNameAsString() {
        load();
        return innerPerson.getNameAsString();
    }

    @Override
    public void setNameAsString(String name) {
        load();
        innerPerson.setNameAsString(name);
    }

    @Override
    public void setName(Data name) {
        load();
        innerPerson.setName(name);
    }
}
