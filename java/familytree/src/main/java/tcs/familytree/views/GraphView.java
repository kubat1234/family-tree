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
//    private final SimpleGraphPainter painter;
//    private final SingleTreeViewModel viewModel;
    private final SimpleObjectProperty<FamilyGraph> graphProperty;
    private ChangeListener<FamilyGraph> listener;

    public GraphView(SimpleGraphPainter painter, SingleTreeViewModel viewModel) {
//        this.painter = painter;
//        this.viewModel = viewModel;
        this.graphProperty = viewModel.getGraphProperty();
        this.listener = new ChangeListener<FamilyGraph>() {
            @Override
            public void changed(ObservableValue<? extends FamilyGraph> observableValue, FamilyGraph familyGraph, FamilyGraph t1) {
                runLater(() -> painter.paintRandomly(graphProperty.get()));
            }; //malowanie musi odbywać się na wątku JavaFX. Można to uzyskać metodą runLater
        };
        graphProperty.addListener(listener);

        viewModel.updateGraph();
//        painter.paintRandomly(new DummyViewModel().provideTemporaryData());
    }

    public void dropListener() {
        graphProperty.removeListener(listener);
    }

}
