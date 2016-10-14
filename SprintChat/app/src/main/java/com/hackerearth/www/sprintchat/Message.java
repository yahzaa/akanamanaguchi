package com.hackerearth.www.sprintchat;

/**
 * Created by ck on 14/10/16.
 */

public class Message {

    private String text;
    private String username;

    public Message() {
    }

    public Message(String text, String username) {
        this.text = text;
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }
}
