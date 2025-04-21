package com.ansh.plannerCrud.Services;

import com.ansh.plannerCrud.DTO.CartDto;
import com.ansh.plannerCrud.Mappers.CartMapper;
import com.ansh.plannerCrud.Modules.Bookings;
import com.ansh.plannerCrud.Modules.Cart;
import com.ansh.plannerCrud.Modules.Products;
import com.ansh.plannerCrud.Modules.Users;
import com.ansh.plannerCrud.Repositories.BookingsRepo;
import com.ansh.plannerCrud.Repositories.CartRepo;
import com.ansh.plannerCrud.Repositories.ProductsRepo;
import com.ansh.plannerCrud.Repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServices {
    @Autowired
    CartRepo repo;

    @Autowired
    CartMapper mapper;

    @Autowired
    ProductsRepo productsRepo;
    @Autowired
    UserRepo userRepo;

    @Autowired
    BookingsRepo bookingsRepo;



    public void addToCart(CartDto cartDto, Principal principal){
try{
//    repo.save(mapper.dtoToModel(cartDto));
    System.out.println(cartDto);
    Products pd= productsRepo.findById(cartDto.getPid()).get();
//    Users ud = userRepo.findById(cartDto.getUid()).get();
    Users ud = userRepo.findByEmail(principal.getName());
    Cart cart= new Cart(ud, pd);

    repo.save(cart);

}

catch (Exception e){
    System.out.println(e);
}
    }


    public List<Cart> display(Principal principal){
        String username = principal.getName();
       Users users = userRepo.findByEmail(username);

       return repo.findByUsers(users);


    }


    public void deleteItem(CartDto data){
        try{
            repo.delete(mapper.dtoToModel(data));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

@Transactional
    public String checkout(List<Integer> id, Principal principal) {

        try{
            Users users = userRepo.findByEmail(principal.getName());
            Bookings bookings = bookingsRepo.findByUsers(users).get();
            List<Products> prods  = new ArrayList<>();
            for(int i = 0;i<id.size();i++){
               prods.add(productsRepo.findById(id.get(i)).get());
            }
            bookings.getProductsList().addAll(prods);

            bookingsRepo.save(bookings);


            List<Cart> cartToUpdate = repo.findByUsers(users);
            repo.deleteAll(cartToUpdate);
            return ("saved bookings");

        }catch (Exception e){
            System.out.println(e);
            return (e.getMessage());
        }
    }
}
