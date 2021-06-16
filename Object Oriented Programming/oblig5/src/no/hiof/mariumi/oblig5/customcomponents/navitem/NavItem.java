package no.hiof.mariumi.oblig5.customcomponents.navitem;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class NavItem extends AnchorPane {

    private Button button;
    private Label label;

    public NavItem() {
        button = new Button();
        label =  new Label();

        setBottomAnchor(this.label, 10.0);

        this.label.setText("Sort");
        this.label.setPrefSize(75, 15);
        this.label.setAlignment(Pos.CENTER);

        this.button.setText("⚌");
        this.button.toFront();
        this.button.setCursor(Cursor.HAND);
        this.button.setPrefSize(75, 75);

        getChildren().add(this.label);
        getChildren().add(this.button);

    }



    public void setBackgroundColor(String color) {
        setStyle("-fx-background-color: " + color + ";");
    }

    public void setSymbolColor(String color) {
        button.setStyle("-fx-text-fill: " + color + ";");
    }

    public void setTextColor(String color) {
        label.setStyle("-fx-text-fill: " + color + ";");
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public void setSymbol(String symbol) {
        button.setText(symbol);
    }

    public Button getButton() {
        return button;
    }
}