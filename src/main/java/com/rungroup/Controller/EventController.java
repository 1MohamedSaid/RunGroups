package com.rungroup.Controller;

import com.rungroup.Domain.Dto.ClubDto;
import com.rungroup.Domain.Dto.EventDto;
import com.rungroup.Domain.Event;
import com.rungroup.Domain.UserEntity;
import com.rungroup.Security.SecurityUtil;
import com.rungroup.Service.EventService;
import com.rungroup.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @GetMapping("/events/{clubId}/new")
    public String createEventForm(@PathVariable("clubId")long clubId, Model model){
        Event event = new Event();
        model.addAttribute("clubId",clubId);
        model.addAttribute("event",event);
        return "events-create";
    }

    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId")long clubId, @ModelAttribute("event")EventDto eventDto,Model model){
        eventService.createEvent(clubId,eventDto);
        model.addAttribute("events",eventDto);
        return "redirect:/clubs/"+clubId;
    }

    @GetMapping("/events")
    public String eventList(Model model){
        UserEntity user= new UserEntity();
        List<EventDto> events = eventService.findAllEvents();
        String username = SecurityUtil.getSessionUser();
        if(username!=null){
            user= userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("events",events);
        return "events-list";
    }

    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") long eventId,Model model){
        UserEntity user= new UserEntity();
        var eventDto=eventService.findEventById(eventId);
        String username = SecurityUtil.getSessionUser();
        if(username!=null){
            user= userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("event",eventDto);
        return "events-detail";
    }
    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId")long eventId,Model model){
        var event= eventService.findEventById(eventId);
        model.addAttribute("event",event);
        return "events-edit";
    }
    @PostMapping("/events/new")
    public String saveEvent(@Valid @ModelAttribute("event")EventDto eventDto,BindingResult result,Model model){
        if(result.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "events-create";
        }
        eventService.saveEvent(eventDto);
        return "redirect:/events";
    }
    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId")long eventId,
                             @Valid @ModelAttribute("event")EventDto eventDto,
                             Model model,
                             BindingResult result) {
        if(result.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "events-edit";
        }
        EventDto eventDto1= eventService.findEventById(eventId);
        eventDto.setId(eventId);
        eventDto.setClub(eventDto1.getClub());
        eventService.updateEvent(eventDto);
        return "redirect:/events";
    }
    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId")long eventId){
        eventService.delete(eventId);
        return "redirect:/events";
    }
}
