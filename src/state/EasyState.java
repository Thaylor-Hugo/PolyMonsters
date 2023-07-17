package state;

public class EasyState implements DifficultyState {
    int hp = 200;
    int damage  = 30;
    int speed = 3;

    @Override
    public void play() {
        System.out.println("Jogando em modo fácil");
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
