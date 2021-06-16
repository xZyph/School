package no.hiof.jornhl.robocode.BackUpAdvanced;


import robocode.*;

import java.awt.*;


public class WindowLickerHalfDicked extends AdvancedRobot {
    private boolean movingForward, huntMode, defMode;
    private boolean cornerCamper, hitWallX, hitWallY;
    private int numberOfVictims;
    private int chosenCorner = 5;

    private String targetName;

    private double bfDiag;
    private double firePowerThingie;


    /*
        TODO:
        1)  Implement better movement
            Camp in corners in melee?

        .1) Implement wall avoidance
        .2) Implement enemy avoidance

        2)  Implement better targeting system

        3)  Implement tracking mode:
            When only one enemy is left, radar sweeps are narrowed to a
            cone, centred on the enemy. Adjust firing parameters.

        4)  Implement dodge method:
            When only one enemy is left (or two?), keep track of its energy,
            as well as shots fired at it. If its energy level has dropped by
            a number not corresponding to the last shot fired at it (or possibly
            an energy drop in the other enemy, if two are left), assume a shot
            has been fired and try to dodge it (stop and change direction, or
            possibly just velocity).

        5)  Keep records of other bots and try to track stats (position, heading,
            energy, velocity, etc.)? Could be useful for picking targets, avoiding
            enemies etc.

        6)  Try to map the battlefield and keep track of own position (and possibly
            enemies)?


            Calculate bots/pixels and modify fire rate/accuracy? In a target rich
            environment, rapid, high-power, poorly targeted fire can be advantageous.
            With few enemies, it's suicidal.
     */


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
            //  Go on the defensive (avoid enemies) when heavily wounded.
            defMode = getEnergy() < 50;
            //  Go on the hunt (establish target lock (gun, not radar) and only fire high-precision shots)
            //  when there are few enemies left.
            //  Convert from binary to stepped (f.ex. complete lock when only one enemy remains)?
            huntMode = numberOfVictims < 3;


            if (!huntMode) {
                cornerMove();
            } else {
                shitMove();
            }
        }
    }


    /*
        =========================================
        ===              MODES                ===
        =========================================
    */
    public void defMove() {

    }

    public void offMove() {

    }

    public void goodFire() {

    }

    public void shitFire() {

    }

    public void cornerMove() {
        if (chosenCorner == 5) {
            chosenCorner = (int) Math.floor(Math.random() * 5);
        }
    }

    public void shitMove() {
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



    /*
        =========================================
        ===           REACTIONS               ===
        =========================================
    */
    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie) + 0.1, e.getEnergy());


        if ((e.getDistance() < 500 && huntMode) || !huntMode) {
            //  Debugging
            if (firePower > 2) {
                setBulletColor(Color.white);
            } else if (firePower > 1) {
                setBulletColor(Color.blue);
            } else {
                setBulletColor(Color.green);
            }

            setFire(firePower);
        }
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