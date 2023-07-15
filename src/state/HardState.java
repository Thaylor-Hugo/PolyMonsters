package state;

public class HardState implements DifficultyState {

    @Override
    public void play() {
        System.out.println("Jogando em modo dificil");
    } 
}
