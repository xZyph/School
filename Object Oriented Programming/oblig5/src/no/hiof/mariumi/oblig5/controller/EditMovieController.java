package no.hiof.mariumi.oblig5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import no.hiof.mariumi.oblig5.ExecMain;
import no.hiof.mariumi.oblig5.customcomponents.numberfield.NumberField;
import no.hiof.mariumi.oblig5.model.movie.Movie;

import java.time.format.DateTimeFormatter;

public class EditMovieController {

    // GUI Elements
    @FXML
    private ListView<Movie> movieList;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private DatePicker releaseDate;
    @FXML
    private NumberField runtime;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @FXML
    public void initialize() {
        // Updating fields based on item chosen.
        movieList.setItems(ExecMain.prodSys.getMovieList());

        // Selecting first item automatically, unless there is no items.
        if(!movieList.getItems().isEmpty()) {
            movieList.getSelectionModel().select(ExecMain.prodSys.MOVIE_SELECTOR);
        }
        else {
            ExecMain.prodSys.viewMovies();
            ExecMain.prodSys.showError("Something went wrong with the list...");
        }

        updateInfo(movieList.getSelectionModel().getSelectedItem());

        // BUTTON ACTIONS
        btnCancel.setOnMouseClicked(e -> {
            ExecMain.prodSys.viewMovies();
        });

        btnSave.setOnMouseClicked(e -> {
            saveMovie();
        });
    }

    /**
     *
     * @param selectedProduction
     */
    private void updateInfo(Movie selectedProduction) {
        titleLabel.setText(selectedProduction.getTitle());
        title.setText(selectedProduction.getTitle());
        description.setText(selectedProduction.getPlot());
        releaseDate.setValue(selectedProduction.getLocalDate());
        runtime.setText(Integer.toString(selectedProduction.getRuntime()));
    }

    /**
     * Exiting editmode and storing data.
     */
    public void saveMovie() {
        try {
            if(title.getText().isEmpty())
                throw new Error("The movie needs a title...");
            if(description.getText().isEmpty())
                throw new Error("It's common courtesy to include a description...");
            if(releaseDate.getValue() == null)
                throw new Error("Ooops, forgot the release date..");
            if(runtime.getText().isEmpty() || Integer.parseInt(runtime.getText()) <= 0)
                throw new Error("Did you forget the runtime perhaps?");
            if(Integer.parseInt(runtime.getText()) >= 6000)
                throw new Error("You expect me to believe that this movie is over a hundred hours long?!");

            updateData();
            ExecMain.prodSys.viewMovies();
        }
        catch (Error e) {
            ExecMain.prodSys.showError(e.getMessage());
        }
    }

    /**
     *
     */
    public void updateData() {
        movieList.getSelectionModel().getSelectedItem().setTitle(title.getText());
        movieList.getSelectionModel().getSelectedItem().setPlot(description.getText());
        movieList.getSelectionModel().getSelectedItem().setReleased(releaseDate.getValue().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
       movieList.getSelectionModel().getSelectedItem().setRuntime(runtime.getText());
    }
}
