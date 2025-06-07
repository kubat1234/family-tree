package tcs.familytree.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import tcs.familytree.core.date.SimpleDate;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.person.SimplePerson;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.place.PlaceType;
import tcs.familytree.core.place.SimplePlace;
import tcs.familytree.viewmodels.GraphViewModel;

public class EditionPlaceController {
    public TextField placeName;
    public ComboBox<Place> superPlace;
    public ComboBox<PlaceType> placeType;
    public Button saveButton;
    public Label label;
    GraphViewModel graphViewModel;

    private FilteredList<Place> filteredPlaces;
    private Place place;


    public void init(GraphViewModel graphViewModel){
        this.graphViewModel = graphViewModel;

        ObservableList<Place> allPlaces = FXCollections.observableArrayList(graphViewModel.getAllPlaces());
        filteredPlaces = new FilteredList<>(allPlaces, p -> true);
        superPlace.setItems(filteredPlaces);
        placeType.getItems().addAll(graphViewModel.getAllPlaceTypes());
        placeType.valueProperty().addListener((obs, oldType, newType) -> {
            filteredPlaces.setPredicate(p -> p.getPlaceType().getId() == newType.getId());
        });
    }

    public void setPlace(Place place){
        this.place = place;
        placeName.setText(place.getName());
        placeType.getSelectionModel().select(place.getPlaceType());
        superPlace.getSelectionModel().select(place.getSuperPlace());
    }

    public void handleSave(ActionEvent actionEvent) {
        try{
            Place newPlace = new SimplePlace(place);
            newPlace.setName(placeName.getText());
            newPlace.setSuperPlace(superPlace.getValue());
            newPlace.setPlaceType(placeType.getValue());

            place.setPlace(newPlace);

            label.setTextFill(Color.GREEN);
            label.setText("Edycja osoby " + place.getId() + " zakończona pomyślnie.");
            graphViewModel.refresh().centeredPersonChange();
        }catch(Exception e){
            label.setTextFill(Color.RED);
            label.setText("Database Error: " + e.getMessage());
        }
    }
}
