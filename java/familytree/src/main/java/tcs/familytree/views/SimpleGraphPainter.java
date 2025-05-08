package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class SimpleGraphPainter {
    @FXML
    protected AnchorPane container;

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
