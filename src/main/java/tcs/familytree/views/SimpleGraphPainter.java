package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import tcs.familytree.TmpUtil;
import tcs.familytree.core.person.Gender;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.views.plane.GraphOnPlane;
import tcs.familytree.views.plane.PersonOnPlane;

import java.util.List;

public class SimpleGraphPainter {
    @FXML
    protected AnchorPane container;

    private Paint getGenderColor(Gender gender){
        if(gender == Gender.MALE){
            return Color.DARKBLUE;
        }
        if(gender == Gender.FEMALE){
            return Color.DARKVIOLET;
        }
        return Color.WHITE;
    }


    public void paintGraphOnPlane(GraphOnPlane graphOnPlane) {
        if(graphOnPlane == null || graphOnPlane.getPersons() == null) {
            throw new NullPointerException();
        }
        try {
            List<PersonOnPlane> personsOnPlane = graphOnPlane.getPersons();
            final int count = personsOnPlane.size();
            Pane[] panes = new Pane[count];
            SimpleGraphVertex[] controllers = new SimpleGraphVertex[count];
            for(int i=0; i<count; i++)
            {
                PersonOnPlane pop = personsOnPlane.get(i);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("simple-graph-vertex.fxml"));
                panes[i] = loader.load();
                controllers[i] = loader.getController();

                controllers[i].setAllDataFromStrings(pop.person().getName(),
                        pop.person().getAllSurnames()==null?"":String.join(" ", pop.person().getAllSurnames()),
                        TmpUtil.randDate(20), TmpUtil.randDate(60),
                        getGenderColor(pop.person().getGender()));
                panes[i].setLayoutX(pop.x());
                panes[i].setLayoutY(pop.y());
            }
            container.getChildren().setAll(panes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paintRandomly(FamilyGraph graph) {
        try {
            final int count = graph.getSize();
            List<Person> personList = graph.getAllPersons().stream().toList();
            Pane[] panes = new Pane[count];
            SimpleGraphVertex[] controllers = new SimpleGraphVertex[count];
            for(int i=0; i<count; i++)
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("simple-graph-vertex.fxml"));
                panes[i] = loader.load();
                controllers[i] = loader.getController();
                controllers[i].setAllDataFromStrings(personList.get(i).getName(),
                         personList.get(i).getFamilySurname(), TmpUtil.randDate(20), TmpUtil.randDate(60),
                        getGenderColor(personList.get(i).getGender())
                        );
                panes[i].setLayoutX(TmpUtil.rand(900) + 10);
                panes[i].setLayoutY(TmpUtil.rand(500) + 10);
            }
            container.getChildren().addAll(panes);
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        for(Person p : graph.getAllPersons()) {
            System.out.println(p.toString() + ": " + graph.getChildren(p).stream().map(Object::toString).collect(Collectors.joining(", ")));
        }
 */
    }
}
