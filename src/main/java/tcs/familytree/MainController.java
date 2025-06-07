package tcs.familytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.GraphProvider;
import tcs.familytree.services.RealGraphProvider;
import tcs.familytree.viewmodels.*;
import tcs.familytree.views.*;

import java.io.IOException;

public class MainController {
    @FXML
    public StackPane mainSpace;
    public StackPane leftPanel2;

    Stage stage;

    private enum OpenedTab {
        NONE,
        MOVABLE_PAINTER,
    }

    private GraphViewModel graphViewModel = null;
    private OpenedTab openedTab = OpenedTab.NONE;
    private GraphView graphView;

    private boolean colorClicked(String value, OpenedTab tab) {
        OpenedTab oldTab = openedTab;
        openedTab = tab;
        if(tab == oldTab) {
            return false;
        }
        if(graphView == null) {
            return true;
        }
        graphView.dropListener();
        return true;
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private <T> T load(String resource) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        try {
            Pane view = loader.load();
            T controller = loader.getController();
            mainSpace.getChildren().setAll(view);
            return controller;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void loaderGraphOrientedViewModel() {
        GraphProvider tdp = new RealGraphProvider();
        try{
            Thread.sleep(500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        GraphProvider tdp = new TemporaryDataProvider2();
        graphViewModel = new GraphOrientedViewModel(tdp.getGraphProperty().get().getPerson(1), tdp, this);
        graphView = null;
        colorClicked("NONE", OpenedTab.NONE);
        System.out.println("Load OK");
        refresh();
    }

    public void refresh() {
        graphViewModel.refresh().hardRefresh();
    }

    public void refreshGraph(){
        graphViewModel.updateGraph();
        if(!colorClicked("movable", OpenedTab.MOVABLE_PAINTER)) {
            return;
        }
        if(graphViewModel == null){
            loaderGraphOrientedViewModel();
        }
        if(graphView == null) {
            SimpleGraphPainter painter = load("views/simple-graph-painter.fxml");
            graphView = new GraphView(painter, graphViewModel);
        }
//        graphView.paintCenteredAtRandom();
        graphView.paintMovableCenteredAtRandom();
    }

    void moveLeft(){
        System.out.println("Move Left");
        graphViewModel.changeMod(-10, 0);
        refresh();
    }

    void moveRight(){
        graphViewModel.changeMod(10, 0);
        refresh();
    }

    void moveUp(){
        graphViewModel.changeMod(0, -10);
        refresh();

    }

    void moveDown(){
        graphViewModel.changeMod(0, 10);
        refresh();
    }

    void move(int x, int y){
        graphViewModel.changeMod(x, y);
    }

    public void openLeftPanel(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/simple-person-description.fxml"));
        try{
            AnchorPane view = loader.load();
            SimplePersonDescription simplePersonDescription = loader.getController();
            simplePersonDescription.init(graphViewModel);
            graphViewModel.setSimplePersonDescription(simplePersonDescription);
            leftPanel2.getChildren().setAll(view);
        }catch (Exception e){
            System.out.println("error");
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void openEditionPanel(Person person, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/edit-person.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            dialogStage.setScene(new Scene(page));

            PersonEditionController controller = loader.getController();
            controller.setViewModel(graphViewModel);
            controller.init();
            controller.setPerson(person);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEditionPanelMainPerson(ActionEvent actionEvent){
        openEditionPanel(graphViewModel.central(),"Edytuj osobę");
    }
    public void openAddingPanel(ActionEvent actionEvent) {
        Person person = graphViewModel.createNewPerson();
        openEditionPanel(person,"Dodaj Osobę");
    }
    public void openAdminPanel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/admin-panel.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Panel Administratora");
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(stage);
            dialogStage.setScene(new Scene(page));
            AdminPanel controller = loader.getController();
            controller.setViewModel(graphViewModel);

            dialogStage.showAndWait();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
