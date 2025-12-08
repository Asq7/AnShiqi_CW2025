package com.comp2042.bricks;

public interface BrickGeneratorInterface {
    /**
     * Gets the current brick to be used in the game
     * @return brick
     */
    BrickInterface getBrick();

    /**
     * Gets the next brick to be used in the game at a specific position
     * @param position the position of the brick in the queue
     * @return the next brick at the specified position
     */
    BrickInterface getNextBrick(int position);
}

