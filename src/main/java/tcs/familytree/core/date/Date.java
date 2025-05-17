package tcs.familytree.core.date;

import tcs.familytree.core.Identifiable;

import java.util.List;

public interface Date extends Identifiable {
    // Interface to Date
    default List<Object> getAllData(){
        return List.of(getId(),getYear(),getMonth(),getDay(),isAccurate());
    }
    // getters
    int getYear();
    int getMonth();
    int getDay();
    boolean isAccurate();
    // TODO getDate() accepts nullables
    default String getDate(){
        return getDay() + "." + getMonth() + "." + getYear();
    }
    default String getDateDescription(){
        return isAccurate() ? "" : "~" + getDate();
    }

    //void setId(int id); // TODO id final ??
    void setYear(int year);
    void setMonth(int month);
    void setDay(int day);
    void setAccurate(boolean accurate);
}
