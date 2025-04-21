package com.ansh.plannerCrud.Controllers;

import com.ansh.plannerCrud.Modules.AuthResponse;
import com.ansh.plannerCrud.Modules.LoginRequest;

import com.ansh.plannerCrud.Modules.Users;
import com.ansh.plannerCrud.Repositories.UserRepo;
import com.ansh.plannerCrud.Security.JwtProvider;
import com.ansh.plannerCrud.Services.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {
@Autowired
    private UserRepo repo;
@Autowired
 private PasswordEncoder passwordEncoder;
@Autowired
    private CustomUserDetails customUserDetails;

@PostMapping("/signup")
public ResponseEntity<AuthResponse>  createUserHandler(@RequestBody Users userr) throws Exception {
    Users isUserExist = repo.findByEmail(userr.getEmail());

    if(isUserExist!=null){
  throw new Exception("email already exists with another account");
    }

Users createdUser = new Users();
    createdUser.setPassword(passwordEncoder.encode(userr.getPassword()));
    createdUser.setEmail(userr.getEmail());
    createdUser.setFullName(userr.getFullName());
    createdUser.setRole("ROLE_"+userr.getRole());

    Users savedUser= repo.save(createdUser);


    List<GrantedAuthority> authorityList = new ArrayList<>();

    authorityList.add(new SimpleGrantedAuthority("ROLE_"+createdUser.getRole()));

    Authentication authentication = new UsernamePasswordAuthenticationToken(userr.getEmail(), userr.getPassword(),authorityList);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = JwtProvider.generateToken(authentication);
    AuthResponse res = new AuthResponse();
    res.setJwt(jwt);
    res.setRole(userr.getRole());
    res.setMessage("Signup successfull");

return new ResponseEntity<>(res, HttpStatus.CREATED);
}

@PostMapping("/signin")
public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest){

    String email = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    Authentication authentication  = authenticate(email, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = JwtProvider.generateToken(authentication);
    String role = repo.findByEmail(email).getRole();
    AuthResponse res = new AuthResponse();
    res.setJwt(jwt);
    res.setRole(role);
    res.setMessage("Signin successfull");

    return new ResponseEntity<>(res, HttpStatus.CREATED);


}

private Authentication authenticate(String username, String password){
    UserDetails userDetails = customUserDetails.loadUserByUsername(username);
    if(userDetails==null){
        throw new BadCredentialsException("Invalid username");
    }
    if(! passwordEncoder.matches(password, userDetails.getPassword())){
        throw new BadCredentialsException("invalid password");

    }

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

}

}
