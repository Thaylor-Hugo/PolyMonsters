package actions.movements;

import java.util.Random;

import actions.movements.enums.MovementDirection;
import entity.Entity;

public class MoveRandom implements MovementStrategy {
    Random rand = new Random();
    int bound;  // max quantity to move before changing directions
    int totalMoved = 0;
    int totalToMove;
    FollowState fState = FollowState.ON_TARGET;

    public MoveRandom(int bound) {
        this.bound = bound;
        totalToMove = rand.nextInt(bound);
    }

    @Override
    public void move(Entity entity) {
        entity.moving = true;
        if (entity.getMovementDirection() == MovementDirection.DOWN)       entity.mapY += entity.speed;
        else if (entity.getMovementDirection() == MovementDirection.UP)    entity.mapY -= entity.speed;
        else if (entity.getMovementDirection() == MovementDirection.RIGHT) entity.mapX += entity.speed;
        else if (entity.getMovementDirection() == MovementDirection.LEFT)  entity.mapX -= entity.speed;
        totalMoved += entity.speed;

        if (totalMoved >= totalToMove) {
            totalToMove = rand.nextInt(bound);
            totalMoved = 0;
            changeDirection(entity);
        } 
    }

    private void changeDirection(Entity entity) {
        // Randomly change direction
        boolean directionChanged = false;
        int nexDirection;
        while (!directionChanged) {
            nexDirection = rand.nextInt(4);
            switch (nexDirection) {
                case 0:
                    if (entity.getMovementDirection() != MovementDirection.DOWN) {
                       entity.setMovementDirection(MovementDirection.DOWN);
                       directionChanged = true; 
                    }
                    break;
                case 1:
                    if (entity.getMovementDirection() != MovementDirection.RIGHT) {
                       entity.setMovementDirection(MovementDirection.RIGHT);
                       directionChanged = true; 
                    }
                    break;
                case 2:
                    if (entity.getMovementDirection() != MovementDirection.UP) {
                       entity.setMovementDirection(MovementDirection.UP);
                       directionChanged = true; 
                    }
                    break;
                case 3:
                    if (entity.getMovementDirection() != MovementDirection.LEFT) {
                       entity.setMovementDirection(MovementDirection.LEFT);
                       directionChanged = true; 
                    }
                    break;
                default:
                    break;
            }
        }


        if (entity.getMovementDirection() == MovementDirection.DOWN) 
            entity.setMovementDirection(MovementDirection.LEFT);
        else if (entity.getMovementDirection() == MovementDirection.LEFT)
            entity.setMovementDirection(MovementDirection.UP);
        else if (entity.getMovementDirection() == MovementDirection.UP)
            entity.setMovementDirection(MovementDirection.RIGHT);
        else if (entity.getMovementDirection() == MovementDirection.RIGHT)
            entity.setMovementDirection(MovementDirection.DOWN);
    }

    @Override
    public void follow(Entity targetEntity, Entity entity, int error) {
        setFollowState(entity, targetEntity, error);
        if (fState != FollowState.ON_TARGET) {
            entity.setMovementDirection(nextFollowDirection(targetEntity, entity));
            move(entity);
        }
    }

    private void setFollowState(Entity entity, Entity target, int error) {
        boolean tryX;   // if false, skips the followState == x (To follow like climbing a ladder)
        if (fState == FollowState.FOLLOW_X) tryX = true;
        else tryX = false;

        if (totalMoved == 0) tryX = !tryX;

        if ((entity.mapX > target.mapX + error || entity.mapX < target.mapX - error) && tryX) {
            fState = FollowState.FOLLOW_X;
        } else
        if ((entity.mapY > target.mapY + error || entity.mapY < target.mapY - error)) {
            fState = FollowState.FOLLOW_Y;
        } else {
            fState = FollowState.ON_TARGET;
            totalMoved = 0;
            entity.moving = false;
        }
    }

    private MovementDirection nextFollowDirection(Entity entity, Entity target) {
        MovementDirection mvDirect;
        if (fState == FollowState.FOLLOW_X) {
            if (target.mapX < entity.mapX) mvDirect = MovementDirection.RIGHT;    
            else mvDirect = MovementDirection.LEFT;
        } else {
            if (target.mapY < entity.mapY) mvDirect = MovementDirection.DOWN;    
            else mvDirect = MovementDirection.UP;
        }
        return mvDirect;
    }

    @Override
    public void setTotalToMove(int newBound) {
        this.bound = newBound;
    }
    
}
