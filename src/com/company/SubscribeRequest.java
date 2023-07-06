package com.company;

import org.json.simple.*;

public class SubscribeRequest extends Request {

    private static final String _class = SubscribeRequest.class.getSimpleName();

    private String channel;
    private String username;

    public SubscribeRequest(String channel, String username) {
        if (channel == null || username == null)
            throw new NullPointerException();
        this.channel = channel;
        this.username = username;
    }

    String getChannel() { return channel; }
    String getUsername() { return username; }

    // Serializes this object into a JSONObject
    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("channel", channel);
        obj.put("username", username);

        FileManagement file = new FileManagement();
        file.writeToFile(obj);

        return obj;
    }

    public static SubscribeRequest fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            if (!_class.equals(obj.get("_class")))
                return null;
            String channel = (String)obj.get("channel");
            String username = (String)obj.get("username");
            return new SubscribeRequest(channel, username);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
