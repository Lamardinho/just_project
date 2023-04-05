package com.example.just_project.common.util;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

@Configuration
@Log4j2
public class AppStartedLogInfo {

    @Value("classpath:appfiles/appstarted.wav")
    private Resource appStartedAudioResource;

    @EventListener(ApplicationReadyEvent.class)
    public void appStartedInfo() {
        printMessage();
        playSound();
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

    private void playSound() {
        try {
            val file = appStartedAudioResource.getFile();
            val audioStream = AudioSystem.getAudioInputStream(file);
            val format = audioStream.getFormat();
            val info = new DataLine.Info(Clip.class, format);
            val clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            log.error("Error playing sound: " + e.getMessage());
        }
    }
}
