package entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import actions.movements.MoveControled;
import actions.movements.MoveRandom;
import actions.movements.MoveSquare;
import actions.movements.MoveUpDown;
import actions.movements.MovementStrategy;
import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
import main.GamePanel;
import main.KeyHandler;

public abstract class Entity {
    public int mapX;    // position related to the world map
    public int mapY;
    public int speed;
    public boolean moving;
    protected MovementDirection mvDirect;
    protected MovementStrategy mvStrategy;
    public Image entityImage;
    public Image battleImage;
    protected GamePanel gp;
    public int hp;              // valor de hp que entidade possui no momento
    public int damage;

    /**
     * Set instance variables to defalt values
     */
    abstract protected void setDefaltValues();

    /**
     * Update the entity values, such as positions
     */
    abstract public void update();

    /**
     * Get the pathname for the entity image
     * @return a pathname string
     */
    abstract protected String getEntityImagePath();
    
    /**
     * Get image that represents the entity and is going to be drawn
     * @return a {@code Image} of the entity
     */
    protected Image getEntityImage() {
        String imagePath = getEntityImagePath();
        if (moving) entityImage = new ImageIcon(imagePath).getImage();
        else try {
                entityImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entityImage;
    }

    /**
     * Draw entity image in the {@code GamePanel}
     * @param g2 {@code Graphics2D} from {@code GamePanel paintComponent} method 
     */
    public void draw(Graphics2D g2) {
        int screenX = mapX - gp.getPlayer().mapX + gp.getPlayer().screenX;
        int screenY = mapY - gp.getPlayer().mapY + gp.getPlayer().screenY;
        entityImage = getEntityImage();
        g2.drawImage(entityImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    /**
     * Get the reference value of a 100% HP for the entity
     * @return {@code int}  that represent 100% HP
     */
    abstract public int getRefHp(); 

    /**
     * Get the current entity's movement direction
     * @return entity's {@code MovementDirection}
     */
    public MovementDirection getMovementDirection() {
        return mvDirect;
    }

    /**
     * Set a new movement direction for entity
     * @param mvDirect new direction entity is moving 
     * @see MovementDirection
     */
    public void setMovementDirection(MovementDirection mvDirect) {
        this.mvDirect = mvDirect;
    }

    /**
     * Set the movement strategy for the entity
     * @param mvType a movement strategy to use
     * @param movementConst a constant used by strategy (what it does depend on strategy)
     * @param keyH a key handler for controled movement (null for the rest)
     * @see MovementTypes
     */
    public void setMovementStrategy(MovementTypes mvType, int movementConst, KeyHandler keyH) {
        switch (mvType) {
            case UP_DOWN:
                mvStrategy = new MoveUpDown(movementConst);
                break;
            case SQUARE:
                mvStrategy = new MoveSquare(movementConst);
                break;
            case RANDOM:
                mvStrategy = new MoveRandom(movementConst);
                break;
            case CONTROLED:
                mvStrategy = new MoveControled(keyH);
                break;
            default:
                mvStrategy = new MoveSquare(movementConst);
                break;
        }
    }
}
