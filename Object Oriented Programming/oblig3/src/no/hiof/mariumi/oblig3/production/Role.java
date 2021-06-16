package no.hiof.mariumi.oblig3.production;

import no.hiof.mariumi.oblig3.people.Person;
import java.util.ArrayList;

/**
 * The Role class is created to represent a persons functional role in the
 * Production. We will of course want to have the possibility for several
 * actors playing the same role. Which I guess could happen if two or more
 * people motion-captured horse or something.
 *
 * @author Marius Selvfölgelig Mikelsen
 * @version 0.1
 */
public class Role {
    private String title;
    private ArrayList<Person> people;

    /* CONSTRUCTORS */

    /**
     * Creates a role with title provided and 100 importanceweight.
     *
     * @param title of role.
     */
    public Role(String title) {
        this.title = title;
        people = new ArrayList<>();
    }

    /**
     * Creates a role with title and importanceweight provided.
     *
     * @param title of role.
     * @param importanceWeight The greater the weight, the more your sink.
     */
    public Role(String title, int importanceWeight) {
        this.title = title;
        people = new ArrayList<>();
    }

    /* SETTERS */

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    /* GETTERS */

    public String getTitle() {
        return title;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    /* OVERRIDES */

    /* METHODS */

    /**
     * Adding a person to the list of people in the respective role.
     *
     * @param dude The person object you are adding.
     */
    public  void addPerson(Person dude) {
        people.add(dude);
    }

    /**
     * Returns the amount of people in the respective role.
     *
     * @return Amount of people, measured in ints.
     */
    public int getMemberCount() {
        return people.size();
    }
}
