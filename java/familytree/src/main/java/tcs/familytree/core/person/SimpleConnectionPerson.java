package tcs.familytree.core.person;

import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.DatabaseError;

public class SimpleConnectionPerson extends AbstractConnectionPerson {
    DatabaseConnection connection;

    public SimpleConnectionPerson(int id, DatabaseConnection connection){
        super(id);
        this.connection = connection;
    }

    public SimpleConnectionPerson(Person person, DatabaseConnection connection){
        super(person);
        this.connection = connection;
    }

    @Override
    public void load() {
        if(isUnloaded()){
            if(!connection.checkIfPersonExist(id)){
                throw new DatabaseError("Person with id: " + id + "cannot load from database: " + connection + ".");
            }
            person = connection.getPerson(id);
        }
    }
}
