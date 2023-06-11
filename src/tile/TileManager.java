package tile;

import main.GamePanel;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {

    public final int NumTiles = 4; //number of all different tile sprites

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        tile = new Tile[NumTiles];

        mapTileNum = new int [gp.maxMapCol][gp.maxMapRow]; 

        getTileImage();
        loadMap("/maps/map03.txt");
    }

    public void getTileImage(){

        //Needs to be updated with all Tile files
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

    public void loadMap (String filePath){
        try {
            //Reads the mapXX.txt
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxMapRow; row++){
                String line = br.readLine();

                for (int col = 0; col < gp.maxMapCol; col++){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]); //change String to Integer
                    mapTileNum[col][row] = num;
                }

            }
            br.close();

        }catch (Exception e){
        }
    }

    public void draw (Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        for (row = 0; row < gp.maxMapRow; row++){
           
            for (col=0; col < gp.maxMapCol; col++){

                int tileNum = mapTileNum[col][row];

                g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
                x += gp.tileSize;
            }
            x = 0;
            y += gp.tileSize;
        }
    }
    
}
