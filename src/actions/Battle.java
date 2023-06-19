// TODO: battleState (improve update() and draw())
package actions;

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
        g2.setColor(new Color(60, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setColor(new Color(0,0,0));
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 48));
		g2.drawString("In battle with monster", (gp.screenWidth/2) - 100, 100);
		g2.drawString("Win Battle", (gp.screenWidth/2) - 50, 400);
		g2.drawImage(battleMonster.entityImage, (gp.screenWidth/2) - 138, 360, 48, 48, null);
    }

    public void update() {
        if (!inBattle) inBattle();
        if (inBattle)
            if(keyH.interrectPressed) {
                inBattle = false;
                monsters.remove(battleMonster);
            }
    }
}
