package com.ansh.plannerCrud.Services;
import com.ansh.plannerCrud.DTO.VenueDTO;
import com.ansh.plannerCrud.Mappers.VenueMapper;
import com.ansh.plannerCrud.Modules.Bookings;
import com.ansh.plannerCrud.Modules.Users;
import com.ansh.plannerCrud.Modules.Venue;
import com.ansh.plannerCrud.Repositories.BookingsRepo;
import com.ansh.plannerCrud.Repositories.UserRepo;
import com.ansh.plannerCrud.Repositories.VenueRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.*;
@Service
public class VenueServices {
    @Autowired
    private VenueRepo repo;

    @Autowired
    private VenueMapper mapper;

    @Autowired
    private BookingsRepo bookingsRepo;

    @Autowired
    private UserRepo userRepo;


    public List<Venue> display(){
        try{
            return(repo.findAll());

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

public void addVenue(VenueDTO venue){
//    System.out.println(venue);
//
    System.out.println(mapper.dtoToModel(venue));
    repo.save(mapper.dtoToModel(venue));
}

public void updateVenue(VenueDTO data){
//    Venue ob = new Venue(data.getId(), data.getName(), data.getAddress(), data.getPrice());
    repo.save(mapper.dtoToModel(data));
}


    @Transactional
    public void delete(VenueDTO venueDTO, Principal principal) {
        // Fetch the user based on the principal
        Users user = userRepo.findByEmail(principal.getName());

        // Fetch the bookings related to the user
        Optional<Bookings> bookingsOpt = bookingsRepo.findByUsers(user);
        if (!bookingsOpt.isPresent()) {
            throw new EntityNotFoundException("Bookings not found for the user");
        }

        Bookings bookings = bookingsOpt.get();

        // Convert VenueDTO to Venue entity
        Venue venueToRemove = mapper.dtoToModel(venueDTO);

        // Check if the venue is part of the user's bookings
        if (bookings.getVenueList().contains(venueToRemove)) {
            // Remove the venue from the bookings' venue list
            bookings.getVenueList().remove(venueToRemove);

            // Also remove the reference to bookings from the venue
            venueToRemove.getBookings().remove(bookings);

            // Save the updated bookings to update the join table (venue_products)
            bookingsRepo.save(bookings);

            // If the venue is no longer referenced by any other bookings, delete it
            if (venueToRemove.getBookings().isEmpty()) {
                repo.delete(venueToRemove);  // Delete the venue if no references exist in the join table
                System.out.println("Venue deleted successfully");
            } else {
                System.out.println("Venue removed from booking but not deleted as it is referenced elsewhere");
            }
        } else {
//            throw new EntityNotFoundException("Venue not found in the user's bookings");

            repo.delete(mapper.dtoToModel(venueDTO));
        }
    }

}
