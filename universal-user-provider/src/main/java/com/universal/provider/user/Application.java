package com.universal.provider.user;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import com.alibaba.dubbo.config.ApplicationConfig;

@SpringBootApplication
public class Application {

    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        
        final SpringApplication application = new SpringApplication(Application.class);
        
        application.addListeners(new ApplicationListener<ApplicationReadyEvent  >() {
            @Override
            public void onApplicationEvent(ApplicationReadyEvent   event) {
                
                final ConfigurableApplicationContext context = event.getApplicationContext();
                final ApplicationConfig applicationConfig = context.getBean(ApplicationConfig.class);
                logger.info("{} is running ...", applicationConfig.getName());
            }
        });

        final ConfigurableApplicationContext context = application.run(args);
        context.registerShutdownHook();
        
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                context.close();
                countDownLatch.countDown();
            }
        });
        
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("interrupted error", e);
        }
        
        final ApplicationConfig applicationConfig = context.getBean(ApplicationConfig.class);
        logger.info("{} has been stopped.", applicationConfig.getName());
    } 
}
