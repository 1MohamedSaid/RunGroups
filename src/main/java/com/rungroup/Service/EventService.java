package com.rungroup.Service;

import com.rungroup.Domain.Dto.EventDto;
import com.rungroup.Domain.Event;

import java.util.List;


public interface EventService {
    void createEvent(Long clubId, EventDto eventDto);

    List<EventDto> findAllEvents();

    EventDto findEventById(long eventId);

    Event saveEvent(EventDto eventDto);

    void updateEvent(EventDto eventDto);

    void delete(long eventId);
}
