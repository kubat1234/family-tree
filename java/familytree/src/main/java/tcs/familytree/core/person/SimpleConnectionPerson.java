package tcs.familytree.core.person;

import tcs.familytree.services.database.DatabaseConection;
import tcs.familytree.services.database.DatabaseError;

public class SimpleConnectionPerson extends AbstractConnectionPerson {
    DatabaseConection connection;

    public SimpleConnectionPerson(int id, DatabaseConection connection){
        super(id);
        this.connection = connection;
    }

    public SimpleConnectionPerson(Person person, DatabaseConection connection){
        super(person);
        this.connection = connection;
    }

    @Override
    void load() {
        if(isUnloaded()){
            if(!connection.checkIfPersonExist(id)){
                throw new DatabaseError("Person with id: " + id + "cannot load from database: " + connection + ".");
            }
            person = connection.getPerson(id);
        }
    }
}
