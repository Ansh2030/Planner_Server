package com.ansh.plannerCrud.DTO;

import lombok.Data;

@Data
public class  CartDto {
    public CartDto(int id, long uid, int pid) {
        this.id = id;
        this.uid = uid;
        this.pid = pid;
    }

    private int id;
    private long uid;
    private int pid;

}
