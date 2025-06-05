package tcs.familytree.views;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import tcs.familytree.core.person.Gender;
import tcs.familytree.core.person.Person;
import tcs.familytree.viewmodels.GraphViewModel;

public class PersonEditionController{
    public TextField nameField;
    public TextField surnameField;
    public Button saveButton;
    public Label label;
    public CheckBox aliveCheckbox;
    public ChoiceBox<Gender> genderBox;
    public TextField allNamesField;
    public ComboBox<Person> motherBox;
    public ComboBox<Person> fatherBox;

    GraphViewModel viewModel;

    Person person;

    public void init() {
        genderBox.getItems().addAll(Gender.MALE, Gender.FEMALE, Gender.OTHER);
        motherBox.getItems().addAll(viewModel.getGraphProperty().get().getAllPersons());
        fatherBox.getItems().addAll(viewModel.getGraphProperty().get().getAllPersons());
    }

    public void setViewModel(GraphViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setPerson(Person person) {
        this.person = person;
        nameField.setText(person.getName());
        surnameField.setText(person.getFamilySurname());
        allNamesField.setText(String.join(" ", person.getAllNames()));
        aliveCheckbox.setSelected(person.isAlive());
        genderBox.getSelectionModel().select(person.getGender());
        motherBox.getSelectionModel().select(person.getMother());
        fatherBox.getSelectionModel().select(person.getFather());
    }

    public void handleSave(ActionEvent actionEvent) {
        try{
            person.setName(nameField.getText());
            person.setAllNames(surnameField.getText().split(" "));
            person.setFamilyName(surnameField.getText());
            person.setAlive(aliveCheckbox.isSelected());
            person.setGender(genderBox.getValue());
            person.setMother(motherBox.getValue());
            person.setFather(fatherBox.getValue());

            label.setTextFill(Color.GREEN);
            label.setText("Edycja osoby " + person.getId() + " zakończona pomyślnie.");
            viewModel.refresh().centeredPersonChange();
        }catch(Exception e){
            label.setTextFill(Color.RED);
            label.setText("Database Error: " + e.getMessage());
        }

    }
}
