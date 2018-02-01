package com.jspgou.cms.service.impl;

import com.jspgou.cms.service.ImageSvc;
import com.jspgou.common.file.FileNameUtils;
import com.jspgou.common.image.ImageUtils;
import com.jspgou.common.upload.FileRepository;
import com.jspgou.common.upload.UploadUtils;
import com.jspgou.common.web.springmvc.RealPathResolver;
import com.jspgou.core.entity.Ftp;
import com.jspgou.core.entity.Website;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageSvcImpl
        implements ImageSvc {

    @Autowired
    protected FileRepository fileRepository;

    @Autowired
    private RealPathResolver realPathResolver;

    public String crawlImg(String imgUrl, Website site) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = httpClientBuilder.build();
        String outFileName = "";
        String fileUrl = "";

        label318:
        try {
            if (validUrl(imgUrl)) {
                URI uri = new URI(imgUrl);
                HttpGet httpget = new HttpGet(uri);
                HttpResponse response = client.execute(httpget);
                InputStream is = null;
                OutputStream os = null;
                HttpEntity entity = null;
                entity = response.getEntity();
                is = entity.getContent();
                String ctx = site.getContextPath();
                String ext = FileNameUtils.getFileSufix(imgUrl);

                if (site.getUploadFtp() != null) {
                    Ftp ftp = site.getUploadFtp();
                    String ftpUrl = ftp.getUrl();
                    fileUrl = ftp.storeByExt("/u", ext, is);

                    fileUrl = ftpUrl + fileUrl;
                    break label318;
                }

                String fileName = UploadUtils.generateRamdonFilename(FileNameUtils.getFileSufix(imgUrl));
                File outFile = new File(this.realPathResolver.get("/u"), fileName);
                UploadUtils.checkDirAndCreate(outFile.getParentFile());
                outFileName = "/u" + fileName;
                os = new FileOutputStream(outFile);
                IOUtils.copy(is, os);

                if ((ctx != null) && (StringUtils.isNotBlank(ctx))) {
                    fileUrl = ctx + outFileName;
                    break label318;
                }
                fileUrl = outFileName;
            }
        } catch (Exception localException) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileUrl;
    }

    public String crawlImg(String imgUrl, String ctx, boolean uploadToDb, String dbFileUri, Ftp ftp, String uploadPath) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = httpClientBuilder.build();
        String outFileName = "";
        String fileUrl = "";

        label277:
        try {
            if (validUrl(imgUrl)) {
                HttpGet httpget = new HttpGet(new URI(imgUrl));
                HttpResponse response = client.execute(httpget);
                InputStream is = null;
                OutputStream os = null;
                HttpEntity entity = null;
                entity = response.getEntity();
                is = entity.getContent();
                String ext = FileNameUtils.getFileSufix(imgUrl);

                if (ftp != null) {
                    String ftpUrl = ftp.getUrl();
                    fileUrl = ftp.storeByExt(uploadPath, ext, is);

                    fileUrl = ftpUrl + fileUrl;
                    break label277;
                }
                outFileName = UploadUtils.generateFilename(uploadPath, FileNameUtils.getFileSufix(imgUrl));
                File outFile = new File(this.realPathResolver.get(outFileName));
                UploadUtils.checkDirAndCreate(outFile.getParentFile());
                os = new FileOutputStream(outFile);
                IOUtils.copy(is, os);

                if ((ctx != null) && (StringUtils.isNotBlank(ctx))) {
                    fileUrl = ctx + outFileName;
                    break label277;
                }
                fileUrl = outFileName;
            }
        } catch (Exception localException) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileUrl;
    }

    private boolean validUrl(String imgUrl) {
        boolean isImage = true;
        try {
            URL url = new URL(imgUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            isImage = ImageUtils.isImage(inputStream);
            inputStream.close();
        } catch (Exception e) {
            isImage = false;
        }
        return isImage;
    }
}

