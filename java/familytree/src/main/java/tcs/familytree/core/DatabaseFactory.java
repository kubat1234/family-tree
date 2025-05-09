package tcs.familytree.core;

import java.util.List;

public interface DatabaseFactory {
    DatabaseConection build();
    DatabaseFactory changeType(Class<? extends DatabaseConection> databaseConectionClass);
    Person getPerson();
    boolean personAvailable();
    List<Person> getAllPersons();
    // TODO mozna dodac nowe rzeczy
}
