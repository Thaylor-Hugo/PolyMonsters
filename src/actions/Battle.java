// TODO: battleState (improve update() and draw())
package actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.Player;
import entity.monsters.Monsters;
import main.GamePanel;
import main.KeyHandler;

public class Battle {
    public boolean inBattle;
    Player player;
    ArrayList<Monsters> monsters;
    GamePanel gp;
    Monsters battleMonster;
    KeyHandler keyH;

    private String[] options = {"Ataque", "Ataque Carregado", "Usar item", "Fugir"};
    private int currentOption = 0;
    private int maxOption = options.length - 1;

    public Battle(ArrayList<Monsters> monsters, Player player, GamePanel gp, KeyHandler keyH) {
        this.monsters = monsters;
        this.player = player;
        this.gp = gp;
        this.keyH = keyH;
    }

    public boolean inBattle() {
        for (Monsters monster : monsters) {
            if (inBattleRange(monster)) {
                battleMonster = monster;
                inBattle = true;
                return inBattle;
            }
        }
        inBattle = false;
        return inBattle;
    }

    private boolean inBattleRange(Monsters monster) {
        int monsterDistanceX = (monster.mapX + (gp.tileSize / 2)) - (player.mapX + (gp.tileSize / 2));
        int monsterDistanceY = (monster.mapY + (gp.tileSize / 2)) - (player.mapY + (gp.tileSize / 2));
        if (monsterDistanceX < 0) monsterDistanceX = monsterDistanceX * -1;
        if (monsterDistanceY < 0) monsterDistanceY = monsterDistanceY * -1;
        double monsterDistance = Math.sqrt(Math.pow(monsterDistanceX, 2) + Math.pow(monsterDistanceY, 2));
        
        if (monsterDistance <= monster.visionRange) return true;
        return false;
    }

    public void draw(Graphics2D g2) {
        // Background Color
        g2.setColor(new Color(31, 38, 8));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
        // Content
        drawBattleDisplay(g2);
        drawOptions(g2);
    }

    private void drawBattleDisplay(Graphics2D g2) {
        // Display window with the battle view
        g2.setColor(new Color(207,229,228));
        g2.fillRect(gp.tileSize, gp.tileSize, gp.screenWidth - 2*gp.tileSize, gp.screenHeight/2);
        
        // Title
        g2.setColor(Color.BLACK);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 48));
		g2.drawString("Batalha", gp.tileSize, gp.tileSize + 48);

        // Borda
        g2.setStroke(new BasicStroke(10));
        g2.drawRect(gp.tileSize-5, gp.tileSize-5, gp.screenWidth - 2*gp.tileSize + 10, gp.screenHeight/2 + 10);
    
        // Cenario
        g2.drawImage(player.entityImage, gp.tileSize * 3, gp.tileSize * 4 - gp.tileSize/2, gp.tileSize * 3, gp.tileSize * 3, null);
        g2.drawImage(battleMonster.entityImage, gp.tileSize * 10, gp.tileSize * 2, gp.tileSize * 3, gp.tileSize * 3, null);
    }

    private void drawOptions(Graphics2D g2) {
        // Draw the player options in a battle

        g2.setColor(new Color(75, 17, 17));
        g2.fillRect(gp.tileSize*1, gp.screenHeight - gp.tileSize*4, gp.tileSize*6, gp.tileSize);
        g2.fillRect(gp.tileSize*9, gp.screenHeight - gp.tileSize*4, gp.tileSize*6, gp.tileSize);
        g2.fillRect(gp.tileSize*1, gp.screenHeight - gp.tileSize*2, gp.tileSize*6, gp.tileSize);
        g2.fillRect(gp.tileSize*9, gp.screenHeight - gp.tileSize*2, gp.tileSize*6, gp.tileSize);
        
        // Diferent color for current option
        g2.setColor(new Color(192, 182, 47));
        if (currentOption == 0) g2.fillRect(gp.tileSize*1, gp.screenHeight - gp.tileSize*4, gp.tileSize*6, gp.tileSize);
        if (currentOption == 1) g2.fillRect(gp.tileSize*9, gp.screenHeight - gp.tileSize*4, gp.tileSize*6, gp.tileSize);
        if (currentOption == 2) g2.fillRect(gp.tileSize*1, gp.screenHeight - gp.tileSize*2, gp.tileSize*6, gp.tileSize);
        if (currentOption == 3) g2.fillRect(gp.tileSize*9, gp.screenHeight - gp.tileSize*2, gp.tileSize*6, gp.tileSize);

        // Options texts
        g2.setColor(Color.WHITE);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        g2.drawString(options[0], gp.tileSize*3, gp.screenHeight - gp.tileSize*4 + gp.tileSize*2/3);
		g2.drawString(options[1], gp.tileSize*10, gp.screenHeight - gp.tileSize*4 + gp.tileSize*2/3);
		g2.drawString(options[2], gp.tileSize*3, gp.screenHeight - gp.tileSize*2 + gp.tileSize*2/3);
		g2.drawString(options[3], gp.tileSize*11 + gp.tileSize/2, gp.screenHeight - gp.tileSize*2 + gp.tileSize*2/3);

        // Bordas
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.BLACK);
        g2.drawRect(gp.tileSize*1 - 5, gp.screenHeight - gp.tileSize*4 - 5, gp.tileSize*6 + 10, gp.tileSize + 10);
        g2.drawRect(gp.tileSize*9 - 5, gp.screenHeight - gp.tileSize*4 - 5, gp.tileSize*6 + 10, gp.tileSize + 10);
        g2.drawRect(gp.tileSize*1 - 5, gp.screenHeight - gp.tileSize*2 - 5, gp.tileSize*6 + 10, gp.tileSize + 10);
        g2.drawRect(gp.tileSize*9 - 5, gp.screenHeight - gp.tileSize*2 - 5, gp.tileSize*6 + 10, gp.tileSize + 10);
 
    }

    public void update() {
        if (!inBattle) inBattle();
        if (inBattle) {
            if(keyH.downPressed) {
			    currentOption += 2;
    			keyH.downPressed = false;
			    if(currentOption > maxOption) currentOption -= options.length;
		    }
            if(keyH.upPressed) {
			    currentOption -= 2;
    			keyH.upPressed = false;
			    if(currentOption < 0) currentOption += options.length;
		    }
            if(keyH.rightPressed) {
			    currentOption += 1;
    			keyH.rightPressed = false;
			    if(currentOption > maxOption) currentOption -= options.length;
		    }
            if(keyH.leftPressed) {
			    currentOption -= 1;
    			keyH.leftPressed = false;
			    if(currentOption < 0) currentOption += options.length;
		    }
            if(keyH.interrectPressed) {
                // TODO: Improve how the battle works
                if (currentOption == 0) {
                    // Normal atack
                    inBattle = false;
                    monsters.remove(battleMonster);
                }
                if (currentOption == 1) {
                    // Charged atack
                    inBattle = false;
                    monsters.remove(battleMonster);
                }
                if (currentOption == 2) {
                    // Itens
                    
                }
                if (currentOption == 3) {
                    // Run away
                    if (player.speed > battleMonster.speed) {
                        // Player is able to run away
                        player.setOnLastSafePosition();
                        inBattle = false;
                    } else {
                        // Couldn't run
                    }
                }
            }
        }
    }
}
