package com.jspgou.common.upload;

import com.jspgou.core.entity.Website;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.web.multipart.MultipartFile;

public abstract interface Repository
{
  public abstract boolean store(String paramString, InputStream paramInputStream)
    throws IOException;

  public abstract boolean retrieve(String paramString, OutputStream paramOutputStream);

  public abstract String upload(Website paramWebsite, String paramString, MultipartFile paramMultipartFile)
    throws IOException;
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.common.upload.Repository
 * JD-Core Version:    0.6.0
 */