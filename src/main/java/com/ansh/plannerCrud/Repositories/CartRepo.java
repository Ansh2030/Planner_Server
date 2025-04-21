package com.ansh.plannerCrud.Repositories;

import com.ansh.plannerCrud.Modules.Cart;

import com.ansh.plannerCrud.Modules.Products;
import com.ansh.plannerCrud.Modules.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo  extends JpaRepository<Cart, Integer> {
    List<Cart> findByUsers(Users users);
    List<Cart> findByProducts(Products products);
     void deleteAllByProducts(Products products);
}
