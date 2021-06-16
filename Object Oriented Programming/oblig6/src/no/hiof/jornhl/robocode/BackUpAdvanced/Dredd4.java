package no.hiof.jornhl.robocode.BackUpAdvanced;


import robocode.*;

import java.awt.*;


/*
    Testbot for developing a more efficient methods
    for indiscriminate murder.
 */
public class Dredd4 extends AdvancedRobot {
    private double bfDiag;
    private double firePowerThingie;
    private boolean movingForward;
    private int numberOfVictims;

    private double gunTurn;

    //  Debugging/stats
    double shotsFired = 0;
    double shotsHit = 0;


    @Override
    public void run() {
        numberOfVictims = getOthers();
        pimpMyRide();
        gunTurn = Double.POSITIVE_INFINITY;

        bfDiag = Math.sqrt(Math.pow(getBattleFieldHeight(), 2) + Math.pow(getBattleFieldWidth(), 2));
        firePowerThingie = bfDiag / 3;


        while (true) {
            setTurnRight(100);

            // Må være motsatt av retningen den sikter i
            setTurnGunRight(gunTurn);
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
        if (!movingForward || e.getDistance() > 800) {
            return;
        }

        /*if ((e.getBearing() > -45 && e.getBearing() < 45) && e.getDistance() < 200) {
            setBack(100);
        }*/

        double bearing = getHeading() - getGunHeading() + e.getBearing();

        gunTurn = 0;
        //setTurnGunLeft(bearing);

        if (bearing < -180) {
            bearing += 360;
        } else if (bearing > 180) {
            bearing -= 360;
        }

        setTurnGunLeft(bearing);


        if (getGunTurnRemaining() < ((numberOfVictims + 1) * 2) && e.getDistance() < 400 && getGunHeat() == 0) {
            setFire(customBullet(e.getDistance(), e.getEnergy()));
            shotsFired++;

            out.println("******************************");
            out.println("Gun heading: " + getGunHeading());
            out.println("Heading: " + getHeading());
            out.println("Enemy bearing: " + e.getBearing());
            out.println("Absolute bearin: " + bearing);
            out.println((bearing > 0 ? bearing : bearing * -1) / e.getDistance());

        } else if (getGunTurnRemaining() < (numberOfVictims + 1) && getGunHeat() == 0) {
            setFire(customBullet(e.getDistance(), e.getEnergy()));
            shotsFired++;

            out.println("******************************");
            out.println("Gun heading: " + getGunHeading());
            out.println("Heading: " + getHeading());
            out.println("Enemy bearing: " + e.getBearing());
            out.println("Absolute bearin: " + bearing);
            out.println((bearing > 0 ? bearing : bearing * -1) / e.getDistance());
        }

        gunTurn = Double.POSITIVE_INFINITY;
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
        double firePower = Math.max((((bfDiag - enemyDist) / firePowerThingie) + (numberOfVictims / 10)), enemyEnergy / 4);

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
    public void noRoadRage() {

    }



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