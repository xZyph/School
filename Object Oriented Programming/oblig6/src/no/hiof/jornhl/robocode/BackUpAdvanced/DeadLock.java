package no.hiof.jornhl.robocode.BackUpAdvanced;


import robocode.*;

import java.awt.*;


public class DeadLock extends AdvancedRobot {
    private boolean movingForward, huntMode, defMode;
    private int numberOfVictims;

    private String targetName;

    private double bfDiag;
    private double firePowerThingie;


    @Override
    public void run() {
        pimpMyRide();
        setAdjustGunForRobotTurn(true);
        //setAdjustRadarForGunTurn(true);
        bfDiag = Math.sqrt(Math.pow(getBattleFieldHeight(), 2) + Math.pow(getBattleFieldWidth(), 2));
        firePowerThingie = bfDiag / 2.9;

        out.println("LOVE AND TOLERANCE!");


        while (true) {
            numberOfVictims = getOthers();
            //out.println(numberOfVictims);
            //  Go on the defensive (avoid enemies) when heavily wounded.
            defMode = getEnergy() < 50;
            //  Go on the hunt (establish target lock (gun, not radar) and only fire high-precision shots)
            //  when there are few enemies left.
            //  Convert from binary to stepped (f.ex. complete lock when only one enemy remains)?
            huntMode = numberOfVictims < 3;


            if (Math.random() < 0.5) {
                setTurnRight(Math.random() * 360);

            } else {
                setTurnLeft(Math.random() * 360);
            }

            //setTurnRadarRight(Double.POSITIVE_INFINITY);
            setTurnGunRight(Double.POSITIVE_INFINITY);
            //turnGunRight(360);
            //setAhead((Math.random() * 200) + 500);
            setAhead(5000);
            movingForward = true;
            waitFor(new TurnCompleteCondition(this));
        }
    }


    /*
        =========================================
        ===            ACTIONS                ===
        =========================================
    */
    public void defensiveMode() {

    }

    public void offensiveMode() {

    }



    /*
        =========================================
        ===           REACTIONS               ===
        =========================================
    */
    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        //  Be less aggressive with fewer enemies?
        //  Less chance of hitting something with stray bullets.

        double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie) + 0.1, e.getEnergy());

        //  Debugging
        if (firePower > 2) {
            setBulletColor(Color.white);
        } else if (firePower > 1) {
            setBulletColor(Color.blue);
        } else {
            setBulletColor(Color.green);
        }

        //stop();
        setFire(firePower);
        //resume();



        //fire(((bfDiag - e.getDistance()) / firePowerThingie) + 0.1);

        /*if (e.getDistance() < 100) {
            fire(3);
        } else if (e.getDistance() < 300) {
            //turnGunRight(e.getBearing());
            fire(2);
        } else {
            //turnGunRight(e.getBearing());
            //turnGunRight(e.getBearing() - getGunHeading());
            fire(1);
        }*/
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        //panicMove();
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        if (movingForward) {
            movingForward = false;
            back(50);
            turnRight(100);
        } else {
            movingForward = true;
            ahead(50);
            turnRight(100);
        }
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
        /*if (Math.random() < 0.5) {
            ahead((Math.random() * 40) + 18);
        } else {
            back((Math.random() * 40) + 18);
        }*/

        movingForward = false;
        back(50);
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
}