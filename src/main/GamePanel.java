package main;

import java.lang.Math;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import entity.Bike;

public class GamePanel extends JPanel implements Runnable {
    
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();

    Thread gameThread;

    Player player = new Player(this, keyH);
    Bike bike = new Bike(this, keyH);

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
    
    public boolean collisionHappened(entity.Entity first_entity, entity.Entity second_entity) {
    	double collision_radius = 20;
    	double distance = Math.sqrt(Math.pow(first_entity.getX() - second_entity.getX(),2) + Math.pow(first_entity.getY() - second_entity.getY(),2));
    	if( distance < collision_radius) {
    		return true;
    	}
    	return false;
    }

    public void update() {
        // Update the information on screen, as such player position
    	boolean bikeCollision = collisionHappened(player, bike);
    	if(bikeCollision != player.isNearBike()) {
    		player.setNearBike(bikeCollision);
    	}
        player.update();
        
        if (player.isOnBike()) {
        	bike.setRidingPlayer(player);
        	bike.setWithPlayer(true);
        }
        else if (bike.isWithPlayer() && !player.isOnBike()) {
            	bike.setRidingPlayer(null);
            	bike.setWithPlayer(false);
        }
        bike.update();

//        System.out.println(player);
//        System.out.println("\n------------------------\n");
//        System.out.println(bike);
//        System.out.println("\n------------------------\n");
    }

    public void paintComponent(Graphics g) {
        // Paint components on screen. Its used by repaint() on loop (repaint updated information) 
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);
        bike.draw(g2);

        g2.dispose();
    }
}
