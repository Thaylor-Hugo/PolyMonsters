package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    
    final int MENU = 0;
    final int PLAYING = 1;

    final int originalTileSize = 16;    //TileSize in bits
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // Map settings:
    public final int maxMapCol = 191;    
    public final int maxMapRow = 91;
    public final int mapWidth = tileSize*maxMapCol;
    public final int mapHeight = tileSize*maxMapRow;

    int FPS = 60;

    int gameState = MENU;

    public void setGameState() {
        if (gameState == MENU) {
            gameState = PLAYING;
        } else {
            gameState = MENU;
        };
    }
    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();

    Menu menu = new Menu(this, keyH);

    Thread gameThread;
    
    Player player = new Player(this, keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.LIGHT_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // Game loop, runs 60 times per second

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                // What actually happens in the loop in each FPS update 
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        // Update the information on screen, as such player position
        if (gameState == MENU) {
            menu.tick();
        } else {
            if (keyH.pausePressed) {
                gameState = MENU;
            } else {
                player.update();        
            }
        }
    }

    public void paintComponent(Graphics g) {
        // Paint components on screen. Its used by repaint() on loop (repaint updated information) 
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        if (gameState == MENU) {
            menu.render(g);
        } else {
            tileM.draw(g2); //needs to be called first to no overlay Player
            player.draw(g2);
        }

        g2.dispose();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public TileManager getTileM() {
        return tileM;
    }

}
