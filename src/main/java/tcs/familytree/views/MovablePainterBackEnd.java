package tcs.familytree.views;

import tcs.familytree.TmpUtil;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.viewmodels.GraphViewModel;
import tcs.familytree.views.plane.GraphOnPlane;
import tcs.familytree.views.plane.PersonOnPlane;
import tcs.familytree.views.plane.SimpleGraphOnPlane;
import tcs.familytree.views.plane.SimplePersonOnPlane;

import java.util.ArrayList;
import java.util.List;

public class MovablePainterBackEnd implements PainterBackEnd {

    GraphViewModel graphViewModel;
    private boolean built = false;
    List<PersonOnPlane> persons = new ArrayList<>();
    Integer oldX = 0, oldY = 0;

    MovablePainterBackEnd(GraphViewModel graphViewModel) {
        this.graphViewModel = graphViewModel;
    }

    @Override
    public GraphOnPlane build() {
        if (built) {
            return update();
        }

        FamilyGraph familyGraph = graphViewModel.getGraphProperty().get();
        Person centralPerson = graphViewModel.central();
        persons.clear();
        oldX = graphViewModel.x();
        oldY = graphViewModel.y();

        persons.add(new SimplePersonOnPlane( 450 + oldX, 250 + oldY, centralPerson));
        for (Person p : familyGraph.getChildren(centralPerson)) {
            persons.add(new SimplePersonOnPlane(TmpUtil.rand(880) + 10,
                    TmpUtil.rand(10) + 395 + oldY, p));
        }
        for (Person p : familyGraph.getParents(centralPerson)) {
            Person p2 = familyGraph.getAllPersons().stream().filter(q -> q.getId() == p.getId()).findFirst().get();
            persons.add(new SimplePersonOnPlane(TmpUtil.rand(880) + 10,
                    TmpUtil.rand(10) + 95 + oldY, p2));
        }
        built = true;
        return new SimpleGraphOnPlane(persons);
    }

    public GraphOnPlane update() {
        for (PersonOnPlane person : persons) {
            person.update((double) graphViewModel.x() - oldX, (double) graphViewModel.y() - oldY);
        }
        oldX = graphViewModel.x();
        oldY = graphViewModel.y();
        return new SimpleGraphOnPlane(persons);
    }
}