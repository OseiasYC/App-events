package com.yc.appevents.repository;

import org.springframework.data.repository.CrudRepository;

import com.yc.appevents.models.Event;
import com.yc.appevents.models.Guest;



public interface GuestRepository extends CrudRepository<Guest, String>{
    Iterable<Guest> findByEvent(Event event);
    
}
