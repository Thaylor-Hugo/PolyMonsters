package state;

public class HardState implements DifficultyState {

    int hp = 100;
    int damage  = 10;
    int speed = 1;

    @Override
    public void play() {
        System.out.println("Jogando em modo dificil");
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
        return 100;
    }
     
}
