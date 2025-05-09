package tcs.familytree.views;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.SimpleGraph;
import tcs.familytree.viewmodels.DummyViewModel;
import tcs.familytree.viewmodels.SingleTreeViewModel;

import static javafx.application.Platform.runLater;

public class GraphView {
    private final SimpleGraphPainter painter;
    private final SingleTreeViewModel viewModel;

    public GraphView(SimpleGraphPainter painter, SingleTreeViewModel viewModel) {
        this.painter = painter;
        this.viewModel = viewModel;
        SimpleObjectProperty<FamilyGraph> graphProperty = viewModel.getGraphProperty();
        graphProperty.addListener((ob, old, newValue) -> {
            System.out.println("Graph Modified!");
            runLater(() -> painter.paintRandomly(graphProperty.get()));
        }); //malowanie musi odbywać się na wątku JavaFX. Można to uzyskać metodą runLater

        System.out.println("Listener added");
        viewModel.updateGraph();
//        painter.paintRandomly(new DummyViewModel().provideTemporaryData());
    }


}
