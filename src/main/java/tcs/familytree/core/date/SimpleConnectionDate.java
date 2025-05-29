package tcs.familytree.core.date;

import tcs.familytree.core.AbstractConnectionData;
import tcs.familytree.core.Identifiable;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.DatabaseError;

import java.util.List;

public class SimpleConnectionDate extends AbstractConnectionData<Date> implements Date {
    public SimpleConnectionDate(Integer id, DatabaseConnection connection){
        super(id,connection);
        connection.getUpdater().registerDate(this);
    }
    public SimpleConnectionDate(Date date, DatabaseConnection connection){
        super(date,connection);
        connection.getUpdater().registerDate(this);
    }

    @Override
    public void load() {
        if(isUnloaded()){
            if(!connection.checkIfDateExist(id)){
                throw new DatabaseError("Date with id: " + id + "cannot load from database: " + connection + ".");
            }
            data = connection.getDate(id);
        }
    }

    @Override
    public List<Object> getAllData() {
        if(isUnloaded()) load();
        return data.getAllData();
    }

    @Override
    public boolean isAccurate() {
        if(isUnloaded()) load();
        return data.isAccurate();
    }

    @Override
    public Integer getDay() {
        if(isUnloaded()) load();
        return data.getDay();
    }

    @Override
    public Integer getMonth() {
        if(isUnloaded()) load();
        return data.getMonth();
    }

    @Override
    public Integer getYear() {
        if(isUnloaded()) load();
        return data.getYear();
    }

    @Override
    public String getDateDescription() {
        if(isUnloaded()) load();
        return data.getDateDescription();
    }

    @Override
    public void setYear(Integer year) {
        if(isUnloaded()) load();
        data.setYear(year);
        if(!connection.updateDate(data)){
            unload();
            throw new DatabaseError("Date with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setMonth(Integer month) {
        if(isUnloaded()) load();
        data.setMonth(month);
        if(!connection.updateDate(data)){
            unload();
            throw new DatabaseError("Date with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setDay(Integer day) {
        if(isUnloaded()) load();
        data.setDay(day);
        if(!connection.updateDate(data)){
            unload();
            throw new DatabaseError("Date with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public void setAccurate(boolean accurate) {
        if(isUnloaded()) load();
        data.setAccurate(accurate);
        if(!connection.updateDate(data)){
            unload();
            throw new DatabaseError("Date with id: " + id + "cannot update in database: " + connection + ".");
        }
    }

    @Override
    public String toString(){
        if(isUnloaded()) load();
        return data.toString();
    }

    @Override
    public Class<? extends Identifiable> getDataClass(){
        return Date.class;
    }
}
