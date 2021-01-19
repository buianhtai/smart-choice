package com.nab.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuditInfo {

    String userName;
    String query;
}
