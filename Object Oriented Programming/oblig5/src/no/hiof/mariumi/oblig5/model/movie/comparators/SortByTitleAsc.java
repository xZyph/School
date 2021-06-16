package no.hiof.mariumi.oblig5.model.movie.comparators;

import no.hiof.mariumi.oblig5.model.movie.Movie;

import java.util.Comparator;

public class SortByTitleAsc extends MovieComparator {
    public SortByTitleAsc() {
        setSortingName("Title \u23F6");
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
