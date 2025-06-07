package tcs.familytree.views;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.place.PlaceType;
import tcs.familytree.viewmodels.GraphViewModel;

public class EditionPlaceController {
    public TextField placeName;
    public ComboBox<Place> superPlace;
    public ComboBox<PlaceType> placeType;
    public Button saveButton;
    GraphViewModel graphViewModel;


    public void init(GraphViewModel graphViewModel){
        this.graphViewModel = graphViewModel;
        superPlace.getItems().addAll(graphViewModel.getAllPlaces());
        placeType.getItems().addAll(graphViewModel.getAllPlaceTypes());
    }

    public void setPlace(Place place){
        placeName.setText(place.getName());
        superPlace.getSelectionModel().select(place.getSuperPlace());
        placeType.getSelectionModel().select(place.getPlaceType());
    }

    public void handleSave(ActionEvent actionEvent) {

    }
}
