package actions.movements;

import entity.Entity;

// Movement Strategy for Entitys
public interface MovementStrategy {
    public void move(Entity entity);
    public void follow(Entity targetEntity, Entity entity, int error);
    public void setTotalToMove(int newTotalToMove);
}

enum FollowState {
    FOLLOW_X, FOLLOW_Y, ON_TARGET;
}
