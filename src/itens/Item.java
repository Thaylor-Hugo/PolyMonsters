package itens;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;

public class Item {
    ItemTypes item;
    
    public Item(ItemTypes item) {
        this.item = item;
    }

    /**
     * Use an item
     * @param entity that is going to receive the item effect
     */
    public void use(Player player) {
        switch (item) {
            case GINGERBREAD:
                player.damage += 1;
                break;
            case CEREAL_BAR:
                player.speed += 1;
                break;
            case FRUIT:
                player.hp = player.getRefHp();
                break;
        }
    }

    /**
     * Get the image of the item instance
     * @return item image
     */
    public Image getItemImage() {
        return getItemImage(item);
    }

    /**
     * Get the image of a item type
     * @param item Item type
     * @return ItemType image
     */
    public static Image getItemImage(ItemTypes item) {
        Image image = null;
        Image gingerBread = null;
        Image fruit = null;
        Image cerealBar = null;
        try {
            gingerBread = ImageIO.read(new File("resources/objects/itens/gingerBread.png"));
            fruit = ImageIO.read(new File("resources/objects/itens/fruit.png"));
            cerealBar = ImageIO.read(new File("resources/objects/itens/cerealBar.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch (item) {
            case GINGERBREAD:
                image = gingerBread;
                break;
            case CEREAL_BAR:
                image = cerealBar;
                break;
            case FRUIT:
                image = fruit;
                break;
        }
        return image;
    }
}
