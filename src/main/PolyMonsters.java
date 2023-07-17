package main;

// Main Class

import java.awt.Toolkit;

import javax.swing.JFrame;

/** Main class */
public class PolyMonsters {
    public static void main(String[] args) {
        JFrame gameWindow = new JFrame();
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setTitle("PolyMonsters");
        gameWindow.setIconImage(Toolkit.getDefaultToolkit().getImage("monster.png"));

        GamePanel gamePanel = new GamePanel();
        gameWindow.add(gamePanel);

        
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true); 
        
        gamePanel.startGameThread();
    }
}
