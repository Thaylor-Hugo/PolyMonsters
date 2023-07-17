package menus;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;
import java.awt.Color;
import java.awt.Font;

public class MenuPersonagens extends Menu{
	String jogador_1 = "resources/player/movement/walking_down.gif";
	String jogador_2 = "resources/monsters/zombies/mostAlive/down.gif";
	Player jogador;

    public MenuPersonagens(GamePanel gp, KeyHandler keyH, Player player) {

		// atualizar os personagens depois
		this.jogador = player;
		options = new String [] {"Personagem 1","Personagem 2"} ; 
		currentOption = 0;
		maxOption = options.length - 1;

		

		setJogadorImage(jogador_1);

		this.gp = gp;
		this.keyH = keyH;
	}

	private void setJogadorImage(String path){
		try {
			player = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	protected void action(){
		if(currentOption == 0) {  //aqui deve-se direcionar para tela do Menu
				gp.setGameState(MenuOptions.ESCOLHERDIFICULDADE);
				jogador.setAsAlternativePlayer(false);
			}
			if(currentOption == 1) {
				jogador.setAsAlternativePlayer(true);
				gp.setGameState(MenuOptions.ESCOLHERDIFICULDADE);
			}
	}

	public void render(Graphics g) {
		g.setColor(new Color(60, 0, 0));
		g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g.setColor(new Color(0,0,0));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 48));
		g.drawString("PolyMonsters", (gp.screenWidth/2) - 100, 100);
		g.drawString(options[0], (gp.screenWidth/2) - 100, 400);
		g.drawString(options[1], (gp.screenWidth/2) - 100, 448);
		if(currentOption == 0){
			setJogadorImage(jogador_1);
			g.drawImage(player, (gp.screenWidth/2) - 150, 360, 48, 48, null);

		}
		if(currentOption == 1){
			setJogadorImage(jogador_2);
			g.drawImage(player, (gp.screenWidth/2) - 150, 408, 48, 48, null);
		}
	}
    
}
