package providers;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import constants.DataPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Credentials;

public class CredentialsProvider {
    @DataProvider(name = "credentials")
    public Object[][] credentials(Method method) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        Credentials[] data = mapper.readValue(
                new File(DataPath.CREDENTIALS_PATH),
                Credentials[].class
        );

        return Arrays.stream(data)
                .filter(d -> d.getScenario()
                        .equals(method.getName()))
                .map(d -> new Object[]{d})
                .toArray(Object[][]::new);
    }
}
