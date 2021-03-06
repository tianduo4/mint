package com.mint.core.manager;

import com.mint.common.file.FileWrap;
import org.springframework.web.multipart.MultipartFile;

public abstract interface TemplateMng {
    public abstract FileWrap getTplFileWrap(String paramString1, String paramString2);

    public abstract FileWrap getResFileWrap(String paramString1, String paramString2);

    public abstract int uploadResourceFile(String paramString, MultipartFile[] paramArrayOfMultipartFile);
}

