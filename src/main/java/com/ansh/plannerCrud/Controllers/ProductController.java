package com.ansh.plannerCrud.Controllers;


import com.ansh.plannerCrud.DTO.ProductDto;
import com.ansh.plannerCrud.Mappers.ProductsMapper;
import com.ansh.plannerCrud.Modules.Products;
import com.ansh.plannerCrud.Repositories.ProductsRepo;
import com.ansh.plannerCrud.Roles.Role;
import com.ansh.plannerCrud.Services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
     private ProductServices services;
    @Autowired
     private ProductsMapper mapper;
    @Autowired
    private Role role;
    @Autowired
    private ProductsRepo repo;

    @PostMapping("/api/Products")
    public void addProduct(@RequestBody  ProductDto productDto, Principal principal){

        System.out.println(principal.getName());
        System.out.println(role.getRole(principal.getName()));


        try{
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
                services.addProduct(productDto);
            else
                System.out.println("Unauthorized request");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @GetMapping("/api/Products")
    public List<Products> getProd(){
        return services.display();
    }

    @PutMapping("/api/Products")
    public void update(@RequestBody ProductDto productDto, Principal principal){
        try{
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
                services.update(productDto);
            else
                System.out.println("Unauthorized request");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @DeleteMapping("/api/Products/{id}")
    public void delete(@PathVariable int id, Principal principal){
        try{
            ProductDto productDto = mapper.modelToDto(repo.findById(id).get());
            System.out.println("data reached the controller "+productDto);
            if(role.getRole(principal.getName()).equals("ROLE_ADMIN"))
                services.delete(productDto,principal);
            else
                System.out.println("Unauthorized request");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }




}
