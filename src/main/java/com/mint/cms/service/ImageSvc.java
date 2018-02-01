package com.mint.cms.service;

import com.mint.core.entity.Ftp;
import com.mint.core.entity.Website;

public abstract interface ImageSvc {
    public abstract String crawlImg(String paramString, Website paramWebsite);

    public abstract String crawlImg(String paramString1, String paramString2, boolean paramBoolean, String paramString3, Ftp paramFtp, String paramString4);
}

