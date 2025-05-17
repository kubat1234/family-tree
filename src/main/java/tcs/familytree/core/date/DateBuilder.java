package tcs.familytree.core.date;

public interface DateBuilder {
    Date build();
    DateBuilder setDate(Date date);
    DateBuilder setId(int id);
    DateBuilder setYear(int year);
    DateBuilder setMonth(int month);
    DateBuilder setDay(int day);
    DateBuilder setAccurate(boolean accurate);
    DateBuilder setDate(int year, int month, int day);
    DateBuilder setDate(int year, int month, int day, boolean accurate);
}
