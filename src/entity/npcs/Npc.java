package entity.npcs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;

import actions.movements.enums.MovementDirection;
import actions.movements.enums.MovementTypes;
import entity.Entity;
import entity.Player;
import main.GamePanel;
import main.KeyHandler;

public class Npc extends Entity {
    private static final String basicPath = "resources/npcs/npc";
    private static final String downPath = "/down.gif";
    private static final String upPath = "/up.gif";
    private static final String rightPath = "/right.gif";
    private static final String leftPath = "/left.gif";
    private static final String []dialog = {
        "Hey you, please be careful, theres a bunch of monster around here", 
        "I'm so afraid! My friend turned into a zombie" , 
        "I don't wanna die!",
        "Please, hide while you can",
        "I wonder why there is so many monster. Where did they come from?",
        "Somebody same me!",
        "Help! Help! There are monsters here",
        "Are you a hero?!"};
    private int npcNum;
    int interrectRange;
    boolean inInterection;
    Player player;
    KeyHandler keyH;
    boolean npcMoves;
    private boolean talking;
    private int talkingTime = 0;
    private final static int totalTalkingTime = 60 * 5;
    private String message;
    static Random rand = new Random();

    public Npc(GamePanel gp, int mapX, int mapY, Player player, KeyHandler keyH) {
        this.gp = gp;
        this.player = player;
        this.keyH = keyH;
        setDefaltValues();
        this.mapX = mapX;
        this.mapY = mapY;
    }

    protected boolean inRange() {
        int distanceX = (mapX + (gp.tileSize / 2)) - (player.mapX + (gp.tileSize / 2));
        int distanceY = (mapY + (gp.tileSize / 2)) - (player.mapY + (gp.tileSize / 2));
        if (distanceX < 0) distanceX = distanceX * -1;
        if (distanceY < 0) distanceY = distanceY * -1;
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        
        if (distance <= interrectRange) return true;
        return false;
    }

    @Override
    protected void setDefaltValues() {
        interrectRange = gp.tileSize * 2;
        npcMoves = rand.nextBoolean();
        npcNum = rand.nextInt(3) + 1;
        mapX = 0;
        mapY = 0;
        speed = 2;
        moving = false;
        mvDirect = MovementDirection.DOWN;
        hp = 100;
        damage = 10;
        setMovementStrategy(MovementTypes.SQUARE, gp.tileSize * 5, null);
    }

    @Override
    public void update() {
        if (inRange() && keyH.interrectPressed) {
            talking = true;
            moving = false;
        } else if(npcMoves && !talking) mvStrategy.move(this);
    }

    private void talk(Graphics2D g2) {
        if (talkingTime == 0) message = dialog[rand.nextInt(dialog.length)];
        int screenX = mapX - gp.getPlayer().mapX + gp.getPlayer().screenX;
        int screenY = mapY - gp.getPlayer().mapY + gp.getPlayer().screenY;
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString(message, screenX, screenY - 8);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if (talking) {
            talk(g2);
            talkingTime++;
            if (talkingTime == totalTalkingTime) {
                talkingTime = 0;
                talking = false;
            }
        }
    }

    @Override
    protected String getEntityImagePath() {
        String imagePath;
        String direction;
        switch (mvDirect) {
            case DOWN:
                direction = downPath;   
                break;

            case LEFT:
                direction = leftPath;
                break;

            case RIGHT:
                direction = rightPath;
                break;

            case UP:
                direction = upPath; 
                break;

            default:
                direction = downPath;
                break;
        }
        imagePath = basicPath + npcNum + direction;
        return imagePath;
    }

    @Override
    public int getRefHp() {
        return 100;
    }
    
}
