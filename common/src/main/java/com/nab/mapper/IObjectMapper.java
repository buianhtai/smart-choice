package com.nab.mapper;

public interface IObjectMapper<I, O> {

    I from(O o);

    O to(I i);
}
