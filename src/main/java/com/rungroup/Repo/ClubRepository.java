package com.rungroup.Repo;

import com.rungroup.Domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club,Long> {

    @Query("SELECT c from Club c WHERE c.title LIKE CONCAT('%', :query, '%')")
    List<Club> searchClubs(@Param("query") String query );
}
