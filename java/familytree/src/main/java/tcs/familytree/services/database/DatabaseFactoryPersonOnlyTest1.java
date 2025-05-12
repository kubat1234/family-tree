package tcs.familytree.services.database;

import tcs.familytree.core.person.Gender;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonBuilder;
import tcs.familytree.core.person.SimplePersonBuilder;

public class DatabaseFactoryPersonOnlyTest1 extends DatabaseFactoryPersonOnly {
    public DatabaseFactoryPersonOnlyTest1(){
        PersonBuilder personBuilder = new SimplePersonBuilder(null);
        Person pr = personBuilder.setId(1).setName("Kuba").setGender(Gender.MALE).build();
        list.add(pr);
        pr = personBuilder.setId(2).setName("Alice").setGender(Gender.FEMALE).build();
        list.add(pr);
        pr = personBuilder.setId(3).setName("Unknown Member of Delta Cult").setGender(Gender.OTHER).build();
        list.add(pr);
    }
}
