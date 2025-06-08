package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import tcs.familytree.core.date.Date;
import tcs.familytree.viewmodels.GraphViewModel;
import tcs.familytree.views.plane.PersonOnPlane;

public class SimpleGraphVertex extends Region {

    GraphViewModel graphViewModel;
    PersonOnPlane person;

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

    public void setAllData(PersonOnPlane person, String firstName, String surname, Date dateOfBirth, Date dateOfDeath, Paint color, GraphViewModel graphViewModel) {
        firstNameLabel.setText(firstName);
        firstNameLabel.setTextFill(color);
        surnameLabel.setText(surname==null?"?": surname);
        surnameLabel.setTextFill(color);
        dateOfBirthLabel.setText(dateOfBirth==null?"":dateOfBirth.toString());
        dateOfDeathLabel.setText(dateOfDeath==null?"":dateOfDeath.toString());
        this.graphViewModel = graphViewModel;
        this.person = person;
    }

    public void vertexClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() >= 2){
            graphViewModel.getMainController().openEditionPanelMainPerson(null);
        }else{
            graphViewModel.updateCentral(person.person());
        }
        vertexLabel.setFill(Color.YELLOW);
    }
}
