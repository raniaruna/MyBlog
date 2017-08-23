package com.hasura.rania.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rania on 8/22/2017.
 */

public class MessageResponse {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
