package tcs.familytree.services.database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import tcs.familytree.core.Updater;
import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.jooq.generated.tables.records.OsobyRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static tcs.familytree.jooq.generated.Tables.DATY;
import static tcs.familytree.jooq.generated.Tables.OSOBY;

public class RealDatabaseConnection implements DatabaseConnection {
    DatabaseConverter databaseConverter;
    DSLContext dsl;
    Updater updater = new Updater();

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

    @Override
    public Updater getUpdater() {
        return updater;
    }

    // Person methods

    @Override
    public List<Person> getAllPersons() {
        return dsl.select().from(OSOBY).fetchInto(OsobyRecord.class).stream().map(databaseConverter::toPerson).toList();
    }

    @Override
    public Person getPerson(int id){
        return databaseConverter.toPerson(dsl.select().from(OSOBY).where(OSOBY.ID.eq(id)).fetchOneInto(OsobyRecord.class));
    }

    @Override
    public List<Person> getChildren(int id){
        return dsl.select().from(OSOBY).where(OSOBY.MATKA.eq(id).or(OSOBY.OJCIEC.eq(id))).fetchInto(OsobyRecord.class).stream().map(databaseConverter::toPerson).toList();
    }

    @Override
    public List<Person> getChildren(Person person){
        return getChildren(person.getId());
    }

    @Override
    public boolean updatePerson(Person person) {
        try {
//            dsl.update(OSOBY).set(OSOBY.IMIE, person.getName()).where(OSOBY.ID.eq(person.getId())).execute();
            OsobyRecord record = databaseConverter.toOsobyRecord(person);
            record.attach(dsl.configuration());
            record.update();
            updater.update(person);
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas aktualizacji osoby o id: " + person.getId() + " " + e.getMessage());
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