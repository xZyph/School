package no.hiof.mariumi.oblig5.model.movie.comparators;

import no.hiof.mariumi.oblig5.model.movie.Movie;

import java.util.Comparator;

public abstract class MovieComparator implements Comparator<Movie> {
    private String SortingName;

    public String getSortingName() {
        return SortingName;
    }

    public void setSortingName(String sortingName) {
        this.SortingName = sortingName;
    }
}
