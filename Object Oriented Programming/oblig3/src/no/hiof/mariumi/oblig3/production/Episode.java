package no.hiof.mariumi.oblig3.production;

/* IMPORTS */

import no.hiof.mariumi.oblig3.production.Production;
import no.hiof.mariumi.oblig3.production.Persona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * These ones share many of the same characteristics as a movie,
 * but they usually are much smaller in size and travel in groups.
 * This behaviour may some times call for different functionality.
 *
 * @author Marius Selvfölgelig Mikelsen
 * @version 0.1
 */
public class Episode extends Production {
    private int episodeNo;
    private int seasonNo;

    /* CONSTRUCTORS */

    /**
     * Creates a dummy episode that produces errors.
     */
    public Episode() {
        super( -1);
        setEpisodeNo(-1);
        setSeasonNo(-1);
    }

    /**
     * Creates a specific episode from a specific season.
     *
     * @param seasonNo
     * @param episodeNo
     */
    public Episode(int seasonNo, int episodeNo) {
        super(ThreadLocalRandom.current().nextInt(20, 30 + 1));
        setEpisodeNo(episodeNo);
        setSeasonNo(seasonNo);
    }

    /**
     * Creates a specific episode from a specific season with releaseDate.
     *
     * @param seasonNo
     * @param episodeNo
     * @param releaseDate
     */
    public Episode(int seasonNo, int episodeNo, LocalDate releaseDate) {
        super(ThreadLocalRandom.current().nextInt(20, 30 + 1));
        setEpisodeNo(episodeNo);
        setSeasonNo(seasonNo);
        setReleaseDate(releaseDate);
    }

    /**
     * Creates a specific episode from a specific season with characters included.
     *
     * @param seasonNo
     * @param episodeNo
     * @param characters
     */
    public Episode(int seasonNo, int episodeNo, ArrayList<Persona> characters) {
        super(ThreadLocalRandom.current().nextInt(20, 30 + 1));
        setEpisodeNo(episodeNo);
        setSeasonNo(seasonNo);
        setCast(characters);
    }

    /**
     * Creates a specific episode from a specific season with title and duration.
     *
     * @param episodeNo
     * @param seasonNo
     * @param title
     * @param duration in minutes
     */
    public Episode(int episodeNo, int seasonNo, String title, int duration) {
        super(duration);
        setEpisodeNo(episodeNo);
        setSeasonNo(seasonNo);
        setTitle(title);
        setRuntime(duration);
    }

    /* GETTERS */

    public int getEpisodeNo() {
        return episodeNo;
    }

    public int getSeasonNo() {
        return seasonNo;
    }

    /* SETTERS */

    public void setEpisodeNo(int episodeNo){
        try {
            if (episodeNo < 0) {
                throw new Error("Episode number cannot be negative.");
            }
            this.episodeNo = episodeNo;
        }
        catch(Error e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }

    public void setSeasonNo(int seasonNo) {
        try {
            if(seasonNo < 1){
                throw new Error("Season value cannot be negative.");
            }
            this.seasonNo = seasonNo;
        }
        catch (Error e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }

    /* OVERRIDES */

    @Override
    public String toString() {
        return getTitle() + " (S" + (seasonNo < 10 ? "0" + seasonNo : seasonNo) + "E" + (episodeNo < 10 ? "0" + episodeNo : episodeNo) + ") " + getRuntime() + " min";
    }
}

/* METHODS */
