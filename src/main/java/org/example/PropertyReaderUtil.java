package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReaderUtil {

    private static  Properties properties;

    private static void loadProperties(String filePath){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getProperty(String key,String defaultValue){
        if(!properties.isEmpty()){
            return properties.getProperty(key);
        }
        return defaultValue;
    }
}
