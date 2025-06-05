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
                                BiFunction<FamilyGraph, Person, Collection<Person>> fun, int slot_height) {
        if(depth > max_depth) {
            return;
        }
        int childrenWidth = 0;
        boolean firstRun = pop.myWidth == 0;
        List<OffsetPersonOnPlane> oPops = new ArrayList<>();
        pop.myWidth = slot_width * (1 + pop.person.getPartners().size());
        int partnersSpace = pop.person.getPartners().size() * slot_width / 2;
        if(firstRun) {
            System.out.println("Moving " + pop.person.getName() + " by " + partnersSpace);
            System.out.println("Size: " + pop.myWidth);
            pop.offsetX -= partnersSpace;
        }
        for(Person p : fun.apply(familyGraph, pop.person)) {
            OffsetPersonOnPlane child = new OffsetPersonOnPlane(p, 0, slot_height);
            oPops.add(child);
            persons.add(child);
            recursiveGraph(child, depth+1, fun, slot_height);
            childrenWidth += child.myWidth;
        }
        pop.myWidth = Math.max(pop.myWidth, childrenWidth);
        childrenWidth = -childrenWidth/2 + partnersSpace;
        for(OffsetPersonOnPlane child : oPops) {
            child.offsetX += childrenWidth + child.myWidth/2;
            childrenWidth += child.myWidth;
            SimpleLineOnPlane line = new SimpleLineOnPlane(0, 0, child.offsetX, child.offsetY);
            pop.lines.add(line);
            lines.add(line);
        }
        if(firstRun) {
            List<Person> list = pop.person.getPartners().stream().toList();
            for(int i=0; i<pop.person.getPartners().size(); i++) {
                Person p = list.get(i);
                OffsetPersonOnPlane partner = new OffsetPersonOnPlane(p, (i+1) * slot_width, 0);
                oPops.add(partner);
                persons.add(partner);
                SimpleLineOnPlane line = new SimpleLineOnPlane(0, 0, partner.offsetX, partner.offsetY);
                pop.lines.add(line);
                lines.add(line);
            }
        }
        pop.oPops.addAll(oPops);
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

    public GraphOnPlane build() {
        persons = new ArrayList<>();
        lines = new ArrayList<>();
        OffsetPersonOnPlane centralOnPlane = new OffsetPersonOnPlane(centralPerson, 0, 0);
        persons.add(centralOnPlane);
        recursiveGraph(centralOnPlane, 0, FamilyGraph::getChildren, slot_height);
        recursiveGraph(centralOnPlane, 0, FamilyGraph::getParents, -slot_height);
        recalculateOffsets(centralOnPlane, 450, 250);
        return new SimpleGraphWithLineOnPlane(persons.stream().map(
                pop -> (PersonOnPlane)new SimplePersonOnPlane(pop.offsetX, pop.offsetY, pop.person)).toList(), lines);
    }
}
