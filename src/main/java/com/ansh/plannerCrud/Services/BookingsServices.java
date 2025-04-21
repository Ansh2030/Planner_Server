package com.ansh.plannerCrud.Services;
import com.ansh.plannerCrud.DTO.BookingsDTO;
import com.ansh.plannerCrud.Modules.*;
import com.ansh.plannerCrud.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class BookingsServices {

    @Autowired
    ProductsRepo productsRepo;
    @Autowired
    EMCRepo emcRepo;
    @Autowired
    VenueRepo venueRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    BookingsRepo bookingsRepo;

    public void addBookings(BookingsDTO bookingsDTO,String username){

        Products products= null;
        EMC emc = null;
        Venue venue = null;
        try{

//            Users users = userRepo.findById(bookingsDTO.getUser_id()).get();

            Users users = userRepo.findByEmail(username);
            if(bookingsDTO.getProd_id()!=0)
            {
                products = productsRepo.findById(bookingsDTO.getProd_id()).get();
                System.out.println("product found"+ products);
            }
            if(bookingsDTO.getEmc_id()!=0)
                emc = emcRepo.findById(bookingsDTO.getEmc_id()).get();
            if(bookingsDTO.getVenue_id()!=0){
                venue = venueRepo.findById(bookingsDTO.getVenue_id()).get();

            }

            //to check existing bookings data for a user or to make a new bookings for user
            Optional<Bookings> bookingsOptional = bookingsRepo.findByUsers(users);
            Bookings bookings= null;
            if(bookingsOptional.isPresent()){
                bookings = bookingsOptional.get();
                System.out.println("already present: "+bookings);
            }
            else
            {
                bookings = new Bookings();
                bookings.setUsers(users);

                System.out.println("user set");
            }

            if(emc!=null)
                bookings.getEMCList().add(emc);
            if(venue!=null)
                bookings.getVenueList().add(venue);
            if(products!=null)
                bookings.getProductsList().add(products);

            System.out.println("bokings: "+ bookings);




            bookingsRepo.save(bookings);

            System.out.println("findall :  "+bookingsRepo.findAll());


        }catch (Exception e){
    throw e;
        }
    }


    public Bookings getBookings(String username){
        Users users = userRepo.findByEmail(username);

        System.out.println("this sis the users bookings :"+users.getBookings());
        Bookings bookings = users.getBookings();
         return bookings;
//        System.out.println(users+" in getbokings");

//        System.out.println("all the bookings : "+bookingsRepo.findAll());
//        Optional<Bookings> bookingsOptional = bookingsRepo.findByUsers(users);
//        if(bookingsOptional.isPresent()){
//            return bookingsOptional.get();
//        }
//        else{
//            System.out.println("No bookings found");
//            return null;
//        }


    }

}
