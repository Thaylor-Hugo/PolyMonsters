package entity.objects;

import java.util.Map;
import java.util.Random;

import entity.Player;
import itens.Item;
import main.GamePanel;
import main.KeyHandler;

public class Backpack extends Object {
    private boolean opened;
    private int color;
    private final String[] closedPath = {"resources/objects/backpacks/blue_closed.png", "resources/objects/backpacks/red_closed.png"};
    private final String[] openedPath = {"resources/objects/backpacks/blue_opened.png", "resources/objects/backpacks/red_opened.png"};

    public Backpack(GamePanel gp, int mapX, int mapY, Player player, KeyHandler keyH, Map<Item, Integer> content) {
        this.inventory = content;
        this.gp = gp;
        this.player = player;
        this.keyH = keyH;
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    @Override
    protected void setDefaltValues() {
        Random rand = new Random();
        color = rand.nextInt(closedPath.length);
        interrectRange = gp.tileSize * 2;
        opened = false;
    }

    @Override
    public void update() {
        if (!inRange()) return;
        if (opened) return;
        if (keyH.interrectPressed) {
            inventory.clear();
            opened = true;
            player.loot(inventory);
        }
    }

    @Override
    protected String getEntityImagePath() {
        if (opened) return openedPath[color];
        else return closedPath[color];
    }

    @Override
    public int getRefHp() {
        return 100;
    }
    
}
