package tcs.familytree.views.painter;

import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.views.plane.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

public class SimplePainterBackEnd {

    class OffsetPersonOnPlane{
        int offsetX, offsetY;
        int myWidth;
        Person person;
        List<OffsetPersonOnPlane> oPops = new ArrayList<>();
        List<SimpleLineOnPlane> lines = new ArrayList<>();
        OffsetPersonOnPlane(Person p, int x, int y) {
            person = p;
            offsetX = x;
            offsetY = y;
        }
    }

    FamilyGraph familyGraph;
    Person centralPerson;
    static final int max_depth = 20;
    static final int slot_width = 150;
    static final int slot_height = 100;
    List<OffsetPersonOnPlane> persons;
    List<LineOnPlane> lines;

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

    private void recursiveGraph(OffsetPersonOnPlane pop, int depth,
                                BiFunction<FamilyGraph, Person, Collection<Person>> fun,
                                BiFunction<Person, Person, Boolean> filter, int slot_height) {
        if(depth > max_depth) {
            return;
        }
        int childrenWidth = 0;
        List<OffsetPersonOnPlane> oPops = new ArrayList<>();
        for(Person p : fun.apply(familyGraph, pop.person)) {
            OffsetPersonOnPlane child = new OffsetPersonOnPlane(p, 0, slot_height);
            List<Person> partnersToDraw = child.person.getPartners()
                    .stream().filter(a -> filter.apply(pop.person, a)).toList();
            addPartners(child, partnersToDraw);
            int partnersSpace = partnersToDraw.size() * slot_width / 2;
            child.offsetX -= partnersSpace;
            child.myWidth = slot_width * (1 + partnersToDraw.size());;
            oPops.add(child);
            persons.add(child);
            recursiveGraph(child, depth+1, fun, filter, slot_height);
            childrenWidth += child.myWidth;
        }
        int newWidth = Math.max(pop.myWidth, childrenWidth);
        System.out.println(pop.person.getName() + ": " + pop.myWidth+ "; " + childrenWidth);
        childrenWidth = -childrenWidth/2 + (pop.myWidth - slot_width) / 2;
        for(OffsetPersonOnPlane child : oPops) {
            child.offsetX += childrenWidth + child.myWidth/2;
            childrenWidth += child.myWidth;
            SimpleLineOnPlane line = new SimpleLineOnPlane(0, 0, child.offsetX, child.offsetY);
            pop.lines.add(line);
            lines.add(line);
        }
        pop.oPops.addAll(oPops);
        pop.myWidth = newWidth;
    }

    private void recalculateOffsets(OffsetPersonOnPlane pop, int x, int y) {
        x += pop.offsetX;
        y += pop.offsetY;
        pop.offsetX = x;
        pop.offsetY = y;
        for(OffsetPersonOnPlane child : pop.oPops) {
            recalculateOffsets(child, x, y);
        }
        for(SimpleLineOnPlane line : pop.lines) {
            line.moveBy(x, y);
        }
    }

    private void addPartners(OffsetPersonOnPlane pop, List<Person> partnersToDraw) {
        for(int i=0; i<partnersToDraw.size(); i++) {
            Person p = partnersToDraw.get(i);
            OffsetPersonOnPlane partner = new OffsetPersonOnPlane(p, (i+1) * slot_width, 0);
            pop.oPops.add(partner);
            persons.add(partner);
            SimpleLineOnPlane line = new SimpleLineOnPlane(0, 0, partner.offsetX, partner.offsetY);
            pop.lines.add(line);
            lines.add(line);
        }

    }

    public GraphOnPlane build() {
        persons = new ArrayList<>();
        lines = new ArrayList<>();
        OffsetPersonOnPlane centralOnPlane = new OffsetPersonOnPlane(centralPerson, 0, 0);
        persons.add(centralOnPlane);
        centralOnPlane.myWidth = slot_width;
        addPartners(centralOnPlane, centralPerson.getPartners());
        recursiveGraph(centralOnPlane, 0, FamilyGraph::getChildren, (a, b) -> true , slot_height);
        recursiveGraph(centralOnPlane, 0, FamilyGraph::getParents, (a, b) -> !a.getParents().contains(b), -slot_height);
        recalculateOffsets(centralOnPlane, 450, 250);
        List<PersonOnPlane> pops = persons.stream().map(
                pop -> (PersonOnPlane)new SimplePersonOnPlane(pop.offsetX, pop.offsetY, pop.person)).toList();
        for(PersonOnPlane p : pops) {
            for(PersonOnPlane q : pops) {
                if(p == q) { //it's fine, they're the same object
                    break;
                }
                if(p.person().getPartners().contains(q.person())) {
                    lines.add(new SimpleLineOnPlane(p, q));
                }
            }
        }
        return new SimpleGraphWithLineOnPlane(pops, lines);
    }
}
