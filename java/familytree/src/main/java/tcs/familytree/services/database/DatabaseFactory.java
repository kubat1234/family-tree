package tcs.familytree.services.database;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.relation.Relation;

import java.util.List;

public interface DatabaseFactory {
    DatabaseConnection build();
    DatabaseFactory changeType(Class<? extends DatabaseConnection> databaseConectionClass);
    Person getPerson();
    boolean personAvailable();
    List<Person> getAllPersons();
    Relation getRelation();
    boolean relationAvailable();
    Date getDate();
    boolean dateAvailable();

    // TODO mozna dodac nowe rzeczy
}
