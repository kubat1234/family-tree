package tcs.familytree.services.database;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import tcs.familytree.core.NotImplemented;
import tcs.familytree.core.Updater;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonBuilder;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.place.PlaceType;
import tcs.familytree.core.relation.Relation;
import tcs.familytree.jooq.generated.tables.records.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static tcs.familytree.jooq.generated.Tables.*;

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
    public Result<org.jooq.Record> sendQuery(String query){
        return dsl.fetch(query);
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    // Person methods

    @Override
    public List<Person> getAllPersons() {
        return dsl.select().from(OSOBY_NAZWISKA).fetchInto(OsobyNazwiskaRecord.class).stream().map(
                x -> addPartners(databaseConverter.toPersonBuilder(x), x.getValue(OSOBY_NAZWISKA.ID)).build()
        ).toList();
    }

    @Override
    public Person getPerson(int id) {
        return addPartners(databaseConverter.toPersonBuilder(
                dsl.select().from(OSOBY_NAZWISKA).where(OSOBY_NAZWISKA.ID.eq(id)).fetchOneInto(OsobyNazwiskaRecord.class)),
                id
        ).build();
    }

    private PersonBuilder addPartners(PersonBuilder builder, int id) {
        dsl.select().from(RELACJE_SYMETRYCZNE).
                where(RELACJE_SYMETRYCZNE.OSOBA1.eq(id)).or(RELACJE_SYMETRYCZNE.OSOBA2.eq(id)).
                fetchInto(RelacjeSymetryczneRecord.class).forEach(r -> builder.addPartner(
                        r.getValue(RELACJE_SYMETRYCZNE.OSOBA1).equals(id)?
                        r.getValue(RELACJE_SYMETRYCZNE.OSOBA2):
                        r.getValue(RELACJE_SYMETRYCZNE.OSOBA1)
                ));
        return builder;
    }

    @Override
    public boolean updatePerson(Person person) {
        try {
            OsobyRecord record = databaseConverter.toOsobyRecord(person);
            record.attach(dsl.configuration());
            record.update();
            updater.updatePerson(person);
            dsl.update(OSOBY_NAZWISKA).set(OSOBY_NAZWISKA.NAZWISKA, String.join(" ",person.getAllSurnames())).where(OSOBY_NAZWISKA.ID.eq(person.getId())).execute();
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas aktualizacji osoby o id: " + person.getId() + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addPerson(Person person) {
        try {
            OsobyRecord record = databaseConverter.toOsobyRecord(person);
            record.attach(dsl.configuration());
            record.store();
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas dodania osoby o id: " + person.getId() + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public Person createNewPerson(){
        return databaseConverter.toPerson(dsl.insertInto(OSOBY).defaultValues().returning().fetchOne());
    }

    @Override
    public boolean deletePerson(int id) {
        try {
            dsl.delete(OSOBY).where(OSOBY.ID.eq(id)).execute();
            updater.updatePerson(id);
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas dodania osoby o id: " + id + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Person> getChildren(int id){
        return dsl.select().from(OSOBY).where(OSOBY.MATKA.eq(id).or(OSOBY.OJCIEC.eq(id))).
                fetchInto(OsobyRecord.class).stream().map(databaseConverter::toPerson).toList();
    }

    @Override
    public List<Person> getChildren(Person person){
        return getChildren(person.getId());
    }

    //Place methods

    @Override
    public List<Place> getAllPlaces() {
        return dsl.select().from(MIEJSCA).fetchInto(MiejscaRecord.class).stream().map(databaseConverter::toPlace).toList();
    }

    @Override
    public Place getPlace(int id) {
        return databaseConverter.toPlace(dsl.select().from(MIEJSCA).where(MIEJSCA.ID.eq(id)).fetchOneInto(MiejscaRecord.class));
    }

    @Override
    public boolean updatePlace(Place place) {
        try {
            MiejscaRecord record = databaseConverter.toMiejscaRecord(place);
            record.attach(dsl.configuration());
            record.update();
            updater.updatePlace(place);
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas aktualizacji miejsca o id: " + place.getId() + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addPlace(Place place) {
        try {
            MiejscaRecord record = databaseConverter.toMiejscaRecord(place);
            record.attach(dsl.configuration());
            record.store();
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas dodania miejsca o id: " + place.getId() + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePlace(int id) {
        try {
            dsl.delete(MIEJSCA).where(MIEJSCA.ID.eq(id)).execute();
            updater.updatePlace(id);
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas dodania miejsca o id: " + id + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public PlaceType getPlaceType(int id) {
        return databaseConverter.toPlaceType(dsl.select().from(TYPY_MIEJSC).where(TYPY_MIEJSC.ID.eq(id)).fetchOneInto(TypyMiejscRecord.class));
    }

    @Override
    public List<PlaceType> getAllPlaceType() {
        return dsl.select().from(TYPY_MIEJSC).fetchInto(TypyMiejscRecord.class).stream().map(databaseConverter::toPlaceType).toList();
    }

    @Override
    public boolean updatePlaceType(PlaceType place) {
        try {
            TypyMiejscRecord record = databaseConverter.toTypyMiejscRecord(place);
            record.attach(dsl.configuration());
            record.update();
            updater.updatePlaceType(place);
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas aktualizacji miejsca o id: " + place.getId() + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addPlaceType(PlaceType place) {
        try {
            TypyMiejscRecord record = databaseConverter.toTypyMiejscRecord(place);
            record.attach(dsl.configuration());
            record.store();
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas dodania miejsca o id: " + place.getId() + " " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePlaceType(int id) {
        try {
            dsl.delete(TYPY_MIEJSC).where(TYPY_MIEJSC.ID.eq(id)).execute();
            updater.updatePlaceType(id);
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas usuwania typu miejsc o id: " + id + " " + e.getMessage());
            return false;
        }
    }


    //Place methods

    @Override
    public List<Relation> getAllRelations() {
        return dsl.select().from(RELACJE_SYMETRYCZNE).fetch().stream().map(databaseConverter::toRelation).toList();
    }

    @Override
    public Relation getRelation(int id) {
        throw new NotImplemented();
//        return databaseConverter.toRelation(dsl.select().from(MIEJSCA).where(MIEJSCA.ID.eq(id)).fetchOne());
    }

    @Override
    public boolean updateRelation(Relation relation) {
        throw new NotImplemented();
    }

    @Override
    public boolean addRelation(Relation relation) {
        throw new NotImplemented();
    }

    @Override
    public boolean deleteRelation(int id) {
        throw new NotImplemented();
    }
}