package com.ansh.plannerCrud.Modules;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class EMC {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public EMC() {
    }

    @Override
    public String toString() {
        return "EMC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", phone='" + phone + '\'' +
                '}';
    }

    public EMC(int id, String name, String address, double price, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.phone = phone;
    }

    private String name;
    private String address;
    private double price;
    private String phone;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(
//            name = "cart_id"
//    )
//     private Cart cart;

//    @ManyToOne
//    @JoinColumn(name ="bookingsId")
//    private Bookings bookings;


    @ManyToMany(mappedBy = "EMCList",  cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bookings> bookings = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMC emc = (EMC) o;
        return Objects.equals(id, emc.id);  // Compare by ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Hash based on ID
    }
}
