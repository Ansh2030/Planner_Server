package com.ansh.plannerCrud.Mappers;


import com.ansh.plannerCrud.DTO.CartDto;
import com.ansh.plannerCrud.Modules.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CartMapper {

    Cart dtoToModel(CartDto cartDto);
    CartDto modelToDto(Cart cart);
}
