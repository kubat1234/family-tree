package tcs.familytree.core;

public class DummyPerson implements Person{
    private Integer id;
    private final Data name = new DataString();

    public DummyPerson(){

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
