package core.platform;

import java.net.URI;
import java.util.Map;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

@PlatformType("android")
public class Android implements IPlatform {

    @Override
    public AppiumDriver createDriver(Map<String, Object> capabilities) {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setAutomationName("UiAutomator2");

        capabilities.forEach(options::setCapability);

        try {
            return new AndroidDriver(
                    URI.create("http://127.0.0.1:4723").toURL(),
                    options
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
