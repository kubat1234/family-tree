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
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        load();
        return innerPerson.getName();
    }

    @Override
    public void setName(String name) {
        load();
        innerPerson.setName(name);
    }
}
