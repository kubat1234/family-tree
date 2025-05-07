package tcs.familytree.core;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConnectionWithoutDatabase implements DatabaseConection {
    Map<Integer, Person> allPeople = new HashMap<>();

    DatabaseConnectionWithoutDatabase(DatabaseFactory databaseFactory){
        while(databaseFactory.personAvailable()){
            Person pr = databaseFactory.getPerson();
            if(pr == null){
                throw new NullPointerException();
            }
            allPeople.put(pr.getId(), pr);
        }
    }

    @Override
    public boolean checkIfPersonExist(int id) {
        return allPeople.containsKey(id);
    }

    @Override
    public boolean checkIfPersonExist(Person person) {
        return allPeople.containsValue(person);
    }
}
