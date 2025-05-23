package tcs.familytree;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import tcs.familytree.viewmodels.HardcodedSingleTreeViewModel;
import tcs.familytree.viewmodels.SingleTreeViewModel;
import tcs.familytree.views.GraphView;
import tcs.familytree.views.SimpleGraphPainter;

public class MainController {
    @FXML
    private Pane mainContainer;

    private enum OpenedTab {
        NONE,
        RANDOM_PAINTER,
        RED_PAINTER,
    }

    private final SingleTreeViewModel viewModel = HardcodedSingleTreeViewModel.getModel();
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
            mainContainer.getChildren().setAll(view);
            return controller;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
