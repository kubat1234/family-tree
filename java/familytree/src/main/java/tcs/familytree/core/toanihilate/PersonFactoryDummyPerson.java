package tcs.familytree.core.toanihilate;

import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonFactory;

public class PersonFactoryDummyPerson implements PersonFactory {
    Integer id;
    boolean ifId = false;
    Data name = new DataString();

    @Override
    public Person build() {
        if(!ifId){
            throw new RuntimeException("Brak id!!!");
        }
        return new DummyPerson(id, (String) name.get());
    }

    @Override
    public PersonFactory setId(int id) {
        this.id = id;
        ifId = true;
        return this;
    }

    @Override
    public PersonFactory setName(String name) {
        this.name.set(name);
        return this;
    }


}
