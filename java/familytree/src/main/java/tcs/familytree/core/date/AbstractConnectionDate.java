package tcs.familytree.core.date;

import java.util.List;

public abstract class AbstractConnectionDate implements Date {
    int id;
    Date date;

    AbstractConnectionDate(int id){
        this.id = id;
    }
    AbstractConnectionDate(Date date){
        this.date = date;
        this.id = date.getId();
    }

    abstract void load();
    void unload(){
        date = null;
    }

    boolean isUnloaded(){
        return date == null;
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public List<Object> getAllData() {
        if(isUnloaded()) load();
        return date.getAllData();
    }

    @Override
    public boolean isAccurate() {
        if(isUnloaded()) load();
        return date.isAccurate();
    }

    @Override
    public int getDay() {
        if(isUnloaded()) load();
        return date.getDay();
    }

    @Override
    public int getMonth() {
        if(isUnloaded()) load();
        return date.getMonth();
    }

    @Override
    public int getYear() {
        if(isUnloaded()) load();
        return date.getYear();
    }

    @Override
    public String getDateDescription() {
        if(isUnloaded()) load();
        return date.getDateDescription();
    }
}
