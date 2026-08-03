package core.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigurationUtils {
    private static final String DEFAULT_ENV = "dev";
    private static final Properties properties = new Properties();

    static {
        loadProperties("config.properties");
        String environment = System.getProperty("env", DEFAULT_ENV);
        loadPropertiesIfExists("config-" + environment + ".properties");
    }

    private ConfigurationUtils() {}

    private static void loadProperties(String fileName) {

        try (InputStream input = ConfigurationUtils.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException(fileName + " not found.");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Unable to load " + fileName, e);
        }
    }

    private static void loadPropertiesIfExists(String fileName) {

        try (InputStream input = ConfigurationUtils.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load " + fileName, e);
        }
    }

    public static String get(String key) {

        // JVM property has highest priority
        String systemValue = System.getProperty(key);

        if (systemValue != null) {
            return systemValue;
        }

        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {

        String value = get(key);

        return value == null ? defaultValue : value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static long getLong(String key) {
        return Long.parseLong(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static double getDouble(String key) {
        return Double.parseDouble(get(key));
    }
}
