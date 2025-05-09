package tcs.familytree.core.toanihilate;

import tcs.familytree.core.person.Person;

public class DummyPerson implements Person {
    private Integer id = 0;
    private Data name = new DataString();

    DummyPerson(int id, String name){
        this.id = id;
        this.name.set(name);
    }

    DummyPerson(Person person){
        id = person.getId();
        name.set(person.getName());
    }

    @Override
    public Person copy() {
        return new DummyPerson(this);
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public String getName(){
        return (String) name.get();
    }

    @Override
    public void setName(String name){
        this.name.set(name);
    }
}
