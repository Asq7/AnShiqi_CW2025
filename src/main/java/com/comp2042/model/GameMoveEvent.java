package com.comp2042.model;
/**
 * Represents a movement event in the game with event type and source information
 */
public final class GameMoveEvent {
    private final GameEventType gameEventType;
    private final EventSource eventSource;
    /**
     * Constructs a GameMoveEvent with the specified event type and source
     * @param gameEventType the type of the event
     * @param eventSource the source of the event
     */
    public GameMoveEvent(GameEventType gameEventType, EventSource eventSource) {
        this.gameEventType = gameEventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the event type
     * @return the GameEventType of this event
     */
    public GameEventType getEventType() {
        return gameEventType;
    }

    /**
     * Gets the event source
     * @return the EventSource of this event
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
