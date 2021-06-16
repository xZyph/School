package no.hiof.jornhl.robocode.BackUpAdvanced;


import robocode.*;

import java.awt.*;


/*
    Testbot for developing a more efficient methods
    for indiscriminate murder.
 */
public class Dredd1 extends AdvancedRobot {
    private double bfDiag;
    private double firePowerThingie;
    private boolean movingForward;
    private int numberOfVictims;

    private double gunTurn;
    //private double speed;


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
            setTurnGunLeft(gunTurn);
            //setMaxVelocity(speed);
            //setTurnRadarRight(Double.POSITIVE_INFINITY);
            setAhead(5000);
            movingForward = true;
            waitFor(new TurnCompleteCondition(this));
        }
    }


    /*
        =========================================
        ===            OVERRIDES              ===
        =========================================
    */
    public void onBulletHit(BulletHitEvent e) {

    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (!movingForward) {
            return;
        }

        //  DENNE FORMELEN FUNKER IKKE
        double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie), e.getEnergy() / 4);
        //double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie), e.getEnergy() / 4);

        /*double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie),
                ((e.getEnergy() / 4) >= 1 ? (e.getEnergy() / 4) + (e.getEnergy() * 2) : e.getEnergy() / 4));*/
        //double firePower = Math.max(((bfDiag - e.getDistance()) / firePowerThingie) + 0.1, e.getEnergy());
        //double bearing = getHeading() + e.getBearing();
        //double bearing = getGunHeading() + e.getBearing();
        double bearing = getHeading() - getGunHeading() + e.getBearing();
        gunTurn = 0;
        /*speed = 4.0;
        setMaxVelocity(4);*/

        //setAhead(0);
        //setTurnRight(0);

        //  Normalize bearing
        //  Moves gun shorter distance, but makes bot less accurate. Math is hard. :(
        /*if (bearing > 180) {
            bearing -= 360;
        } else if (bearing < -180) {
            bearing += 360;
        }*/

        //  Debugging
        if (firePower > 2) {
            setBulletColor(Color.white);
        } else if (firePower > 1) {
            setBulletColor(Color.blue);
        } else {
            setBulletColor(Color.green);
        }


        setTurnGunRight(bearing);


        /*if (e.getBearing() - getHeading() > 0) {
            setTurnGunRight(bearing);
        } else {
            setTurnGunLeft(bearing);
        }*/


        //out.println(getVelocity());

        //  Fewer enemies means greater need for accuracy.
        //  Modify movement and reduce getGunTurnRemaining
        if (getGunTurnRemaining() < 2 && e.getDistance() < 400 && getGunHeat() == 0) {
            //fire(firePower);
            System.out.println("Firing: " + bearing);
            setFire(firePower);
            //speed = Rules.MAX_VELOCITY;
            //gunTurn = Double.POSITIVE_INFINITY;
        } else if (getGunTurnRemaining() < 2 && e.getDistance() < 800 && getGunHeat() == 0) {
            //fire(firePower);
            System.out.println("Firing: " + bearing);
            setFire(firePower);
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

    public void onHitWall(HitWallEvent e) {
        if (movingForward) {
            movingForward = false;
            back(100);
            turnRight(100);
        } else {
            movingForward = true;
            ahead(100);
            turnRight(100);
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
        ===             CUSTOM                ===
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