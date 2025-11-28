package com.comp2042.bricks.impl;

import com.comp2042.bricks.Brick;
import com.comp2042.bricks.BrickGenerator;
import com.comp2042.bricks.impl.shapes.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Randomly generates bricks for the game
 */
public class RandomBrickGenerator implements BrickGenerator {

    private final List<Brick> brickList;

    private final Deque<Brick> nextBricks = new ArrayDeque<>();
    /**
     * Constructor: initializes the list of bricks
     */
    public RandomBrickGenerator() {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new JBrick());
        brickList.add(new LBrick());
        brickList.add(new OBrick());
        brickList.add(new SBrick());
        brickList.add(new TBrick());
        brickList.add(new ZBrick());
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        //nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }
/**
 * Returns a brick from the list at a specific position
 * @param position the position of the brick in the list
 * @return the brick at the specified position
 */
    @Override
    public Brick getNextBrick(int position) {
        // Ensure there are enough bricks in the queue
        while (nextBricks.size() < position) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }

        // Obtain the brick at the specified position
        Iterator<Brick> iterator = nextBricks.iterator();
        Brick result = null;
        for (int i = 0; i < position && iterator.hasNext(); i++) {
            result = iterator.next();
        }
        return result;
    }

    /**
     * Returns a random brick from the list
     * @return a random brick
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }
    /**
     * Returns the next brick in the queue
     * @return the next brick
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}
