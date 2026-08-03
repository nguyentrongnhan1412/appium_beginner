package core.platform;

import java.net.URI;
import java.util.Map;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

@PlatformType("ios")
public class Ios implements IPlatform {

    @Override
    public AppiumDriver createDriver(Map<String, Object> capabilities) {
        XCUITestOptions options =
                new XCUITestOptions();

        options.setAutomationName("XCUITest");

        capabilities.forEach(options::setCapability);

        try {
            return new IOSDriver(
                    URI.create("http://127.0.0.1:4723").toURL(),
                    options
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
