package com.jspgou.common.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class AverageImageScale {
    public static void resizeFix(File srcFile, File destFile, int boxWidth, int boxHeight)
            throws IOException {
        try {
            BufferedImage srcImgBuff = ImageIO.read(srcFile);
            int width = srcImgBuff.getWidth();
            int height = srcImgBuff.getHeight();
            if ((width <= boxWidth) && (height <= boxHeight)) {
                FileUtils.copyFile(srcFile, destFile);
                return;
            }
            int zoomWidth;
            int zoomHeight;
            if (width / height > boxWidth / boxHeight) {
                zoomWidth = boxWidth;
                zoomHeight = Math.round(boxWidth * height / width);
            } else {
                zoomWidth = Math.round(boxHeight * width / height);
                zoomHeight = boxHeight;
            }
            BufferedImage imgBuff = scaleImage(srcImgBuff, width, height,
                    zoomWidth, zoomHeight);
            writeFile(imgBuff, destFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void resizeFix(File file, File file1, int i, int j, int k, int l, int i1, int j1) throws IOException {
        try {
            BufferedImage bufferedimage = ImageIO.read(file);
            bufferedimage = bufferedimage.getSubimage(k, l, i1, j1);
            int k1 = bufferedimage.getWidth();
            int l1 = bufferedimage.getHeight();
            if ((k1 <= i) && (l1 <= j)) {
                writeFile(bufferedimage, file1);
                return;
            }
            int j2;
            int i2;
            if (k1 / l1 > i / j) {
                i2 = i;
                j2 = Math.round(i * l1 / k1);
            } else {
                i2 = Math.round(j * k1 / l1);
                j2 = j;
            }
            BufferedImage bufferedimage1 = scaleImage(bufferedimage, k1, l1, i2, j2);
            writeFile(bufferedimage1, file1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeFile(BufferedImage imgBuf, File destFile) throws IOException {
        File parent = destFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        ImageIO.write(imgBuf, "jpeg", destFile);
    }

    private static BufferedImage scaleImage(BufferedImage srcImgBuff, int width, int height, int zoomWidth, int zoomHeight) {
        int[] colorArray = srcImgBuff.getRGB(0, 0, width, height, null, 0,
                width);
        BufferedImage outBuff = new BufferedImage(zoomWidth, zoomHeight,
                1);

        float wScale = width / zoomWidth;
        int wScaleInt = (int) (wScale + 0.5D);

        float hScale = height / zoomHeight;
        int hScaleInt = (int) (hScale + 0.5D);
        int area = wScaleInt * hScaleInt;

        for (int y = 0; y < zoomHeight; y++) {
            int y0 = (int) (y * hScale);
            int y1 = y0 + hScaleInt;
            for (int x = 0; x < zoomWidth; x++) {
                int x0 = (int) (x * wScale);
                int x1 = x0 + wScaleInt;
                long blue;
                long green;
                long red = green = blue = 0L;
                for (int i = x0; i < x1; i++) {
                    for (int j = y0; j < y1; j++) {
                        int color = colorArray[(width * j + i)];
                        red += getRedValue(color);
                        green += getGreenValue(color);
                        blue += getBlueValue(color);
                    }
                }
                outBuff.setRGB(x, y, comRGB((int) (red / area),
                        (int) (green / area), (int) (blue / area)));
            }
        }
        return outBuff;
    }

    private static int getRedValue(int rgbValue) {
        return (rgbValue & 0xFF0000) >> 16;
    }

    private static int getGreenValue(int rgbValue) {
        return (rgbValue & 0xFF00) >> 8;
    }

    private static int getBlueValue(int rgbValue) {
        return rgbValue & 0xFF;
    }

    private static int comRGB(int redValue, int greenValue, int blueValue) {
        return (redValue << 16) + (greenValue << 8) + blueValue;
    }

    public static void main(String[] args) throws Exception {
        long time = System.currentTimeMillis();
        resizeFix(new File("test/com/jeecms/common/util/1.bmp"),
                new File("test/com/jeecms/common/util/1-n-2.bmp"), 310, 310, 50, 50,
                320, 320);
        time = System.currentTimeMillis() - time;
        System.out.println("resize2 img in " + time + "ms");
    }
}

