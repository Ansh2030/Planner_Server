package com.ansh.plannerCrud.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookingsDTO {

//    @JsonProperty("user_id")
//    private long user_id;

    @JsonProperty("prod_id")
    private int prod_id;

    @JsonProperty("emc_id")
    private int emc_id;

    @JsonProperty("venue_id")
    private int venue_id;

}
