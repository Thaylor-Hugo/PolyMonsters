package menus;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;

/** Create a game manu */
public abstract class Menu {
	
	protected String[] options;
	
	protected int currentOption;
	
	protected int maxOption;
	
	protected BufferedImage player;

	GamePanel gp;
	KeyHandler keyH;

	/**
	 * Change the selected option based on keyboard input
	 */
	public void tick() {
		if(keyH.downPressed) {
			currentOption++;
			keyH.downPressed = false;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		
		if(keyH.upPressed) {
			currentOption--;
			keyH.upPressed = false;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(keyH.interrectPressed) {
			keyH.interrectPressed = false;
			action();
		}
	}

	/**
	 * Realize the chosen action
	 */
	protected abstract void action();
		
	/**
	 * Draw the menu on screen
	 * @param g {@code Graphics} from {@code GamePanel paintComponent} method 
	 */
	public abstract void render(Graphics g);
		
}
