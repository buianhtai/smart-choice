package com.nab.event;

import com.nab.shared.DomainEvent;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchEvent implements DomainEvent<SearchEvent> {

    UUID eventId;
    String userName;
    String query;
}
