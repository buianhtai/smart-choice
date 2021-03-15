package com.ms.smartchoice.event;

import com.ms.event.IEventHandler;
import com.ms.event.SearchEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SearchEventHandler implements IEventHandler<SearchEvent> {

    @Override
    public void handle(SearchEvent event) {
        log.info("EventHandler SearchEvent {}", event);
    }

    @Override
    public boolean isSupport(Class<?> classZ) {
        return SearchEvent.class.equals(classZ);
    }
}
