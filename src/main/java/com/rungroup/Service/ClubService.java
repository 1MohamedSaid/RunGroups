package com.rungroup.Service;


import com.rungroup.Domain.Club;
import com.rungroup.Domain.Dto.ClubDto;

import java.util.List;

public interface ClubService {

    List<ClubDto>findAllClubs();


    ClubDto findClubById(long clubId);

    void updateClub(ClubDto club);

    Club saveClub(ClubDto clubDto);

    void delete(long clubId);

    List<ClubDto> searchClubs(String query);
}

