package entity.monsters;

import javax.swing.ImageIcon;

import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
import main.GamePanel;

public class Rat extends Monsters {
    private static String downPath = "resources/monsters/rats/rat_down.gif";
    private static String upPath = "resources/monsters/rats/rat_up.gif";
    private static String rightPath = "resources/monsters/rats/rat_right.gif";
    private static String leftPath = "resources/monsters/rats/rat_left.gif";
    private static String battlePath = "resources/battle/rat.gif";

    final int tilesToMove = 5;

    public Rat(GamePanel gp, int mapX, int mapY) {
        this.gp = gp;
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 1;
        mapX = gp.tileSize * 10;
        mapY = gp.tileSize;
        speed = 3;
        moving = true;
        battleImage = new ImageIcon(battlePath).getImage();
        hp = 50;
        damage = 5;
        mvDirect = MovementDirection.DOWN;
        setMovementStrategy(MovementTypes.RANDOM, tilesToMove * gp.tileSize, null);
    }

    @Override
    public int getRefHp() {
        return 50;
    }

    @Override
    public void update() {
        mvStrategy.move(this);
    }

    @Override
    protected String getEntityImagePath() {
        String imagePath;
        switch (mvDirect) {
            case DOWN:
                imagePath = downPath;
                break;

            case LEFT:
                imagePath = leftPath;
                break;

            case RIGHT:
                imagePath = rightPath;
                break;

            case UP:
                imagePath = upPath;
                break;

            default:
                imagePath = rightPath;
                break;
        }
        return imagePath;
    }
}
