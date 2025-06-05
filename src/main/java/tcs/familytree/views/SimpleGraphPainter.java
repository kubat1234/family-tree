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
import tcs.familytree.views.plane.LineOnPlane;
import tcs.familytree.views.plane.PersonOnPlane;

import java.util.*;

public class SimpleGraphPainter {
    @FXML
    protected AnchorPane container;
    MouseMove mouseMove = new MouseMove();

    private static class MouseMove {
        int mouseX;
        int mouseY;
        void setPosition(MouseEvent event) {
            localX += (int)event.getSceneX() - mouseX;
            localY += (int)event.getSceneX() - mouseX;
            mouseX = (int)event.getSceneX(); mouseY = (int)event.getSceneY();
        }
        int localX = 0;
        int localY = 0;
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
        if(graphOnPlane == null || graphOnPlane.getPersons() == null || graphViewModel == null) {
            throw new NullPointerException();
        }
        try {
            List<Node> allNodes = new ArrayList<>();

            System.out.println("Before lines:");
            List<LineOnPlane> lines = graphOnPlane.getLines();
            if(lines != null){
                System.out.println("In Lines");
                List<Line> doneLines = new LinkedList<>();
                for(LineOnPlane parentLine: lines){
                    Line line = parentLine.build(graphViewModel.x(), graphViewModel.y());
                    System.out.println("Line: (" + line.getStartX() + ", " + line.getStartY() + ") -> (" +
                            line.getEndX() + ", " + line.getEndY() + ")");
                    line.setStroke(Color.BLACK);
                    line.setStrokeWidth(1.5);
                    doneLines.add(line);
                }
                allNodes.addAll(doneLines); // linie

//                container.getChildren().setAll(doneLines);
            }

            List<PersonOnPlane> personsOnPlane = graphOnPlane.getPersons();
            final int count = personsOnPlane.size();
            Pane[] panes = new Pane[count];
            SimpleGraphVertex[] controllers = new SimpleGraphVertex[count];
            container.setOnMousePressed(this::canvasPressed);
            container.setOnMouseDragged(a -> canvasDragged(a, graphViewModel, allNodes));
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
        mouseMove.setPosition(mouseEvent);
    }

    private void canvasDragged(MouseEvent mouseEvent, GraphViewModel viewModel, List<Node> nodes) {
        int dx = (int)mouseEvent.getSceneX() - mouseMove.mouseX;
        int dy = (int)mouseEvent.getSceneY() - mouseMove.mouseY;
        viewModel.changeMod(dx, dy);
        for(Node p : nodes) {
            p.setLayoutX(p.getLayoutX() + dx);
            p.setLayoutY(p.getLayoutY() + dy);
        }
        mouseMove.setPosition(mouseEvent);
    }
}
