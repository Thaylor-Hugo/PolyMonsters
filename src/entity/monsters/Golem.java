package entity.monsters;

import javax.swing.ImageIcon;

import main.GamePanel;

public class Golem extends Monsters {

    private static String downPath = "resources/monsters/golem/down.gif";
    private static String upPath = "resources/monsters/golem/up.gif";
    private static String battlePath = "resources/monsters/golem/down.gif";
    private static final int movingDown = 1;
    private static final int movingUp = 2;
    private int movementDirection;

    final int tilesToMove = 5;
    int totalMoved = 0;
    boolean forward = true;     // false means backwards

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
    }

    @Override
    public int getRefHp() {
        return 300;
    }

    @Override
    public void update() {
        // TODO better moviment strategy
        int totalToMove = tilesToMove * gp.tileSize;

        if (forward) {
            mapY += speed;
            movementDirection = movingDown;
        }
        else {
            mapY -= speed;
            movementDirection = movingUp;
        } 
        totalMoved += speed;

        if (totalMoved >= totalToMove) {
            if (forward) forward = false;
            else forward = true;
            totalMoved = 0;
        }
    }

    @Override
    protected String getEntityImagePath() {
        String imagePath;
        switch (movementDirection) {
            case movingDown:
                imagePath = downPath;
                break;

            case movingUp:
                imagePath = upPath;
                break;

            default:
                imagePath = downPath;
                break;
        }
        return imagePath;
    }
    
}
