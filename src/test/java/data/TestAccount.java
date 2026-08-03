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
        Account account = ACCOUNTS.get(name);
        if (account == null) {
            throw new IllegalArgumentException("Unknown test account: " + name);
        }

        String username = firstNonBlank(
                System.getProperty("test.account.username"),
                System.getenv("TEST_ACCOUNT_USERNAME"),
                account.getUsername()
        );
        String password = firstNonBlank(
                System.getProperty("test.account.password"),
                System.getenv("TEST_ACCOUNT_PASSWORD"),
                account.getPassword()
        );

        if (isBlank(username) || isBlank(password)) {
            throw new IllegalStateException(
                    "Account '" + name + "' is missing credentials. "
                            + "Set TEST_ACCOUNT_USERNAME / TEST_ACCOUNT_PASSWORD "
                            + "(or -Dtest.account.username / -Dtest.account.password), "
                            + "or fill src/test/resources/data/accounts.json."
            );
        }

        return new Account(username, password);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (!isBlank(value)) {
                return value;
            }
        }
        return null;
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
