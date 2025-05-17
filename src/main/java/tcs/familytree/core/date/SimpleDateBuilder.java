package tcs.familytree.core.date;

public class SimpleDateBuilder implements DateBuilder{
    int id;
    int year;
    int month;
    int day;
    boolean accurate;
    public SimpleDateBuilder(Date date){
        setDate(date);
    }
    @Override
    public Date build() {
        return new SimpleDate(id, year, month, day, accurate);
    }
    @Override
    public DateBuilder setDate(Date date) {
        this.id = date.getId();
        this.year = date.getYear();
        this.month = date.getMonth();
        this.day = date.getDay();
        this.accurate = date.isAccurate();
        return this;
    }

    @Override
    public DateBuilder setId(int id) {
        this.id = id;
        return this;
    }
    @Override
    public DateBuilder setYear(int year) {
        this.year = year;
        return this;
    }
    @Override
    public DateBuilder setMonth(int month) {
        this.month = month;
        return this;
    }
    @Override
    public DateBuilder setDay(int day) {
        this.day = day;
        return this;
    }
    @Override
    public DateBuilder setAccurate(boolean accurate) {
        this.accurate = accurate;
        return this;
    }
    @Override
    public DateBuilder setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        return this;
    }
    @Override
    public DateBuilder setDate(int year, int month, int day, boolean accurate) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.accurate = accurate;
        return this;
    }
}
