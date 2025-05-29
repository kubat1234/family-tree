package tcs.familytree.core.date;

public interface DateBuilder {
    Date build();
    DateBuilder setDate(Date date);
    DateBuilder setId(Integer id);
    DateBuilder setYear(Integer year);
    DateBuilder setMonth(Integer month);
    DateBuilder setDay(Integer day);
    DateBuilder setAccurate(boolean accurate);
    DateBuilder setDate(Integer year, Integer month, Integer day);
    DateBuilder setDate(Integer year, Integer month, Integer day, boolean accurate);
}
