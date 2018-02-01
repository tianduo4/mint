package com.jspgou.common.fck;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesLoader {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
    private static final String DEFAULT_FILENAME = "default.properties";
    private static final String LOCAL_PROPERTIES = "/fckeditor.properties";
    private static Properties properties = new Properties();

    static {
        InputStream in = PropertiesLoader.class
                .getResourceAsStream("default.properties");

        if (in == null) {
            logger.error("{} not found", "default.properties");
            throw new RuntimeException("default.properties not found");
        }
        if (!(in instanceof BufferedInputStream))
            in = new BufferedInputStream(in);
        try {
            properties.load(in);
            in.close();
            logger.debug("{} loaded", "default.properties");
        } catch (Exception e) {
            logger.error("Error while processing {}", "default.properties");
            throw new RuntimeException("Error while processing default.properties",
                    e);
        }

        InputStream in2 = PropertiesLoader.class
                .getResourceAsStream("/fckeditor.properties");

        if (in2 == null) {
            logger.info("{} not found", "/fckeditor.properties");
        } else {
            if (!(in2 instanceof BufferedInputStream))
                in2 = new BufferedInputStream(in2);
            try {
                properties.load(in2);
                in2.close();
                logger.debug("{} loaded", "/fckeditor.properties");
            } catch (Exception e) {
                logger.error("Error while processing {}", "/fckeditor.properties");
                throw new RuntimeException("Error while processing /fckeditor.properties",
                        e);
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public static String getFileResourceTypePath() {
        return properties.getProperty("connector.resourceType.file.path");
    }

    public static String getFlashResourceTypePath() {
        return properties.getProperty("connector.resourceType.flash.path");
    }

    public static String getImageResourceTypePath() {
        return properties.getProperty("connector.resourceType.image.path");
    }

    public static String getMediaResourceTypePath() {
        return properties.getProperty("connector.resourceType.media.path");
    }

    public static String getFileResourceTypeAllowedExtensions() {
        return properties
                .getProperty("connector.resourceType.file.extensions.allowed");
    }

    public static String getFileResourceTypeDeniedExtensions() {
        return properties
                .getProperty("connector.resourceType.file.extensions.denied");
    }

    public static String getFlashResourceTypeAllowedExtensions() {
        return properties
                .getProperty("connector.resourceType.flash.extensions.allowed");
    }

    public static String getFlashResourceTypeDeniedExtensions() {
        return properties
                .getProperty("connector.resourceType.flash.extensions.denied");
    }

    public static String getImageResourceTypeAllowedExtensions() {
        return properties
                .getProperty("connector.resourceType.image.extensions.allowed");
    }

    public static String getImageResourceTypeDeniedExtensions() {
        return properties
                .getProperty("connector.resourceType.image.extensions.denied");
    }

    public static String getMediaResourceTypeAllowedExtensions() {
        return properties
                .getProperty("connector.resourceType.media.extensions.allowed");
    }

    public static String getMediaResourceTypeDeniedExtensions() {
        return properties
                .getProperty("connector.resourceType.media.extensions.denied");
    }

    public static String getUserFilesPath() {
        return properties.getProperty("connector.userFilesPath");
    }

    public static String getUserFilesAbsolutePath() {
        return properties.getProperty("connector.userFilesAbsolutePath");
    }
}

