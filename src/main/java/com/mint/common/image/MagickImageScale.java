package com.mint.common.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import magick.DrawInfo;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;
import org.apache.commons.io.FileUtils;

public class MagickImageScale {
    public static void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight)
            throws IOException, MagickException {
        ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
        MagickImage image = new MagickImage(info);

        Dimension dim = image.getDimension();
        int width = (int) dim.getWidth();
        int height = (int) dim.getHeight();
        int zoomHeight;
        int zoomWidth;
        if (width / height > boxWidth / boxHeight) {
            zoomWidth = boxWidth;
            zoomHeight = Math.round(boxWidth * height / width);
        } else {
            zoomWidth = Math.round(boxHeight * width / height);
            zoomHeight = boxHeight;
        }

        MagickImage scaled = image.scaleImage(zoomWidth, zoomHeight);

        scaled.setFileName(destFile.getAbsolutePath());
        scaled.writeImage(info);
        scaled.destroyImages();
    }

    public static void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight, int cutTop, int cutLeft, int cutWidth, int catHeight)
            throws IOException, MagickException {
        ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
        MagickImage image = new MagickImage(info);

        Rectangle rect = new Rectangle(cutTop, cutLeft, cutWidth, catHeight);

        MagickImage cropped = image.cropImage(rect);
        Dimension dim = cropped.getDimension();
        int width = (int) dim.getWidth();
        int height = (int) dim.getHeight();
        int zoomWidth;
        int zoomHeight;
        if (width / height > boxWidth / boxHeight) {
            zoomWidth = boxWidth;
            zoomHeight = Math.round(boxWidth * height / width);
        } else {
            zoomWidth = Math.round(boxHeight * width / height);
            zoomHeight = boxHeight;
        }

        MagickImage scaled = cropped.scaleImage(zoomWidth, zoomHeight);

        scaled.setFileName(destFile.getAbsolutePath());
        scaled.writeImage(info);
        scaled.destroyImages();
    }

    public static void imageMark(File srcFile, File destFile, int minWidth, int minHeight, int pos, int offsetX, int offsetY, String markContent, Color markColor, int markSize, int alpha)
            throws IOException, MagickException {
        ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
        MagickImage image = new MagickImage(info);

        Dimension dim = image.getDimension();
        int width = (int) dim.getWidth();
        int height = (int) dim.getHeight();
        if ((width < minWidth) || (height < minHeight)) {
            image.destroyImages();
            if (!srcFile.equals(destFile))
                FileUtils.copyFile(srcFile, destFile);
        } else {
            imageMark(image, info, width, height, pos, offsetX, offsetY,
                    markContent, markColor, markSize, alpha);
            image.setFileName(destFile.getAbsolutePath());
            image.writeImage(info);
            image.destroyImages();
        }
    }

    public static void imageMark(File srcFile, File destFile, int minWidth, int minHeight, int pos, int offsetX, int offsetY, File markFile)
            throws IOException, MagickException {
        ImageInfo info = new ImageInfo(srcFile.getAbsolutePath());
        MagickImage image = new MagickImage(info);

        Dimension dim = image.getDimension();
        int width = (int) dim.getWidth();
        int height = (int) dim.getHeight();
        if ((width < minWidth) || (height < minHeight)) {
            image.destroyImages();
            if (!srcFile.equals(destFile))
                FileUtils.copyFile(srcFile, destFile);
        } else {
            imageMark(image, info, width, height, pos, offsetX, offsetY,
                    markFile);
            image.setFileName(destFile.getAbsolutePath());
            image.writeImage(info);
            image.destroyImages();
        }
    }

    private static void imageMark(MagickImage image, ImageInfo info, int width, int height, int pos, int offsetX, int offsetY, String text, Color color, int size, int alpha)
            throws MagickException {
        ImageUtils.Position p = ImageUtils.markPosition(width, height, pos, offsetX,
                offsetY);
        DrawInfo draw = new DrawInfo(info);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        draw.setFill(
                new PixelPacket(r * r, g * g, b * b,
                        65535 - alpha * 65535 / 100));
        draw.setPointsize(size);
        draw.setTextAntialias(true);
        draw.setText(text);
        draw.setGeometry("+" + p.getX() + "+" + p.getY());
        image.annotateImage(draw);
    }

    private static void imageMark(MagickImage image, ImageInfo info, int width, int height, int pos, int offsetX, int offsetY, File markFile)
            throws MagickException {
        ImageUtils.Position p = ImageUtils.markPosition(width, height, pos, offsetX,
                offsetY);
        MagickImage mark = new MagickImage(
                new ImageInfo(markFile
                        .getAbsolutePath()));
        image.compositeImage(3, mark, p.getX(),
                p.getY());
        mark.destroyImages();
    }

    public static void main(String[] args) throws Exception {
        long time = System.currentTimeMillis();
        resizeFix(
                new File("test/com/jeecms/common/util/1.bmp"),
                new File("test/com/jeecms/common/util/1-n-3.bmp"), 310, 310, 50,
                50, 320, 320);
        time = System.currentTimeMillis() - time;
        System.out.println("resize new img in " + time + "ms");
    }
}

