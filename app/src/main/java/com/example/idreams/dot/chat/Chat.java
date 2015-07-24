package com.example.idreams.dot.chat;

public class Chat {

    private String message;
    private String author;
    private String id;

    // Used by firebase. Don't remove it.
    Chat() {
    }

    Chat(String message, String author, String id) {
        this.message = message;
        this.author = author;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }
}
