package data;

import java.io.File;
import java.util.Map;

import constants.DataPath;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Account;

public class TestAccount {
    private static final Map<String, Account> ACCOUNTS;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();

            ACCOUNTS = mapper.readValue(
                    new File(DataPath.ACCOUNTS_PATH),
                    new TypeReference<Map<String, Account>>() {}
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to load test accounts", e);
        }
    }

    public static Account get(String name) {
        return ACCOUNTS.get(name);
    }
}
