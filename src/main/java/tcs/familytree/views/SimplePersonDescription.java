package tcs.familytree.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SimplePersonDescription implements Initializable {


    public TreeView<String> treeView;
    public AnchorPane base;

    public void init(){
        System.out.println("init");
        
        TreeItem<String> rootItem = new TreeItem<>("Person");

        TreeItem<String> branchItem1 = new TreeItem<>("test1");
        TreeItem<String> branchItem2 = new TreeItem<>("test2");
        TreeItem<String> branchItem3 = new TreeItem<>("test3");

        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3);

        treeView.setRoot(rootItem);
    }

    @FXML
    public void selectItem(){
        System.out.println("SELECT ITEM");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialize");
        init();
    }
}
