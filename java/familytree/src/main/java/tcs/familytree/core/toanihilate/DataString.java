package tcs.familytree.core.toanihilate;

public class DataString implements Data {
    private String information;

    public DataString(){
        this.information = null;
    }

    public DataString(String information){
        this.information = information;
    }

    private DataString(Data oldData){
        this.information = (String) oldData.get();
    }

    public String get(){
        return information;
    }

    @Override
    public void set(Object newData) {
        if(!(newData instanceof String)){
            throw new IllegalArgumentException("DataString class: set get not a string");
        }
        information = (String) newData;
    }

    @Override
    public Data copy() {
        return new DataString(this);
    }


}
