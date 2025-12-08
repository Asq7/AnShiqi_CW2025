package com.comp2042.core;

import com.comp2042.core.GameScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameScoreTest {

    private GameScore gameScore;

    @BeforeEach
    void setUp() {
        gameScore = new GameScore();
    }

    @Test
    void testInitialScoreAndLevel() {
        assertEquals(0, gameScore.scoreProperty().get());

        assertEquals(1, gameScore.levelProperty().get());
        assertEquals(1, gameScore.getLevel());
    }

    @Test
    void testAddPoints() {
        assertEquals(0, gameScore.scoreProperty().get());
        assertEquals(1, gameScore.levelProperty().get());

        gameScore.add(50);
        assertEquals(50, gameScore.scoreProperty().get());
        assertEquals(1, gameScore.levelProperty().get());

        gameScore.add(60);
        assertEquals(110, gameScore.scoreProperty().get());
        assertEquals(2, gameScore.levelProperty().get());
    }

    @Test
    void testLevelUpMechanism() {
        assertEquals(1, gameScore.getLevel());

        gameScore.add(100);
        assertEquals(2, gameScore.getLevel());

        gameScore.add(300);
        assertEquals(5, gameScore.getLevel());
    }

    @Test
    void testResetFunctionality() {
        gameScore.add(250);
        assertEquals(250, gameScore.scoreProperty().get());
        assertEquals(3, gameScore.levelProperty().get());

        gameScore.reset();

        assertEquals(0, gameScore.scoreProperty().get());
        assertEquals(1, gameScore.levelProperty().get());
    }

    @Test
    void testScorePropertyBinding() {
        assertSame(gameScore.scoreProperty(), gameScore.scoreProperty());
        assertSame(gameScore.levelProperty(), gameScore.levelProperty());
    }
}
