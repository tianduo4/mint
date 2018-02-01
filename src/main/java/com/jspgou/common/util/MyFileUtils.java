package com.jspgou.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyFileUtils {
    public static List<File> getFiles(File folder) {
        List files = new ArrayList();
        iterateFolder(folder, files);
        return files;
    }

    private static void iterateFolder(File folder, List<File> files) {
        File[] flist = folder.listFiles();
        files.add(folder);
        if ((flist == null) || (flist.length == 0))
            files.add(folder);
        else
            for (File f : flist)
                if (f.isDirectory())
                    iterateFolder(f, files);
                else
                    files.add(f);
    }
}

