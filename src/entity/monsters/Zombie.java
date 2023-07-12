package entity.monsters;

import java.util.Random;

import javax.swing.ImageIcon;

import actions.Sound;
import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
import entity.Player;
import main.GamePanel;

public class Zombie extends Monsters {

    private static String basicPath = "resources/monsters/zombies/";
    private static String mostAlivePath = "mostAlive/";
    private static String mostDeadPath = "mostDead/";
    private static String rightPath = "right.gif";
    private static String leftPath = "left.gif";
    private static String upPath = "up.gif";
    private static String downPath = "down.gif";
    private static String battlePathMostAlive = "resources/battle/zombies/mostAlive.gif";
    private static String battlePathMostDead = "resources/battle/zombies/mostDead.gif";
    private final String finalRightPath;
    private final String finalLeftPath;
    private final String finalUpPath;
    private final String finalDownPath;
    private boolean mostDead; // if false, its a mostAlive zombie
    private Player player;

    final int tilesToMove = 5;

    public Zombie(GamePanel gp, int mapX, int mapY, Player player) {
        this.gp = gp;
        this.player = player;
        Random rand = new Random();
        mostDead = rand.nextBoolean();
        if (mostDead) {
            finalLeftPath = basicPath + mostDeadPath + leftPath;
            finalRightPath = basicPath + mostDeadPath + rightPath;
            finalUpPath = basicPath + mostDeadPath + upPath;
            finalDownPath = basicPath + mostDeadPath + downPath;
        } else {
            finalLeftPath = basicPath + mostAlivePath + leftPath;
            finalRightPath = basicPath + mostAlivePath + rightPath;
            finalUpPath = basicPath + mostAlivePath + upPath;
            finalDownPath = basicPath + mostAlivePath + downPath;
        } 
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 4;
        mapX = gp.tileSize * 50;
        mapY = gp.tileSize;
        moving = true;
        if (mostDead) {
            battleImage = new ImageIcon(battlePathMostDead).getImage();
            speed = 1;
            hp = 60;
            damage = 6;
        } else {
            battleImage = new ImageIcon(battlePathMostAlive).getImage();
            speed = 2;
            hp = 80;
            damage = 8;
        }
        mvDirect = MovementDirection.DOWN;
        setMovementStrategy(MovementTypes.RANDOM, tilesToMove * gp.tileSize, null);
        sound = new Sound(getClass().getResource("/music/zombie.wav"));
    }

    @Override
    public int getRefHp() {
        return 100;
    }

    @Override
    public void update() {
        playSound();
        if (inFollowRange()) mvStrategy.follow(player, this, gp.tileSize);
        else mvStrategy.move(this);
    }

    private boolean inFollowRange() {
        int followRange = visionRange * 2;
        int monsterDistanceX = (mapX + (gp.tileSize / 2)) - (player.mapX + (gp.tileSize / 2));
        int monsterDistanceY = (mapY + (gp.tileSize / 2)) - (player.mapY + (gp.tileSize / 2));
        double monsterDistance = Math.sqrt(Math.pow(monsterDistanceX, 2) + Math.pow(monsterDistanceY, 2));
        
        if (monsterDistance <= followRange) return true;
        return false;
    }

    @Override
    protected String getEntityImagePath() {
        String imagePath;
        switch (mvDirect) {
            case DOWN:
                imagePath = finalDownPath;
                break;

            case LEFT:
                imagePath = finalLeftPath;
                break;

            case RIGHT:
                imagePath = finalRightPath;
                break;

            case UP:
                imagePath = finalUpPath;
                break;

            default:
                imagePath = finalDownPath;
                break;
        }
        return imagePath;
    }
}
