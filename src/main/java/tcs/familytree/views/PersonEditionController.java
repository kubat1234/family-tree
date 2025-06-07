package tcs.familytree.views;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import tcs.familytree.core.date.SimpleDate;
import tcs.familytree.core.person.Gender;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.SimplePerson;
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
    public TextField allSurnamesField;
    public CheckBox deathDateAccurateField;
    public CheckBox birthDateAccurateField;
    public Spinner<Integer> birthYearField;
    public Spinner<Integer> birthMonthField;
    public Spinner<Integer> birthDayField;
    public Spinner<Integer> deathDayField;
    public Spinner<Integer> deathMonthField;
    public Spinner<Integer> deathYearField;

    GraphViewModel viewModel;

    Person person;

    public void init() {
        genderBox.getItems().addAll(Gender.MALE, Gender.FEMALE, Gender.OTHER);
        motherBox.getItems().add(null);
        fatherBox.getItems().add(null);
        motherBox.getItems().addAll(viewModel.getGraphProperty().get().getAllPersons());
        fatherBox.getItems().addAll(viewModel.getGraphProperty().get().getAllPersons());

        birthDayField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31));
        deathDayField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31));

        birthMonthField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12));
        deathMonthField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12));

        birthYearField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20000));
        deathYearField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20000));
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

        birthDayField.getValueFactory().setValue(person.getDateOfBirth().getDay());
        birthMonthField.getValueFactory().setValue(person.getDateOfBirth().getMonth());
        birthYearField.getValueFactory().setValue(person.getDateOfBirth().getYear());

        deathDayField.getValueFactory().setValue(person.getDateOfDeath().getDay());
        deathMonthField.getValueFactory().setValue(person.getDateOfDeath().getMonth());
        deathYearField.getValueFactory().setValue(person.getDateOfDeath().getYear());

        birthDayField.setEditable(true);
        birthMonthField.setEditable(true);
        birthYearField.setEditable(true);
        deathDayField.setEditable(true);
        deathMonthField.setEditable(true);
        deathYearField.setEditable(true);

        birthDateAccurateField.setSelected(person.getDateOfBirth().isAccurate());
        deathDateAccurateField.setSelected(person.getDateOfDeath().isAccurate());

        allNamesField.setText(String.join(" ", person.getAllNames()));
        allSurnamesField.setText(String.join(" ", person.getAllSurnames()));
    }

    public void handleSave(ActionEvent actionEvent) {
        try{
            Person newPerson = new SimplePerson(person);
            newPerson.setName(nameField.getText());
            newPerson.setAllNames(allNamesField.getText().split(" "));
            newPerson.setFamilyName(surnameField.getText());
            newPerson.setAlive(aliveCheckbox.isSelected());
            newPerson.setGender(genderBox.getValue());
            newPerson.setMother(motherBox.getValue());
            newPerson.setFather(fatherBox.getValue());

            newPerson.setDateOfBirth(new SimpleDate(birthYearField.getValue(),birthMonthField.getValue(),birthDayField.getValue(),birthDateAccurateField.isSelected()));

            newPerson.setDateOfDeath(new SimpleDate(deathYearField.getValue(),deathMonthField.getValue(),deathDayField.getValue(),deathDateAccurateField.isSelected()));

            person.setPerson(newPerson);

            label.setTextFill(Color.GREEN);
            label.setText("Edycja osoby " + person.getId() + " zakończona pomyślnie.");
            viewModel.refresh().centeredPersonChange();
        }catch(Exception e){
            label.setTextFill(Color.RED);
            label.setText("Database Error: " + e.getMessage());
        }

    }
}
