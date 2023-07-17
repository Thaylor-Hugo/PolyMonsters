package entity.monsters;

import javax.swing.ImageIcon;

import actions.Sound;
import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
import main.GamePanel;

public class Ghost extends Monsters {
    final int tilesToMove = 5;
    int totalMoved = 0;
    private static String battlePath = "resources/monsters/ghosts/ghost1.gif";

    public Ghost(GamePanel gp, int mapX, int mapY) {
        this.gp = gp;
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        visionRange = gp.tileSize * 3;
        mapX = gp.tileSize;
        mapY = gp.tileSize;
        speed = 2;
        moving = true;
        battleImage = new ImageIcon(battlePath).getImage();
        hp = 200;
        damage = 20;
        mvDirect = MovementDirection.DOWN;
        setMovementStrategy(MovementTypes.SQUARE, tilesToMove * gp.tileSize, null);
        sound = new Sound(getClass().getResource("/music/ghost.wav"));
    }

    @Override
    public int getRefHp() {
        return 200;
    }

    @Override
    public void update() {
        playSound();
        mvStrategy.move(this);
    }

    @Override
    protected String getEntityImagePath() {
        return battlePath;
    }
}
