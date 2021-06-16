package no.hiof.jornhl.robocode;


import robocode.*;
import robocode.Robot;
import java.awt.*;


/**
 * Huggy McLove, the scariest robot since The Terminator.
 * A true rainbow warrior, he has no fear and less sense.
 */
public class HuggyMcLove extends Robot {
    @Override
    public void run() {
        boolean firstTurn = true;
        pimpMyRide();

        while (true) {
            //  Huggy starts each match with a declaration of intent.
            if (firstTurn) {
                out.println("LOVE AND TOLERANCE!");
                firstTurn = false;
            }

            //  Huggy moves in mysterious ways.
            ahead((Math.random() * 191) + 10);
            turnRight(Math.random() * 360);

            //  Continuous radar sweep.
            turnGunRight(360);
        }
    }

    /**
     * Upon seeing an enemy, remote hug is activated.
     * More enthusiastic hugging at close range. <br>
     * <i>P.S. Aiming is for suckers.</i>
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        out.println("LET ME LOVE YOU");
        if (e.getDistance() < 200) {
            fire(2);
        } else {
            fire(1);
        }
    }

    /**
     * In case Huggy accidentally manages to hug someone,
     * do a little dance to celebrate. A happy, suicidal
     * dance in the middle of the battlefield.
     *
     * @param e Various information about the incident.
     */
    public void onBulletHit(BulletHitEvent e) {
        //  A sad loop for a sad bot.
        for (int i = 1; i < 9; i++) {
            if (i % 2 == 0) {
                turnLeft(10);
                turnRight(10);
            } else {
                turnRight(10);
                turnLeft(10);
            }
        }
    }

    /**
     * Reaction to being the recipient of a remote hug.
     *
     * <ul>
     *     <li>Fun fact #1: Huggy's favourite movie is Snatch, and his favourite character's given name is Boris.</li>
     *     <li>Fun fact #2: They are likely to meet the same fate.</li>
     * </ul>
     */
    public void onHitByBullet(HitByBulletEvent e) {
        panicMove();
    }

    /**
     * ... because you just know that this genius of a bot is going
     * to run into walls. A lot of them, often, and with great enthusiasm.
     */
    public void onHitWall(HitWallEvent e) {
        back(100);
        turnRight(180);
    }

    /**
     * Actions to be taken when Huggy's personal space is invaded.
     *
     * @param e An intriguing collection of clues as to
     *          precisely what has transpired.
     */
    public void onHitRobot(HitRobotEvent e) {
        if (e.isMyFault()) {
            out.println("Sorry, my bad.");
        } else {
            out.println("MOVE, ROODY POO!");
        }
        panicMove();
    }

    /**
     * OMGWTFBBQ! Run away!
     */
    public void panicMove() {
        if (Math.random() < 0.5) {
            ahead((Math.random() * 150) + 50);
        } else {
            back((Math.random() * 150) + 50);
        }
    }

    /**
     * Yo, dawg...
     */
    public void pimpMyRide() {
        setBodyColor(Color.red);
        setGunColor(Color.green);
        setRadarColor(Color.blue);
        setBulletColor(Color.yellow);
        setScanColor(Color.pink);
    }

    /**
     * Petty, spiteful vengeance. Critically important!
     * Might be considered a bit of a let-down by those who don't
     * appreciate the importance of psychological warfare.
     *
     * @param name The source of Huggy's incipient fury. Future
     *             murder victim (or at least soon-to-be-slightly-miffed bot).
     */
    public void vengeance(String name) {
        out.println("Die, " + name + ", you horrible bastard!");
        out.println("*shakes fist*");
    }
}