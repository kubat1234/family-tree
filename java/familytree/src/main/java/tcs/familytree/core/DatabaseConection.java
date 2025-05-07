package tcs.familytree.core;

import java.util.List;

public interface DatabaseConection {
    
    default List<Person> getAllPersons(){
        throw new NotImplemented();
    }

    default Person getPerson(int id){
        throw new NotImplemented();
    }



    // More Connection
}
