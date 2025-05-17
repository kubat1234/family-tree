package tcs.familytree.core.date;

public class SimpleDate implements Date {
    int id;
    int year;
    int month;
    int day;
    boolean accurate;

    public SimpleDate(int id, int year, int month, int day, boolean accurate){
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.accurate = accurate;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public boolean isAccurate() {
        return false;
    }

    @Override
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public void setAccurate(boolean accurate) {
        this.accurate = accurate;
    }

    @Override
    public String toString(){
        return getDate();
    }
}
