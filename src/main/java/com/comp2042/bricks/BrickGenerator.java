package com.comp2042.bricks;

public interface BrickGenerator {
    /**
     * Gets the current brick to be used in the game
     * @return brick
     */
    Brick getBrick();

    /**
     * Gets the next brick to be used in the game at a specific position
     * @param position the position of the brick in the queue
     * @return the next brick at the specified position
     */
    Brick getNextBrick(int position);
}

