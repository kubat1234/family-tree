package tcs.familytree.core.date;

import java.util.List;

public interface Date /* extends Identifiable */ {
    // Interface to Date
    default List<Object> getAllData(){
        return List.of(getYear(),getMonth(),getDay(),isAccurate());
    }
    // getters
    Integer getYear();
    Integer getMonth();
    Integer getDay();
    boolean isAccurate();
    // TODO getDate() accepts nullables
    default String getDate(){
        if(getMonth() == null) {
            return String.valueOf(getYear());
        }
        if(getDay() == null) {
            return String.format("%02d", getMonth()) + "." + getYear();
        }
        return getDay() + "." + String.format("%02d", getMonth()) + "." + getYear();
    }
    default String getDateDescription(){
        return isAccurate() ? "" : "~" + getDate();
    }

    void setYear(Integer year);
    void setMonth(Integer month);
    void setDay(Integer day);
    void setAccurate(boolean accurate);
}
