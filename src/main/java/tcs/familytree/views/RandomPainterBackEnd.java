package tcs.familytree.views;

import tcs.familytree.TmpUtil;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.views.plane.GraphOnPlane;
import tcs.familytree.views.plane.PersonOnPlane;
import tcs.familytree.views.plane.SimpleGraphOnPlane;
import tcs.familytree.views.plane.SimplePersonOnPlane;

import java.util.ArrayList;
import java.util.List;

public class RandomPainterBackEnd implements PainterBackEnd {

    FamilyGraph familyGraph;
    RandomPainterBackEnd(FamilyGraph familyGraph) {
        this.familyGraph = familyGraph;
    }


    @Override
    public GraphOnPlane build() {
        List<PersonOnPlane> persons = new ArrayList<>();
        for(Person p : familyGraph.getAllPersons()) {
            persons.add(new SimplePersonOnPlane(TmpUtil.rand(900) + 10,
                    TmpUtil.rand(500) + 10, p));
        }
        return new SimpleGraphOnPlane(persons);
    }
}
