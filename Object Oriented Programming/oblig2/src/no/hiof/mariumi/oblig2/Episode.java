package no.hiof.mariumi.oblig2;

/* IMPORTS */
// import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Episode {
    private int episodeNo;
    private int seasonNo;
    private String title;
    private int duration;

    /* CONSTRUCTORS */

    public Episode() {
        setEpisodeNo(-1);
        setSeasonNo(1);
        setTitle("N/A");
        setDuration(4);
    }

    // Changed from Random to ThreadLocalRandom
    public Episode(int seasonNo) {
        setEpisodeNo(1);
        setSeasonNo(seasonNo);
        setTitle("N/A");
        setDuration(ThreadLocalRandom.current().nextInt(20, 30 + 1));
        // setDuration(new Random().nextInt(30 - 20 + 1) + 20);
    }

    // Changed from Random to ThreadLocalRandom
    public Episode(int seasonNo, int episodeNo) {
        setEpisodeNo(episodeNo);
        setSeasonNo(seasonNo);
        setTitle("N/A");
        setDuration(ThreadLocalRandom.current().nextInt(20, 30 + 1));
        // setDuration(new Random().nextInt(30 - 20 + 1) + 20);
    }

    public Episode(int episodeNo, int seasonNo, String title, int duration) {
        setEpisodeNo(episodeNo);
        setSeasonNo(seasonNo);
        setTitle(title);
        setDuration(duration);
    }

    /* GETTERS */

    public int getEpisodeNo() {
        return episodeNo;
    }

    public int getSeasonNo() {
        return seasonNo;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    /* SETTERS */

    public void setEpisodeNo(int episodeNo){
        try {
            if (episodeNo < 0) {
                throw new Error("Episode number cannot be below 1.");
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
                throw new Error("Season value cannot be below 1.");
            }
            this.seasonNo = seasonNo;
        }
        catch (Error e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Minimum playtime is set to 5 minutes.
    public void setDuration(int duration) {
        try {
            if(duration < 0){
                throw new Error("Duration cannot be negative.");
            }
            else if(duration < 5){
                throw new Error("Duration cannot be below 5 minutes.");
            }
            this.duration = duration;
        }
        catch (Error e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }

    /* OVERRIDES */

    @Override
    public String toString() {
        // Using ternary logic to display SXXEXX correctly.
        // Shoutout to Odd Martin Hansen that pointed out that I didn't need to use keywords here.
        return getTitle() + " (S" + (seasonNo < 10 ? "0" + seasonNo : seasonNo) + "E" + (episodeNo < 10 ? "0" + episodeNo : episodeNo) + ") " + getDuration() + " min";
    }
}

    /* METHODS */
