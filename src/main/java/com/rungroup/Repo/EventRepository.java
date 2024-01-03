package com.rungroup.Repo;

import com.rungroup.Domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long>{

}
