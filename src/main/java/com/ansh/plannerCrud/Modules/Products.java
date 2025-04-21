package com.ansh.plannerCrud.Modules;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String category;

    private double price;

//    @ManyToOne

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
//    @JoinColumn(name="bookingsId")
//    private Bookings bookings;

    @ManyToMany(mappedBy = "productsList",  cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bookings> bookings = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return Objects.equals(id, products.id);  // Compare by ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Hash based on ID
    }



}
