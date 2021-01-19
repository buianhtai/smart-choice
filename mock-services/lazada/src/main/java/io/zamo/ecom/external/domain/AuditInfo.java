package com.nab.ecom.external.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuditInfo {

    String userName;
    String query;
}
