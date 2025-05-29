package tcs.familytree.jooq;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.date.DateBuilder;
import tcs.familytree.core.date.SimpleConnectionDateBuilder;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.PersonBuilder;
import tcs.familytree.core.person.SimpleConnectionPersonBuilder;
import tcs.familytree.jooq.generated.tables.records.DatyRecord;
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
    public Person toPerson(OsobyRecord record){
        if(record == null)return null;
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
    public OsobyRecord toOsobyRecord(Person person){
        if(person == null)throw new NullPointerException("Person can't be null");
        OsobyRecord record = new OsobyRecord();
        record.setId(person.getId());
        record.setImie(person.getName());
        record.setNazwiskoRodowe(person.getFamilySurname());
        record.setPozostaleImiona(person.getAllSurnames() == null ? null : String.join(" ",person.getAllSurnames()));
        record.setMiejsceUr(person.getPlaceOfBirth() == null ? null : person.getPlaceOfBirth().getId());
        record.setDataUr(person.getDateOfBirth() == null ? null : person.getDateOfBirth().getId());
        record.setMiejsceSm(person.getPlaceOfDeath() == null ? null : person.getPlaceOfDeath().getId());
        record.setDataSm(person.getDateOfDeath() == null ? null : person.getDateOfDeath().getId());
        record.setPlec(person.getGender().toBoolean());
        record.setWciazZyje(person.isAlive());
        return record;
    }
    public Date toDate(org.jooq.Record record) {
        if(!(record instanceof DatyRecord) )throw new IllegalArgumentException("Record must be instance of DatyRecord");
        DateBuilder builder = new SimpleConnectionDateBuilder(connection);

        builder.setId(record.getValue(DATY.ID));
        builder.setDay(record.getValue(DATY.DZIEN));
        builder.setMonth(record.getValue(DATY.MIESIAC));
        builder.setYear(record.getValue(DATY.ROK));
        builder.setAccurate(record.getValue(DATY.CZY_DOKLADNA));

        return builder.build();
    }
}
