package no.hiof.mariumi.oblig4.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import no.hiof.mariumi.oblig4.ExecGui;
import no.hiof.mariumi.oblig4.model.Movie;
import no.hiof.mariumi.oblig4.model.Production;

public class AddMovieController {

    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private DatePicker releaseDate;
    @FXML
    private TextField runtime;
    @FXML
    private Button btnDismiss;
    @FXML
    private Button btnSave;

    @FXML
    public void initialize() {
    }

    @FXML
    private void dismiss(ActionEvent actionEvent) {
        Stage stage = (Stage) btnDismiss.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveData(ActionEvent actionEvent) {
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

            Production newObj = new Movie(
                    title.getText(),
                    description.getText(),
                    releaseDate.getValue(),
                    Integer.parseInt(runtime.getText())
            );

            ExecGui.prodSys.getMovieList().add(newObj);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        }
        catch (Error e) {
            ExecGui.prodSys.showError(e.getLocalizedMessage());
        }
    }
}
