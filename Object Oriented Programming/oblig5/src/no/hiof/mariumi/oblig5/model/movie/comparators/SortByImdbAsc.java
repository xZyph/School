package no.hiof.mariumi.oblig5.model.movie.comparators;

import no.hiof.mariumi.oblig5.model.movie.Movie;

import java.util.Comparator;

public class SortByImdbAsc extends MovieComparator {
    public SortByImdbAsc() {
        setSortingName("IMDB \u23F6");
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        return o1.getImdbRating().compareTo(o2.getImdbRating());
    }
}
