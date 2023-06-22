package actions.movements.enums;

import actions.movements.*;

/**
 * Enum for the diferent movement strategies <p>
 * {@code UP_DOWN} move entity in a vertical line (line size defined by given constant) <p>
 * {@code SQUARE} move entity as if in a square side (square side size defined by given constant) <p>
 * {@code RANDOM} move entity randomly (max quantity to move before changing direction defined by given constant) <p>
 * {@code CONTROLED} move entity controled with a keyboard (keyHandler given) <p>
 * {@code SPIRAL} (not implemented yet) move in a circumference (radius defined by given constant)
 * @see MoveUpDown
 * @see MoveSquare
 * @see MoveRandom
 * @see MoveControled
 */

public enum MovementTypes {
    UP_DOWN, SQUARE, RANDOM, SPIRAL, CONTROLED;
}
