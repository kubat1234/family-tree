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
    int max_depth = 20;
    int slot_size = 200;
    int depth_size = 150;
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

    private int recursiveGraphDown(List<PersonOnPlane> list, Person person, int x, int y, int depth){
        System.out.println(person.toString());
        if(depth > max_depth){
            return x;
        }
        int old_x;
        int old_y;
      //  int slots = familyGraph.getWidthDown(person.getId());

        list.add(new SimplePersonOnPlane(x, y + depth * depth_size, person));
        boolean alone = true;
        for(Person p: familyGraph.getChildren(person)){
            x = recursiveGraphDown(list, p, x, y, depth + 1);
            alone = false;
        }
        if(alone){
            x += slot_size;
        }
        return x;
    }

    @Override
    public GraphOnPlane build() {
        List<PersonOnPlane> persons = new ArrayList<>();

        int x = 450, y = 250;
        persons.add(new SimplePersonOnPlane(x, y, centralPerson));


        System.out.println("SimplePainterBackEnd - x");
        recursiveGraphDown(persons, centralPerson, x, y, 0);

//        for(Person p : familyGraph.getChildren(centralPerson)) {
//            persons.add(new SimplePersonOnPlane(TmpUtil.rand(880) + 10,
//                    TmpUtil.rand(10) + 395, p));
//        }
//        for(Person p : familyGraph.getParents(centralPerson)) {
//            Person p2 = familyGraph.getAllPersons().stream().filter(q -> q.getId() == p.getId()).findFirst().get();
//            persons.add(new SimplePersonOnPlane(TmpUtil.rand(880) + 10,
//                    TmpUtil.rand(10) + 95, p2));
//        }


        return new SimpleGraphOnPlane(persons);
    }
}
