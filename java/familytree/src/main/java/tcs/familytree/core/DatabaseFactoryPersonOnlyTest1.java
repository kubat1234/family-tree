package tcs.familytree.core;

public class DatabaseFactoryPersonOnlyTest1 extends DatabaseFactoryPersonOnly{
    public DatabaseFactoryPersonOnlyTest1(){
        PersonFactory personFactory = new PersonFactoryDummyPerson();
        Person pr = personFactory.setId(1).setName(new DataString("Kuba")).build();
//        Person pr = new DummyPerson(1);
//        pr.setName(new DataString("Kuba"));
        list.add(pr);
        pr = personFactory.setId(2).setName(new DataString("Alice")).build();
        list.add(pr);
        pr = personFactory.setId(3).setName(new DataString("Unknown Member of Delta Cult")).build();
        list.add(pr);
    }
}
