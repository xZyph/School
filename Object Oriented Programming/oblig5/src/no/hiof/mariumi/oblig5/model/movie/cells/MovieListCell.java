package no.hiof.mariumi.oblig5.model.movie.cells;

import javafx.application.Platform;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import no.hiof.mariumi.oblig5.model.movie.Movie;

/**
 * Specific cell design for movies. Loads images in new thread.
 */
public class MovieListCell<Mov> extends ListCell<Movie> {
    @Override
    protected void updateItem(Movie movie, boolean empty) {
        Runnable imageParser;

        super.updateItem(movie, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            setText(movie.getTitle() + " (" + movie.getYear() + ")");
            setPrefHeight(30);

            imageParser = new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageView = new ImageView(movie.determinePoster("w45"));
                            imageView.setFitHeight(22.25);
                            imageView.setPreserveRatio(true);

                            setGraphic(imageView);
                        }
                    });
                }
            };

            new Thread(imageParser).start();
        }
    }
}