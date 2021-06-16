package no.hiof.jornhl.robocode.AdvancedLabRats;


import robocode.*;
import java.awt.*;


/*
    Testbot for developing a more efficient methods
    for indiscriminate murder.
 */
public class Dredd extends AdvancedRobot {
    private double firePowerThingie;
    private boolean movingForward;
    private double maxRange;
    private double midRange;
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
        maxRange = 800;
        midRange = maxRange / 2;
        firePowerThingie = maxRange / 3;


        while (true) {
            setTurnRight(100);

            // Må være motsatt av retningen den sikter i
            //  Would have been nice to be able to extend TeamRobot,
            //  which allows for setting the gun's turn rate in degrees
            //  per tick... :|
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
        if (!movingForward || e.getDistance() > maxRange) {
            return;
        }

        //  DEBUG
        //out.println("Firing process started at: " + getTime());

        double bearing = getHeading() - getGunHeading() + e.getBearing();

        gunTurn = 0;
        //setTurnGunLeft(bearing);

        if (bearing < -180) {
            bearing += 360;
        } else if (bearing > 180) {
            bearing -= 360;
        }

        setTurnGunLeft(bearing);


        if (getGunTurnRemaining() < ((numberOfVictims + 1) * 2) && e.getDistance() < midRange && getGunHeat() == 0) {
            setFire(customBullet(e.getDistance(), e.getEnergy()));
            shotsFired++;

            //  DEBUG
            /*out.println("******************************");
            out.println("Gun heading: " + getGunHeading());
            out.println("Heading: " + getHeading());
            out.println("Enemy bearing: " + e.getBearing());
            out.println("Absolute bearin: " + bearing);
            out.println((bearing > 0 ? bearing : bearing * -1) / e.getDistance());*/

        } else if (getGunTurnRemaining() < (numberOfVictims + 1) && getGunHeat() == 0) {
            setFire(customBullet(e.getDistance(), e.getEnergy()));
            shotsFired++;

            //  DEBUG
            /*out.println("******************************");
            out.println("Gun heading: " + getGunHeading());
            out.println("Heading: " + getHeading());
            out.println("Enemy bearing: " + e.getBearing());
            out.println("Absolute bearin: " + bearing);
            out.println((bearing > 0 ? bearing : bearing * -1) / e.getDistance());*/
        }

        //  DEBUG
        //out.println("Firing process finished at: " + getTime());

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
        //double firePower = Math.min((((maxRange - enemyDist) / firePowerThingie) + (numberOfVictims / 10)), enemyEnergy / 4);
        double firePower = Math.max((((maxRange - enemyDist) / firePowerThingie) + (numberOfVictims / 10)), 1);
        firePower = Math.min(firePower, enemyEnergy / 4);


        out.println(maxRange - enemyDist);
        out.println(firePowerThingie);
        out.println((maxRange - enemyDist) / firePowerThingie);
        out.println(numberOfVictims / 10);
        out.println(((maxRange - enemyDist) / firePowerThingie) + (numberOfVictims / 10));
        out.println(firePower);

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