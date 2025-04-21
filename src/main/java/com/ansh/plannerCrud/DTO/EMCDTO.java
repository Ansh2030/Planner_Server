package com.ansh.plannerCrud.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;

import lombok.Data;

@Data
public class EMCDTO {
@Id
@JsonProperty("Id")
    private int id;
@JsonProperty("name")
    private String name;

    public EMCDTO(int id, String name, String address, double price, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.phone = phone;
    }

    @JsonProperty("address")
    private String address;
    @JsonProperty("price")
    private double price;
    @JsonProperty("phone")
    private String phone;

}
