package com.rungroup.Service.Impl;

import com.rungroup.Domain.Club;
import com.rungroup.Domain.Dto.ClubDto;
import com.rungroup.Domain.UserEntity;
import com.rungroup.Repo.ClubRepository;
import com.rungroup.Repo.UserRepository;
import com.rungroup.Security.SecurityUtil;
import com.rungroup.Service.ClubService;
import com.rungroup.mappers.ListMapper;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ClubServiceImpl implements ClubService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ListMapper<Club,ClubDto> clubtoDto;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private final ClubRepository clubRepository;

    @Override
    public List<ClubDto> findAllClubs() {
        var k = clubtoDto.maplist(clubRepository.findAll(),new ClubDto());
        return (List<ClubDto>) k;
    }

    @Override
    public ClubDto findClubById(long clubId) {
        return modelMapper.map(clubRepository.findById(clubId),ClubDto.class);
    }

    @Override
    public void updateClub(ClubDto clubDto){
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Club club =modelMapper.map(clubDto,Club.class);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String username = SecurityUtil.getSessionUser();
        System.out.println(username);
        UserEntity user = userRepository.findByUsername(username);
        System.out.println(user);
        Club club =modelMapper.map(clubDto,Club.class);
        club.setCreatedBy(user);
        System.out.println("user role:"+user.getRoles());
        return clubRepository.save(club);
    }

    @Override
    public void delete(long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
        var k =clubtoDto.maplist(clubRepository.searchClubs(query),new ClubDto());
        return (List<ClubDto>) k;
    }
}
