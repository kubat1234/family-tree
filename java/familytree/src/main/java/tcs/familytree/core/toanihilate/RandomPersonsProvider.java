package tcs.familytree.core.toanihilate;

import tcs.familytree.core.NotImplemented;
import tcs.familytree.core.person.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * It exists only in case someone breaks something, and we need just anything running.
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
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name.get();
        }

        @Override
        public String getSurname() {
            return surname.get();
        }

        @Override
        public Person getMother() {
            return mother;
        }

        @Override
        public Person getFather() {
            return father;
        }

        @Override
        public Person copy() {
            throw new NotImplemented();
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof Person p))
                return false;
            return id == p.getId();
        }

        @Override
        public String toString() {
            return name.get() + " " + surname.get();
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
