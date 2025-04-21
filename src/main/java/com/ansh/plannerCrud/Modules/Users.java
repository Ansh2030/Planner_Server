package com.ansh.plannerCrud.Modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Data
@Entity

public class Users  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  id;
    private String fullName ;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
//                ", bookings=" + bookings +
                '}';
    }

    private String role;
//
// @OneToOne(cascade = CascadeType.ALL)
//    private Bookings bookings;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
//    @JsonManagedReference
    @JsonIgnore
    private Bookings bookings;


// @OneToOne(cascade = CascadeType.ALL)
// @JoinColumn(
//         name="cart_id",
//         referencedColumnName = "id"
// )
//    private Cart cart;




}
