package tcs.familytree.views;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.viewmodels.GraphViewModel;
import tcs.familytree.viewmodels.SingleTreeViewModel;
import tcs.familytree.views.painter.SimplePainterBackEnd;
import tcs.familytree.views.plane.GraphOnPlane;

import java.util.List;

import static javafx.application.Platform.runLater;

public class GraphView {
    private final SimpleGraphPainter painter;
    private final SingleTreeViewModel viewModel;
    private final SimpleObjectProperty<FamilyGraph> graphProperty;
    private ChangeListener<FamilyGraph> listener; //TODO private void setListener() Exception Safe

    public GraphView(SimpleGraphPainter painter, SingleTreeViewModel viewModel) {
        this.painter = painter;
        this.viewModel = viewModel;
        this.graphProperty = viewModel.getGraphProperty();
    }

    public void dropListener() {
        if(listener != null) {
            graphProperty.removeListener(listener);
        }
    }

    public void paintMovableCenteredAtRandom(){
        List<Person> list = graphProperty.get().getAllPersons().stream().toList();
        // If there is no person at all, we cannot select a random person.
        // We have to wait until someone arrives.

        GraphViewModel graphViewModel;
        boolean graphViewModelNotAvailable = false;
        if(viewModel instanceof GraphViewModel){
            graphViewModel = (GraphViewModel) viewModel;
        }else{
            graphViewModel = null;
            graphViewModelNotAvailable = true;
        }
        if(list.isEmpty() || graphViewModelNotAvailable) {
            dropListener();
            throw new RuntimeException("CRITICAL ERROR - NO CONNECTION TO DATABASE!!!");
        }

        dropListener();
        this.listener = new ChangeListener<FamilyGraph>() {
            @Override
            public void changed(ObservableValue<? extends FamilyGraph> observableValue, FamilyGraph familyGraph, FamilyGraph t1) {
//                runLater(() -> painter.paintRandomly(graphProperty.get()));
                GraphOnPlane graphOnPlane = new SimplePainterBackEnd(graphProperty.get(), graphViewModel.central()).build();
                runLater(() -> painter.paintMovableGraphOnPlane(graphOnPlane, graphViewModel));
            } //malowanie musi odbywać się na wątku JavaFX. Można to uzyskać metodą runLater
        };
        graphProperty.addListener(listener);


        GraphOnPlane graphOnPlane = new SimplePainterBackEnd(graphProperty.get(), graphViewModel.central()).build();
        runLater(() -> painter.paintMovableGraphOnPlane(graphOnPlane, graphViewModel));
    }

}
