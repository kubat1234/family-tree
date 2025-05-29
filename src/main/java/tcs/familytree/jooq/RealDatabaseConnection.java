package tcs.familytree.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import tcs.familytree.core.NotImplemented;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Gender;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.SimplePersonBuilder;
import tcs.familytree.services.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static tcs.familytree.jooq.generated.Tables.DATY;
import static tcs.familytree.jooq.generated.Tables.OSOBY;

public class RealDatabaseConnection implements DatabaseConnection {
    DatabaseConverter databaseConverter;
    DSLContext dsl;

    public RealDatabaseConnection(){
        databaseConverter = new DatabaseConverter(this);
        try {
            System.setProperty("org.jooq.no-logo", "true");
            System.setProperty("org.jooq.no-tips", "true");
            System.setProperty("org.jooq.no-banner", "true");
            Connection conn = DriverManager.getConnection(ConnectionConsts.url, ConnectionConsts.userName, ConnectionConsts.password);
            Settings settings = new Settings()
                    .withExecuteLogging(false);
            dsl = DSL.using(conn, SQLDialect.POSTGRES, settings);
        } catch (SQLException e) {
            throw new RuntimeException("Nie można połączyć się z bazą danych", e);
        }
    }

    // Person methods
    @Override
    public List<Person> getAllPersons() {
        return dsl.select().from(OSOBY).fetch().stream().map(databaseConverter::toPerson).toList();
    }

    @Override
    public Person getPerson(int id){
        return databaseConverter.toPerson(dsl.select().from(OSOBY).where(OSOBY.ID.eq(id)).fetchOne());
    }

    @Override
    public List<Person> getChildren(int id){
        return dsl.select().from(OSOBY).where(OSOBY.MATKA.eq(id).or(OSOBY.OJCIEC.eq(id))).fetch().stream().map(databaseConverter::toPerson).toList();
    }

    @Override
    public List<Person> getChildren(Person person){
        return getChildren(person.getId());
    }

    @Override
    public boolean updatePerson(Person person) {
        try {
            dsl.update(OSOBY).set(OSOBY.IMIE, person.getName()).where(OSOBY.ID.eq(person.getId())).execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Date methods
    @Override
    public Date getDate(int id) {
        return databaseConverter.toDate(dsl.select().from(DATY).where(DATY.ID.eq(id)).fetchOne());
    }

    @Override
    public boolean checkIfDateExist(int id) {
        return dsl.select().from(DATY).where(DATY.ID.eq(id)).fetchOne() != null;
    }
}