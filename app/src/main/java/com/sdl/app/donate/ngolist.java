package com.sdl.app.donate;

/**
 * Created by vikas on 10/10/17.
 */

public class ngolist {

    boolean success;
    String msg;
    String name;
    String Address;
    int amount;
    public String userTo;
    public String userFrom;
    public String quantity;
    public String address;
    public String date;

    public String getDate() {
        return date;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public String getUserTo() {
        return userTo;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
