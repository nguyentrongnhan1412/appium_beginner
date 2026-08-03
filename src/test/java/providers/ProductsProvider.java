package providers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import constants.DataPath;
import models.Product;

public class ProductsProvider {
    @DataProvider(name = "products")
    public Object[][] products() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        Product[] data = mapper.readValue(
                new File(DataPath.PRODUCTS_PATH),
                Product[].class
        );

        return new Object[][]{{Arrays.asList(data)}};
    }
}
