package com.rungroup.Controller;

import com.rungroup.Domain.Club;
import com.rungroup.Domain.Dto.ClubDto;
import com.rungroup.Domain.UserEntity;
import com.rungroup.Security.SecurityUtil;
import com.rungroup.Service.ClubService;
import com.rungroup.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClubController {
    @Autowired
    private ClubService clubService;
    @Autowired
    private UserService userService;

    @GetMapping("/clubs")
    public String listClubs (Model model){
        UserEntity user= new UserEntity();
        String username = SecurityUtil.getSessionUser();
        List<ClubDto> clubs = clubService.findAllClubs();
        if(username!=null){
            user= userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);
        model.addAttribute("clubs",clubs);
        return "clubs-list";
    }
    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") long clubId, Model model){
        UserEntity user = new UserEntity();
        ClubDto clubDto = clubService.findClubById(clubId);
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }

    @GetMapping("/clubs/new")
    public String CreateClubForm(Model model){
        Club club= new Club();
        model.addAttribute("club",club);
        return "clubs-create";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId")long clubId,Model model){
        ClubDto clubDto= clubService.findClubById(clubId);
        model.addAttribute("club",clubDto);
        return "clubs-edit";
    }

    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club")ClubDto clubDto,BindingResult result,Model model){
        if(result.hasErrors()) {
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }
        clubService.saveClub(clubDto);
        return "redirect:/clubs";
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId")long clubID,
                             @Valid @ModelAttribute("club")ClubDto clubDto,
                             BindingResult result,Model model) {
        if(result.hasErrors()){
            model.addAttribute("club",clubDto);
            return "clubs-edit";
        }
        clubDto.setId(clubID);
        clubService.updateClub(clubDto);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId")long clubId){
        clubService.delete(clubId);
        return "redirect:/clubs";
    }
    @GetMapping("/clubs/search")
    public String searchClubs(@RequestParam(value = "query") String query, Model model){
        List<ClubDto> clubs = clubService.searchClubs(query);
        model.addAttribute("clubs",clubs);
        return "clubs-list";
    }
}
