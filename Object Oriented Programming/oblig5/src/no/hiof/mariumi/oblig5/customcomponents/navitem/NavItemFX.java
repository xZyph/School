package no.hiof.mariumi.oblig5.customcomponents.navitem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavItemFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        NavItem newButton = new NavItem();

        Scene scene = new Scene(newButton);
        primaryStage.setTitle("SortButton");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
