package com.mint.common.upload;

import com.mint.core.entity.Ftp;
import com.mint.core.entity.Website;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

public class FileRepository
        implements Repository, ServletContextAware {
    private Logger log = LoggerFactory.getLogger(FileRepository.class);
    private ServletContext ctx;

    public String upload(Website site, String ext, MultipartFile file)
            throws IOException {
        String url = "";
        Ftp ftp = site.getUploadFtp();
        if (ftp != null) {
            url = ftp.storeByExts("/u", ext, file.getInputStream());
        } else {
            url = storeByExt("/u", ext, file);
        }
        return url;
    }

    public String storeByExt(String path, String ext, MultipartFile file)
            throws IOException {
        String fileName = UploadUtils.generateRamdonFilename(ext);
        String fileUrl = path + fileName;
        File dest = new File(getRealPath(path), fileName);
        dest = UploadUtils.getUniqueFile(dest);
        stores(file, dest);
        return fileUrl;
    }

    public String storeByExts(String path, String ext, MultipartFile file) throws IOException {
        String fileName = UploadUtils.generateRamdonFilename(ext);
        String fileUrl = path + fileName;
        File dest = new File(getRealPath(path), fileName);
        dest = UploadUtils.getUniqueFile(dest);
        stores(file, dest);
        return fileUrl;
    }

    public String storeByExt(String path, String ext, File file)
            throws IOException {
        String fileName = UploadUtils.generateRamdonFilename(ext);
        String fileUrl = path + fileName;
        File dest = new File(getRealPath(path), fileName);
        dest = UploadUtils.getUniqueFile(dest);
        store(file, dest);
        return fileUrl;
    }

    private void stores(MultipartFile file, File dest) throws IOException {
        try {
            UploadUtils.checkDirAndCreate(dest.getParentFile());
            file.transferTo(dest);
        } catch (IOException e) {
            this.log.error("Transfer file error when upload file", e);
            throw e;
        }
    }

    private void store(File file, File dest) throws IOException {
        try {
            UploadUtils.checkDirAndCreate(dest.getParentFile());
            FileUtils.copyFile(file, dest);
        } catch (IOException e) {
            this.log.error("Transfer file error when upload file", e);
            throw e;
        }
    }

    public String storeByFilePath(String path, String filename, MultipartFile file) throws IOException {
        if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
            return "";
        }
        File dest = new File(getRealPath(path + filename));
        store1(file, dest);
        return path + filename;
    }

    public void setServletContext(ServletContext servletContext) {
        this.ctx = servletContext;
    }

    public boolean store(String s, InputStream inputstream)
            throws FileNotFoundException, IOException {
        IOUtils.copy(inputstream, new FileOutputStream(this.ctx.getRealPath(s)));
        return true;
    }

    private void store1(MultipartFile file, File dest) throws IOException {
        try {
            UploadUtils.checkDirAndCreate(dest.getParentFile());
            file.transferTo(dest);
        } catch (IOException e) {
            this.log.error("Transfer file error when upload file", e);
            throw e;
        }
    }

    public boolean retrieve(String s, OutputStream outputstream) {
        return false;
    }

    private String getRealPath(String name) {
        String realpath = this.ctx.getRealPath(name);
        if (realpath == null) {
            realpath = this.ctx.getRealPath("/") + name;
        }
        return realpath;
    }
}

