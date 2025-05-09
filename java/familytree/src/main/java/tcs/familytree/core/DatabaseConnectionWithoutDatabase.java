package tcs.familytree.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnectionWithoutDatabase implements DatabaseConection {
    Map<Integer, Person> allPeople = new HashMap<>();

    public DatabaseConnectionWithoutDatabase(DatabaseFactory databaseFactory){
        while(databaseFactory.personAvailable()){
            Person pr = databaseFactory.getPerson();
            if(pr == null){
                throw new NullPointerException();
            }
            allPeople.put(pr.getId(), pr);
        }
    }

    @Override
    public Person getPerson(int id) {
        if(checkIfPersonExist(id)){
            throw new DatabaseError("DATABASE " + this + " do not have person on id: " + id);
        }
        return allPeople.get(id).copy();
    }

    @Override
    public List<Person> getAllPersons() {
        return List.copyOf(allPeople.values());
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
