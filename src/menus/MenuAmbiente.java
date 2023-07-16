package menus;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import main.GamePanel;
import main.KeyHandler;

public class MenuAmbiente extends Menu {

    public MenuAmbiente(GamePanel gp, KeyHandler keyH) {

		options = new String [] {"Aventura","Terror"} ; 
		currentOption = 0;
		maxOption = options.length - 1;

		try {
			player = ImageIO.read(new File("resources/player/movement/walking_down.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.gp = gp;
		this.keyH = keyH;
	}

    @Override
    protected void action() {
        if(currentOption == 0) {
            gp.setGameState(MenuOptions.JOGANDO);
        }
        if(currentOption == 1) {
             gp.setGameState(MenuOptions.JOGANDO);
             gp.setAmbiente(true);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(60, 0, 0));
		g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
		g.drawString("PolyMonsters", (gp.screenWidth/2) - 100, 100);
		g.drawString("Aventura", (gp.screenWidth/2) - 50, 400);
		g.drawString("Terror", (gp.screenWidth/2) - 50, 448);
		if(currentOption == 0)g.drawImage(player, (gp.screenWidth/2) - 100, 360, 48, 48, null);
		if(currentOption == 1)g.drawImage(player, (gp.screenWidth/2) - 100, 408, 48, 48, null);
    }
    
}
