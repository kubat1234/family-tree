package tcs.familytree.views;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import tcs.familytree.core.person.Gender;
import tcs.familytree.core.person.Person;
import tcs.familytree.viewmodels.GraphViewModel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PersonFinderController {
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
    public TreeView treeView;

    GraphViewModel viewModel;

    Person person;

    TreeItem<String> printFullPerson(Person displayPerson){
        TreeItem<String> rootItem;
        if(displayPerson.isAlive()){
            rootItem = new TreeItem<>(displayPerson.toString());
        }else{
            rootItem = new TreeItem<>(displayPerson + " (x)");
        }

        TreeItem<String> branchItem1 = new TreeItem<>("Imie: " + displayPerson.getName());
        TreeItem<String> branchItem2 = new TreeItem<>("Nazwisko Rodowe: " + displayPerson.getFamilySurname());
        StringBuilder surnames = new StringBuilder();
        surnames.append(" ");
        for(String surname: displayPerson.getAllSurnames()){
            if(surname != null){
                surnames.append(surname);
            }
        }
        TreeItem<String> branchItem3 = new TreeItem<>("Wszystkie Nazwiska: " + surnames);
        TreeItem<String> branchItem4;

        if(displayPerson.getMother() != null){
            branchItem4 = new TreeItem<>("Matka: " + displayPerson.getMother().toString());
        }else{
            branchItem4 = new TreeItem<>("Matka: [unknown]");
        }
        TreeItem<String> branchItem5;
        if(displayPerson.getFather() != null){
            branchItem5 = new TreeItem<>("Ojciec: " + displayPerson.getFather().toString());
        }else{
            branchItem5 = new TreeItem<>("Ojciec: [unknown]");
        }

        TreeItem<String> branchItem6;
        if(displayPerson.getDateOfBirth() != null){
            branchItem6 = new TreeItem<>("Data urodzenia: " + displayPerson.getDateOfBirth().toString());
        }else{
            branchItem6 = new TreeItem<>("Data urodzenia: [unknown]");
        }

        TreeItem<String> branchItem7;
        if(displayPerson.getDateOfDeath() != null){
            branchItem7 = new TreeItem<>("Data śmierci: " + displayPerson.getDateOfDeath().toString());
        }else{
            branchItem7 = new TreeItem<>("Data śmierci: [unknown]");
        }

        TreeItem<String> branchItem8;
        if(displayPerson.getPlaceOfBirth() != null){
            branchItem8 = new TreeItem<>("Miejsce urodzenia: " + displayPerson.getPlaceOfBirth().toString());
        }else{
            branchItem8 = new TreeItem<>("Miejsce urodzenia: [unknown]");
        }

        TreeItem<String> branchItem9;
        if(displayPerson.getPlaceOfDeath() != null){
            branchItem9 = new TreeItem<>("Miejsce śmierci: " + displayPerson.getPlaceOfDeath().toString());
        }else{
            branchItem9 = new TreeItem<>("Miejsce śmierci: [unknown]");
        }

        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3, branchItem4,
                branchItem5, branchItem6, branchItem7, branchItem8, branchItem9);
        return rootItem;
    }

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
        if(!person.getAllNames().isEmpty())allNamesField.setText(String.join(" ", person.getAllNames()));
        if(!person.getAllSurnames().isEmpty())allSurnamesField.setText(String.join(" ", person.getAllSurnames()));
    }

    public void handleSave(ActionEvent actionEvent) {
        try{
            Collection<Person> list = viewModel.getGraphProperty().get().getAllPersons();
            List<TreeItem<String>> ready = new LinkedList<>();

            for(Person pop: list){
                if(!Objects.equals(nameField.getText(), "") && !Objects.equals(nameField.getText(), null)){
                    if(!Objects.equals(pop.getName(), nameField.getText())){
                        continue;
                    }
                }
                if(!Objects.equals(allNamesField.getText(), "") && !Objects.equals(allNamesField.getText(), null)){
                    if(!Objects.equals(pop.getName(), allNamesField.getText())){
                        continue;
                    }
                }
                if(!Objects.equals(surnameField.getText(), "") && !Objects.equals(surnameField.getText(), null)){
                    if(!Objects.equals(pop.getName(), surnameField.getText())){
                        continue;
                    }
                }

                if(!aliveCheckbox.isSelected() && pop.isAlive()){
                    continue;
                }
                if(aliveCheckbox.isSelected() && !pop.isAlive()){
                    continue;
                }
                if(aliveCheckbox.isSelected() && !pop.isAlive()){
                    continue;
                }
                if(genderBox.getValue() != pop.getGender() && genderBox.getValue() != Gender.OTHER){
                    continue;
                }
                ready.add(printFullPerson(pop));
            }
            TreeItem<String> rootItem = new TreeItem<>("root");

            rootItem.getChildren().setAll(ready);

            treeView.setRoot(rootItem);
            treeView.setShowRoot(false);


            label.setTextFill(Color.GREEN);
            label.setText("Szukanie osób zakończona pomyślnie.");
            viewModel.refresh().centeredPersonChange();
        }catch(Exception e){
            label.setTextFill(Color.RED);
            label.setText("Database Error: " + e.getMessage());
        }

    }
}
