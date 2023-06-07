package tile;
import main.GamePanel;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileManager {

    public final int NumTiles = 10; //number of different tile sprites

    GamePanel gp;
    Tile[] tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        tile = new Tile[NumTiles];

        getTileImage();
    }

    public void getTileImage(){

        try{

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/void.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road.png"));
            
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

        } catch (IOException e){
            e.printStackTrace ();
        }
    }

    public void draw (Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow){
           
            g2.drawImage(tile[1].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }

        g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 48*1, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[2].image, 48*2, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[3].image, 48*3, 0, gp.tileSize, gp.tileSize, null);

    }
    
}
