package com.ansh.plannerCrud.Services;
import com.ansh.plannerCrud.DTO.ProductDto;
import com.ansh.plannerCrud.Mappers.ProductsMapper;
import com.ansh.plannerCrud.Modules.*;
import com.ansh.plannerCrud.Repositories.BookingsRepo;
import com.ansh.plannerCrud.Repositories.CartRepo;
import com.ansh.plannerCrud.Repositories.ProductsRepo;
import com.ansh.plannerCrud.Repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {

    @Autowired
    ProductsRepo repo;

    @Autowired
    ProductsMapper mapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    BookingsRepo bookingsRepo;

    @Autowired
    CartRepo cartRepo;


    public void addProduct(ProductDto data){
        try{
            System.out.println(mapper.dtoToModel(data));
            repo.save(mapper.dtoToModel(data));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public List<Products> display(){
        try {
            return (repo.findAll());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void update(ProductDto data){
        try{
            repo.save(mapper.dtoToModel(data));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

@Transactional
    public void delete(ProductDto productDto, Principal principal) {

            // Fetch the user based on the principal
            Users user = userRepo.findByEmail(principal.getName());
            List<Cart> prodInCart = cartRepo.findByProducts(mapper.dtoToModel(productDto));
    System.out.println("prod in cart "+prodInCart);
            // Fetch the bookings related to the user
            Optional<Bookings> bookingsOpt = bookingsRepo.findByUsers(user);
            if (!bookingsOpt.isPresent()) {
                throw new EntityNotFoundException("Bookings not found for the user");
            }

            Bookings bookings = bookingsOpt.get();

            // Convert VenueDTO to Venue entity

            Products prodToRemove = mapper.dtoToModel(productDto);
//deleting carts with the product to remove
    if(!prodInCart.isEmpty()){
        try{
            cartRepo.deleteAll(prodInCart);
            System.out.println("deleted all carts with products");
        }catch (Exception e){
            System.out.println("error in prod in cart check deletion"+e.getMessage());
        }


    }

            // Check if the venue is part of the user's bookings
            if (bookings.getProductsList().contains(prodToRemove)) {
                // Remove the venue from the bookings' venue list
                bookings.getProductsList().remove(prodToRemove);

                // Also remove the reference to bookings from the venue
                prodToRemove.getBookings().remove(bookings);

                // Save the updated bookings to update the join table (venue_products)
                bookingsRepo.save(bookings);

                // If the venue is no longer referenced by any other bookings, delete it
                if (prodToRemove.getBookings().isEmpty()) {
                    repo.delete(prodToRemove);  // Delete the venue if no references exist in the join table
                    System.out.println("products deleted successfully");
                } else {
                    System.out.println(" Products removed from booking but not deleted as it is referenced elsewhere");
                }
            }



            else {
//                throw new EntityNotFoundException("EMC not found in the user's bookings");
                System.out.println("Im in here services");
                repo.delete(mapper.dtoToModel(productDto));
                System.out.println("deleted");
            }
        }

}