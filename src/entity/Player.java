package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
import itens.Item;
import itens.ItemTypes;
import main.GamePanel;
import main.KeyHandler;
import state.DifficultyState;
import state.EasyState;

/**
 * Game {@code Player}
 */
public class Player extends Entity {

    private static String walkingDownPath = "resources/player/movement/walking_down.gif";
    private static String walkingUpPath = "resources/player/movement/walking_up.gif";
    private static String walkingRightPath = "resources/player/movement/walking_right.gif";
    private static String walkingLeftPath = "resources/player/movement/walking_left.gif";
    private static String sprintingDownPath = "resources/player/movement/sprinting_down.gif";
    private static String sprintingUpPath = "resources/player/movement/sprinting_up.gif";
    private static String sprintingRightPath = "resources/player/movement/sprinting_right.gif";
    private static String sprintingLeftPath = "resources/player/movement/sprinting_left.gif";
    private static String battlePath = "resources/battle/player.gif";

    private static String walkingDownPath2 = "resources/monsters/zombies/mostAlive/down.gif";
    private static String walkingUpPath2 = "resources/monsters/zombies/mostAlive/up.gif";
    private static String walkingRightPath2 = "resources/monsters/zombies/mostAlive/right.gif";
    private static String walkingLeftPath2 = "resources/monsters/zombies/mostAlive/left.gif";

    private boolean alternativePlayer = false;

    private DifficultyState dificuldade;
    
    //player position related to the screen
    public final int screenX;
    public final int screenY;

    KeyHandler keyH;
    public boolean sprinting;

    private final int safeDistance;
    private int posibleSafeX;
    private int posibleSafeY;
    private int lastSafeX;
    private int lastSafeY;
    private int inventoryOption;
    private ItemTypes []inventiryOptions = {ItemTypes.GINGERBREAD, ItemTypes.CEREAL_BAR, ItemTypes.FRUIT};
    private Buff buff;

    /**
     * Set player image as player 2
     * @param alternative If true, player is a zombie, otherwise if false
     */
    public void setAsAlternativePlayer(boolean alternative){
        alternativePlayer =  alternative;
    }

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        dificuldade = new EasyState();

