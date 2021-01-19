package com.nab.domain;

import lombok.Value;

@Value
public class SearchRequest {

    String query;
    Sort sort;


}
