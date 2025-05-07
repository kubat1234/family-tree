package tcs.familytree.core;

public class DummyPerson implements Person{
    private Integer id = 0;
    private final Data name = new DataString();


    public DummyPerson(){
        id = 0;
    }

    public DummyPerson(int id){
        this.id = id;
    }

    public DummyPerson(int id, Data name){
        this.id = id;
        this.name.set(name.get());
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
    public void setName(Data name){
        name.set(name.get());
    }


}
