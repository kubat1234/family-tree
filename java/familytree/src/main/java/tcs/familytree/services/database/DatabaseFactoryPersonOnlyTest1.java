package tcs.familytree.services.database;

import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonBuilder;
import tcs.familytree.core.toanihilate.PersonFactoryDummyPerson;

public class DatabaseFactoryPersonOnlyTest1 extends DatabaseFactoryPersonOnly {
    public DatabaseFactoryPersonOnlyTest1(){
        PersonBuilder personBuilder = new PersonFactoryDummyPerson();
        Person pr = personBuilder.setId(1).setName("Kuba").build();
//        Person pr = new DummyPerson(1);
//        pr.setName(new DataString("Kuba"));
        list.add(pr);
        pr = personBuilder.setId(2).setName("Alice").build();
        list.add(pr);
        pr = personBuilder.setId(3).setName("Unknown Member of Delta Cult").build();
        list.add(pr);
    }
}
