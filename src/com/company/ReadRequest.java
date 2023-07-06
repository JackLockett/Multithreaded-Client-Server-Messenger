package com.company;

import org.json.simple.*;

public class ReadRequest extends Request {

    private static final String _class = ReadRequest.class.getSimpleName();

    public ReadRequest() {}

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);
        return obj;
    }

    public static ReadRequest fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            if (!_class.equals(obj.get("_class")))
                return null;
            return new ReadRequest();
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
