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
        PAINTER,
        HELLO_WORLD
    }

    private final SingleTreeViewModel viewModel = HardcodedSingleTreeViewModel.getModel();
    private OpenedTab openedTab = OpenedTab.NONE;
    private GraphView graphView;

    private void colorClicked(String value) {
        //System.out.println(value);
    }

    private boolean colorClicked(String value, OpenedTab tab) {
        colorClicked(value);
        if(tab == openedTab) {
            return false;
        }
        if(openedTab == OpenedTab.PAINTER) {
            if(graphView == null) {
                throw new NullPointerException();
            }
            graphView.dropListener();
        }
        openedTab = tab;
        return true;
    }

    @FXML
    protected void red() {
        colorClicked("Red");
    }

    @FXML
    protected void green() {
        if(!colorClicked("Green", OpenedTab.PAINTER)) {
            return;
        }
        SimpleGraphPainter painter = load("views/simple-graph-painter.fxml");
        graphView = new GraphView(painter, viewModel);
    }

    @FXML
    protected void blue() {
        if(!colorClicked("Blue", OpenedTab.HELLO_WORLD)) {
            return;
        }
        HelloController helloController = load("hello-view.fxml");
        helloController.setWelcomeText("Greetings from Blue!");
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
