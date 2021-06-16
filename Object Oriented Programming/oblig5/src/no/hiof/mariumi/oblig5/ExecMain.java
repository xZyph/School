package no.hiof.mariumi.oblig5;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.mariumi.oblig5.model.movie.Movie;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

/**
 * ITF10611 - OOP
 * Oblig 5
 *
 * @author Marius Selvfölgelig Mikelsen
 * @version 0.1
 *
 * Welcome to the mess!
 *
 * Task 3:
 *          Added import-button to navbar.
 *          Proper errorhandling is not added, so follow the serialized id's from Movie.java
 *          The list is automatically saved every time the application closes normally.
 *
 * Task 4:
 *          Wrong path will not usually happen with a filepicker.
 *          If currentMovieList.json doesn't exist, it gets created.
 *
 * Task 5:
 *          Demonstrated in:
 *              no.hiof.mariumi.oblig5.model.movie.cells.MovieListCell
 *              no.hiof.mariumi.oblig5.model.movie.cells.SearchResultCell
 *
 * Task 6:
 *          Demonstrated in:
 *              no.hiof.mariumi.oblig5.controller.ViewMovieController
 *              no.hiof.mariumi.oblig5.controller.EditMovieController
 *              no.hiof.mariumi.oblig5.model.movie.cells.MovieListCell
 *              no.hiof.mariumi.oblig5.model.movie.cells.SearchResultCell
 *
 * Task 7:
 *          Added as OnMouseClicked lambda expression in:
 *              no.hiof.mariumi.oblig5.controller.ViewMovieController
 *
 * Task 8:
 *          Series and Production classes are removed for cleanliness.
 *          These classes would not be constructed as they were, if they were built to fit this application.
 *
 * Task 9:
 *          Really? That's alot of work...
 *
 * Task 10:
 *          Demonstrated this in the cell classes from Task 5-6.
 *
 * Task 11:
 *          If I get time..
 *
 * Task 12:
 *          Series is excluded from the program.
 *          Adding this would not take that much time though.
 *
 * Task 13:
 *          Same as above.
 *
 * Task X1:
 *          Movies can be added directly from IMDB through my custom client.
 *          If movie is added from IMDB, an additional IMDB rating is shown in the information node.
 *
 *
 * SHOULD-DO:
 *   - Add code to restrict shown title length in information-node, to width of parent node (Title overlaps imdbPane).
 *   - Add confirmation to delete and delete-all buttons.
 *   - Add automatic search for imdbID's based on titles, during import from JSON.
 *   - Optimize everywhere, program is slow as heck!
 *
 */
public class ExecMain extends Application{
    public static ExecMain prodSys;
    public static int MOVIE_SELECTOR = 0;
    public static Gson gson = new Gson();


    private String CSS_MAIN = getClass().getResource("main.css").toExternalForm();
    private final String SAVEPATH_MOVIELIST = getClass().getResource("./db/").getPath() + "currentMovieList.json";
    private static Stage primaryStage;
    private ObservableList<Movie> movieList = FXCollections.observableArrayList();

    public ExecMain() {
        prodSys = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream( "media/icon.png" )));

        loadMovieList();
        viewMovies();
    }

    @Override
    public void stop() throws Exception {
        saveCurrentData();
        super.stop();
    }

    /**
     *  Supermethod to collect all save-methods.
     */
    private void saveCurrentData() {
        saveMovieList();
    }

    /**
     *  Save method to save existing ListView in currentMovieList.json
     */
    private void saveMovieList() {
        BufferedWriter save = null;

        try {
            String path = getClass().getResource("./db/").getPath() + "currentMovieList.json";
            File saveData = new File(path);

            if(!saveData.exists())
                saveData.createNewFile();

            FileWriter saveWriter = new FileWriter(saveData, false);
            save = new BufferedWriter(saveWriter);
            save.write(gson.toJson(movieList));
            save.flush();
        }
        catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if(save != null)
                    save.close();
            }
            catch (Exception x) {
                System.out.println(x.getLocalizedMessage());
            }
        }
    }

    /**
     * Loading the ListView with movies from currentMovieList.json
     */
    private void loadMovieList() {
        File saveFile = new File(SAVEPATH_MOVIELIST);

        Gson gson = new Gson();
        Type listType = new TypeToken<ObservableList<Movie>>(){}.getType();

        try {
            List<Movie> listFromSave = gson.fromJson(new JsonReader(new FileReader(saveFile)), listType);
            movieList.addAll(listFromSave);

        }
        catch (FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     *  Loads the View Movies fxml into primaryStage.
     */
    public void viewMovies() {
        Parent viewMoviesFXML = null;

        try {
            viewMoviesFXML = new FXMLLoader().load(getClass().getResource("view/ViewMovies.fxml"));
        }
        catch (IOException e) {
            showError(e.getMessage());
        }

        Scene viewMovies = new Scene(viewMoviesFXML);
        viewMovies.getStylesheets().add(CSS_MAIN);

        primaryStage.setScene(viewMovies);
        primaryStage.setTitle("prodSys - keep track of your addiction to movies!");
        primaryStage.show();
    }

    /**
     * Loads the Edit Movies fxml into primaryStage.
     */
    public void editMovies() {
        Parent editMoviesFXML = null;

        try {
            editMoviesFXML = new FXMLLoader().load(getClass().getResource("view/EditMovies.fxml"));
        }
        catch (IOException e) {
            showError(e.getMessage());
        }

        Scene editMovies = new Scene(editMoviesFXML);
        editMovies.getStylesheets().add(CSS_MAIN);

        primaryStage.setScene(editMovies);
        primaryStage.setTitle("Movie Overview");
        primaryStage.show();
    }

    /**
     * Loads the Add Movies fxml into new stage owned by primaryStage.
     */
    public void addMovie() {
        Parent addMovieFXML = null;

        try {
            addMovieFXML = FXMLLoader.load(getClass().getResource("./view/AddMovie.fxml"));
        }
        catch (IOException e) {
            showError(e.getLocalizedMessage());
        }

        Stage newMovie = new Stage();
        Scene addMovie = new Scene(addMovieFXML);
        addMovie.getStylesheets().add(CSS_MAIN);

        newMovie.setScene(addMovie);
        newMovie.initOwner(primaryStage);
        newMovie.initModality(Modality.WINDOW_MODAL);
        newMovie.showAndWait();
    }

    /**
     * Starts a FilePicker to choose .json-file for import.
     */
    public void addJson() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose JSON file to import.");
        fileChooser.setInitialDirectory(new File(getClass().getResource("./db/").getFile()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON File", "*.json"));
        File fileChosen = fileChooser.showOpenDialog(primaryStage);

        if(fileChosen != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ObservableList<Movie>>(){}.getType();

            try {
                List<Movie> listFromSave = gson.fromJson(new JsonReader(new FileReader(fileChosen)), listType);

                for (Movie x : listFromSave) {
                    if(x.getYear() == null)
                        x.updateBasedOnTitle();
                }

                movieList.addAll(listFromSave);
            } catch (FileNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    /**
     *  Adds a Movie object to the ListView
     * @param movie
     */
    public void addMovieToList(Movie movie) {
        movieList.add(movie);
    }

    /**
     * Creates an alert with the supplied message
     * @param msg
     */
    public void showError(String msg) {
        Alert e = new Alert(Alert.AlertType.ERROR);
        e.setTitle("Wait.. what? There seems to be an error here...");
        e.setHeaderText(null);
        e.setContentText(msg);
        e.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Movie> getMovieList() {
        return movieList;
    }
}
