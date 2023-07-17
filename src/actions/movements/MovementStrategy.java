package actions.movements;

import entity.Entity;
import actions.movements.enums.MovementTypes;

/**
 * A interface for movement strategies used by entities 
 */
public interface MovementStrategy {
    /**
     * Move the entity
     * @param entity to be moved
     */
    public void move(Entity entity);

    /**
     * Follow a target entity
     * @param targetEntity to be followed
     * @param entity to be moved
     * @param error diference in target and entity position that can be considered {@code ON_TARGET}
     */
    public void follow(Entity targetEntity, Entity entity, int error);

    /**
     * Set a new constant used for the movement strategy.
     * Constant use varies with strategy
     * @param newTotalToMove new constant
     * @throws IllegalArgumentException if newTotalToMove is not positive
     * @see MovementTypes
     */
    public void setTotalToMove(int newTotalToMove) throws IllegalArgumentException;
}

/**
 * Enum for possible states for the follower
 * {@code FOLLOW_X} need to follow in the x axis
 * {@code FOLLOW_Y} need to follow in the y axis
 * {@code ON_TARGET} is on target 
 */
enum FollowState {
    FOLLOW_X, FOLLOW_Y, ON_TARGET;
}
