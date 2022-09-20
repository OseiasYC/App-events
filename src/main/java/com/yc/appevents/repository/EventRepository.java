package com.yc.appevents.repository;

import org.springframework.data.repository.CrudRepository;

import com.yc.appevents.models.Event;

public interface EventRepository extends CrudRepository<Event, String>{
    
}
