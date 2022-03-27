package lab2.practice;

import java.util.HashMap;
import java.util.Map;

public class PropertyRepository {
    private final static Map<String, String> map = new HashMap<>() {
        {
            put("user", "tolik");
            put("password", "*****");
            put("url", "www.github.com/login");
            put("message", "miraculous");
        }
    };

    public static String get(String key) {
        return map.get(key);
    }
}
