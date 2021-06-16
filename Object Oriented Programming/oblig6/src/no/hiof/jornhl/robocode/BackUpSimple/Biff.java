package no.hiof.jornhl.robocode.BackUpSimple;


import robocode.*;
import robocode.Robot;

import java.awt.*;
import java.util.Random;


public class Biff extends Robot {
    //static Point2D.Double[] enemyPoints;
    //int count;

    boolean firstRound = true;

    int numberOfVictims;
    String targetName;
    Random randomValues = new Random();


    @Override
    public void run() {
        numberOfVictims = getOthers();
        //enemyPoints = new Point2D.Double[numberOfVictims];

        pimpMyRide();

        while (true) {
            if (firstRound) {
                out.println("LOVE AND TOLEARNCE!");
                firstRound = false;
            }

            ahead((Math.random() * 81) + 20);
            turnRight(Math.random() * 45);
            //turnGunRight(360);
        }
    }


    /*
        =========================================
        ===            ACTIONS                ===
        =========================================
    */




    /*
        =========================================
        ===           REACTIONS               ===
        =========================================
    */
    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
		/*double absBearing = e.getBearingRadians() + getHeadingRadians();
		enemyPoints[count] = new Point2D.Double(getX()+e.getDistance()*Math.sin(absBearing),getY()+e.getDistance()*Math.cos(absBearing));

		if (++count >= numberOfVictims) {
	    	count=0;
		}*/

        if (e.getDistance() > 50) {
            fire(3);
        } else if (e.getDistance() > 100) {
            fire(2);
        } else {
            fire(1);
        }
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        panicMove();
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        back(10);
        turnRight(180);
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.isMyFault()) {
            out.println("Sorry, my bad.");
        } else {
            out.println("roody poo!");
        }

        panicMove();
    }

    /*
        =========================================
        ===             UTIL                  ===
        =========================================
    */
    public void panicMove() {
        if (Math.random() < 0.5) {
            ahead((Math.random() * 40) + 18);
        } else {
            back((Math.random() * 40) + 18);
        }
    }


    /*
        =========================================
        ===            ONE-OFFS               ===
        =========================================
    */
    public void pimpMyRide() {
        setBodyColor(Color.black);
        setGunColor(Color.red);
        setRadarColor(Color.black);
        setBulletColor(Color.white);
        setScanColor(Color.green);
    }


    /*
        =========================================
        ===           GOBSHITE                ===
        =========================================
    */
    /**
     * Method for petty, spiteful vengeance. Critically important!
     *
     * @param name Superfluous?
     */
    public void vengeance(String name) {
        /*
        Alright
        Let's Go

        You've hated me from childhood
        You sent me far away
        but I came back with vengeance
        and now I'll make you pay !

        You told me to be quiet
        you nailed me to the floor
        Now pain and retribution
        are knocking on your door !

        Yeah !
        Vengeance !

        Pain and retribution, knocking on your door !
        All the people fear me
        The universe is mine
        Obliterate your planet
        My power is divine!

        Here comes Armageddon
        you know it's all a farce
        to face a hundred billion tons
        of mechanized assault !

        Here comes Armageddon
        Soon the universe belongs to me !
        Yeah !
        Oh, Come on !
        Face a hundred billion tons of mechanized assault

        Power beyond reckoning, Overwhelming Force
        Power beyond reckoning, nothing will deter me from my course !

        Ravaging accross the galaxies
        Harbingers of famine and disease
        Army of Apocalypse and Doom
        We'll take your planet soon !

        The ship is fully loaded
        there's nothing left to kill
        Freight limit exceeded
        but we are hungry still

        We'll hunt for new horizons
        beyond the atmosphere
        New places to conquer,
        Interstellar Warfare oh yeah !

        Ravaging accross the galaxies
        Harbingers of famine and disease
        Army of Apocalypse and Doom
        We'll take your planet soon ! (It's ours !)
        Army of Apocolypse and Doom!
        */
    }
}
