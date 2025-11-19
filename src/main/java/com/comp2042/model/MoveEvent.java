package com.comp2042.model;
/**
 * Represents a movement event in the game with event type and source information
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;
    /**
     * Constructs a MoveEvent with the specified event type and source
     * @param eventType the type of the event
     * @param eventSource the source of the event
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the event type
     * @return the EventType of this event
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Gets the event source
     * @return the EventSource of this event
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
