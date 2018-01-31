package com.jspgou.core.manager;

import com.jspgou.common.file.FileWrap;
import org.springframework.web.multipart.MultipartFile;

public abstract interface TemplateMng
{
  public abstract FileWrap getTplFileWrap(String paramString1, String paramString2);

  public abstract FileWrap getResFileWrap(String paramString1, String paramString2);

  public abstract int uploadResourceFile(String paramString, MultipartFile[] paramArrayOfMultipartFile);
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.core.manager.TemplateMng
 * JD-Core Version:    0.6.0
 */