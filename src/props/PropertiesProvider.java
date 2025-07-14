package props;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesProvider {
    public static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(new FileInputStream("database.properties"));
        } catch (IOException e) {
            throw new RuntimeException("ERROR, didn't find the properties😓" + e.getMessage());
        }
    }
}
