package no.hiof.mariumi.oblig3;

/* IMPORTS */

import no.hiof.mariumi.oblig3.production.Persona;
import no.hiof.mariumi.oblig3.production.Role;
import no.hiof.mariumi.oblig3.production.Movie;
import no.hiof.mariumi.oblig3.production.Episode;
import no.hiof.mariumi.oblig3.production.Series;
import no.hiof.mariumi.oblig3.people.Person;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * ITF10611 - OOP
 * Oblig 3
 *
 * @author Marius Selvfölgelig Mikelsen
 * @version 0.1
 *
 * TODO:
 *   - Clean up unused code.
 *   - Remove clever workarounds that didn't actually work.
 */

public class Main {

    public static void main(String[] args) {

        // Firefly
        Series firefly = new Series(
                "Firefly",
                "The show explores the lives of a group of people, some of whom fought on the losing side of a civil war...",
                LocalDate.of(2002, 9, 20)
        );

        Movie serenity = new Movie("Serenity", 119);

        // Firefly - People
        Person nathanFillion = new Person("Nathan Fillion", LocalDate.of(1971, 3, 27), 'M');
        Person jossWhedon = new Person("Joss Whedon", LocalDate.of(1964, 6, 23), 'M');
        Person ginaTorres = new Person( "Gina Torres", LocalDate.of(1969, 4, 25), 'F');
        Person alanTudyk = new Person( "Alan Tudyk", LocalDate.of(1971, 3, 16), 'M');
        Person morenaBaccarin = new Person("Morena Baccarin", LocalDate.of(1979, 6, 2), 'F');
        Person adamBaldwin = new Person("Adam Baldwin", LocalDate.of(1962, 2, 27), 'M');
        Person markSheppard = new Person("Mark Sheppard", LocalDate.of(1964, 5, 30), 'M');

        // Firefly - Roles
        Role directors = new Role("Director");
        directors.addPerson(jossWhedon);

        // Firefly - Characters
        Persona captainTightpants = new Persona("Captain Malcolm 'Mal' Reynolds");
        captainTightpants.addPerson(nathanFillion);

        Persona zoe = new Persona("Zoë Washburne");
        zoe.addPerson(ginaTorres);

        Persona wash = new Persona("Hoban 'Wash' Washburne");
        wash.addPerson(alanTudyk);

        Persona inara = new Persona("Inara Serra");
        inara.addPerson(morenaBaccarin);

        Persona jayne = new Persona("Jayne Cobb");
        jayne.addPerson(adamBaldwin);

        Persona badger = new Persona("Badger");
        badger.addPerson(markSheppard);

        // Firefly - Lists
        ArrayList<Persona> firefly_MainCharacterList = new ArrayList<>();
        firefly_MainCharacterList.add(captainTightpants);
        firefly_MainCharacterList.add(zoe);
        firefly_MainCharacterList.add(wash);
        firefly_MainCharacterList.add(inara);
        firefly_MainCharacterList.add(jayne);

        ArrayList<Role> firefly_Crew = new ArrayList<>();
        firefly_Crew.add(directors);

        // Firefly Episodes

        Episode theTrainJob = new Episode(1, 1, "The Train Job", 42);
        theTrainJob.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        theTrainJob.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(theTrainJob);

        Episode bushwhacked = new Episode(2, 1, "Bushwhacked", 44);
        bushwhacked.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        bushwhacked.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(bushwhacked);

        Episode ourMrsReynolds = new Episode(3, 1, "Our Mrs Reynolds", 44);
        ourMrsReynolds.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        ourMrsReynolds.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(ourMrsReynolds);

        Episode jaynesTown = new Episode(4, 1, "Jayne's Town", 44);
        jaynesTown.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        jaynesTown.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(jaynesTown);

        Episode outOfGas = new Episode(5, 1, "Out of Gas", 44);
        outOfGas.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        outOfGas.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(outOfGas);

        Episode shindig = new Episode(6, 1, "Shindig", 44);
        shindig.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        shindig.addCast(badger);
        shindig.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(shindig);

        Episode safe = new Episode(7, 1, "Safe", 43);
        safe.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        safe.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(safe);

        Episode ariel = new Episode(8, 1, "Ariel", 43);
        ariel.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        ariel.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(ariel);

        Episode warStories = new Episode(9, 1, "War Stories", 43);
        warStories.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        warStories.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(warStories);

        Episode objectsInSpace = new Episode(10, 1, "Objects in Space", 44);
        objectsInSpace.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        objectsInSpace.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(objectsInSpace);

        Episode serenityEp = new Episode(11, 1, "Serenity", 86);
        serenityEp.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        serenityEp.addCast(badger);
        serenityEp.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(serenityEp);

        Episode heartOfGold = new Episode(12, 1, "Heart of Gold", 42);
        heartOfGold.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        heartOfGold.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(heartOfGold);

        Episode trash = new Episode(13, 1, "Trash", 44);
        trash.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        trash.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(trash);

        Episode theMessage = new Episode(14, 1, "The Message", 44);
        theMessage.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        theMessage.setCrew((ArrayList<Role>) firefly_Crew.clone());
        firefly.addEpisode(theMessage);

        // Serenity Cast & Crew
        serenity.setCast((ArrayList<Persona>) firefly_MainCharacterList.clone());
        serenity.setCrew((ArrayList<Role>) firefly_Crew.clone());

        // Days of Our Lives
        Series daysOfOurLives = new Series("Days of Our Lives");
        LocalDate from = LocalDate.of(1965, 11, 8);
        daysOfOurLives.createEpisodeAndSeasonsOnlyOnWeekdays(from, LocalDate.now());

        // TEST AREA
        serenity.printCast();
        System.out.println();
        firefly.printActorOverview();
        System.out.println("\nAntall episoder av Days of Our Lives: " + daysOfOurLives.getEpisodeList().size());
    }
}
