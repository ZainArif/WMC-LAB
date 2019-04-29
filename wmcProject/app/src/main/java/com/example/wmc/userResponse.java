package com.example.wmc;

import com.google.gson.annotations.SerializedName;

public class userResponse {
    @SerializedName("user")
    private userData userData;

    @SerializedName("userStatus")
    private String UserStatus;

    public com.example.wmc.userData getUserData() {
        return userData;
    }

    public String getUserStatus() {
        return UserStatus;
    }
}
