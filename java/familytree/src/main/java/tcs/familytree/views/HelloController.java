package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tcs.familytree.viewmodels.DummyViewModel;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(new DummyViewModel().getDummyData() + " and Views!");
    }
}