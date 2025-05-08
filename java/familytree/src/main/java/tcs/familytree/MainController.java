package tcs.familytree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MainController {
    @FXML
    private Pane mainContainer;

    @FXML
    private Label helloLabel;

    private void changeLabel(String value) {
        System.out.println(value);
    }

    @FXML
    protected void red() {
        changeLabel("Red");
    }

    @FXML
    protected void green() {
        changeLabel("Green");
    }

    @FXML
    protected void blue() {
        changeLabel("Blue");
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
