package com.bloodapp.Model;

public class Token {

    String uid;
    String token;

    public Token(String uid, String token) {
        this.token = token;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