        //player's position fixed in the center of screen
        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        setDefaltValues();
        safeDistance = gp.tileSize * 5;
    }
    
    @Override
    protected void setDefaltValues() {
        mapX = gp.tileSize*36;     //position related to the world map
        mapY = gp.tileSize*84/2;
        speed = 2;
        sprinting = false;
        moving = false;
        mvDirect = MovementDirection.DOWN;
        lastSafeX = mapX;
        lastSafeY = mapY;
        posibleSafeX = mapX;
        posibleSafeY = mapY;
        battleImage = new ImageIcon(battlePath).getImage();
        hp = 100;
        damage = 10;
        setMovementStrategy(MovementTypes.CONTROLED, 0, keyH);
        setInventory();
        buff = new Buff(speed, damage);
    }
    
    /**
     * Set a initial inventory
     */
    private void setInventory() {
        inventory = new HashMap<>();
        inventory.put(ItemTypes.CEREAL_BAR, new ArrayList<>());
        inventory.put(ItemTypes.FRUIT, new ArrayList<>());
        inventory.put(ItemTypes.GINGERBREAD, new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            inventory.get(ItemTypes.CEREAL_BAR).add(new Item(ItemTypes.CEREAL_BAR));
            inventory.get(ItemTypes.FRUIT).add(new Item(ItemTypes.FRUIT));
            inventory.get(ItemTypes.GINGERBREAD).add(new Item(ItemTypes.GINGERBREAD));
        }
    }

    public void setDifficultyState(DifficultyState state) {
        this.dificuldade = state;
        buff.setBaseDamage(dificuldade.getDamage());
        buff.setBaseSpeed(dificuldade.getSpeed());
    }
    
    public int getHp(){
        return dificuldade.getHp();
    }

    public void setHp(int hp) {
        dificuldade.setHp(hp);
    }

    public void setDamage(int damage) {
        dificuldade.setDamage(damage);
        buff.setBaseDamage(damage);
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed) {
        dificuldade.setSpeed(speed);
        buff.setBaseSpeed(speed);
    }

    public void playGame() {
        dificuldade.play();
    }

    @Override
    public int getRefHp() {
        return dificuldade.getRefHp();
    }
    
    @Override
    public void update() {
        speed = buff.getSpeed();
        if (gp.showInventory) {
            updateInventary();
            moving = false;
        } else {
            if (keyH.sprintPressed) {
                sprinting = true;
                speed += 2;
            } else sprinting = false;
            
            mvStrategy.move(this);
            setLastSafePosition();
            if (sprinting) {
                speed -= 2;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        entityImage = getEntityImage();
        g2.drawImage(entityImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
        drawBuff(g2, screenX, screenY, gp.tileSize, false);
        if (gp.showInventory) {
            updateInventary();
            drawInventary(g2);
        }
    }

    @Override
    protected String getEntityImagePath() {
        String imagePath;
        switch (mvDirect) {
            case DOWN:
                if(alternativePlayer) imagePath = walkingDownPath2;
                else if (sprinting) imagePath = sprintingDownPath;
                else imagePath = walkingDownPath;   
                break;

            case LEFT:
                if(alternativePlayer) imagePath = walkingLeftPath2;
                else if (sprinting) imagePath = sprintingLeftPath;
                else imagePath = walkingLeftPath;
                break;

            case RIGHT:
                if(alternativePlayer) imagePath = walkingRightPath2;
                else if (sprinting) imagePath = sprintingRightPath;
                else imagePath = walkingRightPath;
                break;

            case UP:
                if(alternativePlayer) imagePath = walkingUpPath2;
                else if (sprinting) imagePath = sprintingUpPath; 
                else imagePath = walkingUpPath; 
                break;

            default:
                if(alternativePlayer) imagePath = walkingDownPath2;
                else if (sprinting) imagePath = sprintingDownPath;
                else imagePath = walkingDownPath;
                break;
        }
        return imagePath;
    }

    /**
     * Set player on their last safe position
     */
    public void setOnLastSafePosition() {
        mapX = lastSafeX;
        mapY = lastSafeY;
    }

    /**
     * Define the player last position
     */
    private void setLastSafePosition() {
        if (Math.abs(mapX - posibleSafeX) >= safeDistance) {
            lastSafeX = posibleSafeX;
            posibleSafeX = mapX;
        }
        if (Math.abs(mapY - posibleSafeY) >= safeDistance) {
            lastSafeY = posibleSafeY;
            posibleSafeY = mapY;
        }
    }

    /**
     * Loot a backpack content
     * @param loot Content being looted
     */
    public void loot(Map <ItemTypes, ArrayList<Item>> loot) {
        for (ItemTypes itemTypes : inventiryOptions) {
            if (loot.get(itemTypes) == null) continue;
            inventory.get(itemTypes).addAll(loot.get(itemTypes));
        }
    }

    /**
     * Update user inventary, selecting and using itens
     */
    public void updateInventary() {
        if(keyH.rightPressed) {
            inventoryOption += 1;
        	keyH.rightPressed = false;
            if(inventoryOption > 2) inventoryOption -= 3;
        } else if(keyH.leftPressed) {
            inventoryOption -= 1;
        	keyH.leftPressed = false;
            if(inventoryOption < 0) inventoryOption += 3;
        } else if (keyH.interrectPressed) {
            keyH.interrectPressed = false;
            if (!inventory.get(inventiryOptions[inventoryOption]).isEmpty()) {
                if (inventiryOptions[inventoryOption] == ItemTypes.GINGERBREAD && !buff.damageBuffOn) {
                    inventory.get(inventiryOptions[inventoryOption]).remove(0).use(this);
                } else
                if (inventiryOptions[inventoryOption] == ItemTypes.CEREAL_BAR && !buff.speedBuffOn) {
                    inventory.get(inventiryOptions[inventoryOption]).remove(0).use(this);
                } else 
                if (inventiryOptions[inventoryOption] == ItemTypes.FRUIT && hp != getRefHp()) {
                    inventory.get(inventiryOptions[inventoryOption]).remove(0).use(this);
                }
                
            }
        }
    }
    
    /**
     * Draw the player inventory on screen
     * @param g2 Graphics2D used for drawing
     */
    public void drawInventary(Graphics2D g2) {
        // backGroung
        g2.setStroke(new BasicStroke(12));
        g2.setColor(Color.BLACK);
        g2.drawRect(gp.tileSize*5/2-5, gp.screenHeight - gp.tileSize*5-5, gp.tileSize*11 + 10, gp.tileSize*4 + 10);
        g2.setColor(new Color(31, 38, 8));
        g2.fillRect(gp.tileSize*5/2, gp.screenHeight - gp.tileSize*5, gp.tileSize * 11, gp.tileSize * 4);

        // Itens
        g2.setColor(new Color(75, 17, 17));
        g2.fillRect(gp.tileSize*3, gp.screenHeight - gp.tileSize*9/2, gp.tileSize*3, gp.tileSize*3);
        g2.fillRect(gp.tileSize*13/2, gp.screenHeight - gp.tileSize*9/2, gp.tileSize*3, gp.tileSize*3);
        g2.fillRect(gp.tileSize*10, gp.screenHeight - gp.tileSize*9/2, gp.tileSize*3, gp.tileSize*3);
        
        // Diferent color for current option
        g2.setColor(new Color(192, 182, 47));
        if (inventoryOption == 0) g2.fillRect(gp.tileSize*3, gp.screenHeight - gp.tileSize*9/2, gp.tileSize*3, gp.tileSize*3);
        if (inventoryOption == 1) g2.fillRect(gp.tileSize*13/2, gp.screenHeight - gp.tileSize*9/2, gp.tileSize*3, gp.tileSize*3);
        if (inventoryOption == 2) g2.fillRect(gp.tileSize*10, gp.screenHeight - gp.tileSize*9/2, gp.tileSize*3, gp.tileSize*3);
        
        // Bordas
        g2.setStroke(new BasicStroke(8));
        g2.setColor(Color.BLACK);
        g2.drawRect(gp.tileSize*3, gp.screenHeight - gp.tileSize*9/2, gp.tileSize*3, gp.tileSize*3);
        g2.drawRect(gp.tileSize*13/2, gp.screenHeight - gp.tileSize*9/2, gp.tileSize*3, gp.tileSize*3);
        g2.drawRect(gp.tileSize*10, gp.screenHeight - gp.tileSize*9/2, gp.tileSize*3, gp.tileSize*3);
        
        // Itens and texts
        g2.setColor(Color.WHITE);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString("Ginger Bread: " + inventory.get(inventiryOptions[0]).size(), gp.tileSize*3 + 5, gp.screenHeight - gp.tileSize - 5);
        g2.drawString("Cereal Bar: " + inventory.get(inventiryOptions[1]).size(), gp.tileSize*13/2 + 5, gp.screenHeight - gp.tileSize - 5);
        g2.drawString("Fruit: " + inventory.get(inventiryOptions[2]).size(), gp.tileSize*10 + 5, gp.screenHeight - gp.tileSize - 5);

        // Itens Images
        
        g2.drawImage(Item.getItemImage(ItemTypes.GINGERBREAD), gp.tileSize*3 + gp.tileSize/2, gp.screenHeight - gp.tileSize*9/2 + gp.tileSize/2, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(Item.getItemImage(ItemTypes.CEREAL_BAR), gp.tileSize*13/2 + gp.tileSize/2, gp.screenHeight - gp.tileSize*9/2 + gp.tileSize/2, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(Item.getItemImage(ItemTypes.FRUIT), gp.tileSize*10 + gp.tileSize/2, gp.screenHeight - gp.tileSize*9/2 + gp.tileSize/2, gp.tileSize * 2, gp.tileSize * 2, null);
    }

    /**
     * Get player damage
     * @return Damage
     */
    public int getDamage() {
        return buff.getDamage();
    }

    /**
     * Activate a damage buff
     */
    public void activateDamageBuff() {
        buff.activateDamageBuff();
    }

    /**
     * Activate a speed buff
     */
    public void activateSpeedBuff() {
        buff.activateSpeedBuff();
    }

    /**
     * Draw damage animation
     * @param g2 {@code Graphics2D} from {@code GamePanel paintComponent} method 
     * @param screenX Player x position
     * @param screenY Player y position
     * @param tileSize Game panel tile size
     * @param inBattle If player is in battle
     */
    public void drawBuff(Graphics2D g2, int screenX, int screenY, int tileSize, boolean inBattle) {
        buff.draw(g2, screenX, screenY, tileSize, inBattle);
    }

    /**
     * Cure the player
     */
    public void activateHpCure() {
        hp = getRefHp();
        buff.activateHpCure();
    }
}
