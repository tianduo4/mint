package com.mint.cms.ueditor.upload;

import com.mint.cms.ueditor.PathFormat;
import com.mint.cms.ueditor.define.BaseState;
import com.mint.cms.ueditor.define.FileType;
import com.mint.cms.ueditor.define.State;
import com.mint.common.web.springmvc.RealPathResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class BinaryUploader {
    public static final State save(HttpServletRequest request, RealPathResolver realPathResolver) {
        FileItemStream fileStream = null;
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, 5);
        }

        ServletFileUpload upload = new ServletFileUpload(
                new DiskFileItemFactory());

        if (isAjaxUpload) {
            upload.setHeaderEncoding("UTF-8");
        }
        try {
            FileItemIterator iterator = upload.getItemIterator(request);

            while (iterator.hasNext()) {
                fileStream = iterator.next();

                if (!fileStream.isFormField())
                    break;
                fileStream = null;
            }

            if (fileStream == null) {
                return new BaseState(false, 7);
            }

            String savePath = "/u/cms/www/";
            String originFileName = fileStream.getName();
            String suffix = FileType.getSuffixByFilename(originFileName);

            originFileName = originFileName.substring(0,
                    originFileName.length() - suffix.length());
            savePath = savePath + suffix;

            savePath = PathFormat.parse(savePath, originFileName);

            String physicalPath = realPathResolver.get(savePath);

            InputStream is = fileStream.openStream();
            State storageState = StorageManager.saveFileByInputStream(is,
                    physicalPath);
            is.close();

            if (storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }

            return storageState;
        } catch (FileUploadException e) {
            return new BaseState(false, 6);
        } catch (IOException localIOException) {
        }
        return new BaseState(false, 4);
    }

    private static boolean validType(String type, String[] allowTypes) {
        List list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}

