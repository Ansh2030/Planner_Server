package com.ansh.plannerCrud.Modules;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
//@ToString(exclude = "users")
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @JsonBackReference
    private Users users;

    //

    @Override
    public String toString() {
        return "Bookings{" +
                "id=" + id +
                ", users=" + users +
                ", productsList=" + productsList +
                ", venueList=" + venueList +
                ", EMCList=" + EMCList +
                '}';
    }



    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(

            name = "Venue_Products",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "venue_id")
    )
    @JsonManagedReference
    private List<Venue> venueList= new ArrayList<>();


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "EMC_Products",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "EMC_id")
    )
    private List<EMC> EMCList = new ArrayList<>() ;



    @ManyToMany(cascade = CascadeType.ALL)
//    @JsonIgnore
    @JoinTable(
            name = "Bookings_Products",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "prod_id")
    )
    private List<Products> productsList = new ArrayList<>();

}
