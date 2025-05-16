package tcs.familytree.jooq;

import static tcs.familytree.jooq.generated.Tables.*;

import org.jooq.impl.DSL.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;

public class Test {
    public static void main(String[] args) {
        String userName = "family_tree";
        String password = "family_tree";
        String url = "jdbc:postgresql://localhost:5432/family_tree";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<org.jooq.Record> result = create.select().from(OSOBY).fetch();

            for (org.jooq.Record r : result) {
                Integer id = r.getValue(OSOBY.ID);
                String firstName = r.getValue(OSOBY.IMIE);
                String lastName = r.getValue(OSOBY.NAZWISKO_RODOWE);

                System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
            }
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
