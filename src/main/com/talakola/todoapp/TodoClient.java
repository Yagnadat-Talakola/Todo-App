

package com.talakola.todoapp;

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class TodoClient {
    private static TodoModel newTodo;
    private static TodoStatus todoStatus;

    public static void main(String[] args) throws IOException {
        System.out.println("TodoApp helps you save your Todos securely in the database");

        // try-with-resources ensures auto-closure of resources.
        // input and output streams created to read(from server stream)and write(to server stream)respectively.
        try (
                Socket socket = new Socket("pop-os", 4444);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Enter your Todo: ");
            Scanner userInput = new Scanner(System.in);
            String todo = null;
            int statusCode;
            boolean save = false;

            while (save == false) {
                todo = userInput.nextLine();
                System.out.println("Enter status code: 1.Completed; 2.To start; 3.In Progress");
                statusCode = userInput.nextInt();
                System.out.println("Enter Y to save Todo");

                if (userInput.hasNext("Y")) {

                    if (statusCode == 1) {
                        todoStatus = TodoStatus.COMPLETED;
                    } else if (statusCode == 2) {
                        todoStatus = TodoStatus.TO_START;
                    } else {
                        todoStatus = TodoStatus.IN_PROGRESS;
                    }

                    save = true;
                    userInput.close();
                    break;
                }
            }

            newTodo = new TodoModel(todo, todoStatus);
            System.out.println(newTodo.toString());

            // writing the newTodo to the output stream for Server to read from.
            //out.println(newTodo);

            // writing to the output stream using ObjectOutputStream to properly serialize
            outputStream.writeObject(newTodo);

            // read from server to see database insertion was successful.
            String fromServer;
            fromServer = in.readLine();
            while(fromServer != null) {
                System.out.println(fromServer);
                break;
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
        }


    }
}
