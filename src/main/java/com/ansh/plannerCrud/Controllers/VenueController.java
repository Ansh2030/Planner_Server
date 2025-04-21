package com.ansh.plannerCrud.Controllers;

import com.ansh.plannerCrud.DTO.VenueDTO;
import com.ansh.plannerCrud.Mappers.VenueMapper;
import com.ansh.plannerCrud.Modules.Venue;
import com.ansh.plannerCrud.Repositories.VenueRepo;
import com.ansh.plannerCrud.Roles.Role;

import com.ansh.plannerCrud.Services.VenueServices;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

@RestController
public class VenueController {
    @Autowired
     VenueServices services;

    @Autowired
    private Role role;
    @Autowired
    private VenueMapper mapper;
    @Autowired
    private VenueRepo repo;

    @GetMapping("/api/venue")
    public List<Venue> display(Principal principal){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication.getName());
        System.out.println(principal.getName());
        return services.display();
    }



    @PostMapping("/api/addVenue")
    public String add(@RequestBody VenueDTO data, Principal principal){

        try{
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
            {
                services.addVenue(data);
                return ("Venue added");
            }
            else
                return ("Unauthorized request");
        }catch (Exception e){
            return(e.getMessage());
        }


    }

    @PutMapping("/api/venue")
    public String update(@RequestBody VenueDTO data, Principal principal){

        try{
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
            {
                services.updateVenue(data);
                return ("Updated");
            }
            else
                return ("Unauthorized request");
        }catch (Exception e){
            return(e.getMessage());
        }



    }

  @DeleteMapping("/api/venue")
    public String deleteVenue(@RequestBody VenueDTO data, Principal principal){

        try{
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
            {
                services.delete(data, principal);
                return "deleted successfully";
            }
            else
                return("Unauthorized request");
        }catch (Exception e){
            System.out.println(e);
            return(e.getMessage());
        }



    }
    @DeleteMapping("/api/venue/{id}")
    public String deleteVenue(@PathVariable int id, Principal principal){

        try{
            VenueDTO data = mapper.modelToDTo(repo.findById(id).get());
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
            {
                services.delete(data, principal);
                return "deleted successfully";
            }
            else
                return("Unauthorized request");
        }catch (Exception e){
            System.out.println(e);
            return(e.getMessage());
        }



    }


}
