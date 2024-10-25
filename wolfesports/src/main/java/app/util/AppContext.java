package app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext {
    private static final Logger logger = LoggerFactory.getLogger(AppContext.class);

    private static ApplicationContext applicationContext;

    static {
        try {
            applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        } catch (Exception e) {
            logger.error("Error creating application context", e);
        }
    }

    public static ApplicationContext getAppContext() {
        return applicationContext;
    }
}
