package lab2.practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyReader {
    private final Properties properties;

    public PropertyReader(String url) {
        properties = new Properties();
        loadProperties(url);
    }

    public Properties getProperties() {
        return new Properties(properties);
    }

    private void loadProperties(String url) {
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(url);
             InputStreamReader fileReader = new InputStreamReader(resourceAsStream)) {
            properties.load(fileReader);
        } catch (IOException e) {
            throw new RuntimeException("Can't establish connection by url :" + url, e);
        }
    }
}