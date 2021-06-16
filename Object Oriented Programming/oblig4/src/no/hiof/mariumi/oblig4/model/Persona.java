package no.hiof.mariumi.oblig4.model;

/**
 * Now this is a class that represents an imaginary, or real character.
 * Since I wanted functionality that specifically targeted only acted
 * characters, the Persona class was born.
 * I figured Persona was a good substitute for the already taken
 * Character class name.
 *
 * @author Marius Selvfölgelig Mikelsen
 */
public class Persona extends Role {
    private String characterName;

    /* CONSTRUCTORS */

    /**
     * Creates a character for a role in a motion picture.
     *
     * @param characterName
     * @param importanceWeight The greater the weight, the more your sink.
     */
    public Persona(String characterName, int importanceWeight) {
        super("Actor", importanceWeight);
        this.characterName = characterName;
    }

    /**
     * Creates a character for a role in a motion picture.
     *
     * @param characterName
     */
    public Persona(String characterName) {
        super("Actor");
        this.characterName = characterName;
    }

    /* GETTERS */

    public String getCharacterName() {
        return characterName;
    }

    /* SETTERS */

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    /* OVERRIDES */

    @Override
    public String toString() {
        return characterName + " - Portrayed by: " + (getPeople().size() > 1 ? "several actors." : getPeople().get(0));
    }
}
