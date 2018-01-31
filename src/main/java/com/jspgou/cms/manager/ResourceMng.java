package com.jspgou.cms.manager;

import com.jspgou.cms.entity.ShopPlug;
import com.jspgou.common.file.FileWrap;
import com.jspgou.common.util.Zipper.FileEntry;
import com.jspgou.core.entity.Website;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public abstract interface ResourceMng
{
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

  public abstract void unZipFile(File paramFile)
    throws IOException;

  public abstract void deleteZipFile(File paramFile)
    throws IOException;

  public abstract String readFileFromZip(File paramFile, String paramString)
    throws IOException;

  public abstract void installPlug(File paramFile, ShopPlug paramShopPlug)
    throws IOException;

  public abstract String[] getSolutions(String paramString);

  public abstract List<Zipper.FileEntry> export(Website paramWebsite, String paramString);

  public abstract void imoport(File paramFile, Website paramWebsite)
    throws IOException;
}

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.manager.ResourceMng
 * JD-Core Version:    0.6.0
 */