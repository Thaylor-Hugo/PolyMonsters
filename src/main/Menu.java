import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Menu {
	
public String[] options = {"jogar","carregar jogo","sair"};
	
	public int currentOption = 0;
	
	public int maxOption = options.length - 1;
	
	public boolean up,down,ok;
	
	public BufferedImage player;
	
	public Menu() {
		player = Game.spritesheet.getSprite(48, 0, 16, 16);
	}
	
	public void tick() {
		System.out.println(""+currentOption);
		
		if(down) {
			currentOption++;
			down = false;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		
		if(up) {
			currentOption--;
			up = false;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(ok) {
			ok = false;
			if(currentOption == 0) {
				//inicia o jogo
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
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(new Color(0,0,0));
		g.drawString("Jogar", (Game.WIDTH/2) - 20, 80);
		g.drawString("Carregar", (Game.WIDTH/2) - 33, 100);
		g.drawString("Sair", (Game.WIDTH/2) - 14, 120);
		if(currentOption == 0)g.drawImage(player,(Game.WIDTH/2) - 40, 64,null);
		if(currentOption == 1)g.drawImage(player, (Game.WIDTH/2) - 53, 84,null);
		if(currentOption == 2)g.drawImage(player,  (Game.WIDTH/2) - 34, 104,null);
	}
}
