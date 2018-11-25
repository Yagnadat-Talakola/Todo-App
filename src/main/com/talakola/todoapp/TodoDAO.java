package com.talakola.todoapp;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

// Todo: receive the newTodo from TodoServer and save it as a document in MongoDB. (Complete)
// Todo: Display all todos from the Todos collection.
public class TodoDAO {

    // if database insertion is a success, return true to server.
    public boolean todoInsertToDb(TodoModel newTodo) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("TodoApp");

        MongoCollection<Document> collection = database.getCollection("Todos");

        Document document = new Document(newTodo.todo, newTodo.status.toString());
        collection.insertOne(document);

        // Todo: check for duplicates in the collection.
        // Todo: return true only if insertion is a success.
        return true;
    }

    public void displayAllTodos() {

    }

}

