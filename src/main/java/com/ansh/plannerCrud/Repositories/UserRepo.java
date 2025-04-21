package com.ansh.plannerCrud.Repositories;


import com.ansh.plannerCrud.Modules.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Long> {
Users findByEmail(String email);

}
