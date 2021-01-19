package com.nab.smartchoice.util.product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuditInfo {

    String userName;
    String query;
}
