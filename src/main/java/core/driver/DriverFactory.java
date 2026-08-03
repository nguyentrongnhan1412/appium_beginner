package core.driver;

import java.util.Map;

import core.platform.IPlatform;
import core.platform.PlatformFactory;
import io.appium.java_client.AppiumDriver;

public class DriverFactory {
    public static AppiumDriver createDriver(
            String platform,
            Map<String, Object> capabilities) {

        IPlatform platformDriver = PlatformFactory.create(platform);

        return platformDriver.createDriver(capabilities);
    }
}
