package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.viewmodels.GraphViewModel;

import java.util.*;

public class SimplePersonDescription {

    Map<TreeItem<String>, Person> treeItemPersonMap = new HashMap<>();
    Map<TreeItem<String>, Place> treeItemPlaceMap = new HashMap<>();
    TreeItem<String> globalPersonList = null;

    GraphViewModel viewModel = null;

    public TreeView<String> treeView;

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
            treeItemPersonMap.put(branchItem4, displayPerson.getMother());
        }else{
            branchItem4 = new TreeItem<>("Matka: [unknown]");
        }
        TreeItem<String> branchItem5;
        if(displayPerson.getFather() != null){
            branchItem5 = new TreeItem<>("Ojciec: " + displayPerson.getFather().toString());
            treeItemPersonMap.put(branchItem5, displayPerson.getFather());
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
            treeItemPlaceMap.put(branchItem8, displayPerson.getPlaceOfBirth());
        }else{
            branchItem8 = new TreeItem<>("Miejsce urodzenia: [unknown]");
        }

        TreeItem<String> branchItem9;
        if(displayPerson.getPlaceOfDeath() != null){
            branchItem9 = new TreeItem<>("Miejsce śmierci: " + displayPerson.getPlaceOfDeath().toString());
            treeItemPlaceMap.put(branchItem9, displayPerson.getPlaceOfDeath());
        }else{
            branchItem9 = new TreeItem<>("Miejsce śmierci: [unknown]");
        }

        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3, branchItem4,
                branchItem5, branchItem6, branchItem7, branchItem8, branchItem9);
        treeItemPersonMap.put(rootItem, displayPerson);
        return rootItem;
    }

    TreeItem<String> printFullList(){
        if(globalPersonList != null){
            return globalPersonList;
        }

        TreeItem<String> personListItem = new TreeItem<>("Lista Wszystkich ludzi");
        List<TreeItem<String>> personList = new LinkedList<>();

        for(Person person: viewModel.getGraphProperty().get().getAllPersons()){
            personList.add(printFullPerson(person));
        }
        personListItem.getChildren().setAll(personList);
        return personListItem;
    }

    public void lightRefresh(){
        init(viewModel);
    }

    public void hardRefresh(){
        globalPersonList = null;
        init(viewModel);
    }

    public void init(GraphViewModel viewModel){
        this.viewModel = viewModel;
        if(viewModel == null){
            throw new NullPointerException("INIT - view model don't exist");
        }
        treeItemPersonMap.clear();
        Person displayPerson = viewModel.central();
        TreeItem<String> rootPersonItem = printFullPerson(displayPerson);
        TreeItem<String> rootItem = new TreeItem<>("root");


        TreeItem<String> personListItem = printFullList();

        rootItem.getChildren().setAll(rootPersonItem, personListItem);
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);

    }

    @FXML
    public void selectItem(MouseEvent event){
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2) {
            if(treeItemPersonMap.containsKey(selectedItem)){
                viewModel.updateCentral(treeItemPersonMap.get(selectedItem));
            }
            if(treeItemPlaceMap.containsKey(selectedItem)){
                viewModel.getMainController().openEditionPanel(treeItemPlaceMap.get(selectedItem), "Place edition");
            }
        }
    }
}
