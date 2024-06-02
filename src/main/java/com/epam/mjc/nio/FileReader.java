package com.epam.mjc.nio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; // Import Log4j Logger

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader {
    private static final Logger logger = LogManager.getLogger(FileReader.class);

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        Profile profile = fileReader.getDataFromFile(new File("src/main/resources/Profile.txt"));
        logger.info(profile);
    }

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];
                    switch (key) {
                        case "Name":
                            profile.setName(value);
                            break;
                        case "Age":
                            try {
                                profile.setAge(Integer.parseInt(value));
                            } catch (NumberFormatException e) {
                                logger.error("Error parsing age: {}", e.getMessage());
                            }
                            break;
                        case "Email":
                            profile.setEmail(value);
                            break;
                        case "Phone":
                            try {
                                profile.setPhone(Long.parseLong(value));
                            } catch (NumberFormatException e) {
                                logger.error("Error parsing phone number: {}", e.getMessage());
                            }
                            break;
                        default:
                            logger.warn("Unknown key: {}", key);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage());
        }
        return profile;
    }
}