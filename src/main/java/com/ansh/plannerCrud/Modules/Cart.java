package com.ansh.plannerCrud.Modules;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;

    public Cart(Users users, Products products) {
        this.users = users;
        this.products = products;
    }

    @OneToOne
     private Users users ;

    @OneToOne
    private  Products products;

    public Cart() {
    }
}
