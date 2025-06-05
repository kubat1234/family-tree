package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import tcs.familytree.core.person.Gender;
import tcs.familytree.viewmodels.GraphViewModel;
import tcs.familytree.views.plane.GraphOnPlane;
import tcs.familytree.views.plane.ParentLineOnPlane;
import tcs.familytree.views.plane.PersonOnPlane;

import java.util.*;

public class SimpleGraphPainter {
    @FXML
    protected AnchorPane container;
    boolean listenersPresent = false;

    private static class MouseMove {
        static double mouseX;
        static double mouseY;
        static void setPosition(MouseEvent event) {
            mouseX = event.getSceneX(); mouseY = event.getSceneY();
        }
    }

    private Paint getGenderColor(Gender gender){
        if(gender == Gender.MALE){
            return Color.DARKBLUE;
        }
        if(gender == Gender.FEMALE){
            return Color.DARKVIOLET;
        }
        return Color.WHITE;
    }

    public void paintMovableGraphOnPlane(GraphOnPlane graphOnPlane, GraphViewModel graphViewModel) {
        if(!listenersPresent) {
            listenersPresent = true;
            container.setOnMousePressed(this::canvasPressed);
            container.setOnMouseDragged(this::canvasDragged);
        }
        if(graphOnPlane == null || graphOnPlane.getPersons() == null || graphViewModel == null) {
            throw new NullPointerException();
        }
        try {
            List<Node> allNodes = new ArrayList<>();

            System.out.println("Before lines:");
            List<ParentLineOnPlane> lines = graphOnPlane.getParents();
            if(lines != null){
                System.out.println("In Lines");
                List<Line> doneLines = new LinkedList<>();
                for(ParentLineOnPlane parentLine: lines){
                    Line line = parentLine.build(graphViewModel.x(), graphViewModel.y());
                    System.out.println("Line: (" + line.getStartX() + ", " + line.getStartY() + ") -> (" +
                            line.getEndX() + ", " + line.getEndY() + ")");
                    line.setStroke(Color.BLACK);
                    line.setStrokeWidth(1.5);
                    doneLines.add(line);
                }
                allNodes.addAll(doneLines); // linie

                container.getChildren().setAll(doneLines);
            }

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

                controllers[i].setAllData(pop, pop.person().getName(),
                        pop.person().getAllSurnames()==null?"":String.join(" ", pop.person().getAllSurnames()),
                        pop.person().getDateOfBirth(), pop.person().getDateOfDeath(),
                        getGenderColor(pop.person().getGender()), graphViewModel);
                panes[i].setLayoutX(pop.x() + graphViewModel.x());
                panes[i].setLayoutY(pop.y() + graphViewModel.y());
            }
            allNodes.addAll(Arrays.asList(panes));
            container.getChildren().setAll(allNodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void canvasPressed(MouseEvent mouseEvent) {
        MouseMove.setPosition(mouseEvent);
    }

    private void canvasDragged(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getSceneX() - MouseMove.mouseX + ", " + (mouseEvent.getSceneY() - MouseMove.mouseY));
        MouseMove.setPosition(mouseEvent);
    }
}
