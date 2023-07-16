package tests.entity;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;

public class PlayerTest {
    GamePanel gp = new GamePanel();
    KeyHandler keyH = new KeyHandler();
    Player player = new Player(gp, keyH);

    static final int playerDefaultX = 100;
    static final int playerDefaultY = 100;
    static final int playerDefaultSpeed = 2;
    static final boolean playerDefaultSprinting = false;
    static final boolean playerDefaultMoving = false;
    static final int playerDefaultMovingDirection = 1;
    
    @ParameterizedTest
    @DisplayName("Test position updates")
    @CsvSource({
        "true, false, false, false, false",
        "false, true, false, false, false",
        "false, false, true, false, false",
        "false, false, false, true, false",
        "true, false, false, false, true",
        "false, true, false, false, true",
        "false, false, true, false, true",
        "false, false, false, true, true"
    })
    void updateTest(boolean up, boolean down, boolean left, boolean right, boolean sprint) {
        int previusX = player.mapX;
        int previusY = player.mapY;
        keyH.upPressed = up;
        keyH.downPressed = down;
        keyH.leftPressed = left;
        keyH.rightPressed = right;
        keyH.sprintPressed = sprint;
        player.update();
        if (keyH.upPressed) {
            if (sprint) {
                assertTrue(player.mapY < previusY - player.speed);
            } else {
                assertTrue(player.mapY == previusY - player.speed);
            }
        }
        if (keyH.leftPressed) {
            if (sprint) {
                assertTrue(player.mapX < previusX - player.speed);
            } else {
                assertTrue(player.mapX == previusX - player.speed);
            }
        }
        if (keyH.downPressed) {
            if (sprint) {
                assertTrue(player.mapY > previusY + player.speed);
            } else {
                assertTrue(player.mapY == previusY + player.speed);
            }
        }
        if (keyH.rightPressed) {
            if (sprint) {
                assertTrue(player.mapX > previusX + player.speed);
            } else {
                assertTrue(player.mapX == previusX + player.speed);
            }
        }
    }
}