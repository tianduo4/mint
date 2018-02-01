package com.jspgou.cms.action.admin;

import com.jspgou.common.file.FileNameUtils;
import com.jspgou.common.image.ImageUtils;
import com.jspgou.core.entity.Ftp;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@Controller
public class ImageUploadAct
        implements ServletContextAware {
    private static final Logger log = LoggerFactory.getLogger(ImageUploadAct.class);
    private static final String RESULT_PAGE = "/common/iframe_upload";
    private static final String RESULT_SWITCH_PAGE = "/common/iframe_switch_upload";
    private static final String RESULT_BIG_PAGE = "/common/iframe_big_upload";
    private static final String RESULT_AMP_PAGE = "/common/iframe_amp_upload";
    public static final String ERROR = "error";
    private ServletContext servletContext;

    @RequestMapping({"/common/o_upload_image.do"})
    public String execute(String fileName, Integer uploadNum, Integer zoomWidth, Integer zoomHeight, @RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, ModelMap model)
            throws IOException {
        WebErrors errors = validate(fileName, file, request);
        if (errors.hasErrors()) {
            model.addAttribute("error", errors.getErrors().get(0));
            return "/common/iframe_upload";
        }
        Website web = SiteUtils.getWeb(request);
        String real = this.servletContext.getRealPath(web.getUploadRel());
        String dateDir = FileNameUtils.genPathName();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            String relPath;
            if (web.getUploadFtp() != null) {
                Ftp ftp = web.getUploadFtp();
                String ftpUrl = ftp.getUrl();
                if (!StringUtils.isBlank(fileName)) {
                    fileName = fileName.substring(ftpUrl.length());
                    relPath = ftp.storeByFilename(fileName, file
                            .getInputStream());
                } else {
                    String ctx = request.getContextPath();
                    relPath = ctx + "/" + "u" + "/" + fileName;
                    relPath = ftp.storeByExt(relPath, ext,
                            file.getInputStream());

                    relPath = ftpUrl + relPath;
                }
            } else {
                File root = new File(real, dateDir);
                if (!root.exists()) {
                    root.mkdirs();
                }

                if (StringUtils.isBlank(fileName))
                    fileName = FileNameUtils.genFileName(ext);
                else {
                    fileName = FilenameUtils.getName(fileName);
                }

                File tempFile = new File(root, fileName);

                String ctx = request.getContextPath();
                relPath = ctx + "/" + "u" + "/" + dateDir + "/" + fileName;
                model.addAttribute("zoomWidth", zoomWidth);
                model.addAttribute("zoomHeight", zoomHeight);
                file.transferTo(tempFile);
            }
            model.addAttribute("uploadPath", relPath);
            model.addAttribute("uploadNum", uploadNum);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        }
        return "/common/iframe_upload";
    }

    @RequestMapping({"/common/o_upload_switch_image.do"})
    public String executeSwitch(String fileName, Integer uploadNum, @RequestParam(value = "uploadFileSwitch", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validate(fileName, file, request);
        if (errors.hasErrors()) {
            model.addAttribute("error", errors.getErrors().get(0));
            return "/common/iframe_switch_upload";
        }
        Website web = SiteUtils.getWeb(request);
        String real = this.servletContext.getRealPath(web.getUploadRel());
        String dateDir = FileNameUtils.genPathName();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            String relPath;
            if (web.getUploadFtp() != null) {
                Ftp ftp = web.getUploadFtp();
                String ftpUrl = ftp.getUrl();
                if (!StringUtils.isBlank(fileName)) {
                    fileName = fileName.substring(ftpUrl.length());
                    relPath = ftp.storeByFilename(fileName, file
                            .getInputStream());
                } else {
                    String ctx = request.getContextPath();
                    relPath = ctx + "/" + "u" + "/" + fileName;
                    relPath = ftp.storeByExt(relPath, ext,
                            file.getInputStream());

                    relPath = ftpUrl + relPath;
                }
            } else {
                File root = new File(real, dateDir);
                if (!root.exists()) {
                    root.mkdirs();
                }

                if (StringUtils.isBlank(fileName))
                    fileName = FileNameUtils.genFileName(ext);
                else {
                    fileName = FilenameUtils.getName(fileName);
                }

                File tempFile = new File(root, fileName);

                String ctx = request.getContextPath();
                relPath = ctx + "/" + "u" + "/" + dateDir + "/" + fileName;
                file.transferTo(tempFile);
            }
            model.addAttribute("uploadPath", relPath);
            model.addAttribute("uploadNum", uploadNum);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        }
        return "/common/iframe_switch_upload";
    }

    @RequestMapping({"/common/o_upload_big_image.do"})
    public String executeBig(String fileName, Integer uploadNum, @RequestParam(value = "uploadFileBig", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validate(fileName, file, request);
        if (errors.hasErrors()) {
            model.addAttribute("error", errors.getErrors().get(0));
            return "/common/iframe_big_upload";
        }
        Website web = SiteUtils.getWeb(request);
        String real = this.servletContext.getRealPath(web.getUploadRel());
        String dateDir = FileNameUtils.genPathName();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            String relPath;
            if (web.getUploadFtp() != null) {
                Ftp ftp = web.getUploadFtp();
                String ftpUrl = ftp.getUrl();
                if (!StringUtils.isBlank(fileName)) {
                    fileName = fileName.substring(ftpUrl.length());
                    relPath = ftp.storeByFilename(fileName, file
                            .getInputStream());
                } else {
                    String ctx = request.getContextPath();
                    relPath = ctx + "/" + "u" + "/" + fileName;
                    relPath = ftp.storeByExt(relPath, ext,
                            file.getInputStream());

                    relPath = ftpUrl + relPath;
                }
            } else {
                File root = new File(real, dateDir);
                if (!root.exists()) {
                    root.mkdirs();
                }

                if (StringUtils.isBlank(fileName)) {
                    fileName = FileNameUtils.genFileName(ext);
                } else fileName = FilenameUtils.getName(fileName);

                File tempFile = new File(root, fileName);

                String ctx = request.getContextPath();
                relPath = ctx + "/" + "u" + "/" + dateDir + "/" + fileName;

                file.transferTo(tempFile);
            }
            model.addAttribute("uploadPath", relPath);
            model.addAttribute("uploadNum", uploadNum);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        }
        return "/common/iframe_big_upload";
    }

    @RequestMapping({"/common/o_upload_amp_image.do"})
    public String executeAmp(String fileName, Integer uploadNum, @RequestParam(value = "uploadFileAmp", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validate(fileName, file, request);
        if (errors.hasErrors()) {
            model.addAttribute("error", errors.getErrors().get(0));
            return "/common/iframe_amp_upload";
        }
        Website web = SiteUtils.getWeb(request);
        String real = this.servletContext.getRealPath(web.getUploadRel());
        String dateDir = FileNameUtils.genPathName();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            String relPath;
            if (web.getUploadFtp() != null) {
                Ftp ftp = web.getUploadFtp();
                String ftpUrl = ftp.getUrl();
                if (!StringUtils.isBlank(fileName)) {
                    fileName = fileName.substring(ftpUrl.length());
                    relPath = ftp.storeByFilename(fileName, file
                            .getInputStream());
                } else {
                    String ctx = request.getContextPath();
                    relPath = ctx + "/" + "u" + "/" + fileName;
                    relPath = ftp.storeByExt(relPath, ext,
                            file.getInputStream());

                    relPath = ftpUrl + relPath;
                }
            } else {
                File root = new File(real, dateDir);
                if (!root.exists()) {
                    root.mkdirs();
                }

                if (StringUtils.isBlank(fileName)) {
                    fileName = FileNameUtils.genFileName(ext);
                } else fileName = FilenameUtils.getName(fileName);

                File tempFile = new File(root, fileName);

                String ctx = request.getContextPath();
                relPath = ctx + "/" + "u" + "/" + dateDir + "/" + fileName;

                file.transferTo(tempFile);
            }
            model.addAttribute("uploadPath", relPath);
            model.addAttribute("uploadNum", uploadNum);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        }
        return "/common/iframe_amp_upload";
    }

    private WebErrors validate(String fileName, MultipartFile file, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (file == null) {
            errors.addErrorCode("imageupload.error.noFileToUpload");
            return errors;
        }
        if (StringUtils.isBlank(fileName)) {
            fileName = file.getOriginalFilename();
        }
        String ext = FilenameUtils.getExtension(fileName);
        if (!ImageUtils.isImage(ext)) {
            errors.addErrorCode("imageupload.error.notSupportExt", new Object[]{ext});
            return errors;
        }
        return errors;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

