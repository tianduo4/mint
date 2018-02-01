package com.mint.cms.action.admin.assist;

import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopPlug;
import com.mint.cms.manager.ResourceMng;
import com.mint.cms.manager.ShopPlugMng;
import com.mint.cms.web.threadvariable.AdminThread;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.upload.FileRepository;
import com.mint.common.web.CookieUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.common.web.springmvc.RealPathResolver;
import com.mint.core.entity.Website;
import com.mint.core.manager.LogMng;
import com.mint.core.tpl.TplManager;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PlugAct {
    private static final Logger log = LoggerFactory.getLogger(PlugAct.class);
    private static final String PLUG_CONFIG_INI = "plug.ini";
    private static final String PLUG_CONFIG_KEY_REPAIR = "plug_repair";

    @Autowired
    private ShopPlugMng manager;

    @Autowired
    private ResourceMng resourceMng;

    @Autowired
    private TplManager tplManager;

    @Autowired
    protected FileRepository fileRepository;

    @Autowired
    private RealPathResolver realPathResolver;

    @Autowired
    private LogMng cmsLogMng;

    @RequestMapping({"/plug/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        Pagination pagination = this.manager.getPage(SimplePage.cpn(pageNo),
                CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", Integer.valueOf(pagination.getPageNo()));
        return "plug/list";
    }

    @RequestMapping({"/plug/v_add.do"})
    public String add(ModelMap model) {
        return "plug/add";
    }

    @RequestMapping({"/plug/v_edit.do"})
    public String edit(Long id, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("plug", this.manager.findById(id));
        model.addAttribute("pageNo", pageNo);
        return "plug/edit";
    }

    @RequestMapping({"/plug/o_save.do"})
    public String save(ShopPlug bean, HttpServletRequest request, ModelMap model) throws IOException {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }

        File file = new File(this.realPathResolver.get(bean.getPath()));
        if (file.exists()) {
            if (!isRepairPlug(file)) {
                boolean fileConflict = isFileConflict(file);
                bean.setFileConflict(fileConflict);
            } else {
                bean.setFileConflict(false);
            }
        }
        bean.setUploadTime(Calendar.getInstance().getTime());
        bean = this.manager.save(bean);
        log.info("save CmsPlug id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/plug/o_update.do"})
    public String update(ShopPlug bean, Integer pageNo, HttpServletRequest request, ModelMap model) throws IOException {
        WebErrors errors = validateUpdate(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }

        File file = new File(this.realPathResolver.get(bean.getPath()));
        if (file.exists()) {
            if (!isRepairPlug(file)) {
                boolean fileConflict = isFileConflict(file);
                bean.setFileConflict(fileConflict);
            } else {
                bean.setFileConflict(false);
            }
        }
        bean = this.manager.update(bean);
        log.info("update CmsPlug id={}.", bean.getId());
        return list(pageNo, request, model);
    }

    @RequestMapping({"/plug/o_upload.do"})
    public String uploadSubmit(@RequestParam(value = "plugFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IOException {
        WebErrors errors = validateUpload(file, request);
        if (errors.hasErrors()) {
            model.addAttribute("error", errors.getErrors().get(0));
            return "plug/upload_iframe";
        }
        String origName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(origName).toLowerCase(
                Locale.ENGLISH);
        try {
            String filename = "/WEB-INF/plug/" + file.getOriginalFilename();
            File oldFile = new File(this.realPathResolver.get(filename));
            if (oldFile.exists()) {
                oldFile.delete();
            }
            String fileUrl = this.fileRepository.storeByFilePath("/WEB-INF/plug/", file.getOriginalFilename(), file);
            model.addAttribute("plugPath", fileUrl);
            model.addAttribute("plugExt", ext);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            log.error("upload file error!", e);
        }
        this.cmsLogMng.operating(request, "plug.log.upload", "filename=" + file.getName());
        return "plug/upload_iframe";
    }

    @RequestMapping({"/plug/o_install.do"})
    public void install(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IOException, JSONException {
        ShopAdmin user = AdminThread.get();
        JSONObject object = new JSONObject();
        if (user == null) {
            object.put("login", false);
        } else {
            ShopPlug plug = this.manager.findById(id);
            if ((plug != null) && (fileExist(plug.getPath()))) {
                File zipFile = new File(this.realPathResolver.get(plug.getPath()));
                boolean isRepairPlug = isRepairPlug(zipFile);

                if (isRepairPlug) {
                    installPlug(zipFile, plug, true, request);
                } else {
                    boolean fileConflict = isFileConflict(zipFile);
                    if (fileConflict) {
                        object.put("conflict", true);
                    } else {
                        object.put("conflict", false);
                        installPlug(zipFile, plug, false, request);
                    }
                }
                object.put("exist", true);
            } else {
                object.put("exist", false);
            }
            object.put("login", true);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    private void installPlug(File zipFile, ShopPlug plug, boolean isRepairPlug, HttpServletRequest request) throws IOException {
        plug.setInstallTime(Calendar.getInstance().getTime());
        plug.setUsed(true);
        plug.setPlugRepair(isRepairPlug);
        this.resourceMng.installPlug(zipFile, plug);
        this.cmsLogMng.operating(request, "plug.log.install", "name=" + plug.getName());
    }

    @RequestMapping({"/plug/o_uninstall.do"})
    public void uninstall(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IOException, JSONException {
        ShopAdmin user = AdminThread.get();
        JSONObject object = new JSONObject();
        if (user == null) {
            object.put("login", false);
        } else {
            ShopPlug plug = this.manager.findById(id);
            if ((plug != null) && (fileExist(plug.getPath()))) {
                File file = new File(this.realPathResolver.get(plug.getPath()));

                if (plug.getPlugRepair()) {
                    object.put("repair", true);
                } else {
                    object.put("repair", false);
                    boolean fileConflict = plug.getFileConflict();
                    if (!fileConflict) {
                        this.resourceMng.deleteZipFile(file);
                        plug.setUninstallTime(Calendar.getInstance().getTime());
                        plug.setUsed(false);
                        this.manager.update(plug);
                        log.info("delete plug name={}", plug.getPath());
                        this.cmsLogMng.operating(request, "plug.log.uninstall", "filename=" + plug.getName());
                        object.put("conflict", false);
                    } else {
                        object.put("conflict", true);
                    }
                }
                object.put("exist", true);
            } else {
                object.put("exist", false);
            }
            object.put("login", true);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @RequestMapping({"/plug/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        ShopPlug[] beans = this.manager.deleteByIds(ids);
        for (ShopPlug bean : beans) {
            this.tplManager.delete(new String[]{bean.getPath()});
            log.info("delete CmsPlug id={}", bean.getId());
        }
        return list(pageNo, request, model);
    }

    private boolean isRepairPlug(File file) {
        boolean isRepairPlug = false;
        String plugIni = "";
        String repairStr = "";
        try {
            plugIni = this.resourceMng.readFileFromZip(file, "plug.ini");
            if (StringUtils.isNotBlank(plugIni)) {
                String[] configs = plugIni.split(";");
                for (String c : configs) {
                    String[] configAtt = c.split("=");
                    if ((configAtt == null) || (configAtt.length != 2) ||
                            (!configAtt[0].equals("plug_repair"))) continue;
                    repairStr = configAtt[1];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if ((StringUtils.isNotBlank(repairStr)) && (repairStr.toLowerCase().equals("true"))) {
            isRepairPlug = true;
        }
        return isRepairPlug;
    }

    private boolean isFileConflict(File file) throws IOException {
        ZipFile zip = new ZipFile(file, "GBK");

        boolean fileConflict = false;
        Enumeration en = zip.getEntries();
        while (en.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) en.nextElement();
            String name = entry.getName();
            if (!entry.isDirectory()) {
                name = entry.getName();
                String filename = name;
                File outFile = new File(this.realPathResolver.get(filename));
                if (outFile.exists()) {
                    fileConflict = true;
                    break;
                }
            }
        }
        zip.close();
        return fileConflict;
    }

    private WebErrors validateSave(ShopPlug bean, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        return errors;
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website site = SiteUtils.getWeb(request);
        if (vldExist(id, site.getId().longValue(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateUpdate(ShopPlug plug, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website site = SiteUtils.getWeb(request);
        if (vldExist(plug.getId(), site.getId().longValue(), errors)) {
            return errors;
        }
        ShopPlug dbPlug = this.manager.findById(plug.getId());

        if ((dbPlug != null) && (dbPlug.getUsed()) && (!dbPlug.getPath().equals(plug.getPath()))) {
            errors.addErrorCode("error.plug.upload", new Object[]{dbPlug.getName()});
        }
        return errors;
    }

    private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website site = SiteUtils.getWeb(request);
        if (errors.ifEmpty(ids, "ids")) {
            return errors;
        }
        for (Long id : ids) {
            vldExist(id, site.getId().longValue(), errors);
            vldUsed(id, errors);
        }
        return errors;
    }

    private WebErrors validateUpload(MultipartFile file, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (errors.ifNull(file, "file")) {
            return errors;
        }
        String filename = file.getOriginalFilename();
        if ((filename != null) && (filename.indexOf("") != -1)) {
            errors.addErrorCode("upload.error.filename", new Object[]{filename});
        }
        String filePath = "/WEB-INF/plug/" + filename;

        ShopPlug plug = this.manager.findByPath(filePath);
        File tempFile = new File(this.realPathResolver.get(filePath));

        if ((plug != null) && (plug.getUsed()) && (tempFile.exists())) {
            errors.addErrorCode("error.plug.upload", new Object[]{plug.getName()});
        }
        return errors;
    }

    private boolean vldExist(Long id, long siteId, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        ShopPlug entity = this.manager.findById(id);

        return errors.ifNotExist(entity, ShopPlug.class, id);
    }

    private boolean vldUsed(Long id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        ShopPlug entity = this.manager.findById(id);
        if (entity.getUsed()) {
            errors.addErrorCode("error.plug.delele", new Object[]{entity.getName()});
        }
        return false;
    }

    private boolean fileExist(String filePath) {
        File file = new File(this.realPathResolver.get(filePath));
        return file.exists();
    }
}

