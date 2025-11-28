package com.comp2042.bricks;

public interface BrickGenerator {
    /**
     * Gets the current brick to be used in the game
     * @return brick
     */
    Brick getBrick();
    /**
     * Gets the next brick to be used in the game
     * @return brick
     */
    Brick getNextBrick();
    Brick getNextBrick(int position);
}

