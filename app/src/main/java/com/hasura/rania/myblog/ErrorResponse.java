package com.hasura.rania.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rania on 8/22/2017.
 */

public class ErrorResponse {
    @SerializedName("error")
    String error;

    public String getError() {
        return error;
    }
}
