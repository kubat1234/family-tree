package tcs.familytree.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseFactoryPersonOnly implements DatabaseFactory {
    List<Person> list = new ArrayList<>();
    int counter = 0;

    @Override
    public Person getPerson() {
        if(counter >= list.size()){
            throw new IllegalArgumentException();
        }
        counter++;
        return list.get(counter - 1);
    }

    @Override
    public boolean personAvailable(){
        return counter < list.size();
    }

    @Override
    public List<Person> getAllPersons() {
        return list;
    }


}
