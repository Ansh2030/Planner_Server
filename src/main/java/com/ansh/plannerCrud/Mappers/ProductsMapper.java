package com.ansh.plannerCrud.Mappers;


import com.ansh.plannerCrud.DTO.ProductDto;
import com.ansh.plannerCrud.Modules.Products;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductsMapper {
    Products dtoToModel(ProductDto productDto);
    ProductDto modelToDto(Products products);
}
