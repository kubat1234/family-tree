package tcs.familytree.views.painter;

import tcs.familytree.TmpUtil;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.views.plane.GraphOnPlane;
import tcs.familytree.views.plane.PersonOnPlane;
import tcs.familytree.views.plane.SimpleGraphOnPlane;
import tcs.familytree.views.plane.SimplePersonOnPlane;

import java.util.ArrayList;
import java.util.List;

public class SimplePainterBackEnd implements PainterBackEnd {

    FamilyGraph familyGraph;
    Person centralPerson;
    public SimplePainterBackEnd(FamilyGraph familyGraph, Person person) {
        if(familyGraph == null || person == null) {
            throw new NullPointerException();
        }
        this.familyGraph = familyGraph;
        this.centralPerson = person;
        if(!familyGraph.getAllPersons().contains(person)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public GraphOnPlane build() {
        List<PersonOnPlane> persons = new ArrayList<>();
        persons.add(new SimplePersonOnPlane(450, 250, centralPerson));
        for(Person p : familyGraph.getChildren(centralPerson)) {
            persons.add(new SimplePersonOnPlane(TmpUtil.rand(880) + 10,
                    TmpUtil.rand(10) + 395, p));
        }
        for(Person p : familyGraph.getParents(centralPerson)) {
            Person p2 = familyGraph.getAllPersons().stream().filter(q -> q.getId() == p.getId()).findFirst().get();
            persons.add(new SimplePersonOnPlane(TmpUtil.rand(880) + 10,
                    TmpUtil.rand(10) + 95, p2));
        }
        return new SimpleGraphOnPlane(persons);
    }
}
