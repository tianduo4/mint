package com.mint.common.upload;

import com.mint.core.entity.Website;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public abstract interface Repository {
    public abstract boolean store(String paramString, InputStream paramInputStream)
            throws IOException;

    public abstract boolean retrieve(String paramString, OutputStream paramOutputStream);

    public abstract String upload(Website paramWebsite, String paramString, MultipartFile paramMultipartFile)
            throws IOException;
}

