package com.hasura.rania.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rania on 8/22/2017.
 */

public class AuthenticationRequest {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
