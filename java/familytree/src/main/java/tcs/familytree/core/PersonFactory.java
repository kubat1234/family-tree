package tcs.familytree.core;

public interface PersonFactory {

    Person build();
    PersonFactory setId(int id);
    PersonFactory setName(Data name);
}
