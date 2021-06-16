package no.hiof.mariumi.oblig4.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Well this class pretty much explains itself, doesn't it?
 * It is used to store the characteristics we associate with
 * a human person.
 *
 * @author Marius Selvfölgelig Mikelsen
 * @version 0.1
 */
public class Person{
    private String fullName;
    private LocalDate birthdate;
    private List<Episode> productionHistory;
    private static ArrayList<Person> people = new ArrayList<>();
    private char sex;

    /* CONSTRUCTORS */

    public Person(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Creates a person object.
     *
     * @param fullName Name of person.
     * @param birthdate LocalDate.of(yyyy, mm, dd)
     * @param binaryGenderInitials M/F - Based on the idea of two genders defined by chromosomes.
     */
    public Person(String fullName, LocalDate birthdate, char binaryGenderInitials) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.sex = binaryGenderInitials;
        people.add(this);
    }

    /**
     * Creates a person object.
     *
     * @param fullName Name of person.
     * @param birthdate LocalDate.of(yyyy, mm, dd)
     * @param productionHistory Insert ArrayList<> here.
     * @param sex M/F
     */
    public Person(String fullName, LocalDate birthdate, ArrayList<Episode> productionHistory, char sex) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.productionHistory = productionHistory;
        this.sex = sex;
        people.add(this);
    }

    /* GETTERS */

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public List<Episode> getProductionHistory() {
        return productionHistory;
    }

    public static ArrayList<Person> getPeople() {
        return people;
    }

    /* SETTERS */

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setProductionHistory(List<Episode> productionHistory) {
        this.productionHistory = productionHistory;
    }

    /* OVERRIDES */

    @Override
    public String toString() {
            return fullName;
    }

    /* METHODS */

    /**
     * Ask yourself, do I really need to explain this?
     * 
     * @return age of person measured in ints.
     */
    public int getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
