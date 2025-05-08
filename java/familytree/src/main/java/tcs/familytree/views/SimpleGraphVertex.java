package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class SimpleGraphVertex extends Region {
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
     * @param firstName name of given person
     * @param surname surname of given person
     * @param dob date of birth (as String)
     * @param dod date of death (as String)
     */
    public void setAllDataFromStrings(String firstName, String surname, String dob, String dod) {
        firstNameLabel.setText(firstName);
        surnameLabel.setText(surname);
        dateOfBirthLabel.setText(dob);
        dateOfDeathLabel.setText(dod);
    }
}
