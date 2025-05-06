package tcs.familytree.core;

import java.util.List;

public interface DatabaseFactory {
    Person getPerson();
    boolean personAvailable();
    List<Person> getAllPersons();
    // TODO mozna dodac nowe rzeczy
}
