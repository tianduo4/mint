package com.mint.cms.api.common;

import com.mint.cms.service.ImageSvc;
import com.mint.cms.ueditor.PathFormat;
import com.mint.cms.ueditor.define.BaseState;
import com.mint.cms.ueditor.define.MultiState;
import com.mint.cms.ueditor.define.State;
import com.mint.cms.ueditor.hunter.ImageHunter;
import com.mint.cms.ueditor.upload.StorageManager;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.common.image.ImageUtils;
import com.mint.common.ueditor.LocalizedMessages;
import com.mint.common.ueditor.ResourceType;
import com.mint.common.ueditor.Utils;
import com.mint.common.upload.FileRepository;
import com.mint.common.web.RequestUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.common.web.springmvc.RealPathResolver;
import com.mint.core.entity.Ftp;
import com.mint.core.entity.Website;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class UeditorController {
    private static final Logger log = LoggerFactory.getLogger(UeditorController.class);
    private static final String[] allowFilesuffix = {"jpg", "png", "bmp", "gif", "txt", "doc", "docx", "xls", "xlsl", "ppt", "pptx", "wps", "odt"};
    private static final String STATE = "state";
    private static final String SUCCESS = "SUCCESS";
    private static final String URL = "url";
    private static final String ORIGINAL = "original";
    private static final String TITLE = "title";
    private FileRepository fileRepository;
    private RealPathResolver realPathResolver;

    @Autowired
    private ImageSvc imgSvc;

    @RequestMapping({"/ueditor/upload"})
    public void upload(@RequestParam(value = "Type", required = false) String typeStr, Boolean mark, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        responseInit(response);
        if (Utils.isEmpty(typeStr)) {
            typeStr = "File";
        }
        if (mark == null) {
            mark = Boolean.valueOf(false);
        }
        JSONObject json = new JSONObject();
        JSONObject ob = validateUpload(request, typeStr);
        if (ob == null)
            json = doUpload(request, typeStr, mark);
        else {
            json = ob;
        }

        ResponseUtils.renderText(response, json.toString());
    }

    @RequestMapping({"/ueditor/getRemoteImage"})
    public void getRemoteImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] list = request.getParameterValues("source[]");
        Website site = CmsThreadVariable.getSite();
        State state = new ImageHunter(this.imgSvc, site).captureByApi(site.getUrlPrefix(RequestUtils.getRequestAgreement(request)), list);

        System.out.println("state.toJSONString()->" + state.toJSONString());
        ResponseUtils.renderJson(response, state.toJSONString());
    }

    @RequestMapping({"/ueditor/imageManager"})
    public void imageManager(Integer picNum, Boolean insite, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        State state = listFile(request, getStartIndex(request));
        ResponseUtils.renderText(response, state.toJSONString());
    }

    @RequestMapping({"/ueditor/scrawlImage"})
    public void scrawlImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        State state = scrawlImage(request.getParameter("upfile"), CmsThreadVariable.getSite());
        ResponseUtils.renderText(response, state.toString());
    }

    private JSONObject doUpload(HttpServletRequest request, String typeStr, Boolean mark) throws Exception {
        ResourceType type = ResourceType.getDefaultResourceType(typeStr);
        JSONObject result = new JSONObject();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

            MultipartFile uplFile =
                    (MultipartFile) ((Map.Entry) multipartRequest.getFileMap().entrySet()
                            .iterator().next()).getValue();

            String filename = FilenameUtils.getName(uplFile
                    .getOriginalFilename());
            log.debug("Parameter NewFile: {}", filename);
            String ext = FilenameUtils.getExtension(filename);
            if (type.isDeniedExtension(ext)) {
                result.put("state",
                        LocalizedMessages.getInvalidFileTypeSpecified(request));
                return result;
            }
            if ((type.equals(ResourceType.IMAGE)) &&
                    (!ImageUtils.isImage(uplFile.getInputStream()))) {
                result.put("state",
                        LocalizedMessages.getInvalidFileTypeSpecified(request));
                return result;
            }

            Website site = CmsThreadVariable.getSite();
            String fileUrl;
            if (site.getUploadFtp() != null) {
                Ftp ftp = site.getUploadFtp();
                fileUrl = ftp.storeByExt("/u", ext, uplFile
                        .getInputStream());

                fileUrl = ftp.getUrl() + fileUrl;
            } else {
                fileUrl = this.fileRepository.storeByExt("/u",
                        ext, uplFile);

                fileUrl = site.getUrlPrefix(RequestUtils.getRequestAgreement(request)) + request.getContextPath() + fileUrl;
            }

            result.put("url", fileUrl);
            result.put("original", filename);
            result.put("type", ext);

            result.put("state", "SUCCESS");
            result.put("title", filename);

            return result;
        } catch (IOException e) {
            result.put("state",
                    LocalizedMessages.getFileUploadWriteError(request));
        }
        return result;
    }

    public State listFile(HttpServletRequest request, int index) {
        Website site = CmsThreadVariable.getSite();
        String uploadPath = "/u";
        File dir = new File(this.realPathResolver.get(uploadPath));
        State state = null;
        if (!dir.exists()) {
            return new BaseState(false, 302);
        }

        if (!dir.isDirectory()) {
            return new BaseState(false, 301);
        }

        Collection list = FileUtils.listFiles(dir, null, true);

        if ((index < 0) || (index > list.size())) {
            state = new MultiState(true);
        } else {
            Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + 20);
            state = getState(this.realPathResolver.get(""), site.getContextPath(), fileList);
        }

        state.putInfo("start", index);
        state.putInfo("total", list.size());

        return state;
    }

    public int getStartIndex(HttpServletRequest request) {
        String start = request.getParameter("start");
        try {
            return Integer.parseInt(start);
        } catch (Exception e) {
        }
        return 0;
    }

    private State getState(String rootPath, String contextPath, Object[] files) {
        MultiState state = new MultiState(true);
        BaseState fileState = null;

        File file = null;

        for (Object obj : files) {
            if (obj == null) {
                break;
            }
            file = (File) obj;
            fileState = new BaseState(true);
            fileState.putInfo("url", PathFormat.format(getPath(rootPath, contextPath, file)));
            state.addState(fileState);
        }
        return state;
    }

    private String getPath(String rootPath, String contextPath, File file) {
        String path = file.getAbsolutePath();
        if (StringUtils.isNotBlank(contextPath)) {
            return contextPath + path.replace(rootPath, "/");
        }
        return path.replace(rootPath, "/");
    }

    private void responseInit(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        response.setHeader("Cache-Control", "no-cache");
    }

    private JSONObject validateUpload(HttpServletRequest request, String typeStr)
            throws JSONException {
        JSONObject result = new JSONObject();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile uplFile =
                (MultipartFile) ((Map.Entry) multipartRequest.getFileMap().entrySet()
                        .iterator().next()).getValue();
        String filename = FilenameUtils.getName(uplFile.getOriginalFilename());
        String ext = FilenameUtils.getExtension(filename).toLowerCase(
                Locale.ENGLISH);
        boolean isallowSuffix = false;
        int i = 0;
        for (int len = allowFilesuffix.length; i < len; i++) {
            if (allowFilesuffix[i].equals(ext)) {
                isallowSuffix = true;
            }
        }

        if (!isallowSuffix) {
            result.put("state",
                    LocalizedMessages.getInvalidFileSuffixSpecified(request));
            return result;
        }
        if (!ResourceType.isValidType(typeStr)) {
            result.put("state",
                    LocalizedMessages.getInvalidResouceTypeSpecified(request));
            return result;
        }
        return null;
    }

    public State scrawlImage(String content, Website site) {
        byte[] data = decode(content);

        String suffix = "jpg";

        String savePath = site.getContextPath() + "/u" + "/temp.jpg";

        String physicalPath = this.realPathResolver.get(savePath);

        State storageState = StorageManager.saveBinaryFile(data, physicalPath);

        File file = new File(physicalPath);

        String fileUrl = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            if (site.getUploadFtp() != null) {
                Ftp ftp = site.getUploadFtp();
                fileUrl = ftp.storeByExt("/u", suffix, fileInputStream);

                fileUrl = ftp.getUrl() + fileUrl;
            } else {
                fileUrl = this.fileRepository.storeByExt("/u", suffix, file);

                fileUrl = site.getContextPath() + fileUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (storageState.isSuccess()) {
            storageState.putInfo("url", fileUrl);
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", "");
        }

        return storageState;
    }

    private static byte[] decode(String content) {
        return Base64.decodeBase64(content);
    }

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Autowired
    public void setRealPathResolver(RealPathResolver realPathResolver) {
        this.realPathResolver = realPathResolver;
    }
}

