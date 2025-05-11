package tcs.familytree.services.database;

import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonBuilder;
import tcs.familytree.core.person.SimplePersonBuilder;

public class DatabaseFactoryPersonOnlyTest1 extends DatabaseFactoryPersonOnly {
    public DatabaseFactoryPersonOnlyTest1(){
        PersonBuilder personBuilder = new SimplePersonBuilder(null);
        Person pr = personBuilder.setId(1).setName("Kuba").build();
        list.add(pr);
        pr = personBuilder.setId(2).setName("Alice").build();
        list.add(pr);
        pr = personBuilder.setId(3).setName("Unknown Member of Delta Cult").build();
        list.add(pr);
    }
}
