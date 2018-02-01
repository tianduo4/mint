package com.mint.common.file;

import com.mint.common.image.ImageUtils;
import com.mint.common.util.Num62;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;

public class FileNameUtils {
    public static final DateFormat pathDf = new SimpleDateFormat("yyyyMM");

    public static final DateFormat nameDf = new SimpleDateFormat("ddHHmmss");

    public static String genPathName() {
        return pathDf.format(new Date());
    }

    public static String genFileName() {
        return nameDf.format(new Date()) +
                RandomStringUtils.random(4, Num62.N36_CHARS);
    }

    public static String genFileName(String ext) {
        return genFileName() + "." + ext;
    }

    public static String getFileSufix(String fileName) {
        boolean normalImg = false;
        for (String imgExt : ImageUtils.IMAGE_EXT) {
            if (fileName.endsWith(imgExt)) {
                normalImg = true;
            }
        }
        String suffix = "";
        if (normalImg) {
            int splitIndex = fileName.lastIndexOf(".");
            suffix = fileName.substring(splitIndex + 1);
        } else {
            suffix = ImageUtils.IMAGE_EXT[0];
        }
        return suffix;
    }

    public static void main(String[] args) {
        System.out.println(genPathName());
        System.out.println(genFileName());
    }
}

