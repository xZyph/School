package no.hiof.mariumi.oblig5.customcomponents.numberfield;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NumberFieldFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        NumberField numberField = new NumberField();
        numberField.setLayoutX(10.0);
        numberField.setLayoutY(10.0);

        Scene scene = new Scene(numberField);
        primaryStage.setTitle("NumberField");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
