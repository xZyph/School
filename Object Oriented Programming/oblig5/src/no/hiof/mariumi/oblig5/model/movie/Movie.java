package no.hiof.mariumi.oblig5.model.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.scene.image.Image;
import no.hiof.mariumi.oblig5.customclients.OMDB_Query;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movie implements Comparable<Movie> {
    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Year")
    @Expose
    private Integer year;

    @SerializedName("Released")
    @Expose
    private String released;

    @SerializedName("Runtime")
    @Expose
    private String runtime;

    @SerializedName("Plot")
    @Expose
    private String plot;

    @SerializedName("Poster")
    @Expose
    private String poster;

    @SerializedName("Metascore")
    @Expose
    private String metascore;

    @SerializedName("imdbRating")
    @Expose
    private Float imdbRating;

    @SerializedName("imdbVotes")
    @Expose
    private String imdbVotes;

    @SerializedName("imdbID")
    @Expose
    private String imdbID;

    @SerializedName("Type")
    @Expose
    private String type;

    @SerializedName("Actor")
    @Expose
    private String[] actor;

    public Movie(String title, String released, String runtime, String plot, Integer year) {
        this.title = title;
        this.year = year;
        this.released = released;
        this.runtime = runtime;
        this.plot = plot;
    }

    @Override
    public int compareTo(Movie o) {
        return getTitle().compareTo(o.getTitle());
    }

    @Override
    public String toString() {
        return title + " (" + year + ")";
    }

    public LocalDate getLocalDate() {
        String[] inp = this.getReleased().split(" ");
        String[] months = {"jan", "feb", "mar", "apr", "jun", "jul", "aug", "sep", "oct", "nov", "des"};

        for(int i = 0; i < months.length; i++) {
            if(months[i].equalsIgnoreCase(inp[1]))
                inp[1] = Integer.toString(i);
        }

        return LocalDate.of(Integer.parseInt(inp[2]), Integer.parseInt(inp[1]), Integer.parseInt(inp[0]));
    }

    public String getRuntimeHrsAndMins() {
        return (getRuntime() / 60) + "hrs " + (getRuntime() % 60) + "min";
    }

    public String convertToImdbDate(LocalDate value) {
        return value.format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
    }

    public boolean hasPoster() {
        return poster != null;
    }

    public void updateBasedOnTitle() {
        LocalDate date = LocalDate.parse(released);
        this.released = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        this.year = date.getYear();
    }

    public Image determinePoster(String size) {
        HttpURLConnection url;
        Image image = new Image(getClass().getResourceAsStream("../../media/na.png"));

        if(hasPoster() && OMDB_Query.isOnline()) {
            try {
                url = (HttpURLConnection) new URL(getPoster()).openConnection();

                if(url.getResponseCode() == 200)
                    image = new Image(getPoster());
            }
            catch (IOException io) {
                try {
                    // Might be stored as /xxxxx.jpg
                    url = (HttpURLConnection) new URL("https://image.tmdb.org/t/p/" + size + getPoster()).openConnection();

                    if(url.getResponseCode() == 200)
                        image = new Image("https://image.tmdb.org/t/p/" + size + getPoster());
                }
                catch (IOException doesntExist) {
                    // Do nothing...
                }
            }
        }

        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public int getRuntime() {
        return Integer.parseInt(runtime.replaceAll("[^\\d.]", ""));
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public Float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }
}
