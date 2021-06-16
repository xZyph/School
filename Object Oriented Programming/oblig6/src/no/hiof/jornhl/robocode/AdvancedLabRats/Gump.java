package no.hiof.jornhl.robocode.AdvancedLabRats;


import robocode.*;

import java.awt.*;


/*
    Testbot for developing a decent movement system.
    Will hopefully not be immediately murdered.
 */
public class Gump extends AdvancedRobot {
    private boolean movingForward;


    @Override
    public void run() {
        pimpMyRide();


        while (true) {

        }
    }

    /*
        =========================================
        ===            OVERRIDES              ===
        =========================================
    */
    public void onScannedRobot(ScannedRobotEvent e) {
        if ((e.getBearing() > -45 && e.getBearing() < 45) && e.getDistance() < 200) {
            setBack(100);
        }
    }

    public void onHitByBullet(HitByBulletEvent e) {

    }

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
    }


    /*
        =========================================
        ===             CUSTOM                ===
        =========================================
    */
    public void pimpMyRide() {
        setBodyColor(Color.black);
        setGunColor(Color.red);
        setRadarColor(Color.black);
        setBulletColor(Color.white);
        setScanColor(Color.green);
    }
}