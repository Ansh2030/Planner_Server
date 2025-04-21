package com.ansh.plannerCrud.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CartCheckoutDTO {
    private List<Integer> id ;

    public CartCheckoutDTO() {
    }

    public CartCheckoutDTO(List<Integer> id) {
        this.id = id;
    }
}
