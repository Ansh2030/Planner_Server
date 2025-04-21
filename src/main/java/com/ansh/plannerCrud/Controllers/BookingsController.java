package com.ansh.plannerCrud.Controllers;
import com.ansh.plannerCrud.DTO.BookingsDTO;
import com.ansh.plannerCrud.Modules.Bookings;
import com.ansh.plannerCrud.Services.BookingsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class BookingsController {
    @Autowired
    BookingsServices services;


@PostMapping("/api/bookings")
    public String add(@RequestBody  BookingsDTO bookingsDTO, Principal principal){

        services.addBookings(bookingsDTO, principal.getName());
        return "added successfully";
    }

    @GetMapping("/api/bookings")
    public Bookings getBookings(Principal principal){
    try{
        return services.getBookings(principal.getName());
    }catch (Exception e){
        throw e;
    }

    }
}
