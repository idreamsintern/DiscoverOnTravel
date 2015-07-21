package com.example.idreams.dot.chat;

public class Chat {

    private String message;
    private String author;

    Chat(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
