package tcs.familytree.core.person;

public interface PersonFactory {

    Person build();
    PersonFactory setId(int id);
    PersonFactory setName(String name);
}
