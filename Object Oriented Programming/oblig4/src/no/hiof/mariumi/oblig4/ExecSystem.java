package no.hiof.mariumi.oblig4;

/* IMPORTS */

import no.hiof.mariumi.oblig4.model.Episode;
import no.hiof.mariumi.oblig4.model.Movie;
import no.hiof.mariumi.oblig4.model.Series;

/**
 * ITF10611 - OOP
 * Oblig 4
 *
 * @author Marius Selvfölgelig Mikelsen
 * @version 0.1
 *
 * TODO:
 *   -
 */

public class ExecSystem {

    public static void main(String[] args) {
        // MOVIES
        new Movie("The Matrix");
        new Movie("Inception");
        new Movie("Se7en");
        new Movie("Gladiator");
        new Movie("The Prestige");

        System.out.println("\nSORTED MOVIES:\n");
        for(Movie m : Movie.getMovieList()) {
            System.out.println(m);
        }

        // SERIES (Also adding episodes so season count works in the outputted list).
        new Series("Quantum Leap");
        new Series("Rick & Morty");
        Series firefly = new Series("Firefly");

        firefly.addEpisode(new Episode(1, 5));
        firefly.addEpisode(new Episode(1, 2));
        firefly.addEpisode(new Episode(1, 3));
        firefly.addEpisode(new Episode(1, 1));
        firefly.addEpisode(new Episode(1, 4));

        System.out.println("\nSORTED SERIES:\n");
        for(Series s : Series.seriesList) {
            System.out.println(s);
        }

        // EPISODES

        System.out.println("\nSORTED EPISODES:\n");
        for(Episode e : firefly.getEpisodeList()) {
            System.out.println(e);
        }
    }
}
