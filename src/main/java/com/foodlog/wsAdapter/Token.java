package com.foodlog.wsAdapter;

import java.io.Serializable;

/**
 * Created by rafael on 16/11/17.
 */
public class Token implements Serializable {
    private String id_token;

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }
}
