package entity.monsters;

import java.util.Random;

import javax.swing.ImageIcon;

import actions.Sound;
import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
import main.GamePanel;

public class Sereia extends Monsters {

    private static String basicPath = "resources/monsters/sereias/";
    private static String pinkPath = "pink/";
    private static String greenPath = "green/";
    private static String rightPath = "right.gif";
    private static String leftPath = "left.gif";
    private static String battlePath = "left.gif";
    private final String finalRightPath;
    private final String finalLeftPath;
    private boolean pink; // if false, its a green mermaid

    final int tilesToMove = 5;

    public Sereia(GamePanel gp, int mapX, int mapY) {
        this.gp = gp;
        Random rand = new Random();
        pink = rand.nextBoolean();
        if (pink) {
            finalLeftPath = basicPath + pinkPath + leftPath;
            finalRightPath = basicPath + pinkPath + rightPath;
        } else {
            finalLeftPath = basicPath + greenPath + leftPath;
            finalRightPath = basicPath + greenPath + rightPath;
        } 
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 4;
        mapX = gp.tileSize * 40;
        mapY = gp.tileSize;
        speed = 3;
        moving = true;
        if (pink) {
            battleImage = new ImageIcon(basicPath + pinkPath + battlePath).getImage();
        } else battleImage = new ImageIcon(basicPath + greenPath + battlePath).getImage();
        hp = 150;
        damage = 15;
        mvDirect = MovementDirection.DOWN;
        setMovementStrategy(MovementTypes.SQUARE, tilesToMove * gp.tileSize, null);
        sound = new Sound(getClass().getResource("/music/swim.wav"));
    }

    @Override
    public int getRefHp() {
        return 150;
    }

    @Override
    public void update() {
        playSound();
        mvStrategy.move(this);
    }

    @Override
    protected String getEntityImagePath() {
        String imagePath;
        switch (mvDirect) {
            case DOWN:
                imagePath = finalLeftPath;
                break;

            case LEFT:
                imagePath = finalLeftPath;
                break;

            case RIGHT:
                imagePath = finalRightPath;
                break;

            case UP:
                imagePath = finalRightPath;
                break;

            default:
                imagePath = finalRightPath;
                break;
        }
        return imagePath;
    }
}
