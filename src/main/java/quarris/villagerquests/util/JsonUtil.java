package quarris.villagerquests.util;

import com.google.gson.JsonObject;

public class JsonUtil {

    public static int get(JsonObject object, String name, int def) {
        return object.has(name) ? object.get(name).getAsInt() : def;
    }

    public static boolean get(JsonObject object, String name, boolean def) {
        return object.has(name) ? object.get(name).getAsBoolean() : def;
    }

    public static float get(JsonObject object, String name, float def) {
        return object.has(name) ? object.get(name).getAsFloat() : def;
    }

    public static String get(JsonObject object, String name, String def) {
        return object.has(name) ? object.get(name).getAsString() : def;
    }
}
