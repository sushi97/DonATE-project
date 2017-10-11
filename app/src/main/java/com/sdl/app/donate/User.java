package com.sdl.app.donate;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vishvanatarajan on 10/10/17.
 */

public class User {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("phoneNo")
    private String phoneNo;


    public User(String name, String email, String password, String phoneNo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    private String username;
    private int phone;
    //    private String city;
    private String location;
    private String token;

    public String getUsername() {
        return username;
    }




    public int getPhone() {
        return phone;
    }

//   public String getCity() {
//        return city;
//    }

    public String getLocation() {
        return location;
    }

    public String getToken() {
        return token;
    }

}
