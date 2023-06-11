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

        int mapCol = 0;
        int mapRow = 0;

        for (mapRow = 0; mapRow < gp.maxMapRow; mapRow++){
           
            for (mapCol=0; mapCol < gp.maxMapCol; mapCol++){

                int tileNum = mapTileNum[mapCol][mapRow];

                //Tile's position relative to world map
                int mapX = mapCol * gp.tileSize;
                int mapY = mapRow * gp.tileSize;

                //Tile's position relative to screen
                int screenX = mapX - gp.getPlayer().mapX + gp.getPlayer().screenX;
                int screenY = mapY - gp.getPlayer().mapY + gp.getPlayer().screenY;

                //Makes method only draw Tiles visible in the screen
                if (mapX + gp.tileSize > gp.getPlayer().mapX - gp.getPlayer().screenX &&
                    mapX - gp.tileSize < gp.getPlayer().mapX + gp.getPlayer().screenX &&
                    mapY + gp.tileSize > gp.getPlayer().mapY - gp.getPlayer().screenY &&
                    mapY - gp.tileSize < gp.getPlayer().mapY + gp.getPlayer().screenY )
                    {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
    
}
