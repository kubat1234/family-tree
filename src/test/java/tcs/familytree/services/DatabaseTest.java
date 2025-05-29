package tcs.familytree.services;

import org.jooq.meta.derby.sys.Sys;
import org.junit.jupiter.api.Test;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.SimplePerson;
import tcs.familytree.core.person.SimplePersonBuilder;
import tcs.familytree.jooq.RealDatabaseConnection;
import tcs.familytree.services.database.DatabaseConnection;

public class DatabaseTest{
    @Test
    public void test1() {
        DatabaseConnection connection = new RealDatabaseConnection();
        Person person = connection.getPerson(1);
        System.out.println(person + " to " + "Stefania11");
        Person newPerson = new SimplePersonBuilder(connection, person).setName("Stefania11").build();
        connection.updatePerson(newPerson);
        System.out.println(person.getName());
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
