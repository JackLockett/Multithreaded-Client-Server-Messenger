package com.company;

import java.net.*;
import java.io.*;
import java.util.*;
import org.json.simple.*;

public class Server {

    static class Clock {
        private long t;
        public Clock() { t = 0; }
        public synchronized long tick() { return ++t; }
    }


    static class ClientHandler extends Thread {

        private static List<Message> board = new ArrayList<Message>();
        private static Clock clock = new Clock();
        private int read;
        private String login;
        private Socket client;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) throws IOException {
            client = socket;
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            read = 0;
            login = null;
        }

        public void run() {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    long ts = clock.tick();

                    if (login != null)
                        System.out.println(login + ": " + inputLine);
                    else
                        System.out.println(inputLine);

                    Object json = JSONValue.parse(inputLine);
                    Request req;

                    if (login == null && (req = LoginRequest.fromJSON(json)) != null) {
                        login = ((LoginRequest)req).getName();
                        out.println(new SuccessResponse());
                        continue;
                    }

                    if (login != null &&
                            (req = PostRequest.fromJSON(json)) != null) {
                        String message = ((PostRequest)req).getMessage();
                        synchronized (ClientHandler.class) {
                            board.add(new Message(message, login, ts));
                        }
                        out.println(new SuccessResponse());
                        continue;
                    }

                    if (login != null && ReadRequest.fromJSON(json) != null) {
                        List<Message> msgs;
                        synchronized (ClientHandler.class) {
                            msgs = board.subList(read, board.size());
                        }
                        read = board.size();
                        out.println(new MessageListResponse(msgs));
                        continue;
                    }

                    if (login != null && QuitRequest.fromJSON(json) != null) {
                        in.close();
                        out.close();
                        return;
                    }

                    out.println(new ErrorResponse("ILLEGAL REQUEST"));
                }
            } catch (IOException e) {
                System.out.println("Exception while connected");
                System.out.println(e.getMessage());
            }
        }
    }


    public static void main(String[] args) {


        int portNumber = 12345;

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
        ) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Exception listening for connection on port " +
                    portNumber);
            System.out.println(e.getMessage());
        }
    }
}
