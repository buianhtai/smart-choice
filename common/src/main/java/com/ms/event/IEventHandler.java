package com.ms.event;

/**
 * This interface can be implemented to handle different types of messages. Every handler is
 * responsible for a single of type message
 *
 * @param <E> Handler can handle events of type E
 */
public interface IEventHandler<E extends Event> {

    /**
     * The handle method should implement and handle behavior related to the event. This can be as
     * simple as calling another service to handle the event on publishing the event on a queue to
     * be consumed by other sub systems.
     *
     * @param event the {@link Event} object to be handled.
     */
    void handle(E event);

    /**
     * Used to check whether or not support handle target class
     * @param classZ the given target class
     * @return boolean
     *
     */
    boolean isSupport(Class<?> classZ);

}
