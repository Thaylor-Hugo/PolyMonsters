package entity.monsters;

import entity.Entity;
import entity.Player;

public abstract class Monsters extends Entity {
    public int visionRange; // Range to start a battle
    protected Player player;
}
