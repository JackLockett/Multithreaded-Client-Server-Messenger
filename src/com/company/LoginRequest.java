package com.company;

import org.json.simple.*;

import java.io.FileWriter;
import java.io.IOException;

public class LoginRequest extends Request {

    private static final String _class = LoginRequest.class.getSimpleName();
    private final String name;

    public LoginRequest(String name) {
        if (name == null)
            throw new NullPointerException();
        this.name = name;
    }

    String getName() { return name; }

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        obj.put("name", name);

        FileManagement file = new FileManagement();
        file.writeToFile(obj);

        return obj;
    }

    public static LoginRequest fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            if (!_class.equals(obj.get("_class")))
                return null;
            String name = (String)obj.get("name");
            return new LoginRequest(name);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
