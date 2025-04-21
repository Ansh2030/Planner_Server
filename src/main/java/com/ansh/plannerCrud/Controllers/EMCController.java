package com.ansh.plannerCrud.Controllers;

import com.ansh.plannerCrud.DTO.EMCDTO;
import com.ansh.plannerCrud.Mappers.EMCMapper;
import com.ansh.plannerCrud.Modules.EMC;
import com.ansh.plannerCrud.Repositories.EMCRepo;
import com.ansh.plannerCrud.Roles.Role;
import com.ansh.plannerCrud.Services.EMCServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class EMCController {
@Autowired
    EMCServices service;

@Autowired
private Role role;

@Autowired
private EMCRepo emcRepo;
@Autowired
private EMCMapper mapper;


@PostMapping("/api/emc")
public void addEMC(@RequestBody EMCDTO data, Principal principal){
try{
    if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
    service.addMVC(data);
    else
        System.out.println("Unauthorized request");
}catch (Exception e){
    System.out.println(e);
}
}

@PutMapping("/api/emc")
    public void updateEMC(@RequestBody EMCDTO data, Principal principal){
        try{
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
                service.updateMVC(data);
            else
                System.out.println("Unauthorized request");
        }catch (Exception e){
            System.out.println(e);
        }
    }

@GetMapping("/api/emc")
    public List<EMC> displayEMC(Principal principal){
        try{
               return service.displayEMC();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
@DeleteMapping("/api/emc")
    public void deleteEMC(@RequestBody EMCDTO data,Principal principal){
        try{
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
                service.delete(data, principal);
            else
                System.out.println("Unauthorized request");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @DeleteMapping("/api/emc/{id}")
    public void deleteEMC(@PathVariable int id,Principal principal){
        try{
            EMC emc = emcRepo.findById(id).get();
            EMCDTO data = mapper.modelToDto(emc);
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
                service.delete(data, principal);
            else
                System.out.println("Unauthorized request");
        }catch (Exception e){
            System.out.println(e);
        }
    }




}
