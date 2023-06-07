package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();

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
        player.update();
    }

    public void paintComponent(Graphics g) {
        // Paint components on screen. Its used by repaint() on loop (repaint updated information) 
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        tileM.draw(g2); //needs to be called first to no overlay Player

        player.draw(g2);

        g2.dispose();
    }
}
