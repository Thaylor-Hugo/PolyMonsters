package tile;
import entity.Entity;
import main.GamePanel;

public class CollisionChecker {

    Entity entity;

    public CollisionChecker (Entity entity){
        this.entity = entity;
    }

    public boolean checkTile (Entity entity){

        GamePanel gp = entity.getGamePanel();

        int entityLeftMapX = entity.mapX + entity.getCollisionArea().x;
        int entityRightMapX = entity.mapX + entity.getCollisionArea().x + entity.getCollisionArea().width;
        int entityTopMapY = entity.mapY + entity.getCollisionArea().y;
        int entityBottomMapY = entity.mapY + entity.getCollisionArea().y + entity.getCollisionArea().height;

        int entityLeftCol = entityLeftMapX/gp.tileSize;
        int entityRightCol = entityRightMapX/gp.tileSize;
        int entityTopRow = entityTopMapY/gp.tileSize;
        int entityBottomRow = entityBottomMapY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.getMovementDirection()) {
            case LEFT:
                entityLeftCol = (entityLeftMapX - entity.speed)/gp.tileSize;
                tileNum1 = gp.getTileM().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[entityLeftCol][entityBottomRow];
                if (gp.getTileM().tile[tileNum1].collision || gp.getTileM().tile[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case RIGHT:
                entityRightCol = (entityRightMapX + entity.speed)/gp.tileSize;
                tileNum1 = gp.getTileM().getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gp.getTileM().tile[tileNum1].collision || gp.getTileM().tile[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case UP:
                entityTopRow = (entityTopMapY - entity.speed)/gp.tileSize;
                tileNum1 = gp.getTileM().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[entityRightCol][entityTopRow];
                if (gp.getTileM().tile[tileNum1].collision || gp.getTileM().tile[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
            case DOWN:
                entityBottomRow = (entityBottomMapY + entity.speed)/gp.tileSize;
                tileNum1 = gp.getTileM().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileM().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gp.getTileM().tile[tileNum1].collision || gp.getTileM().tile[tileNum2].collision) {
                    entity.setCollisionOn(true);
                }
                break;
        }
        return entity.getCollisionOn();
    }

}