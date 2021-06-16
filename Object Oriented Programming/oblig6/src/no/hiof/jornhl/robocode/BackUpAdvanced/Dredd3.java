package no.hiof.jornhl.robocode.BackUpAdvanced;


import robocode.*;

import java.awt.*;


/*
    Testbot for developing a more efficient methods
    for indiscriminate murder.
 */
public class Dredd3 extends AdvancedRobot {
    private double bfDiag;
    private double firePowerThingie;
    private boolean movingForward;
    private int numberOfVictims;

    private double gunTurn;
    //private double speed;

    //  Debugging/stats
    double shotsFired = 0;
    double shotsHit = 0;


    @Override
    public void run() {
        numberOfVictims = getOthers();
        pimpMyRide();
        gunTurn = Double.POSITIVE_INFINITY;
        //speed = 8.0;


        while (true) {
            /*if (Math.random() < 0.5) {
                setTurnRight(Math.random() * 360);
            } else {
                setTurnLeft(Math.random() * 360);
            }*/

            setTurnRight(100);
            //setTurnGunRight(gunTurn);

            // Må være motsatt av retningen den sikter i
            setTurnGunRight(gunTurn);
            //setTurnGunLeft(gunTurn);
            //setMaxVelocity(speed);
            //setTurnRadarRight(Double.POSITIVE_INFINITY);
            setAhead(5000);
            movingForward = true;
            waitFor(new TurnCompleteCondition(this));
        }
    }


    /*
        =========================================
        ===            REACTIONS              ===
        =========================================
    */
    public void onBulletHit(BulletHitEvent e) {
        out.println("Hit!");
        shotsHit++;
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (!movingForward) {
            return;
        }

        //  DENNE FORMELEN FUNKER DÅRLIG
        double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie), e.getEnergy() / 4);
        //double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie), e.getEnergy() / 4);

        /*double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie),
                ((e.getEnergy() / 4) >= 1 ? (e.getEnergy() / 4) + (e.getEnergy() * 2) : e.getEnergy() / 4));*/
        //double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie) + 0.1, e.getEnergy());
        //double bearing = getHeading() + e.getBearing();
        //double bearing = getGunHeading() + e.getBearing();
        double bearing = getHeading() - getGunHeading() + e.getBearing();
        boolean adjustmentThingieIDontReallyUnderstand = ((bearing > 0 ? bearing : bearing * -1) / e.getDistance()) < 0.5;

        gunTurn = 0;

        /*if (bearing > 360) {
            bearing -= 360;
        } else if (bearing < -360) {
            bearing += 360;
        }*/

        /*speed = 4.0;
        setMaxVelocity(4);*/

        //setAhead(0);
        //setTurnRight(0);

        /*out.println("Gun heading: " +getGunHeading());
        out.println("Heading: " + getHeading());
        out.println("Enemy bearing: " + e.getBearing());*/

        //  Normalize bearing
        //  Moves gun shorter distance, but makes bot less accurate. Math is hard. :(
        /*if (bearing > 180) {
            bearing -= 360;
        } else if (bearing < -180) {
            bearing += 360;
        }*/

        //  Debugging
        /*if (firePower > 2) {
            setBulletColor(Color.white);
        } else if (firePower > 1) {
            setBulletColor(Color.blue);
        } else {
            setBulletColor(Color.green);
        }*/


        //  Decide gun turning direction based on the enemy's bearing, direction, distance and velocity,
        // relative to own heading and velocity.
        setTurnGunLeft(bearing);
        //setTurnGunRight(bearing);

        /*if (e.getBearing() > 0) {
            setTurnGunRight(bearing);
        } else {
            setTurnGunLeft(bearing);
        }*/


        /*if (e.getBearing() - getHeading() > 0) {
            setTurnGunRight(bearing);
        } else {
            setTurnGunLeft(bearing);
        }*/


        //out.println(getVelocity());

        //  Fewer enemies means greater need for accuracy.
        //  Modify movement and reduce getGunTurnRemaining
        if (getGunTurnRemaining() < ((numberOfVictims + 1) * 2) && e.getDistance() < 400 && getGunHeat() == 0) {
            //fire(firePower);
            //System.out.println("Firing: " + bearing);
            //System.out.println("Firing: " + e.getBearing());
            setFire(customBullet(e.getDistance(), e.getEnergy()));
            shotsFired++;

            out.println("******************************");
            out.println("Gun heading: " +getGunHeading());
            out.println("Heading: " + getHeading());
            out.println("Enemy bearing: " + e.getBearing());
            out.println("Absolute bearin: " + bearing);
            out.println((bearing > 0 ? bearing : bearing * -1) / e.getDistance());

            //setFire(firePower);
            //speed = Rules.MAX_VELOCITY;
            //gunTurn = Double.POSITIVE_INFINITY;
        } else if (getGunTurnRemaining() < (numberOfVictims + 1) && e.getDistance() < 800 && getGunHeat() == 0 && adjustmentThingieIDontReallyUnderstand) {
            //fire(firePower);
            //System.out.println("Firing: " + bearing);
            //System.out.println("Firing: " + e.getBearing());
            setFire(customBullet(e.getDistance(), e.getEnergy()));
            shotsFired++;

            out.println("******************************");
            out.println("Gun heading: " + getGunHeading());
            out.println("Heading: " + getHeading());
            out.println("Enemy bearing: " + e.getBearing());
            out.println("Absolute bearin: " + bearing);
            out.println((bearing > 0 ? bearing : bearing * -1) / e.getDistance());

            //setFire(firePower);
            //speed = Rules.MAX_VELOCITY;
            //gunTurn = Double.POSITIVE_INFINITY;
        } /*else {
            //speed = Rules.MAX_VELOCITY;
            gunTurn = Double.POSITIVE_INFINITY;
        }*/


        gunTurn = Double.POSITIVE_INFINITY;

        //  Doesn't work
        //speed = Rules.MAX_VELOCITY;

        /*if (getGunTurnRemaining() < 20) {
            setFire(firePower);
        }*/

        //setFire(firePower);
    }

    public void onRoundEnded(RoundEndedEvent e) {
        double hitPercentage = (shotsHit/shotsFired) * 100;
        out.println("Successful hits: " + shotsHit + " / " + shotsFired + " (" + hitPercentage + "%)");
    }

    public void onHitWall(HitWallEvent e) {
        if (movingForward) {
            movingForward = false;

            setTurnRight(100);
            setBack(150);
            //back(150);
            //turnRight(100);
        } else {
            movingForward = true;
            setTurnRight(100);
            setAhead(150);

        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.isMyFault()) {
            out.println("Sorry, my bad.");
        } else {
            out.println("roody poo!");
        }
    }


    /*
        =========================================
        ===             SHOOTING              ===
        =========================================
    */
    public double customBullet(double enemyDist, double enemyEnergy) {
        double firePower = Math.max(((bfDiag - enemyDist) / firePowerThingie), enemyEnergy / 4);

        //  Debugging
        if (firePower > 2) {
            setBulletColor(Color.white);
        } else if (firePower > 1) {
            setBulletColor(Color.blue);
        } else {
            setBulletColor(Color.green);
        }

        return firePower;
    }



    /*
        =========================================
        ===             MOVING                ===
        =========================================
    */



    /*
        =========================================
        ===              OTHER                ===
        =========================================
    */
    public void pimpMyRide() {
        setBodyColor(Color.black);
        setGunColor(Color.red);
        setRadarColor(Color.yellow);
        setBulletColor(Color.white);
        setScanColor(Color.green);
    }
}