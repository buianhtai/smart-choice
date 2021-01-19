package com.nab.ecom.external.domain;

public interface EventHandler<T> {

    void handle(T event);
}
