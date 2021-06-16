package no.hiof.mariumi.oblig5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Callback;
import no.hiof.mariumi.oblig5.ExecMain;
import no.hiof.mariumi.oblig5.customcomponents.numberfield.NumberField;
import no.hiof.mariumi.oblig5.model.movie.Movie;
import no.hiof.mariumi.oblig5.model.movie.SearchResult;
import no.hiof.mariumi.oblig5.model.movie.cells.SearchResultCell;
import no.hiof.mariumi.oblig5.customclients.OMDB_Query;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddMovieController {
    private static final String MAIN_COLOR = "-fx-ackground-color: rgba(52, 114, 216, 0.7);";
    private static final String MAIN_COLOR_HOVER = "-fx-background-color: rgba(52, 114, 216, 0.4);";

    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private DatePicker releaseDate;
    @FXML
    private NumberField runtime;
    @FXML
    private Button btnDismiss;
    @FXML
    private Button btnSave;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Movie> searchResults;

    @FXML
    public void initialize() {
        initializeButtons();
        initializeSearch();
    }

    /**
     *
     */
    private void initializeButtons() {
        // BUTTON ACTIONS
        btnSave.setOnMouseClicked(e -> {
            saveData();
        });

        btnDismiss.setOnMouseClicked(e -> {
            closeStage();
        });
    }

    /**
     *
     */
    private void initializeSearch() {
        searchResults.setCellFactory(new Callback<ListView<Movie>, ListCell<Movie>>() {
            @Override
            public SearchResultCell<Movie> call(ListView<Movie> param) {
                return new SearchResultCell<>();
            }
        });

        searchResults.setOnKeyPressed(key -> {
            if(key.getCode().equals(KeyCode.ENTER))
                addMovieToList(searchResults.getSelectionModel().getSelectedItem().getImdbID());
        });

        searchResults.setOnMouseClicked(click -> {
            if(click.getClickCount() == 2) {
                addMovieToList(searchResults.getSelectionModel().getSelectedItem().getImdbID());
            }
        });

        searchField.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                searchResults.getItems().removeAll(searchResults.getItems());
                SearchResult movies = ExecMain.gson.fromJson(OMDB_Query.searchMovieByTitle(searchField.getText()), SearchResult.class);

                if(movies.getResponse()) {
                    searchResults.setMinHeight((Math.min(movies.getTotalResults(), 5) * 45));
                    for (int i = 0; i < Math.min(movies.getTotalResults(), 5) ; i++) {
                        searchResults.getItems().add(movies.getSearch()[i]);
                    }
                    searchResults.setVisible(true);
                }
            }
            else {
                searchResults.setVisible(false);
            }
        });
    }

    /**
     *
     */
    private void saveData() {
        try {
            if(title.getText().isEmpty())
                throw new Error("Dear sir/madam, the movie your are trying to add really needs a title...");
            if(description.getText().isEmpty())
                throw new Error("It is common courtesy to add a description when adding a movie.");
            if(releaseDate.getValue() == null)
                throw new Error("You seriously forgot to try the awesome JavaFX DatePicker?!");
            if(runtime.getText().isEmpty() || Integer.parseInt(runtime.getText()) <= 0)
                throw new Error("Who would watch a movie without a runtime?");
            if(Integer.parseInt(runtime.getText()) >= 6000)
                throw new Error("Say what? I don't want movies that last that long...");

            Movie newObj = new Movie(
                    title.getText(),
                    convertToImdbDate(releaseDate.getValue()),
                    runtime.getText(),
                    description.getText(),
                    releaseDate.getValue().getYear()
            );

            ExecMain.prodSys.getMovieList().add(newObj);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        }
        catch (Error e) {
            ExecMain.prodSys.showError(e.getLocalizedMessage());
        }
    }

    private void addMovieToList(String imdbID) {
        Movie movie = ExecMain.gson.fromJson(OMDB_Query.fetchMovie(imdbID), Movie.class);
        ExecMain.prodSys.addMovieToList(movie);
        closeStage();
    }

    private String convertToImdbDate(LocalDate value) {
        return value.format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
    }

    private void closeStage() {
        Stage stage = (Stage) btnDismiss.getScene().getWindow();
        stage.close();
    }
}
