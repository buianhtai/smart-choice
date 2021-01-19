package com.nab.ecom.external.domain;

import lombok.Value;

@Value
public class SearchRequest {

    String query;
    Sort sort;


}
