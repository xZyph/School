package no.hiof.mariumi.oblig5.model.movie.comparators;

import no.hiof.mariumi.oblig5.model.movie.Movie;

import java.util.Comparator;

public class SortByImdbDesc extends MovieComparator {
    public SortByImdbDesc() {
        setSortingName("IMDB \u23F7");
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        int usually = o1.getImdbRating().compareTo(o2.getImdbRating());

        if(usually == 0)
            return usually;

        else
            return usually > 0 ? -1 : 1;
    }
}
