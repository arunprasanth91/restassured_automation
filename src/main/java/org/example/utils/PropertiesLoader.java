package org.example.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {


    public static String getProperty(String key) throws FileNotFoundException {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/main/resources/maps.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop.getProperty(key);
    }
}
