package com.ansh.plannerCrud.Mappers;


import com.ansh.plannerCrud.DTO.EMCDTO;
import com.ansh.plannerCrud.Modules.EMC;
import org.mapstruct.Mapper;


@Mapper(componentModel = "Spring")
public interface EMCMapper {
    EMCDTO modelToDto(EMC emc);
    EMC dtoToModel(EMCDTO emcDto);
}
