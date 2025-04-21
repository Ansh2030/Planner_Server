package com.ansh.plannerCrud.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VenueDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
 private String name;
    @JsonProperty("address")
 private String address;


    @JsonProperty("price")
 private double price;


    public VenueDTO(int id, String name, String address, double price) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
    }



}
