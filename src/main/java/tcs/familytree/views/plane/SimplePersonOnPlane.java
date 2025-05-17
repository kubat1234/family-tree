package tcs.familytree.views.plane;

import tcs.familytree.core.person.Person;

public class SimplePersonOnPlane implements PersonOnPlane {
    int x, y;
    Person person;

    public SimplePersonOnPlane(int x, int y, Person person) {
        this.x = x;
        this.y = y;
        this.person = person;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public Person person() {
        return person;
    }
}
