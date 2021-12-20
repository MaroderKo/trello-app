package spd.trello;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {
    public static Properties loadProperties() throws IOException {
        InputStream in = Util.class.getClassLoader()
                .getResourceAsStream("application.properties");
        Properties properties = new Properties();
        properties.load(in);

        return properties;
    }
}
