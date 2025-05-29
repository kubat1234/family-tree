package tcs.familytree.services;

import tcs.familytree.core.person.Person;
import tcs.familytree.jooq.RealDatabaseConnection;
import tcs.familytree.services.database.DatabaseConnection;

public class DatabaseTest{
    public static void main(String[] args) {
        DatabaseConnection connection = new RealDatabaseConnection();
        for(Person p : connection.getAllPersons()) {
            System.out.println(p + " | " + p.getMother() + " | " + p.getFather());
            System.out.println(connection.getChildren(p));
        }
    }
}
