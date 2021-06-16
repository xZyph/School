package no.hiof.mariumi.oblig5.model.movie.comparators;

import no.hiof.mariumi.oblig5.model.movie.Movie;

import java.util.Comparator;

public class SortByTitleDesc extends MovieComparator {
    public SortByTitleDesc() {
        setSortingName("Title \u23F7");
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        int usually = o1.getTitle().compareTo(o2.getTitle());

        if(usually == 0)
            return usually;

        else
            return usually > 0 ? -1 : 1;
    }
}
