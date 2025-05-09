package tcs.familytree.core;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFactoryPersonOnly implements DatabaseFactory {
    List<Person> list = new ArrayList<>();
    int counter = 0;
    Class<DatabaseConection> databaseConnectionClass = null;

    @Override
    public DatabaseConection build() {
        if(databaseConnectionClass == null){
            return new DatabaseConnectionWithoutDatabase(this);
        }
        if(databaseConnectionClass.equals(DatabaseConnectionWithoutDatabase.class)){
            return new DatabaseConnectionWithoutDatabase(this);
        }
        throw new NotImplemented("NOT IMPLEMENTED CLASS FACTORY:" + databaseConnectionClass);
        //return null;
    }

    @Override
    public DatabaseFactory changeType(Class<DatabaseConection> databaseConectionClass) {
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
