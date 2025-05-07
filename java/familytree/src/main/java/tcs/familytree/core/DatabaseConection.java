package tcs.familytree.core;

import java.util.List;

public interface DatabaseConection {
    
    default List<Person> getAllPersons(){
        throw new NotImplemented();
    }

    default Person getPerson(int id){
        throw new NotImplemented();
    }

    default boolean checkIfPersonExist(int id){
        throw new NotImplemented();
    }

    default boolean checkIfPersonExist(Person person){
        throw new NotImplemented();
    }

    // More Connection
}
