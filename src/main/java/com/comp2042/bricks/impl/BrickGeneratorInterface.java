package com.comp2042.bricks.impl;

import com.comp2042.bricks.BrickInterface;
import com.comp2042.bricks.impl.shapes.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Randomly generates bricks for the game
 */
public class BrickGeneratorInterface implements com.comp2042.bricks.BrickGeneratorInterface {

    private final List<BrickInterface> brickInterfaceList;

    private final Deque<BrickInterface> nextBrickInterfaces = new ArrayDeque<>();
    /**
     * Constructor: initializes the list of bricks
     */
    public BrickGeneratorInterface() {
        brickInterfaceList = new ArrayList<>();
        brickInterfaceList.add(new IBrick());
        brickInterfaceList.add(new JBrick());
        brickInterfaceList.add(new LBrick());
        brickInterfaceList.add(new OBrick());
        brickInterfaceList.add(new SBrick());
        brickInterfaceList.add(new TBrick());
        brickInterfaceList.add(new ZBrick());
        nextBrickInterfaces.add(brickInterfaceList.get(ThreadLocalRandom.current().nextInt(brickInterfaceList.size())));
        //nextBrickInterfaces.add(brickInterfaceList.get(ThreadLocalRandom.current().nextInt(brickInterfaceList.size())));
    }
/**
 * Returns a brick from the list at a specific position
 * @param position the position of the brick in the list
 * @return the brick at the specified position
 */
    @Override
    public BrickInterface getNextBrick(int position) {
        // Ensure there are enough bricks in the queue
        while (nextBrickInterfaces.size() < position) {
            nextBrickInterfaces.add(brickInterfaceList.get(ThreadLocalRandom.current().nextInt(brickInterfaceList.size())));
        }

        // Obtain the brick at the specified position
        Iterator<BrickInterface> iterator = nextBrickInterfaces.iterator();
        BrickInterface result = null;
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
    public BrickInterface getBrick() {
        if (nextBrickInterfaces.size() <= 1) {
            nextBrickInterfaces.add(brickInterfaceList.get(ThreadLocalRandom.current().nextInt(brickInterfaceList.size())));
        }
        return nextBrickInterfaces.poll();
    }
    /**
     * Returns the next brick in the queue
     * @return the next brick
     */
    public BrickInterface getNextBrick() {
        return nextBrickInterfaces.peek();
    }
}


