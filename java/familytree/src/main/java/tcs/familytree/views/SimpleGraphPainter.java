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
                controllers[i].setAllDataFromStrings(((DataString) personList.get(i).getName()).get(),
                        ((DataString) personList.get(i).getSurname()).get(), TmpUtil.randDate(20), TmpUtil.randDate(60));
                panes[i].setLayoutX(TmpUtil.rand(900) + 10);
                panes[i].setLayoutY(TmpUtil.rand(500) + 10);
            }
            container.getChildren().addAll(panes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillSomeData() {
        try {
            final int count = 3;
            SimpleGraphVertex[] graphVertices = new SimpleGraphVertex[count];
            Pane[] panes = new Pane[count];
            for(int i=0; i<count; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("simple-graph-vertex.fxml"));
                panes[i] = loader.load();
                graphVertices[i] = loader.getController();
                panes[i].setLayoutX(100 + 300*i);
                panes[i].setLayoutY(100);
            }
            container.getChildren().addAll(panes);
//            container.getChildren().setAll(view);
            graphVertices[0].setAllDataFromStrings("Jan", "Kowalski", "01.01.1946", "20.12.2024");
            graphVertices[2].setAllDataFromStrings("Karolina", "Nowak", "01.01.1990", null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        container.getChildren().add()
    }
}
