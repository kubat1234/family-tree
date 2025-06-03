package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tcs.familytree.core.person.Person;
import tcs.familytree.viewmodels.GraphOrientedViewModel;
import tcs.familytree.viewmodels.GraphViewModel;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class SimplePersonDescription implements Initializable {

    GraphViewModel viewModel = null;

    public TreeView<String> treeView;

    TreeItem<String> printFullPerson(Person displayPerson){
        TreeItem<String> rootItem = new TreeItem<>(displayPerson.toString());
        TreeItem<String> branchItem1 = new TreeItem<>("Imie: " + displayPerson.getName());
        TreeItem<String> branchItem2 = new TreeItem<>("Nazwisko Rodowe: " + displayPerson.getFamilySurname());
        StringBuilder surnames = new StringBuilder();
        surnames.append(" ");
        for(String surname: displayPerson.getAllSurnames()){
            if(surname != null){
                surnames.append(surname);
            }
        }
        TreeItem<String> branchItem3 = new TreeItem<>("Wszystkie Nazwiska: " + surnames.toString());



        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3);
        return rootItem;
    }


    public void init(GraphViewModel viewModel){
        System.out.println("init");
        this.viewModel = viewModel;

        Person displayPerson = viewModel.central();
        TreeItem<String> rootPersonItem = printFullPerson(displayPerson);
        TreeItem<String> rootItem = new TreeItem<>("root");
        TreeItem<String> personListItem = new TreeItem<>("List of all person");
        List<TreeItem<String>> personList = new LinkedList<>();

        for(Person person: viewModel.getGraphProperty().get().getAllPersons()){
            personList.add(printFullPerson(person));
        }
        personListItem.getChildren().setAll(personList);

        rootItem.getChildren().setAll(rootPersonItem, personListItem);

        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);

    }

    @FXML
    public void selectItem(MouseEvent event){
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            System.out.println("SELECT ITEM: " + selectedItem.getValue());
        }
        System.out.println("SELECT ITEM");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialize");
//        init();
    }
}
