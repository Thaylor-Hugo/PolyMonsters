package actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

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
    private boolean playerTurn;
    private boolean chargedAtack;
    private int chargedPosition;
    private int chargedSpeed;
    private int chargedGoal;
    private int chargedRange;
    private boolean chargedForward; // If false, chargePosition going backwards
    private final int displayTime = 60; // Time to display damage taken or dealt
    private boolean wasDamageDealt = false;
    private boolean wasDamageTaken = false;
    private boolean damageDealtOnDisplay = false;
    private boolean damageTakenOnDisplay = false;
    private int damageDealtTime = 0; // time on display
    private int damageTakenTime = 0;
    private double damageDealt = 0;    // Value of the damage
    private int damageTaken = 0;

    public Battle(ArrayList<Monsters> monsters, Player player, GamePanel gp, KeyHandler keyH) {
        this.monsters = monsters;
        this.player = player;
        this.gp = gp;
        this.keyH = keyH;
    }

    public boolean inBattle() {
        Random rand = new Random();
        for (Monsters monster : monsters) {
            if (inBattleRange(monster)) {
                battleMonster = monster;
                inBattle = true;
                playerTurn = rand.nextBoolean();
                chargedAtack = false;
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

    private void drawLifeBar(Graphics2D g2, int hp, int refHp, int x, int y) {
        int lifeBarWidth = gp.tileSize * 3 * hp / refHp;
        g2.setColor(Color.RED);
        g2.fillRect(x, y, lifeBarWidth, gp.tileSize / 5);
        
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(x, y, gp.tileSize * 3, gp.tileSize / 5);
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
        g2.drawImage(player.battleImage, gp.tileSize * 3, gp.tileSize * 4 - gp.tileSize/2, gp.tileSize * 3, gp.tileSize * 3, null);
        g2.drawImage(battleMonster.battleImage, gp.tileSize * 10, gp.tileSize * 2, gp.tileSize * 3, gp.tileSize * 3, null);
        drawLifeBar(g2, player.getHp(), player.getRefHp(), gp.tileSize * 3, gp.tileSize * 4 - gp.tileSize);
        drawLifeBar(g2, battleMonster.hp, battleMonster.getRefHp(), gp.tileSize * 10, gp.tileSize * 2 - gp.tileSize/2);
                
        drawDamage(g2, gp.tileSize * 3, gp.tileSize * 4 - gp.tileSize - 7, gp.tileSize * 10, gp.tileSize * 2 - gp.tileSize/2 - 7);

        if (chargedAtack) drawChargedAtack(g2);
    }

    private void drawDamage(Graphics2D g2, int damageTakenX, int damageTakenY, int damageDealtX, int damageDealtY) {
        if (wasDamageDealt) {
            damageDealtOnDisplay = true;
            damageDealtTime = 0;
            wasDamageDealt = false;
        }
        if (wasDamageTaken) {
            damageTakenOnDisplay = true;
            damageTakenTime = 0;
            wasDamageTaken = false;
        }
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        if (damageDealtOnDisplay) {
            if (damageDealtTime >= displayTime) damageDealtOnDisplay = false;
            g2.drawString(""+(int)damageDealt, damageDealtX, damageDealtY);
            damageDealtTime += 1;
        }
        if (damageTakenOnDisplay) {
            if (damageTakenTime >= displayTime) damageTakenOnDisplay = false;
            g2.drawString(""+(int)damageTaken, damageTakenX, damageTakenY);
            damageTakenTime += 1;
        }

    }

    private void drawChargedAtack(Graphics2D g2) {
        int positionOrigin = gp.tileSize * 8;
        int barWidth = gp.tileSize * 6;
        int positionEnd = positionOrigin + barWidth;
        int goalWidth = 2*chargedRange;
        int goalPosition = chargedGoal - chargedRange;
        int penaltyWidth = 4*chargedRange;
        int penaltyPosition = chargedGoal - 2*chargedRange;

        // Penalty or Goal is on left edge
        if (goalPosition + positionOrigin < positionOrigin) {
            goalWidth += goalPosition;
            goalPosition = 0;
        }
        if (penaltyPosition + positionOrigin < positionOrigin) {
            penaltyWidth += penaltyPosition;
            penaltyPosition = 0;
        }
        // Penalty or Goal is on right edge
        if (goalPosition + goalWidth + positionOrigin > positionEnd) {
            goalWidth -= goalPosition + goalWidth + positionOrigin - positionEnd;
        }
        if (penaltyPosition + penaltyWidth + positionOrigin > positionEnd) {
            penaltyWidth -= penaltyPosition + penaltyWidth + positionOrigin - positionEnd;
        }
        
        updateChargedPosition();
    
        // Bar
        g2.setColor(new Color(49, 2, 97));
        g2.fillRect(positionOrigin, gp.screenHeight/2, barWidth , gp.tileSize / 5);
        
        // Penalty
        g2.setColor(Color.RED);
        g2.fillRect(penaltyPosition + positionOrigin, gp.screenHeight/2, penaltyWidth , gp.tileSize / 5);
        
        // Goal
        g2.setColor(Color.YELLOW);
        g2.fillRect(goalPosition + positionOrigin, gp.screenHeight/2, goalWidth , gp.tileSize / 5);
        
        // Borda
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(positionOrigin, gp.screenHeight/2, barWidth, gp.tileSize / 5);
        
        // ChargedPosition
        g2.fillRect(chargedPosition + positionOrigin, gp.screenHeight/2 - gp.tileSize / 5, 5, 3 * gp.tileSize / 5);
    }

    private void updateChargedPosition() {
        if (chargedPosition >= gp.tileSize*6) {
            chargedForward = false;
            chargedSpeed += 1;
        }
        if (chargedPosition <= 0) {
            chargedForward = true;
            chargedSpeed += 1;
        }
        if (chargedSpeed > 10) chargedSpeed = 10;
        if (chargedForward) {
            chargedPosition += chargedSpeed;
        } else {
            chargedPosition -= chargedSpeed;
        }    
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
            if (chargedAtack) {
                if(keyH.interrectPressed) {
                    Random rand = new Random();
                    if (chargedPosition <= chargedGoal + chargedRange && chargedPosition >= chargedGoal - chargedRange) {
                        damageDealt = player.getDamage() * (rand.nextDouble() * 2 + 1); // between 1 and 3
                        battleMonster.hp -= damageDealt;
                        wasDamageDealt = true;
                        playerTurn = false;
                        chargedAtack = false;
                    } else if (chargedPosition <= chargedGoal + 2*chargedRange && chargedPosition >= chargedGoal - 2*chargedRange) {
                        damageDealt = player.getDamage() * rand.nextDouble();
                        battleMonster.hp -= damageDealt;
                        wasDamageDealt = true;
                        playerTurn = false;
                        chargedAtack = false;
                    } else {
                        battleMonster.hp -= player.getDamage();
                        damageDealt = player.getDamage();
                        wasDamageDealt = true;
                        playerTurn = false;
                        chargedAtack = false;
                    }
                    keyH.interrectPressed = false;
                }
            } else {
                choseOption();
                if (playerTurn) {
                    if(keyH.interrectPressed) playerAction();    
                }
                else {
                    player.setHp(player.getHp() - battleMonster.damage);
                    playerTurn = true;
                    wasDamageTaken = true;
                    damageTaken = battleMonster.damage;
                }
            }
            if (player.getHp() <= 0) {
                player.setOnLastSafePosition();
                player.setHp(player.getRefHp());
                battleMonster.hp = battleMonster.getRefHp();
                inBattle = false;
            }
            if (battleMonster.hp <= 0) {
                monsters.remove(battleMonster);
                inBattle = false;
            }
        }
    }

    private void playerAction() {
        if (currentOption == 0) {
            // Normal atack
            battleMonster.hp -= player.getDamage();
            damageDealt = player.getDamage();
            wasDamageDealt = true;
            playerTurn = false;
        }
        if (currentOption == 1) {
            // Charged atack
            Random rand = new Random();
            chargedAtack = true;
            chargedGoal = rand.nextInt(gp.tileSize*6);
            chargedRange = rand.nextInt(gp.tileSize - 5) + 5;
            chargedPosition = rand.nextInt(gp.tileSize*6);
            chargedSpeed = rand.nextInt(5) + 1;
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
            } else if (player.speed == battleMonster.speed) {
                player.setHp(player.getHp() - battleMonster.damage);
                
                damageTaken = battleMonster.damage;
                wasDamageTaken = true;
            } else {
                player.setHp(player.getHp() - battleMonster.damage * 2); // Bonus atack cause failed to run
                damageTaken = battleMonster.damage * 2;
                wasDamageTaken = true;
            }
        }
        keyH.interrectPressed = false;
    }

    private void choseOption() {
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
    }
}
