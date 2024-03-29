package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A {@code KeyHandler} for keyboard inputs
 */
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, sprintPressed;
    public boolean interrectPressed, inventoryPressed, pausePressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SHIFT) {
            sprintPressed = true;
        }
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E) {
            interrectPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            pausePressed = true;
        }
        if (code == KeyEvent.VK_Q) {
            inventoryPressed = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            sprintPressed = false;
        }
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E) {
            interrectPressed = false;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            pausePressed = false;
        }
        if (code == KeyEvent.VK_Q) {
            inventoryPressed = false;
        }
    }
    
}
