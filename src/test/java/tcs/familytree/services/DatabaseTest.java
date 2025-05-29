package tcs.familytree.services;

import org.jooq.meta.derby.sys.Sys;
import org.junit.jupiter.api.Test;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.jooq.RealDatabaseConnection;
import tcs.familytree.services.database.DatabaseConnection;

public class DatabaseTest{
    @Test
    public void test1() {
        DatabaseConnection connection = new RealDatabaseConnection();
        System.out.println(connection.getAllPersons());
        for(Person p : connection.getAllPersons()) {
            System.out.println(p + " | " + p.getMother() + " | " + p.getFather());
            System.out.println(connection.getChildren(p));
        }
    }

    @Test
    public void DateTest() {
        DatabaseConnection connection = new RealDatabaseConnection();
        Date d = connection.getDate(8);
        System.out.println(d.toString());
    }
}
