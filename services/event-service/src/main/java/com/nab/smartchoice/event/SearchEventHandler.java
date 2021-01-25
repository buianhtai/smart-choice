package com.nab.smartchoice.event;

import com.nab.event.IEventHandler;
import com.nab.event.SearchEvent;
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
