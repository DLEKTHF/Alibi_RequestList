package com.example.dasolee.registeration;

public class Request {
    String title;
    String content;

    public Request(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
}
