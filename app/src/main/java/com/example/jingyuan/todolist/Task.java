package com.example.jingyuan.todolist;

import java.io.Serializable;

/**
 * Created by jingyuan on 9/26/17.
 */

public class Task implements Serializable{
    private String title, content;

    public Task(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

}
