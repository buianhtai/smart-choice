package com.nab.domain;

public interface EventHandler<T> {

    void handle(T event);
}
