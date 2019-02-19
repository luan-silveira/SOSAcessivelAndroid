package br.com.luansilveira.sosacessvel.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSON {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static JSONObject parseObj(Object obj){
        Class cls = obj.getClass();
        JSONObject json = new JSONObject();

        for (Field campo: cls.getDeclaredFields()) {
            Boolean isAcessivel = campo.isAccessible();
            if (!isAcessivel) campo.setAccessible(true);

            try {
                Object value = campo.get(obj);
                if (value instanceof Date) value = format.format(value);
                json.put(campo.getName(), value);
            } catch (JSONException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (!isAcessivel) campo.setAccessible(false);
        }

        return json;
    }
}
