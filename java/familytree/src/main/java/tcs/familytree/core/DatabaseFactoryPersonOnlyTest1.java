package tcs.familytree.core;

public class DatabaseFactoryPersonOnlyTest1 extends DatabaseFactoryPersonOnly{
    DatabaseFactoryPersonOnlyTest1(){
        Person pr = new DummyPerson();
        pr.setName(new DataString("Kuba"));
        list.add(pr);
        pr = new DummyPerson();
        pr.setName(new DataString("Alice"));
        list.add(pr);
        pr = new DummyPerson();
        pr.setName(new DataString("Unnamed Person"));
        list.add(pr);
    }
}
