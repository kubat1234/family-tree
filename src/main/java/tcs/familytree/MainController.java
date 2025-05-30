package tcs.familytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import tcs.familytree.services.GraphProvider;
import tcs.familytree.services.RealGraphProvider;
import tcs.familytree.viewmodels.*;
import tcs.familytree.views.GraphView;
import tcs.familytree.views.SimpleGraphPainter;
import tcs.familytree.views.SimplePersonDescription;

import java.io.IOException;

public class MainController {
    @FXML
    public StackPane mainSpace;
    public ScrollPane leftPanel;
    public StackPane leftPanel2;


//    @FXML
//    public void initialize(){
//        Platform.runLater(() ->{
//            Scene scene = mainSpace.getScene();
//            mainSpace.setFocusTraversable(true);
//            mainSpace.requestFocus();
//            scene.setOnKeyPressed(
//                    (KeyEvent event) -> {
//                        if(event.getCode() == KeyCode.A){
//                            System.out.println("Pressed A");
//                            if(graphViewModel != null){
//                                graphViewModel.changeMod(0, -10);
//                            }
//                        }
//                        if(event.getCode() == KeyCode.SPACE){
//                            System.out.println("Pressed SPACE");
//                        }
//                    }
//            );
//        });
//        mainSpace.setFocusTraversable(true);

        //Platform.runLater(() -> scene.requestFocus());

//        mainSpace.requestFocus();
//    }


    private enum OpenedTab {
        NONE,
        RANDOM_PAINTER,
        RED_PAINTER,
        MOVABLE_PAINTER,
    }

    private final SingleTreeViewModel viewModel = new SingleDatabaseViewModel();//HardcodedSingleTreeViewModel.getModel();
    private GraphViewModel graphViewModel = null;
    private OpenedTab openedTab = OpenedTab.NONE;
    private GraphView graphView;

    private void colorClicked(String value) {
        //System.out.println(value);
    }

    private boolean colorClicked(String value, OpenedTab tab) {
        colorClicked(value);
        OpenedTab oldTab = openedTab;
        openedTab = tab;
        if(tab == oldTab) {
            return false;
        }
        if(graphView == null) {
            return true;
        }
        graphView.dropListener();
        /*
        if(openedTab == OpenedTab.RANDOM_PAINTER) {
            if(graphView == null) {
                throw new NullPointerException();
            }
            graphView.dropListener();
        }
         */
        return true;
    }

    @FXML
    protected void red() {
        if(!colorClicked("Red", OpenedTab.RED_PAINTER)) {
            return;
        }
        if(graphView == null) {
            SimpleGraphPainter painter = load("views/simple-graph-painter.fxml");
            graphView = new GraphView(painter, viewModel);
        }
        graphView.paintCenteredAtRandom();
    }

    @FXML
    protected void green() {
        if(!colorClicked("Green", OpenedTab.RANDOM_PAINTER)) {
            return;
        }
        if(graphView == null) {
            SimpleGraphPainter painter = load("views/simple-graph-painter.fxml");
            graphView = new GraphView(painter, viewModel);
        }
        graphView.paintRandomly();
    }

    @FXML
    protected void blue() {

        if(viewModel != null) {
            viewModel.updateGraph();
        }
        else {
            throw new IllegalStateException("There is no viewModel??");
        }

        /*
        if(!colorClicked("Blue", OpenedTab.HELLO_WORLD)) {
            return;
        }
        HelloController helloController = load("hello-view.fxml");
        helloController.setWelcomeText("Greetings from Blue!");
         */
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
        graphViewModel.changeMod(10, 0);
        refresh();
    }

    void moveRight(){
        graphViewModel.changeMod(-10, 0);
        refresh();
    }

    void moveUp(){
        graphViewModel.changeMod(0, 10);
        refresh();

    }

    void moveDown(){
        graphViewModel.changeMod(0, -10);
        refresh();
    }

    public void OpenLeftPanel(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/simple-person-description.fxml"));
        try{
            Pane view = loader.load();
            SimplePersonDescription simplePersonDescription = loader.getController();
            //simplePersonDescription.init();
            leftPanel2.getChildren().setAll(view);
        }catch (Exception e){
            System.out.println("error");
        }

    }
}
