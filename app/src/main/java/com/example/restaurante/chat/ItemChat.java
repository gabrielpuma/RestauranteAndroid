package com.example.restaurante.chat;

public class ItemChat {
    String name;
    String message;
    ItemChat(String a, String b){
        name=a;
        message=b;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
