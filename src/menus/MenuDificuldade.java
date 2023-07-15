package menus;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import main.GamePanel;
import main.KeyHandler;

public class MenuDificuldade extends Menu {

    public MenuDificuldade(GamePanel gp, KeyHandler keyH) {

		options = new String [] {"Facil","Medio","Dificil"} ; 
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
            gp.setGameState(MenuOptions.ESCOLHERAMBIENTE);
            gp.setDificult(Dificuldades.FACIL);
        }
        if(currentOption == 1) {
             gp.setGameState(MenuOptions.ESCOLHERAMBIENTE);
              gp.setDificult(Dificuldades.MEDIO);
        }
        if(currentOption == 2) {
             gp.setGameState(MenuOptions.ESCOLHERAMBIENTE);
              gp.setDificult(Dificuldades.DIFICIL);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(60, 0, 0));
		g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
		g.drawString("PolyMonsters", (gp.screenWidth/2) - 100, 100);
		g.drawString("Facil", (gp.screenWidth/2) - 50, 400);
		g.drawString("Medio", (gp.screenWidth/2) - 50, 448);
		g.drawString("Dificil", (gp.screenWidth/2) - 50, 496);
		if(currentOption == 0)g.drawImage(player, (gp.screenWidth/2) - 100, 360, 48, 48, null);
		if(currentOption == 1)g.drawImage(player, (gp.screenWidth/2) - 100, 408, 48, 48, null);
		if(currentOption == 2)g.drawImage(player, (gp.screenWidth/2) - 100, 456, 48, 48, null);
    }
    
}
