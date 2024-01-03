package com.rungroup.Service.Impl;

import com.rungroup.Domain.Club;
import com.rungroup.Domain.Dto.EventDto;
import com.rungroup.Domain.Event;
import com.rungroup.Repo.ClubRepository;
import com.rungroup.Repo.EventRepository;
import com.rungroup.Service.EventService;
import com.rungroup.mappers.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rungroup.mappers.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    ClubRepository clubRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ListMapper<Event,EventDto> eventToDto;

    @Override
    public void createEvent(Long clubId, EventDto eventDto) {
       Club club=clubRepository.findById(clubId).get();
       Event event = modelMapper.map(eventDto,Event.class);
       event.setClub(club);
       eventRepository.save(event);
    }
    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> mapToEventDto(event)).collect(Collectors.toList());
    }

    @Override
    public EventDto findEventById(long eventId) {
        var k = modelMapper.map(eventRepository.findById(eventId).get(),EventDto.class);
        return k;
    }

    @Override
    public Event saveEvent(EventDto eventDto) {
        return eventRepository.save(modelMapper.map(eventDto,Event.class));
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        eventRepository.save(modelMapper.map(eventDto,Event.class));
    }

    @Override
    public void delete(long eventId) {
        eventRepository.deleteById(eventId);
    }
}
