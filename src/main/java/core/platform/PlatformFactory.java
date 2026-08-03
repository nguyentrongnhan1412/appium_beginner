package core.platform;

import org.reflections.Reflections;

import java.util.Set;

public class PlatformFactory {
    private static final Reflections reflections = new Reflections("core.platform");
    public static IPlatform create(String platformName) {
        Set<Class<?>> platformClasses = reflections.getTypesAnnotatedWith(PlatformType.class);
        for (Class<?> clazz : platformClasses) {
            PlatformType annotation =
                    clazz.getAnnotation(
                            PlatformType.class
                    );
            if (annotation.value()
                    .equalsIgnoreCase(platformName)) {
                try {
                    return (IPlatform)
                            clazz.getDeclaredConstructor()
                                    .newInstance();

                } catch (Exception e) {

                    throw new RuntimeException(
                            "Cannot instantiate platform: "
                                    + clazz.getName(), e
                    );
                }
            }
        }
        throw new IllegalArgumentException(
                "Unsupported platform: "
                        + platformName
        );
    }
}
