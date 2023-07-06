package com.company;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.simple.*;

public class Client {

    public static boolean loggedIn = false;
    private static String username;

    private static final Set<String> subscribedChannels = new HashSet<>();

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        boolean menuLoop;

        String hostName = "localhost";
        int portNumber = 12345;

        Socket socket;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        do {
            menuLoop = true;
            System.out.println("\nClient-Server Messenger");
            System.out.println("-----------------------");

            if (loggedIn)
                System.out.println("Logged in as: " + username);
            else
                System.out.println("Not logged in");

            System.out.println("\n1) Messenger");
            System.out.println("2) Login");
            System.out.println("3) Subscribe");
            System.out.println("4) Unsubscribe");
            System.out.println("9) Quit");
            System.out.println("\nPlease enter option: ");

            if (input.hasNextLine()) {
                String value = input.nextLine();

                switch (value) {
                    case "1" -> {
                        Messenger(in, out);
                    }
                    case "2" -> {
                        if (loggedIn) {
                            System.out.println("You are already logged in as " + username + ".");
                        } else {
                            System.out.println("Enter your username: ");
                            String username = input.nextLine();

                            Request req = new LoginRequest(username);
                            assert out != null;
                            out.println(req);

                            assert in != null;
                            String serverResponse = in.readLine();
                            Object json = JSONValue.parse(serverResponse);
                            Response resp;
                            if ((resp = SuccessResponse.fromJSON(json)) != null) {
                                loggedIn = true;
                                Client.username = username;
                                System.out.println("Successfully logged in as " + username);
                            } else if ((resp = ErrorResponse.fromJSON(json)) != null) {
                                System.out.println(((ErrorResponse) resp).getError());
                            }
                        }
                    }
                    case "3" -> {
                        if (!loggedIn) {
                            System.out.println("You must log in before subscribing to a channel.");
                        } else {
                            System.out.println("Enter the name of the channel you want to subscribe to: ");
                            String channel = input.nextLine();

                            Request req = new SubscribeRequest(channel, username);
                            assert out != null;
                            out.println(req);

                            assert in != null;
                            String serverResponse = in.readLine();
                            Object json = JSONValue.parse(serverResponse);
                            Response resp;
                            subscribedChannels.add(channel);
                            System.out.println("Successfully subscribed to " + channel);
                        }
                    }
                    case "4" -> {
                        if (!loggedIn) {
                            System.out.println("You must log in before unsubscribing from a channel.");
                        } else {
                            System.out.println("Enter the name of the channel you want to unsubscribe from: ");
                            String channel = input.nextLine();

                            Request req = new UnsubscribeRequest(channel, username);
                            assert out != null;
                            out.println(req);

                            assert in != null;
                            String serverResponse = in.readLine();
                            JSONValue.parse(serverResponse);
                            subscribedChannels.remove(channel);
                            System.out.println("Successfully unsubscribed to " + channel);
                        }
                    }
                    case "9" -> {
                        Request req = new QuitRequest();
                        assert out != null;
                        out.println(req);
                        menuLoop = false;
                    }
                    default -> System.out.println("\nPlease enter a valid option.");
                }
            }
        } while (menuLoop);

        // Close the input Scanner
        try {
            input.close();
        } catch (Exception e) {
            // Handle exception
        }

        try {
            if (in != null) {
                in.close();
            }
            out.close();
        } catch (Exception e) {
            // Handle exception
        }
    }

    public static void Messenger(BufferedReader in, PrintWriter out) throws IOException {
        // Check the user's login status
        if (!loggedIn) {
            System.out.println("You must log in before accessing the messenger.");
            return;
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String channel;
        while (true) {
            System.out.println("\nEnter a channel name or 'q' to return to the main menu: ");
            channel = stdIn.readLine();
            if (channel.equals("q")) {
                break;
            }
            if (!subscribedChannels.contains(channel)) {
                System.out.println("You are not subscribed to channel " + channel + ".");
                continue;
            }
            System.out.println("Enter a message or 'q' to switch channels: ");
            while (true) {
                String message = stdIn.readLine();
                if (message.equals("q")) {
                    break;
                }
                if (message.equals("r")) {
                    ReadRequest req = new ReadRequest();
                    System.out.println(req);
                    continue;
                }
                Request req = new PostRequest(message, channel, username);
                out.println(req);
            }
        }
    }
}

