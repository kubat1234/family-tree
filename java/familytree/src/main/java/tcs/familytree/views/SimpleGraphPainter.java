package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import tcs.familytree.TmpUtil;
import tcs.familytree.core.DataString;
import tcs.familytree.core.Person;
import tcs.familytree.services.FamilyGraph;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleGraphPainter {
    @FXML
    protected AnchorPane container;

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
                         personList.get(i).getSurname(), TmpUtil.randDate(20), TmpUtil.randDate(60));
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
