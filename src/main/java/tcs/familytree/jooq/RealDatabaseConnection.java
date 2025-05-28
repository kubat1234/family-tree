package tcs.familytree.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import tcs.familytree.core.person.Gender;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.SimplePersonBuilder;
import tcs.familytree.services.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static tcs.familytree.jooq.generated.Tables.OSOBY;

public class RealDatabaseConnection implements DatabaseConnection {
    @Override
    public List<Person> getAllPersons() {
        try (Connection conn = DriverManager.getConnection(ConnectionConsts.url,
                ConnectionConsts.userName, ConnectionConsts.password)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = create.select().from(OSOBY).fetch();
            List<Person> personList = new ArrayList<>();
            for (org.jooq.Record r : result) {
                Integer id = r.getValue(OSOBY.ID);
                String firstName = r.getValue(OSOBY.IMIE);
                String lastName = r.getValue(OSOBY.NAZWISKO_RODOWE);
                personList.add(new SimplePersonBuilder(null).setId(id).setName(firstName)
                        .setSurnames(lastName==null?null:lastName.toUpperCase()).setGender(Gender.MALE).build());
            }
            return personList;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}