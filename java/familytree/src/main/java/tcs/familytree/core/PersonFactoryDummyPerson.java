package tcs.familytree.core;

public class PersonFactoryDummyPerson implements PersonFactory{
    Integer id;
    boolean ifId = false;
    Data name = new DataString();

    @Override
    public Person build() {
        if(!ifId){
            throw new RuntimeException("Brak id!!!");
        }
        return new DummyPerson(id, name);
    }

    @Override
    public PersonFactory setId(int id) {
        this.id = id;
        ifId = true;
        return this;
    }

    @Override
    public PersonFactory setName(Data name) {
        this.name.set(name.get());
        return this;
    }


}
