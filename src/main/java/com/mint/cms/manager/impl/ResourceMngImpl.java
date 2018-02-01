package com.mint.cms.manager.impl;

import com.mint.cms.entity.ShopPlug;
import com.mint.cms.manager.ResourceMng;
import com.mint.cms.manager.ShopPlugMng;
import com.mint.common.file.FileWrap;
import com.mint.common.util.Zipper;
import com.mint.common.web.springmvc.RealPathResolver;
import com.mint.core.entity.Website;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResourceMngImpl
        implements ResourceMng {
    private static final String PLUG_PERMS = "plug.perms";
    private static final Logger log = LoggerFactory.getLogger(ResourceMngImpl.class);

    private FileFilter filter = new FileFilter() {
        public boolean accept(File file) {
            return (file.isDirectory()) || (FileWrap.editableExt(FilenameUtils.getExtension(file.getName())));
        }
    };
    private RealPathResolver realPathResolver;

    @Autowired
    private ShopPlugMng plugMng;

    public List<FileWrap> listFile(String path, boolean dirAndEditable) {
        File parent = new File(this.realPathResolver.get(path));
        if (parent.exists()) {
            File[] files;
            if (dirAndEditable)
                files = parent.listFiles(this.filter);
            else {
                files = parent.listFiles();
            }
            Arrays.sort(files, new FileWrap.FileComparator());
            List list = new ArrayList(files.length);
            for (File f : files) {
                list.add(new FileWrap(f, this.realPathResolver.get("")));
            }
            return list;
        }
        return new ArrayList(0);
    }

    public boolean createDir(String path, String dirName) {
        File parent = new File(this.realPathResolver.get(path));
        parent.mkdirs();
        File dir = new File(parent, dirName);
        return dir.mkdir();
    }

    public void saveFile(String path, MultipartFile file)
            throws IllegalStateException, IOException {
        File dest = new File(this.realPathResolver.get(path), file
                .getOriginalFilename());
        file.transferTo(dest);
    }

    public void createFile(String path, String filename, String data)
            throws IOException {
        File parent = new File(this.realPathResolver.get(path));
        parent.mkdirs();
        File file = new File(parent, filename);
        FileUtils.writeStringToFile(file, data, "UTF-8");
    }

    public String readFile(String name) throws IOException {
        File file = new File(this.realPathResolver.get(name));
        return FileUtils.readFileToString(file, "UTF-8");
    }

    public void updateFile(String name, String data) throws IOException {
        File file = new File(this.realPathResolver.get(name));
        FileUtils.writeStringToFile(file, data, "UTF-8");
    }

    public int delete(String[] names) {
        int count = 0;

        for (String name : names) {
            File f = new File(this.realPathResolver.get(name));
            if (FileUtils.deleteQuietly(f)) {
                count++;
            }
        }
        return count;
    }

    public void rename(String origName, String destName) {
        File orig = new File(this.realPathResolver.get(origName));
        File dest = new File(this.realPathResolver.get(destName));
        orig.renameTo(dest);
    }

    public String[] getSolutions(String path) {
        File tpl = new File(this.realPathResolver.get(path));
        return tpl.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return dir.isDirectory();
            }
        });
    }

    public List<Zipper.FileEntry> export(Website site, String solution) {
        List fileEntrys = new ArrayList();
        File tpl = new File(this.realPathResolver.get(site.getTplPath()), solution);
        fileEntrys.add(new Zipper.FileEntry("", "", tpl));
        File res = new File(this.realPathResolver.get("/r/gou/www"));
        if (res.exists()) {
            for (File r : res.listFiles()) {
                fileEntrys.add(new Zipper.FileEntry("${res}", r));
            }
        }
        return fileEntrys;
    }

    public void imoport(File file, Website site)
            throws IOException {
        String resRoot = "/r/gou/www";
        String tplRoot = "/WEB-INF/t/gou";

        ZipFile zip = new ZipFile(file, "GBK");

        byte[] buf = new byte[1024];

        InputStream is = null;
        OutputStream os = null;
        String solution = null;

        Enumeration en = zip.getEntries();
        while (en.hasMoreElements()) {
            String name = ((ZipEntry) en.nextElement()).getName();
            if (!name.startsWith("${res}")) {
                solution = name.substring(0, name.indexOf("/"));
                break;
            }
        }
        if (solution == null) {
            return;
        }
        en = zip.getEntries();
        while (en.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) en.nextElement();
            if (!entry.isDirectory()) {
                String name = entry.getName();
                log.debug("unzip file：{}", name);
                String filename;
                if (name.startsWith("${res}"))
                    filename = resRoot + name.substring("${res}".length());
                else {
                    filename = tplRoot + "/" + name;
                }
                log.debug("解压地址：{}", filename);
                File outFile = new File(this.realPathResolver.get(filename));
                File pfile = outFile.getParentFile();
                if (!pfile.exists())
                    pfile.mkdirs();
                int len;
                try {
                    is = zip.getInputStream(entry);
                    os = new FileOutputStream(outFile);
                    while ((len = is.read(buf)) != -1) {
                        os.write(buf, 0, len);
                    }
                } finally {
                    if (is != null) {
                        is.close();
                        is = null;
                    }
                    if (os != null) {
                        os.close();
                        os = null;
                    }
                }
            }
        }
    }

    @Autowired
    public void setRealPathResolver(RealPathResolver realPathResolver) {
        this.realPathResolver = realPathResolver;
    }

    private void createFolder(File f) {
        File parent = f.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
            createFolder(parent);
        }
    }

    public void unZipFile(File file)
            throws IOException {
        ZipFile zip = new ZipFile(file, "GBK");

        byte[] buf = new byte[1024];

        InputStream is = null;
        OutputStream os = null;
        Enumeration en = zip.getEntries();
        while (en.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) en.nextElement();
            String name = entry.getName();
            if (!entry.isDirectory()) {
                name = entry.getName();
                String filename = name;
                File outFile = new File(this.realPathResolver.get(filename));
                if (outFile.exists()) {
                    break;
                }
                File pfile = outFile.getParentFile();
                if (!pfile.exists()) {
                    createFolder(outFile);
                }
                int len;
                try {
                    is = zip.getInputStream(entry);
                    os = new FileOutputStream(outFile);
                    while ((len = is.read(buf)) != -1) {
                        os.write(buf, 0, len);
                    }
                } finally {
                    if (is != null) {
                        is.close();
                        is = null;
                    }
                    if (os != null) {
                        os.close();
                        os = null;
                    }
                }
            }
        }
        zip.close();
    }

    private boolean directoryHasFile(File directory) {
        File[] files = directory.listFiles();
        if ((files != null) && (files.length > 0)) {
            for (File f : files) {
                if (directoryHasFile(f)) {
                    return true;
                }

            }

            return false;
        }
        return false;
    }

    public void deleteZipFile(File file)
            throws IOException {
        ZipFile zip = new ZipFile(file, "GBK");

        Enumeration en = zip.getEntries();
        while (en.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) en.nextElement();
            if (!entry.isDirectory()) {
                String name = entry.getName();
                String filename = name;
                File directory = new File(this.realPathResolver.get(filename));
                if (directory.exists()) {
                    directory.delete();
                }
            }
        }

        en = zip.getEntries();
        while (en.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) en.nextElement();
            if (entry.isDirectory()) {
                String name = entry.getName();
                String filename = name;
                File directory = new File(this.realPathResolver.get(filename));
                if (!directoryHasFile(directory)) {
                    directory.delete();
                }
            }
        }
        zip.close();
    }

    public String readFileFromZip(File file, String readFileName)
            throws IOException {
        ZipFile zip = new ZipFile(file, "GBK");

        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader in = null;
        Enumeration en = zip.getEntries();
        StringBuffer buff = new StringBuffer();

        while (en.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) en.nextElement();
            String name = entry.getName();
            if (!entry.isDirectory()) {
                name = entry.getName();
                String filename = name;
                if (!filename.endsWith(readFileName)) continue;
                String line;
                try {
                    is = zip.getInputStream(entry);
                    reader = new InputStreamReader(is);
                    in = new BufferedReader(reader);
                    line = in.readLine();
                    while (StringUtils.isNotBlank(line)) {
                        buff.append(line);
                        line = in.readLine();
                    }
                } finally {
                    if (is != null) {
                        is.close();
                        is = null;
                    }
                    if (reader != null) {
                        reader.close();
                        reader = null;
                    }
                    if (in != null) {
                        in.close();
                        in = null;
                    }
                }
                break;
            }
        }

        zip.close();
        return buff.toString();
    }

    public void installPlug(File zipFile, ShopPlug plug)
            throws IOException {
        ZipFile zip = new ZipFile(zipFile, "GBK");

        byte[] buf = new byte[1024];

        InputStream is = null;
        OutputStream os = null;
        Enumeration en = zip.getEntries();
        StringBuffer buff = new StringBuffer();
        while (en.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) en.nextElement();
            String name = entry.getName();
            if (!entry.isDirectory()) {
                name = entry.getName();
                String filename = name;
                File outFile = new File(this.realPathResolver.get(filename));
                if (outFile.exists()) {
                    break;
                }
                File pfile = outFile.getParentFile();
                if (!pfile.exists()) {
                    createFolder(outFile);
                }
                int len;
                try {
                    is = zip.getInputStream(entry);
                    os = new FileOutputStream(outFile);
                    while ((len = is.read(buf)) != -1) {
                        os.write(buf, 0, len);
                    }
                } finally {
                    if (is != null) {
                        is.close();
                        is = null;
                    }
                    if (os != null) {
                        os.close();
                        os = null;
                    }
                }

                if ((filename.toLowerCase().endsWith(".properties")) && (!filename.toLowerCase().contains("messages"))) {
                    Properties p = new Properties();
                    p.load(new FileInputStream(outFile));
                    Set pKeys = p.keySet();
                    for (Iterator localIterator = pKeys.iterator(); localIterator.hasNext(); ) {
                        Object pk = localIterator.next();
                        if (pk.toString().startsWith("plug.perms")) {
                            buff.append(p.getProperty((String) pk) + ";");
                        }
                    }
                }
            }
        }
        zip.close();
        plug.setPlugPerms(buff.toString());
        this.plugMng.update(plug);
    }
}

