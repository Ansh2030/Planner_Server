package com.ansh.plannerCrud.Services;



import com.ansh.plannerCrud.Modules.Users;
import com.ansh.plannerCrud.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private UserRepo repo;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Users user = repo.findByEmail(username);
         if(user==null)
         {
             throw  new UsernameNotFoundException("User not found");
         }
         List<GrantedAuthority> authorityList = new ArrayList<>();
         authorityList.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorityList);
    }
}
