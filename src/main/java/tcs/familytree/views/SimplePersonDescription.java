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
        TreeItem<String> rootItem = printFullPerson(displayPerson);




        TreeItem<String> branchItem4 = new TreeItem<>("test3");


        treeView.setRoot(rootItem);
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
