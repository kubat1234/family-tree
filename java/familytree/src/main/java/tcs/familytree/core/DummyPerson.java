package tcs.familytree.core;

public class DummyPerson implements Person{
    private Integer id = 0;
    private Data name = new DataString();

    DummyPerson(int id, Data name){
        this.id = id;
        this.name.set(name.get());
    }

    DummyPerson(Person person){
        id = person.getId();
        name = person.getName();
    }

    @Override
    public Person copy() {
        return new DummyPerson(this);
    }

    @Override
    public Integer getId(){
        return id;
    }

    @Override
    public Data getName(){
        return name.copy();
    }

    @Override
    public String getNameAsString(){
        Object o = name.get();
        if(o instanceof String){
            return (String) o;
        }
        throw new IllegalArgumentException("Name is not a string");
    }

    @Override
    public void setName(Data name){
        this.name.set(name.get());
    }

    @Override
    public void setNameAsString(String name){
        this.name.set(name);
    }


}
