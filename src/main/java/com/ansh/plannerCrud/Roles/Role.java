package com.ansh.plannerCrud.Roles;

import com.ansh.plannerCrud.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Role {
   private String role;

   @Autowired
   UserRepo userRepo;

   public String getRole(String username){
       try{
           String  result = userRepo.findByEmail(username).getRole();
           return result;
       }
       catch (Exception e){
           System.out.println(e);
           return null;
       }


   }
}
