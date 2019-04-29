package com.example.wmc;

import com.google.gson.annotations.SerializedName;

public class userData {

    @SerializedName("_id")
    private String Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("email")
    private String Email;

    @SerializedName("password")
    private String Password;

    @SerializedName("delStatus")
    private String delstatus;

    @SerializedName("updateStatus")
    private String updatestatus;


    public userData(String name, String email, String password) {
        Name = name;
        Email = email;
        Password = password;
    }

    public userData(String id, String name, String email, String password) {
        Id = id;
        Name = name;
        Email = email;
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getDelstatus() {
        return delstatus;
    }

    public String getUpdatestatus() {
        return updatestatus;
    }
}