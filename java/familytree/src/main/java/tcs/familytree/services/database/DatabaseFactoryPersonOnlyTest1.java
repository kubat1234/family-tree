package tcs.familytree.services.database;

import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonFactory;
import tcs.familytree.core.toanihilate.PersonFactoryDummyPerson;

public class DatabaseFactoryPersonOnlyTest1 extends DatabaseFactoryPersonOnly {
    public DatabaseFactoryPersonOnlyTest1(){
        PersonFactory personFactory = new PersonFactoryDummyPerson();
        Person pr = personFactory.setId(1).setFirstName("Kuba").build();
//        Person pr = new DummyPerson(1);
//        pr.setName(new DataString("Kuba"));
        list.add(pr);
        pr = personFactory.setId(2).setFirstName("Alice").build();
        list.add(pr);
        pr = personFactory.setId(3).setFirstName("Unknown Member of Delta Cult").build();
        list.add(pr);
    }
}
