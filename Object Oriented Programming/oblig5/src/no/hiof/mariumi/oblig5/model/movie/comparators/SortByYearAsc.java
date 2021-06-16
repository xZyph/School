package no.hiof.mariumi.oblig5.model.movie.comparators;

import no.hiof.mariumi.oblig5.model.movie.Movie;

import java.util.Comparator;

public class SortByYearAsc extends MovieComparator {
    public SortByYearAsc() {
        setSortingName("Year \u23F6");
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        return o1.getYear().compareTo(o2.getYear());
    }

}
