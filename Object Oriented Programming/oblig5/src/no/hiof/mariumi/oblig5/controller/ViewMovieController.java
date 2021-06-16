package no.hiof.mariumi.oblig5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import no.hiof.mariumi.oblig5.ExecMain;
import no.hiof.mariumi.oblig5.customcomponents.navitem.NavItem;
import no.hiof.mariumi.oblig5.model.movie.Movie;
import no.hiof.mariumi.oblig5.model.movie.cells.MovieListCell;
import no.hiof.mariumi.oblig5.model.movie.comparators.*;
import java.util.ArrayList;

public class ViewMovieController {
    private int SORTINGSTATE = 0;

    // GUI Elements
    @FXML
    private ListView<Movie> movieList;
    @FXML
    private Label movieTitle;
    @FXML
    private TextArea description;
    @FXML
    private TextField releaseDate;
    @FXML
    private TextField runtime;
    @FXML
    private NavItem btnNew;
    @FXML
    private NavItem btnEdit;
    @FXML
    private NavItem btnDelete;
    @FXML
    private NavItem btnDeleteAll;
    @FXML
    private NavItem btnAddJson;
    @FXML
    private ImageView moviePoster;
    @FXML
    private AnchorPane imdbPane;
    @FXML
    private Label imdbRating;
    @FXML
    private HBox posterContainer;
    @FXML
    private NavItem btnSort;

    /**
     *
     */
    @FXML
    public void initialize() {
        initialize_MovieList();
        initialize_Buttons();
    }

    /**
     *
     */
    private void initialize_MovieList() {
        // Updating info-section based on item chosen.
        movieList.setItems(ExecMain.prodSys.getMovieList());

        movieList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ExecMain.prodSys.MOVIE_SELECTOR = movieList.getSelectionModel().getSelectedIndex();
                updateInfo(newValue);
            }
        }));

        // Selecting first item automatically, unless there is no items.
        if (!movieList.getItems().isEmpty()) {
            movieList.getSelectionModel().select(ExecMain.prodSys.MOVIE_SELECTOR);
        }

        movieList.setCellFactory(new Callback<ListView<Movie>, ListCell<Movie>>() {
            @Override
            public MovieListCell<Movie> call(ListView<Movie> param) {
                return new MovieListCell<>();
            }
        });
    }

    /**
     * Initializes all button events.
     */
    private void initialize_Buttons() {
        // NEW BUTTON
        btnNew.setSymbol("α");
        btnNew.setLabel("Add");
        btnNew.setBackgroundColor("transparent");
        btnNew.getButton().setOnMouseClicked(click -> ExecMain.prodSys.addMovie());

        // EDIT BUTTON
        btnEdit.setSymbol("ε");
        btnEdit.setLabel("Edit");
        btnEdit.setBackgroundColor("transparent");
        btnEdit.getButton().setOnMouseClicked(click -> {
            if (!movieList.getSelectionModel().isEmpty()) {
                ExecMain.prodSys.editMovies();
            }
        });

        // DELETE BUTTON
        btnDelete.setSymbol("δ");
        btnDelete.setLabel("Delete");
        btnDelete.setBackgroundColor("transparent");
        btnDelete.getButton().setOnMouseClicked(click -> {
            if (!movieList.getItems().isEmpty()) {
                movieList.getItems().remove(movieList.getSelectionModel().getSelectedIndex());
            } else {
                ExecMain.prodSys.showError("Stop deleting stuff that isn't there man..");
            }
        });

        // DELETE ALL BUTTON
        btnDeleteAll.setSymbol("Δ");
        btnDeleteAll.setSymbolColor("rgb(214, 50, 113)");
        btnDeleteAll.setLabel("Delete All");
        btnDeleteAll.setBackgroundColor("transparent");
        btnDeleteAll.getButton().setOnMouseClicked(click -> {
            movieList.getItems().removeAll(movieList.getItems());
        });

        // MOVIE COMPARATORS
        ArrayList<MovieComparator> comparators = new ArrayList<>();
        comparators.add(new SortByTitleAsc());
        comparators.add(new SortByTitleDesc());
        comparators.add(new SortByYearAsc());
        comparators.add(new SortByYearDesc());
        comparators.add(new SortByImdbAsc());
        comparators.add(new SortByImdbDesc());

        // SORT BUTTON
        btnSort.setSymbol("Σ");
        btnSort.setBackgroundColor("transparent");
        btnSort.getButton().setOnMouseClicked(click -> {
            if (SORTINGSTATE == comparators.size())
                SORTINGSTATE = 0;
            btnSort.setLabel(comparators.get(SORTINGSTATE).getSortingName());
            movieList.getItems().sort(comparators.get(SORTINGSTATE));

            SORTINGSTATE++;
        });

        // IMPORT JSON BUTTON
        btnAddJson.setSymbol("+");
        btnAddJson.setLabel("Import JSON");
        btnAddJson.setBackgroundColor("transparent");
        btnAddJson.getButton().setOnMouseClicked(click -> {
            ExecMain.prodSys.addJson();
        });
    }

    /**
     * Updates the information section with information from the chosen movie.
     *
     * @param movie Any movie of the Movie class.
     */
    private void updateInfo(Movie movie) {
        Image movieImage = movie.determinePoster("w500");

        if(movie.getImdbID() != null) {
            imdbPane.setVisible(true);
            imdbRating.setText(movie.getImdbRating() + " / 10");
        }
        else
            imdbPane.setVisible(false);

        moviePoster.setImage(movieImage);
        moviePoster.fitHeightProperty().bind(posterContainer.heightProperty());

        movieTitle.setText(movie.getTitle());
        description.setText(movie.getPlot());
        releaseDate.setText(movie.getReleased());
        runtime.setText(movie.getRuntimeHrsAndMins());
    }
}