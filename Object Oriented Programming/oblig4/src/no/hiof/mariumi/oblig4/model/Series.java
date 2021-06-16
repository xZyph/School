package no.hiof.mariumi.oblig4.model;

/* IMPORTS */

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

/**
 * A Series can be viewed as a cocoon containing groupings of Episode-larvae.
 * Very similar to a spiders, the cocoon may hatch thousands of Episodes, but
 * the absurdity comes with the fact that none of her spawn are actually hers.
 *
 * @author Marius Selvfölgelig Mikelsen
 * @version 0.1
 */
public class Series implements Comparable {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private List<Episode> episodeList;
    private int avgRuntime;
    private int seasonSize;
    private HashMap<Person, Integer> actorOverview = new HashMap();

    public static ArrayList<Series> seriesList = new ArrayList<>();

    /* CONSTRUCTORS */

    /**
     * Creates a series with a specific title.
     *
     * @param title of series
     */
    public Series(String title) {
        setTitle(title);
        setDescription("Default description");
        setReleaseDate(LocalDate.now());
        setEpisodeList(new ArrayList<Episode>());

        seriesList.add(this);
        Collections.sort(seriesList);
    }

    /**
     * Creates a series with title, description and release date.
     *
     * @param title ex. Svartedauden
     * @param description ex. Det kom et skip til Bjørgvin i 1349
     * @param releaseDate ex. LocalDate.of(1349, 10, 20)
     */
    public Series(String title, String description, LocalDate releaseDate) {
        setTitle(title);
        setDescription(description);
        setReleaseDate(releaseDate);
        setEpisodeList(new ArrayList<Episode>());

        seriesList.add(this);
        Collections.sort(seriesList);
    }

    /* GETTERS */

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public int getAvgRuntime() {
        return avgRuntime;
    }

    public int getSeasonSize() {
        return seasonSize;
    }

    public HashMap<Person, Integer> getActorOverview() {

        return actorOverview;
    }

    /* SETTERS */

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setEpisodeList(ArrayList<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    /* OVERRIDES */

    @Override
    public String toString() {
        return getTitle() + " - " + getSeasonSize() + " Seasons";
    }

    @Override
    public int compareTo(Object o) {
        return title.toUpperCase().compareTo(((Series) o).getTitle().toUpperCase());
    }

    /* METHODS */

    /**
     * Adds an episode to a series.
     *
     * @param newEpisode that you want to add
     */
    public void addEpisode(Episode newEpisode) {
        try {
            if (newEpisode.getSeasonNo() > (getSeasonSize() + 1)) {
                throw new Error("Cannot add episode from a season higher than " + (getSeasonSize() + 1));
            }

            for(Role character : newEpisode.getCast()) {
                for (Person actor : character.getPeople()) {
                    if(actorOverview.containsKey(actor)) {
                        Integer count = actorOverview.get(actor) + 1;
                        actorOverview.put(actor, count);
                    }
                    else {
                        actorOverview.put(actor, 1);
                    }
                }
            }

            episodeList.add(newEpisode);
            seasonSize = newEpisode.getSeasonNo();
            updateAvgRuntime();
            Collections.sort(episodeList);
        }
        catch (Error e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }

    /**
     * Prints all episodes from a season.
     *
     * @param season ex. 1
     */
    public void printAllEpisodes(int season) {
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("* " + getTitle() + " - Season " + season + " (" + getReleaseDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")) + ")");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        for(int i = 0; i < episodeList.size(); i++) {

            if(episodeList.get(i).getSeasonNo() == season) {
                System.out.println("* " + episodeList.get(i));
            }
        }

        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    /**
     * Prints all episodes from all seasons.
     */
    public void printAllEpisodes() {
        for(int i = 1; i <= seasonSize; i++) {
            printAllEpisodes(i);
        }
    }

    /**
     * Sum runtime for all episodes in a series.
     *
     * @return Overall runtime in minutes
     */
    public int calcTotalRuntimeAllEpisodes() {
        int totalRuntime = 0;

        for(int i = 0; i < episodeList.size(); i++) {
            totalRuntime += episodeList.get(i).getRuntime();
        }

        return  totalRuntime;
    }

    /**
     * Updates average runtime for series.
     */
    private void updateAvgRuntime() {
        avgRuntime = calcTotalRuntimeAllEpisodes() / episodeList.size();
    }

    /**
     * Prints an overview of all actors in a series, also with amount of episodes they appeared in.
     */
    public void printActorOverview() {
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("* Actor Overview - " + getTitle() + " (" + episodeList.size() + " episodes)");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        for(Map.Entry<Person, Integer> entry : getActorOverview().entrySet()) {
            System.out.println("*  " + entry.getKey() + " (Appears in " + entry.getValue() + " episodes)");
        }
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    /**
     * Creates and adds episodes every weekday from given date to given date.
     * This method increments season every year.
     *
     * @param from
     * @param to
     */
    public void createEpisodeAndSeasonsOnlyOnWeekdays(LocalDate from, LocalDate to) {
        int season = 1;
        int episode = 1;

        while(!from.equals(to)) {
            if(!(from.getDayOfWeek() == DayOfWeek.SATURDAY) && !(from.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                addEpisode(new Episode(season, episode, from));
                episode++;
            }
            if(from.getYear() < from.plusDays(1).getYear()) {
                season++;
                episode = 1;
            }
            from = from.plusDays(1);
        }
    }
}