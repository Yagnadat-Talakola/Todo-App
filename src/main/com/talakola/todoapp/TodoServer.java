package com.talakola.todoapp;

import java.net.*;
import java.io.*;

/*
    * ObjectInputStream enables objects to be deserialized from the clientSocket's input stream.
    * Will delete BufferedReader instance if not making use of it.
 */

public class TodoServer {

    private static final int PORTNUMBER = 4444;

    public static void main(String[] args) throws IOException {

        TodoModel newTodo = null;

        try (
                ServerSocket serverSocket = new ServerSocket(PORTNUMBER);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream())

        ) {

            // using ObjectInputStream to deserialize input from client in Object form.
            try {
                newTodo = (TodoModel) objectInputStream.readObject();
                System.out.println(newTodo.toString());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }

            // send TodoModel object to DAO to insert to database.
            TodoDAO databaseDAO = new TodoDAO();
            boolean result = databaseDAO.todoInsertToDb(newTodo);


            if (result == true) {
                out.println("database insert successful");
            } else {
                out.println("database insert did not go through. try again");
            }


        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + PORTNUMBER + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

}
