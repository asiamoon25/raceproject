package com.rc.raceproject.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LogDirInitializer {
    @PostConstruct
    public void initialize() {
        String logDir = System.getProperty("LOG_DIR");
        File dir = new File(logDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }
}
