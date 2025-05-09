package tcs.familytree.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Temporary class, feel free to refactor other classes and remove it.
 */
public class RandomPersonsProvider {
    static class MyPerson implements Person {
        private final int id;
        private final DataString name;
        private final DataString surname;
        private final Person mother;
        private final Person father;

        public MyPerson(int id, String name, String surname, Person mother, Person father) {
            this.id = id;
            this.name = new DataString(name);
            this.surname = new DataString(surname);
            this.mother = mother;
            this.father = father;
        }

        public MyPerson(int id, String name, String surname) {
            this(id, name, surname, null, null);
        }

        @Override
        public Person copy() {
            throw new NotImplemented();
           // return null;
        }

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public Data getName() {
            return name;
        }

        @Override
        public Data getSurname() {
            return surname;
        }

        @Override
        public Person getMother() {
            return mother;
        }

        @Override
        public Person getFather() {
            return father;
        }
    }

    public List<Person> getStaticData() {
        List<Person> randomData = new ArrayList<>();
        randomData.add(new MyPerson(1, "Jan", "Kowalski"));
        randomData.add(new MyPerson(2, "Karolina", "Nowak", null, randomData.getFirst()));
        randomData.add(new MyPerson(3, "Łukasz", "Nowak"));
        randomData.add(new MyPerson(4, "Marzena", "Nowak", randomData.get(1), randomData.get(2)));
        randomData.add(new MyPerson(5, "Norbert", "Nowak", randomData.get(1), randomData.get(2)));
        randomData.add(new MyPerson(6, "Oliwia", "Nowak"));
        randomData.add(new MyPerson(7, "Piotr", "Kowalski", null, randomData.getFirst()));
        return randomData;
    }
}
