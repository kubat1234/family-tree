package tcs.familytree.core.date;

public class SimpleDate implements Date {
    Integer year;
    Integer month;
    Integer day;
    boolean accurate;

    public SimpleDate(Integer year, Integer month, Integer day, boolean accurate){
        this.year = year;
        this.month = month;
        this.day = day;
        this.accurate = accurate;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    @Override
    public Integer getMonth() {
        return month;
    }

    @Override
    public Integer getDay() {
        return day;
    }

    @Override
    public boolean isAccurate() {
        return accurate;
    }

    @Override
    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public void setAccurate(boolean accurate) {
        this.accurate = accurate;
    }

    @Override
    public String toString(){
        return getDateDescription();
    }
}
