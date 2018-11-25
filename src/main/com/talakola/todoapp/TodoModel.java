package com.talakola.todoapp;

import java.io.Serializable;

// Serializable enables objects of this class to be serialized and deserialized.
public class TodoModel implements Serializable {

    public String todo;
    public TodoStatus status;

    public TodoModel(String todo, TodoStatus status) {
        this.todo = todo;
        this.status = status;
    }

    @Override
    public String toString() {
        return "TodoModel{" +
                "todo='" + todo + '\'' +
                ", status=" + status +
                '}';
    }
}
