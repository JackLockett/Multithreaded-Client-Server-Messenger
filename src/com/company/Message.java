package com.company;

import org.json.simple.*;

public class Message {
    private static final String _class = Message.class.getSimpleName();

    private final String body;
    private final String author;
    private final long   timestamp;

    public Message(String body, String author, long timestamp) {
        if (body == null || author == null)
            throw new NullPointerException();
        this.body      = body;
        this.author    = author;
        this.timestamp = timestamp;
    }

    public String getBody()      { return body; }
    public String getAuthor()    { return author; }
    public long   getTimestamp() { return timestamp; }

    public String toString() {
        return author + ": " + body + " (" + timestamp + ")";
    }

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class",    _class);
        obj.put("body",      body);
        obj.put("author",    author);
        obj.put("timestamp", timestamp);
        return obj;
    }

    public static Message fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            if (!_class.equals(obj.get("_class")))
                return null;
            String body      = (String)obj.get("body");
            String author    = (String)obj.get("author");
            long   timestamp = (long)obj.get("timestamp");
            return new Message(body, author, timestamp);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
