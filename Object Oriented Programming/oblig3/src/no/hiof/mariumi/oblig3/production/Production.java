package no.hiof.mariumi.oblig3.production;

import no.hiof.mariumi.oblig3.people.Person;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Abstract Production class to symbolize all possible motion picture productions.
 *
 * @author Marius Selvfölgelig Mikelsen
 * @version 0.1
 */
public abstract class Production {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private int runtime;
    private ArrayList<Persona> cast;
    private ArrayList<Role> crew;
    private static ArrayList<Production> productions = new ArrayList<>();

    /* CONSTRUCTORS */

    /**
     * Creates a generic production with only runtime specified.
     *
     * @param runtime Total length in minutes.
     */
    public Production(int runtime) {
        setTitle("N/A");
        setRuntime(runtime);
        cast = new ArrayList<>();
        crew = new ArrayList<>();
        productions.add(this);
    }

    /**
     * Creates a generic production with runtime and title specified.
     *
     * @param title Title of production
     * @param runtime Total length in minutes.
     */
    public Production(String title, int runtime) {
        setTitle(title);
        setRuntime(runtime);
        cast = new ArrayList<>();
        crew = new ArrayList<>();
        productions.add(this);
    }

    /* GETTERS */

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRuntime() {
        return runtime;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Returns all of cast - but without duplicates.
     *
     * @return cast v2.0
     */
    public ArrayList<Persona> getCast() {
        ArrayList<Persona> cast = new ArrayList<>();
        for(Persona character : this.cast) {
            if(!cast.contains(character))
                cast.add(character);
        }
        return cast;
    }

    public ArrayList<Role> getCrew() {
        return crew;
    }

    public static ArrayList<Production> getProductions() {
        return productions;
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

    public void setCast(ArrayList<Persona> cast) {
        this.cast = cast;
    }

    public void setCrew(ArrayList<Role> crew) {
        this.crew = crew;
    }

    public void setRuntime(int totalRuntime) {
        try {
            if(totalRuntime < 0){
                throw new Error("Total runtime cannot be negative.");
            }
            else if(totalRuntime > 2_147_483_647){
                throw new Error("Wait a second.. A production that has a total of more than 4085 years of playtime?");
            }
            this.runtime = totalRuntime;
        }
        catch (Error e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }

    /* OVERRIDES */

    @Override
    public String toString() {
        return "Hi there! I am an abstract class. I should not be seen.";
    }

    /* METHODS */

    /**
     * Adds a character to the list of all production cast.
     *
     * @param identity James Bond, Frodo, Captain Picard etc.
     */
    public void addCast(Persona identity) {
        cast.add(identity);
    }

    /**
     * Adds a person to the list of all production crew.
     *
     * @param crewmember Director, Gaffer, Webdeveloper etc.
     */
    public void addCrew(Role crewmember) {
        crew.add(crewmember);
    }

    public void printCrew() {
        System.out.println("CREW:");
        for(Role category : crew) {
            System.out.println(category.getTitle() + (category.getMemberCount() > 1 ? "s:" : ":"));
            for (Person crewmember : category.getPeople()) {
                System.out.println("  " + crewmember.getFullName());
            }
            System.out.println("\n");
        }
    }

    public void printCast() {
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("* CAST");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        for(Persona category : cast) {
            System.out.println("* " + category.getCharacterName());
            for (Person actor : category.getPeople()) {
                System.out.println("*   - " + actor.getFullName());
            }
            System.out.println("*");
        }
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }
}
