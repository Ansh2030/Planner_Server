package com.ansh.plannerCrud.Controllers;
import com.ansh.plannerCrud.DTO.CartCheckoutDTO;
import com.ansh.plannerCrud.DTO.CartDto;
import com.ansh.plannerCrud.Mappers.CartMapper;
import com.ansh.plannerCrud.Modules.Cart;
import com.ansh.plannerCrud.Modules.Products;
import com.ansh.plannerCrud.Repositories.CartRepo;
import com.ansh.plannerCrud.Repositories.ProductsRepo;
import com.ansh.plannerCrud.Services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Executable;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

 @Autowired
CartServices services;

 @Autowired
    CartRepo cartRepo;

 @Autowired
    CartMapper mapper;

 @Autowired
    ProductsRepo productsRepo;

@PostMapping("cart")
  public void addToCart(@RequestBody CartDto cartDto, Principal principal){
      try{
          services.addToCart(cartDto, principal);
      }catch (Exception e){
          System.out.println(e.getMessage());
      }
  }
@GetMapping("cart")
    public List<Cart>displayProductsByUser(Principal principal){
        try{
           return  services.display(principal);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/cart/{id}")
    public String deleteFromCart(@PathVariable int id, Principal principal) {
        try {

            services.deleteItem(mapper.modelToDto(cartRepo.findById(id).get()));
            return ("Deleted successfully");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
@PostMapping("/cart/checkout")
    public String checkout(@RequestBody CartCheckoutDTO data, Principal principal){
    try{
         String res= services.checkout(data.getId(),principal);
        return (res);
    } catch (Exception e) {
        return ("error in controll "+e.getMessage());
    }
    }

}
