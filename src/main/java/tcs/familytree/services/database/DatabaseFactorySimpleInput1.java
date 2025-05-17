package tcs.familytree.services.database;

import tcs.familytree.core.person.Gender;
import tcs.familytree.core.person.PersonBuilder;
import tcs.familytree.core.person.SimplePersonBuilder;

public class DatabaseFactorySimpleInput1 extends DatabaseFactorySimple{

    public DatabaseFactorySimpleInput1(){
        // TODO
        PersonBuilder pb = new SimplePersonBuilder(null);
        pb.setId(1).setName("Stefania").setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(2).setName("Jan").setSurnames("Nowak").setMother(1).setAlive(false).setGender(Gender.MALE);
        personList.add(pb.build());
        pb.setId(3).setName("Genowefa").setSurnames("Wilkoryj").setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(4).setName("Stanisław").setSurnames("Nowak").setFather(2).setMother(3).setAlive(false).setGender(Gender.MALE);
        personList.add(pb.build());
        pb.setId(5).setName("Dorota").setSurnames("Nowak").setFather(2).setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(6).setName("Krzysztof").setSurnames("Kowal").setFather(7).setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(7).setName("Adam").setSurnames("Kowal").setFather(6).setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(8).setName("Antoni").setSurnames("Kowal").setFather(6).setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(9).setName("Konrad").setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(10).setName("Martyna").setSurnames("Wielka").setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(11).setName("Jakub").setSurnames("Kowal").setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(12).setName("Maria").setSurnames("Kowal").setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        // TODO on person Factory
    }

    public DatabaseFactorySimpleInput1(DatabaseConnection databaseConnection){
        // TODO
        PersonBuilder pb = new SimplePersonBuilder(databaseConnection);
        pb.setId(1).setName("Stefania").setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(2).setName("Jan").setSurnames("Nowak").setMother(1).setAlive(false).setGender(Gender.MALE);
        personList.add(pb.build());
        pb.setId(3).setName("Genowefa").setSurnames("Wilkoryj").setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(4).setName("Stanisław").setSurnames("Nowak").setFather(2).setMother(3).setAlive(false).setGender(Gender.MALE);
        personList.add(pb.build());
        pb.setId(5).setName("Dorota").setSurnames("Nowak").setFather(2).setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(6).setName("Krzysztof").setSurnames("Kowal").setFather(7).setMother(1).setAlive(false).setGender(Gender.MALE);
        personList.add(pb.build());
        pb.setId(7).setName("Adam").setSurnames("Kowal").setFather(6).setMother(1).setAlive(false).setGender(Gender.MALE);
        personList.add(pb.build());
        pb.setId(8).setName("Antoni").setSurnames("Kowal").setFather(6).setMother(1).setAlive(false).setGender(Gender.MALE);
        personList.add(pb.build());
        pb.setId(9).setName("Konrad").setMother(1).setAlive(false).setGender(Gender.MALE);
        personList.add(pb.build());
        pb.setId(10).setName("Martyna").setSurnames("Wielka").setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        pb.setId(11).setName("Jakub").setSurnames("Kowal").setMother(1).setAlive(false).setGender(Gender.MALE);
        personList.add(pb.build());
        pb.setId(12).setName("Maria").setSurnames("Kowal").setMother(1).setAlive(false).setGender(Gender.FEMALE);
        personList.add(pb.build());
        // TODO on person Factory
    }


}
