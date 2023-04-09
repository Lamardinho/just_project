package com.example.just_project.common.util;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@Log4j2
public class AppStartedLogInfo {

    @EventListener(ApplicationReadyEvent.class)
    public void appStartedInfo() {
        printMessage();
    }

    private static void printMessage() {
        val str = "                                                                \n" +
                " _____ _____ _____    _____ _____ _____ _____ _____ _____ ____  \n" +
                "|  _  |  _  |  _  |  |   __|_   _|  _  | __  |_   _|   __|    \\ \n" +
                "|     |   __|   __|  |__   | | | |     |    -| | | |   __|  |  |\n" +
                "|__|__|__|  |__|     |_____| |_| |__|__|__|__| |_| |_____|____/ \n" +
                "                                                                ";

        log.info(str);
    }
}
