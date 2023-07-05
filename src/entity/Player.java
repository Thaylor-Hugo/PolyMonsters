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
    private ItemTypes []inventiryOptions = {ItemTypes.CEREAL_BAR, ItemTypes.FRUIT, ItemTypes.GINGERBREAD};

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

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
    }
    
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

    @Override
    public int getRefHp() {
        return 100;
    }
    
    @Override
    public void update() {
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
                if (sprinting) imagePath = sprintingDownPath;
                else imagePath = walkingDownPath;   
                break;

            case LEFT:
                if (sprinting) imagePath = sprintingLeftPath;
                else imagePath = walkingLeftPath;
                break;

            case RIGHT:
                if (sprinting) imagePath = sprintingRightPath;
                else imagePath = walkingRightPath;
                break;

            case UP:
                if (sprinting) imagePath = sprintingUpPath; 
                else imagePath = walkingUpPath; 
                break;

            default:
                if (sprinting) imagePath = sprintingDownPath;
                else imagePath = walkingDownPath;
                break;
        }
        return imagePath;
    }

    public void setOnLastSafePosition() {
        mapX = lastSafeX;
        mapY = lastSafeY;
    }

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

    public void loot(Map <ItemTypes, ArrayList<Item>> loot) {
        for (ItemTypes itemTypes : inventiryOptions) {
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
            if (!inventory.get(inventiryOptions[inventoryOption]).isEmpty())
                inventory.get(inventiryOptions[inventoryOption]).remove(0).use(this);
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
}
