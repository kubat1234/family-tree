package tcs.familytree.services.database;

import tcs.familytree.core.NotImplemented;
import tcs.familytree.core.person.Person;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFactoryPersonOnly implements DatabaseFactory {
    List<Person> list = new ArrayList<>();
    int counter = 0;
    Class<? extends DatabaseConection> databaseConnectionClass = null;
    private Class<?> extened;

    @Override
    public DatabaseConection build() {
        if(databaseConnectionClass == null){
            return new DatabaseConnectionWithoutDatabase(this);
        }
        if(databaseConnectionClass.equals(DatabaseConnectionWithoutDatabase.class)){
            return new DatabaseConnectionWithoutDatabase(this);
        }
        throw new NotImplemented("NOT IMPLEMENTED CLASS FACTORY: " + databaseConnectionClass);
        //return null;
    }

    @Override
    public DatabaseFactory changeType(Class<? extends DatabaseConection> databaseConectionClass) {
        this.databaseConnectionClass = databaseConectionClass;
        return this;
    }

    @Override
    public Person getPerson() {
        if(counter >= list.size()){
            throw new IllegalArgumentException();
        }
        counter++;
        return list.get(counter - 1);
    }

    @Override
    public boolean personAvailable(){
        return counter < list.size();
    }

    @Override
    public List<Person> getAllPersons() {
        return list;
    }


}
