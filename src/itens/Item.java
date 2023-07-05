package itens;

import entity.Player;

public class Item {

    /**
     * Use an item
     * @param entity that is going to receive the item effect
     */
    public void use(Player player, ItemTypes item) {
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
}
