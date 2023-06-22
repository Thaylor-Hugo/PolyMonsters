package entity.monsters;

import javax.swing.ImageIcon;

import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
import main.GamePanel;

public class Golem extends Monsters {

    private static String downPath = "resources/monsters/golem/down.gif";
    private static String upPath = "resources/monsters/golem/up.gif";
    private static String battlePath = "resources/monsters/golem/down.gif";

    final int tilesToMove = 5;

    public Golem(GamePanel gp, int mapX, int mapY) {
        this.gp = gp;
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 5;
        mapX = gp.tileSize * 30;
        mapY = gp.tileSize;
        speed = 1;
        moving = true;
        battleImage = new ImageIcon(battlePath).getImage();
        hp = 300;
        damage = 30;
        mvDirect = MovementDirection.DOWN;
        setMovementStrategy(MovementTypes.UP_DOWN, tilesToMove * gp.tileSize, null);
    }

    @Override
    public int getRefHp() {
        return 300;
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

            case UP:
                imagePath = upPath;
                break;

            default:
                imagePath = downPath;
                break;
        }
        return imagePath;
    }
}
