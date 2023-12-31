package com.company;

import org.json.simple.*;

public class QuitRequest extends Request {

    private static final String _class = QuitRequest.class.getSimpleName();

    public QuitRequest() {}

    @SuppressWarnings("unchecked")
    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("_class", _class);

        FileManagement file = new FileManagement();
        file.writeToFile(obj);

        return obj;
    }

    public static QuitRequest fromJSON(Object val) {
        try {
            JSONObject obj = (JSONObject)val;
            if (!_class.equals(obj.get("_class")))
                return null;
            return new QuitRequest();
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }
}
