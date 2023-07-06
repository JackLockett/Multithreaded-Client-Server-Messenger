package com.company;

import org.json.simple.*;

import java.io.FileWriter;
import java.io.IOException;

public class PostRequest extends Request {

    private static final String _class = PostRequest.class.getSimpleName();

    private String channel;
    private String message;
    private String username;

    public PostRequest(String message, String channel, String username) {
        if (channel == null || message == null || username == null)
            throw new NullPointerException();
        this.channel = channel;
        this.message = message;
        this.username = username;
    }

    String getChannel() { return channel; }
    String getMessage() { return message; }
    String getUsername() { return username; }

    // Serializes this object into a JSONObject
    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("channel", channel);
        obj.put("message", message);

        FileManagement file = new FileManagement();
        file.writeToFile(obj);

        return obj;
    }

    public static PostRequest fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            if (!_class.equals(obj.get("_class")))
                return null;
            String channel = (String)obj.get("channel");
            String message = (String)obj.get("message");
            String username = (String)obj.get("username");
            return new PostRequest(channel, message, username);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
