package tcs.familytree.core.date;

import tcs.familytree.core.ConnectionData;

import java.util.List;

public abstract class AbstractConnectionDate implements Date, ConnectionData {
    int id;
    Date date;

    public AbstractConnectionDate(int id){
        this.id = id;
    }
    public AbstractConnectionDate(Date date){
        this.date = date;
        this.id = date.getId();
    }

    public abstract void load();
    public void unload(){
        date = null;
    }

    public boolean isUnloaded(){
        return date == null;
    }

    public boolean isLoaded(){
        return date != null;
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

    @Override
    public void setYear(int year) {
        if(isUnloaded()) load();
        date.setYear(year);
    }

    @Override
    public void setMonth(int month) {
        if(isUnloaded()) load();
        date.setMonth(month);
    }

    @Override
    public void setDay(int day) {
        if(isUnloaded()) load();
        date.setDay(day);
    }

    @Override
    public void setAccurate(boolean accurate) {
        if(isUnloaded()) load();
        date.setAccurate(accurate);
    }

    @Override
    public String toString(){
        return date.toString();
    }
}
