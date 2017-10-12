package com.sdl.app.donate;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vishvanatarajan on 10/10/17.
 */

public class MSG {

    @SerializedName("success")
    private Boolean success;
    @SerializedName("token")
    private String token;

    /**
     * No args constructor for use in serialization
     */
    public MSG() {
    }

    /**
     * @param success
     * @param token
     */
    public MSG(Boolean success, String token) {
        super();
        this.success = success;
        this.token = token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
