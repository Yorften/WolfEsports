package util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceUtil {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceUtil.class);
    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            Map<String, Object> properties = new HashMap<>();

            String url = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : "";
            String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "";
            String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "";

            if (url == null || user == null || password == null || url.isEmpty() || user.isEmpty()) {
                logger.error("One or more required environment variables are missing.");
                throw new IllegalStateException("Database configuration is incomplete.");
            }

            String persistenceUnitName = System.getProperty("persistence.unit.name", "WOLFESPORTS_PU");

            if (persistenceUnitName.equals("test_WOLFESPORTS_PU"))
                url = url + "_test";

            if (persistenceUnitName.equals("H2_WOLFESPORTS_PU")){
                url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=LEGACY";
                user = "sa";
                password = "";
            }
            properties.put("javax.persistence.jdbc.url", url);
            properties.put("javax.persistence.jdbc.user", user);
            properties.put("javax.persistence.jdbc.password", password);

            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
        } catch (Throwable ex) {
            logger.error("Initial EntityManagerFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
