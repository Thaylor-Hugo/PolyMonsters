package state;

public class MediumState implements DifficultyState  {
    int hp = 150;
    int damage  = 60;
    int speed = 60;

    @Override
    public void play() {
        System.out.println("Jogando em modo medio");
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getRefHp() {
        return 200;
    }
    
    
}
