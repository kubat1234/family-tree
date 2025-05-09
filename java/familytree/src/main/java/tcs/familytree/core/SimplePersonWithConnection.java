package tcs.familytree.core;

public class SimplePersonWithConnection extends AbstractPersonWithConnection{
    DatabaseConection connection;

    SimplePersonWithConnection(int id, DatabaseConection connection){
        super(id);
        this.connection = connection;
    }

    SimplePersonWithConnection(Person person, DatabaseConection connection){
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
