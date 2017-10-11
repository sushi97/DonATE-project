package com.sdl.app.donate;

/**
 * Created by vikas on 9/10/17.
 */

public class user_info {
    boolean success;
    String token;
    userdon user;
    String phoneno;
    String address;
    boolean receiver;


    public static class userdon {
        String id;
        String name;
        String username;
        String password;
        String email;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(userdon user) {
        this.user = user;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setReceiver(boolean receiver) {
        this.receiver = receiver;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getAddress() {
        return address;
    }

    public boolean isReceiver() {
        return receiver;
    }

    public String getPhoneno() {
        return phoneno;

    }

    public userdon getUser() {
        return user;
    }
}
