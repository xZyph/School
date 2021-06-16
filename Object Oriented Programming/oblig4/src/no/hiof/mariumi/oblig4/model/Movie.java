package no.hiof.mariumi.oblig4.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Born from the bones of the abstract Production class, we get the Movie class.
 * This class represents all movies, in the conventional sense.
 *
 * @author Marius Selvfölgelig Mikelsen
 * @version 0.1
 */
public class Movie extends Production implements Comparable<Movie> {
    public static ArrayList<Movie> movieList = new ArrayList<>();

    /* CONSTRUCTORS */

    /**
     * Creates a generic movie object, including random runtime.
     */
    public Movie() {
        super("N/A", ThreadLocalRandom.current().nextInt(80, 220 + 1));
        movieList.add(this);
        Collections.sort(movieList);
    }

    /**
     * Creates a generic movie with a specified title.
     * @param title
     */
    public Movie(String title) {
        super(title, ThreadLocalRandom.current().nextInt(80, 220 + 1));
        movieList.add(this);
        Collections.sort(movieList);
    }

    /**
     * Creates a movie object with title and runtime provided.
     *
     * @param title of movie.
     * @param runtime Length of movie in minutes.
     */
    public Movie(String title, int runtime) {
        super(title, runtime);
        movieList.add(this);
        Collections.sort(movieList);
    }

    public Movie(String title, String description, LocalDate releaseDate, int runtime) {
        super(title, description, releaseDate, runtime);
        movieList.add(this);
        Collections.sort(movieList);
    }

    /* GETTERS */
    public static ArrayList<Movie> getMovieList() {
        return movieList;
    }

    /* SETTERS */

    /* OVERRIDES */

    @Override
    public String toString() {
        return getTitle() + " (" + getReleaseDate().getYear() + ")";
    }

    @Override
    public int compareTo(Movie o) {
        return getTitle().toUpperCase().compareTo(o.getTitle().toUpperCase());
    }
}
