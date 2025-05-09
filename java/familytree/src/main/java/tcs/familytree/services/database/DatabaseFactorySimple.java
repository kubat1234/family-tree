package tcs.familytree.services.database;

import tcs.familytree.core.NotImplemented;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.relation.Relation;

import java.util.LinkedList;
import java.util.List;

public class DatabaseFactorySimple implements DatabaseFactory {
    List<Person> personList = new LinkedList<>();
    int personCounter = 0;
    List<Relation> relationList = new LinkedList<>();
    int relationCounter = 0;
    List<Date> dateList = new LinkedList<>();
    int dateCounter = 0;
    Class<? extends DatabaseConnection> databaseConnectionClass = null;

    @Override
    public DatabaseConnection build() {
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
    public DatabaseFactory changeType(Class<? extends DatabaseConnection> databaseConectionClass) {
        this.databaseConnectionClass = databaseConectionClass;
        return this;
    }

    @Override
    public Person getPerson() {
        if(personCounter >= personList.size()){
            throw new IllegalArgumentException("Database Factory person get out of bounds");
        }
        personCounter++;
        return personList.get(personCounter - 1);
    }

    @Override
    public boolean personAvailable(){
        return personCounter < personList.size();
    }

    @Override
    public List<Person> getAllPersons() {
        return personList;
    }

    @Override
    public Relation getRelation() {
        if(relationCounter >= relationList.size()){
            throw new IllegalArgumentException("Database Factory relation get out of bounds");
        }
        relationCounter++;
        return relationList.get(relationCounter - 1);
    }

    @Override
    public boolean relationAvailable() {
        return relationCounter < relationList.size();
    }

    @Override
    public Date getDate() {
        if(dateCounter >= dateList.size()){
            throw new IllegalArgumentException("Database Factory data get out of bounds");
        }
        dateCounter++;
        return dateList.get(dateCounter - 1);
    }

    @Override
    public boolean dateAvailable() {
        return dateCounter < dateList.size();
    }

}
