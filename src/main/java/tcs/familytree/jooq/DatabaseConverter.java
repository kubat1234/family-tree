package tcs.familytree.jooq;

import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonBuilder;
import tcs.familytree.core.person.SimpleConnectionPersonBuilder;
import tcs.familytree.jooq.generated.tables.records.OsobyRecord;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.core.person.Gender;

import static tcs.familytree.jooq.generated.Tables.*;

public class DatabaseConverter {
    DatabaseConnection connection;

    public DatabaseConverter(DatabaseConnection connection){
        if(connection == null)throw new NullPointerException("Connection can't be null");
        this.connection = connection;
    }
    public Person toPerson(org.jooq.Record record){
        if(!(record instanceof OsobyRecord))throw new IllegalArgumentException("Record must be instance of OsobyRecord");
        PersonBuilder builder = new SimpleConnectionPersonBuilder(connection);
        builder.setId(record.getValue(OSOBY.ID));
        builder.setName(record.getValue(OSOBY.IMIE));
        builder.setSurnames(record.getValue(OSOBY.NAZWISKO_RODOWE));
        builder.setNames(record.getValue(OSOBY.POZOSTALE_IMIONA) == null ? null : record.getValue(OSOBY.POZOSTALE_IMIONA).split(" "));
        builder.setFamilyName(record.getValue(OSOBY.NAZWISKO_RODOWE));
//        builder.setSurnames(record.getValue(OSOBY.)) TODO nazwiska
        builder.setMother(record.getValue(OSOBY.MATKA));
        builder.setFather(record.getValue(OSOBY.OJCIEC));
        builder.setAlive(record.getValue(OSOBY.WCIAZ_ZYJE));
        builder.setPlaceOfBirth(record.getValue(OSOBY.MIEJSCE_UR));
        builder.setPlaceOfDeath(record.getValue(OSOBY.MIEJSCE_SM));
        builder.setDateOfBirth(record.getValue(OSOBY.DATA_UR));
        builder.setDateOfDeath(record.getValue(OSOBY.DATA_SM));
        builder.setGender(Gender.fromBoolean(record.getValue(OSOBY.PLEC)));

        return builder.build();
    }
}
