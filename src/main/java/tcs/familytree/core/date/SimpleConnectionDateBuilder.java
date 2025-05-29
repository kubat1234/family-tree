package tcs.familytree.core.date;

import tcs.familytree.services.database.DatabaseConnection;


public class SimpleConnectionDateBuilder implements DateBuilder{

    DatabaseConnection connection;
    DateBuilder builder;

    public SimpleConnectionDateBuilder(DatabaseConnection connection) {
        this.connection = connection;
        this.builder = new SimpleDateBuilder();
    }

    SimpleConnectionDateBuilder(DatabaseConnection connection, Date date) {
        this.connection = connection;
        this.builder = new SimpleDateBuilder(date);
    }

    SimpleConnectionDateBuilder(DatabaseConnection connection, DateBuilder builder) {
        this.connection = connection;
        this.builder = builder;
    }

    @Override
    public Date build() {
        return new SimpleConnectionDate(builder.build(), connection);
    }

    @Override
    public DateBuilder setDate(Date date) {
        builder.setDate(date);
        return this;
    }

    @Override
    public DateBuilder setId(Integer id) {
        builder.setId(id);
        return this;
    }

    @Override
    public DateBuilder setYear(Integer year) {
        builder.setYear(year);
        return this;
    }

    @Override
    public DateBuilder setMonth(Integer month) {
        builder.setMonth(month);
        return this;
    }

    @Override
    public DateBuilder setDay(Integer day) {
        builder.setDay(day);
        return this;
    }

    @Override
    public DateBuilder setAccurate(boolean accurate) {
        builder.setAccurate(accurate);
        return this;
    }

    @Override
    public DateBuilder setDate(Integer year, Integer month, Integer day) {
        builder.setDate(year, month, day);
        return this;
    }

    @Override
    public DateBuilder setDate(Integer year, Integer month, Integer day, boolean accurate) {
        builder.setDate(year, month, day, accurate);
        return this;
    }
}
