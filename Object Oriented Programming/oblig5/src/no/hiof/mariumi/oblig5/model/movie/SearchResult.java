package no.hiof.mariumi.oblig5.model.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import no.hiof.mariumi.oblig5.model.movie.Movie;

public class SearchResult {
    @SerializedName("Search")
    @Expose
    private Movie[] search;

    @SerializedName("totalResults")
    @Expose
    private int totalResults;

    @SerializedName("Error")
    @Expose
    private String error;

    @SerializedName("Response")
    @Expose
    private boolean response;



    public Movie[] getSearch() {
        return search;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public String getError() {
        return error;
    }

    public boolean getResponse() {
        return response;
    }
}
