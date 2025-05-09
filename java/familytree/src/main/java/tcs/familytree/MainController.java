package tcs.familytree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import tcs.familytree.viewmodels.DummyViewModel;
import tcs.familytree.views.SimpleGraphPainter;
import tcs.familytree.views.SimpleGraphVertex;

public class MainController {
    @FXML
    private Pane mainContainer;

    private void colorClicked(String value) {
        System.out.println(value);
    }

    @FXML
    protected void red() {
        colorClicked("Red");
    }

    @FXML
    protected void green() {
        colorClicked("Green");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/simple-graph-painter.fxml"));
        try {
            Pane view = loader.load();
            SimpleGraphPainter graphPainter = loader.getController();
            mainContainer.getChildren().setAll(view);
            graphPainter.paintRandomly(new DummyViewModel().provideTemporaryData());
//            graphPainter.fillSomeData();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void blue() {
        colorClicked("Blue");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        try {
            Pane view = loader.load();
            HelloController helloController = loader.getController();
            mainContainer.getChildren().setAll(view);
            helloController.setWelcomeText("Greetings from Blue!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
