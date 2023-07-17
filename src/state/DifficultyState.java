package state;

public interface DifficultyState {
    int hp = 200;
    int damage  = 50;
    int speed = 50;

    public void play();

    public int getHp();
    
    public void setHp(int hp);

    public int getDamage();
        
    public void setDamage(int damage); 
        
    public int getSpeed();
       
    public void setSpeed(int speed);

    public int getRefHp(); 
        
}
