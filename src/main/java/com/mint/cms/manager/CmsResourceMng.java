package com.mint.cms.manager;

import com.mint.common.file.FileWrap;
import com.mint.common.util.Zipper;
import com.mint.core.entity.Website;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public abstract interface CmsResourceMng {
    public abstract List<FileWrap> listFile(String paramString, boolean paramBoolean);

    public abstract void saveFile(String paramString, MultipartFile paramMultipartFile)
            throws IllegalStateException, IOException;

    public abstract boolean createDir(String paramString1, String paramString2);

    public abstract void createFile(String paramString1, String paramString2, String paramString3)
            throws IOException;

    public abstract String readFile(String paramString)
            throws IOException;

    public abstract void updateFile(String paramString1, String paramString2)
            throws IOException;

    public abstract void rename(String paramString1, String paramString2);

    public abstract int delete(String[] paramArrayOfString);

    public abstract void copyTplAndRes(Website paramWebsite1, Website paramWebsite2)
            throws IOException;

    public abstract void delTplAndRes(Website paramWebsite)
            throws IOException;

    public abstract String[] getSolutions(String paramString);

    public abstract List<Zipper.FileEntry> export(Website paramWebsite, String paramString);

    public abstract void imoport(File paramFile, Website paramWebsite)
            throws IOException;

    public abstract void deleteZipFile(File paramFile)
            throws IOException;

    public abstract String readFileFromZip(File paramFile, String paramString)
            throws IOException;
}

