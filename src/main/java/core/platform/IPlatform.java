package core.platform;

import java.net.URI;
import java.util.Map;

import io.appium.java_client.AppiumDriver;

public interface IPlatform {
    AppiumDriver createDriver(
            Map<String, Object> capabilities
    );
}
