package tcs.familytree;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tcs.familytree.viewmodels.DummyViewModel;
import tcs.familytree.views.DummyView;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(new DummyView().getDummyData());
    }

    public void setWelcomeText(String value) {
        welcomeText.setText(value);
    }
}