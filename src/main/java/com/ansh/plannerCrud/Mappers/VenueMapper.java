package com.ansh.plannerCrud.Mappers;

import com.ansh.plannerCrud.DTO.VenueDTO;
import com.ansh.plannerCrud.Modules.Venue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface VenueMapper {
    VenueDTO modelToDTo (Venue venue);
    Venue dtoToModel(VenueDTO venueDTO);
}
