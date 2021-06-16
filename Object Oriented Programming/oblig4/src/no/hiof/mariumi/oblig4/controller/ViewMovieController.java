package no.hiof.mariumi.oblig4.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import no.hiof.mariumi.oblig4.ExecGui;
import no.hiof.mariumi.oblig4.model.Movie;
import no.hiof.mariumi.oblig4.model.Production;

public class ViewMovieController {

    // GUI Elements
    @FXML
    private ListView<Production> movieList;
    @FXML
    private Label titleLabel;
    @FXML
    private TextArea description;
    @FXML
    private DatePicker releaseDate;
    @FXML
    private TextField runtime;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;

    @FXML
    public void initialize() {
        // Updating fields based on item chosen.
        movieList.setItems(ExecGui.prodSys.getMovieList());
        movieList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Production>() {
            @Override
            public void changed(ObservableValue<? extends Production> observable, Production oldValue, Production newValue) {
                if(newValue != null) {
                    ExecGui.prodSys.selectedItem = movieList.getSelectionModel().getSelectedIndex();
                    updateInfo(newValue);
                }
            }
        });

        // Selecting first item automatically, unless there is no items.
        if(!movieList.getItems().isEmpty()) {
            movieList.getSelectionModel().select(ExecGui.prodSys.selectedItem);
        }

        // Disallowing the date to be chosen
        releaseDate.setOnMouseClicked(e -> {
            if (releaseDate.isShowing())
                releaseDate.hide();
        });
    }

    /**
     * Updates relevant information
     * @param selectedProduction Any Production object
     */
    private void updateInfo(Production selectedProduction) {
        titleLabel.setText(selectedProduction.getTitle());
        description.setText(selectedProduction.getDescription());
        releaseDate.setValue(selectedProduction.getReleaseDate());
        runtime.setText(selectedProduction.getRuntimeHrsAndMins());
    }

    /**
     * Opens addMovie window
     * @param actionEvent
     */
    public void goToAddMovie(ActionEvent actionEvent) {
        ExecGui.prodSys.addMovie();
    }

    /**
     * Deletes selected movie
     * @param actionEvent
     */
    public void deleteMovie(ActionEvent actionEvent) {
        if(!movieList.getItems().isEmpty()){
            movieList.getItems().remove(movieList.getSelectionModel().getSelectedIndex());
        }
        else {
            ExecGui.prodSys.showError("Stop deleting stuff that isn't there man..");
        }
    }

    public void editMovie(ActionEvent actionEvent) {
        if(!movieList.getSelectionModel().isEmpty()) {
            ExecGui.prodSys.editMovies();
        }
    }
}