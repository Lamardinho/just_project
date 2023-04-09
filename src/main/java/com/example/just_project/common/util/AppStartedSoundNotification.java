package com.example.just_project.common.util;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

@Profile("local")
@Component
@Log4j2
public class AppStartedSoundNotification {

    @Value("classpath:appfiles/appstarted.wav")
    private Resource appStartedAudioResource;

    @EventListener(ApplicationReadyEvent.class)
    public void playSound() {
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
