package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SimpleGraphVertex extends Region {
    @FXML
    public Rectangle vertexLabel;

    @FXML
    protected Label firstNameLabel;

    @FXML
    public Label surnameLabel;

    @FXML
    protected Label dateOfBirthLabel;

    @FXML
    protected Label dateOfDeathLabel;

    /**
     * Temporary method, sets values of every label in Simple Vertex. Null safe.
     */
    public void setAllDataFromStrings(String firstName, String surname, String dateOfBirth, String dateOfDeath, Paint color) {
        firstNameLabel.setText(firstName);
        firstNameLabel.setTextFill(color);
        surnameLabel.setText(surname);
        surnameLabel.setTextFill(color);
        dateOfBirthLabel.setText(dateOfBirth);
        dateOfDeathLabel.setText(dateOfDeath);
    }

    public void vertexClicked(MouseEvent mouseEvent) {
        vertexLabel.setFill(Color.YELLOW);
    }
}
