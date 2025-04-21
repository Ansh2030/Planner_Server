package com.ansh.plannerCrud.Services;
import com.ansh.plannerCrud.DTO.EMCDTO;
import com.ansh.plannerCrud.DTO.VenueDTO;
import com.ansh.plannerCrud.Mappers.EMCMapper;
import com.ansh.plannerCrud.Modules.Bookings;
import com.ansh.plannerCrud.Modules.EMC;
import com.ansh.plannerCrud.Modules.Users;
import com.ansh.plannerCrud.Modules.Venue;
import com.ansh.plannerCrud.Repositories.BookingsRepo;
import com.ansh.plannerCrud.Repositories.EMCRepo;
import com.ansh.plannerCrud.Repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class EMCServices {

    @Autowired
    EMCRepo repo;
    @Autowired
    EMCMapper mapper;

    @Autowired
    BookingsRepo bookingsRepo;
    @Autowired
    UserRepo userRepo;

    public void addMVC(EMCDTO data){
    repo.save(mapper.dtoToModel(data));
    }

    public List<EMC> displayEMC(){
       try{
           return repo.findAll();
       }catch (Exception e){
           System.out.println(e);
           return null;
       }
    }


    public void updateMVC(EMCDTO data){
        repo.save(mapper.dtoToModel(data));

    }

    @Transactional
    public void delete(EMCDTO data, Principal principal){
        // Fetch the user based on the principal
            Users user = userRepo.findByEmail(principal.getName());

            // Fetch the bookings related to the user
            Optional<Bookings> bookingsOpt = bookingsRepo.findByUsers(user);
            if (!bookingsOpt.isPresent()) {
                throw new EntityNotFoundException("Bookings not found for the user");
            }

            Bookings bookings = bookingsOpt.get();

            // Convert VenueDTO to Venue entity
//            Venue EMCToRemove = mapper.dtoToModel(venueDTO);
        EMC EMCToRemove = mapper.dtoToModel(data);

            // Check if the venue is part of the user's bookings
            if (bookings.getEMCList().contains(EMCToRemove)) {
                // Remove the venue from the bookings' venue list
                bookings.getEMCList().remove(EMCToRemove);

                // Also remove the reference to bookings from the venue
                EMCToRemove.getBookings().remove(bookings);

                // Save the updated bookings to update the join table (venue_products)
                bookingsRepo.save(bookings);

                // If the venue is no longer referenced by any other bookings, delete it
                if (EMCToRemove.getBookings().isEmpty()) {
                    repo.delete(EMCToRemove);  // Delete the venue if no references exist in the join table
                    System.out.println("EMC deleted successfully");
                } else {
                    System.out.println("EMC removed from booking but not deleted as it is referenced elsewhere");
                }
            } else {
//                throw new EntityNotFoundException("EMC not found in the user's bookings");

                repo.delete(mapper.dtoToModel(data));
            }
        }
    



}
