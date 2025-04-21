package com.ansh.plannerCrud.Modules;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
@Entity
public class Venue {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String address;

    @Override
    public String toString() {
        return "Venue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +

                '}';
    }

    private double price;

    public Venue(){

    }

    public Venue(int id, String name, String address, double price) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
    }

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(
//            name="cart_id"
//    )
//   private  Cart cart;


@ManyToMany(mappedBy = "venueList", cascade = CascadeType.ALL)
//@JsonIgnore
@JsonBackReference
private List<Bookings> bookings = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return Objects.equals(id, venue.id);  // Compare by ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Hash based on ID
    }
}
