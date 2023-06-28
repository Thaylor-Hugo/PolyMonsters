package entity.monsters;

import javax.swing.ImageIcon;

import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
import main.GamePanel;

public class Goblin extends Monsters {

    private static String downPath = "resources/monsters/goblins/small/down.gif";
    private static String upPath = "resources/monsters/goblins/small/up.gif";
    private static String battlePath = "resources/monsters/goblins/small/down.gif";

    final int tilesToMove = 5;

    public Goblin(GamePanel gp, int mapX, int mapY) {
        this.gp = gp;
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 3;
        mapX = gp.tileSize * 20;
        mapY = gp.tileSize;
        speed = 3;
        moving = true;
        battleImage = new ImageIcon(battlePath).getImage();
        hp = 100;
        damage = 10;
        mvDirect = MovementDirection.DOWN;
        setMovementStrategy(MovementTypes.UP_DOWN, tilesToMove * gp.tileSize, null);
    }

    @Override
    public int getRefHp() {
        return 100;
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
