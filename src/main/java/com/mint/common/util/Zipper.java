package com.mint.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class Zipper {
    private static final Logger log = LoggerFactory.getLogger(Zipper.class);

    private byte[] buf = new byte[1024];
    private ZipOutputStream zipOut;

    public static void zip(OutputStream out, List<FileEntry> fileEntrys, String encoding) {
        new Zipper(out, fileEntrys, encoding);
    }

    public static void zip(OutputStream out, List<FileEntry> fileEntrys) {
        new Zipper(out, fileEntrys, null);
    }

    protected Zipper(OutputStream out, List<FileEntry> fileEntrys, String encoding) {
        Assert.notEmpty(fileEntrys);
        long begin = System.currentTimeMillis();
        log.debug("开始制作压缩包");
        try {
            try {
                this.zipOut = new ZipOutputStream(out);
                if (!StringUtils.isBlank(encoding)) {
                    log.debug("using encoding: {}", encoding);
                    this.zipOut.setEncoding(encoding);
                } else {
                    log.debug("using default encoding");
                }
                for (FileEntry fe : fileEntrys)
                    zip(fe.getFile(), fe.getFilter(), fe.getZipEntry(), fe
                            .getPrefix());
            } finally {
                this.zipOut.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("制作压缩包时，出现IO异常！", e);
        }
        long end = System.currentTimeMillis();
        log.info("制作压缩包成功。耗时：{}ms。", Long.valueOf(end - begin));
    }

    private void zip(File srcFile, FilenameFilter filter, ZipEntry pentry, String prefix)
            throws IOException {
        if (srcFile.isDirectory()) {
            ZipEntry entry;
            if (pentry == null)
                entry = new ZipEntry(srcFile.getName());
            else {
                entry = new ZipEntry(pentry.getName() + "/" + srcFile.getName());
            }
            File[] files = srcFile.listFiles(filter);
            for (File f : files)
                zip(f, filter, entry, prefix);
        } else {
            ZipEntry entry;
            if (pentry == null)
                entry = new ZipEntry(prefix + srcFile.getName());
            else {
                entry = new ZipEntry(pentry.getName() + "/" + prefix +
                        srcFile.getName());
            }
            try {
                log.debug("读取文件：{}", srcFile.getAbsolutePath());
                FileInputStream in = new FileInputStream(srcFile);
                try {
                    this.zipOut.putNextEntry(entry);
                    int len;
                    while ((len = in.read(this.buf)) > 0) {
                        this.zipOut.write(this.buf, 0, len);
                    }
                    this.zipOut.closeEntry();
                } finally {
                    in.close();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("制作压缩包时，源文件不存在：" +
                        srcFile.getAbsolutePath(), e);
            }
        }
    }

    public static class FileEntry {
        private FilenameFilter filter;
        private String parent;
        private File file;
        private String prefix;

        public FileEntry(String parent, String prefix, File file, FilenameFilter filter) {
            this.parent = parent;
            this.prefix = prefix;
            this.file = file;
            this.filter = filter;
        }

        public FileEntry(String parent, File file) {
            this.parent = parent;
            this.file = file;
        }

        public FileEntry(String parent, String prefix, File file) {
            this(parent, prefix, file, null);
        }

        public ZipEntry getZipEntry() {
            if (StringUtils.isBlank(this.parent)) {
                return null;
            }
            return new ZipEntry(this.parent);
        }

        public FilenameFilter getFilter() {
            return this.filter;
        }

        public void setFilter(FilenameFilter filter) {
            this.filter = filter;
        }

        public String getParent() {
            return this.parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public File getFile() {
            return this.file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getPrefix() {
            if (this.prefix == null) {
                return "";
            }
            return this.prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}

