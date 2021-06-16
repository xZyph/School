package no.hiof.mariumi.oblig5.model.movie.cells;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import no.hiof.mariumi.oblig5.model.movie.Movie;

/**
 * Specific cell design for movies.
 */
public class SearchResultCell<Mov> extends ListCell<Movie> {

    @Override
    protected void updateItem(Movie item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            setText(item.getTitle() + " (" + item.getYear() + ")");

            ImageView imageView = new ImageView(item.determinePoster(""));
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);

            setGraphic(imageView);
        }
    }


}