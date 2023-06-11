package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Menu {
	
public String[] options = {"jogar","carregar jogo","sair"};
	
	public int currentOption = 0;
	
	public int maxOption = options.length - 1;
	
	public BufferedImage player;

	GamePanel gp;
	KeyHandler keyH;

	public Menu(GamePanel gp, KeyHandler keyH) {
		try {
			player = ImageIO.read(new File("resources/player/movement/walking_down.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.gp = gp;
		this.keyH = keyH;
	}
	
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
			if(currentOption == 0) {
				gp.setGameState();
			}
			if(currentOption == 1) {
				//carrega o jogo
			}
			if(currentOption == 2) {
				System.exit(0);
			}
		}
	}
	public void render(Graphics g) {
		g.setColor(new Color(60, 0, 0));
		g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
		g.drawString("PolyMonsters", (gp.screenWidth/2) - 100, 100);
		g.drawString("Jogar", (gp.screenWidth/2) - 50, 400);
		g.drawString("Carregar", (gp.screenWidth/2) - 75, 448);
		g.drawString("Sair", (gp.screenWidth/2) - 40, 496);
		if(currentOption == 0)g.drawImage(player, (gp.screenWidth/2) - 138, 360, 48, 48, null);
		if(currentOption == 1)g.drawImage(player, (gp.screenWidth/2) - 163, 408, 48, 48, null);
		if(currentOption == 2)g.drawImage(player, (gp.screenWidth/2) - 128, 456, 48, 48, null);
	}
}
