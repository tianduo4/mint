package com.mint.cms.action.admin;

import com.mint.common.fck.Command;
import com.mint.common.fck.ResourceType;
import com.mint.common.fck.UploadResponse;
import com.mint.common.fck.Utils;
import com.mint.common.upload.FileRepository;
import com.mint.common.upload.UploadUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FckAct {
    public static final String UPLOAD_PATH = "/u/mint/";
    private static final Logger log = LoggerFactory.getLogger(FckAct.class);
    private FileRepository fileRepository;

    @RequestMapping(value = {"/fck/upload.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public void upload(@RequestParam(value = "Command", required = false) String commandStr, @RequestParam(value = "Type", required = false) String typeStr, @RequestParam(value = "CurrentFolder", required = false) String currentFolderStr, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.debug("Entering Dispatcher#doPost");
        responseInit(response);
        if ((Utils.isEmpty(commandStr)) && (Utils.isEmpty(currentFolderStr))) {
            commandStr = "QuickUpload";
            currentFolderStr = "/";
            if (Utils.isEmpty(typeStr)) {
                typeStr = "File";
            }
        }
        if ((currentFolderStr != null) && (!currentFolderStr.startsWith("/"))) {
            currentFolderStr = "/".concat(currentFolderStr);
        }
        log.debug("Parameter Command: {}", commandStr);
        log.debug("Parameter Type: {}", typeStr);
        log.debug("Parameter CurrentFolder: {}", currentFolderStr);
        UploadResponse ur = validateUpload(request, commandStr, typeStr,
                currentFolderStr);
        if (ur == null) {
            ur = doUpload(request, typeStr, currentFolderStr);
        }
        PrintWriter out = response.getWriter();
        out.print(ur);
        out.flush();
        out.close();
    }

    private UploadResponse doUpload(HttpServletRequest request, String typeStr, String currentFolderStr)
            throws Exception {
        ResourceType type = ResourceType.getDefaultResourceType(typeStr);
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile uplFile = (MultipartFile) ((Map.Entry) multipartRequest.getFileMap().entrySet().iterator().next()).getValue();
            String filename = FilenameUtils.getName(uplFile
                    .getOriginalFilename());
            log.debug("Parameter NewFile: {}", filename);
            String ext = FilenameUtils.getExtension(filename);
            if (type.isDeniedExtension(ext)) {
                return UploadResponse.getInvalidFileTypeError(request);
            }

            String fileUrl = this.fileRepository.storeByExt("/u/mint/", ext, uplFile);

            fileUrl = request.getContextPath() + fileUrl;
            return UploadResponse.getOK(request, fileUrl);
        } catch (IOException e) {
        }
        return UploadResponse.getFileUploadWriteError(request);
    }

    private void responseInit(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
    }

    private UploadResponse validateUpload(HttpServletRequest request, String commandStr, String typeStr, String currentFolderStr) {
        if (!Command.isValidForPost(commandStr)) {
            return UploadResponse.getInvalidCommandError(request);
        }
        if (!ResourceType.isValidType(typeStr)) {
            return UploadResponse.getInvalidResourceTypeError(request);
        }
        if (!UploadUtils.isValidPath(currentFolderStr)) {
            return UploadResponse.getInvalidCurrentFolderError(request);
        }
        return null;
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
}

