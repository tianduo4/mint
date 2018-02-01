package com.mint.cms.api.admin;

import com.google.gson.JsonObject;
import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.manager.CmsResourceMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.cms.web.SignValidate;
import com.mint.common.file.FileWrap;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.tpl.Tpl;
import com.mint.core.tpl.TplManager;
import com.mint.core.web.WebErrors;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ResourceController {

    @Autowired
    private TplManager tplManager;

    @Autowired
    private CmsResourceMng resourceMng;

    @RequestMapping({"/resource/tree"})
    public void tree(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Website site = CmsThreadVariable.getSite();
        JSONArray jsonArray = new JSONArray();
        String body = "\"\"";
        String message = "\"\"";
        int code = 200;
        String root = site.getResPath();
        JSONObject result = new JSONObject();
        List list = this.resourceMng.listFile(root, true);
        if ((list != null) && (list.size() > 0)) {
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(i, ((FileWrap) list.get(i)).convertToTreeJson((FileWrap) list.get(i)));
            }
        }
        message = "\"success\"";
        result.put("resources", jsonArray);
        result.put("rootPath", site.getResPath());
        body = result.toString();
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/resource/list"})
    public void resourceList(String root, HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        Website site = CmsThreadVariable.getSite();
        JSONArray jsonArray = new JSONArray();
        String body = "\"\"";
        String message = "\"\"";
        int code = 200;
        if (StringUtils.isBlank(root)) {
            root = site.getResPath();
        }
        WebErrors errors = validateList(root, site.getResPath(), request);
        if (errors.hasErrors()) {
            code = 202;
            message = "\"param error\"";
        } else {
            List list = this.resourceMng.listFile(root, false);
            if ((list != null) && (list.size() > 0)) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((FileWrap) list.get(i)).convertToJson());
                }
            }
            message = "\"success\"";
            code = 200;
            body = jsonArray.toString();
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/resource/dir_save"})
    public void createDir(String root, String dirName, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website site = CmsThreadVariable.getSite();
        String body = "\"\"";
        String message = "\"param required\"";
        int code = 201;
        if (StringUtils.isBlank(root)) {
            root = site.getResPath();
        }
        WebErrors errors = WebErrors.create(request);

        errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                root, dirName});
        if (!errors.hasErrors()) {
            errors = validateList(root, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            this.resourceMng.createDir(root, dirName);
            message = "\"success\"";
            code = 200;
        } else {
            message = "\"param error\"";
            code = 202;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/resource/get"})
    public void get(String name, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website site = CmsThreadVariable.getSite();
        String body = "";
        String message = "param required";
        int code = 201;
        WebErrors errors = WebErrors.create(request);

        errors = ApiValidate.validateRequiredParams(errors, new Object[]{name});
        if (!errors.hasErrors()) {
            errors = validateList(name, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            String source = "";
            try {
                source = this.resourceMng.readFile(name);
            } catch (IOException localIOException) {
            }
            message = "success";
            code = 200;
            body = source;
        } else {
            message = "param error";
            code = 202;
        }

        JsonObject json = new JsonObject();
        json.addProperty("body", body);
        json.addProperty("message", message);
        json.addProperty("code", Integer.valueOf(code));
        ResponseUtils.renderJson(response, json.toString());
    }

    @SignValidate
    @RequestMapping({"/resource/save"})
    public void save(String root, String filename, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String body = "\"\"";
        String message = "\"param required\"";
        int code = 201;
        Website site = CmsThreadVariable.getSite();
        WebErrors errors = WebErrors.create(request);
        if (StringUtils.isBlank(root)) {
            root = site.getResPath();
        }

        errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                root, filename, source});
        if (!errors.hasErrors()) {
            errors = validateList(root, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            try {
                this.resourceMng.createFile(root, filename, source);
            } catch (IOException localIOException) {
            }
            message = "\"success\"";
            code = 200;
        } else {
            message = "\"param error\"";
            code = 202;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/resource/update"})
    public void update(String filename, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String body = "\"\"";
        String message = "\"param required\"";
        int code = 201;
        Website site = CmsThreadVariable.getSite();
        WebErrors errors = WebErrors.create(request);

        errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                filename, source});
        if (!errors.hasErrors()) {
            errors = validateList(filename, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            try {
                this.resourceMng.updateFile(filename, source);
            } catch (IOException e) {
                e.printStackTrace();
            }
            message = "\"success\"";
            code = 200;
        } else {
            message = "\"param error\"";
            code = 202;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/resource/delete"})
    public void delete(String names, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String body = "\"\"";
        String message = "\"param required\"";
        int code = 201;
        Website site = CmsThreadVariable.getSite();
        WebErrors errors = WebErrors.create(request);

        errors = ApiValidate.validateRequiredParams(errors, new Object[]{names});
        String[] nameArray = null;
        if (!errors.hasErrors()) {
            nameArray = names.split(",");
            for (String n : nameArray) {
                errors = validatePath(n, site.getResPath(), errors);
                if (errors.hasErrors()) {
                    break;
                }
            }
        }
        if (!errors.hasErrors()) {
            try {
                this.resourceMng.delete(nameArray);
                message = "\"success\"";
                code = 200;
            } catch (Exception e) {
                message = "\"delete error\"";
                code = 205;
            }
        } else {
            message = "\"param error\"";
            code = 202;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/resource/rename"})
    public void rename(String origName, String distName, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String body = "\"\"";
        String message = "\"param required\"";
        int code = 201;
        Website site = CmsThreadVariable.getSite();
        WebErrors errors = WebErrors.create(request);

        errors = ApiValidate.validateRequiredParams(errors, new Object[]{origName, distName});
        if (!errors.hasErrors()) {
            errors = validateRename(origName, distName, site.getResPath(), request);
        }
        if (!errors.hasErrors()) {
            this.resourceMng.rename(origName, distName);
            message = "\"success\"";
            code = 200;
        } else {
            message = "\"param error\"";
            code = 202;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/resource/upload"})
    public void upload(String root, @RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String body = "\"\"";
        String message = "\"param required\"";
        int code = 201;
        Website site = CmsThreadVariable.getSite();
        WebErrors errors = WebErrors.create(request);

        errors = ApiValidate.validateRequiredParams(errors, new Object[]{root, file});
        if (!errors.hasErrors()) {
            errors = validateUpload(root, site.getResPath(), file, request);
        }
        if (!errors.hasErrors()) {
            try {
                String origName = file.getOriginalFilename();
                String ext = FilenameUtils.getExtension(origName).toLowerCase(
                        Locale.ENGLISH);
                this.resourceMng.saveFile(root, file);
                JSONObject json = new JSONObject();
                json.put("ext", ext.toUpperCase());
                json.put("size", file.getSize());
                json.put("url", root + "/" + origName);
                json.put("name", file.getOriginalFilename());
                body = json.toString();
                message = "\"success\"";
                code = 200;
            } catch (Exception e) {
                e.printStackTrace();
                message = "\"upload file error!\"";
                code = 400;
            }
        } else {
            message = "\"param error\"";
            code = 202;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    private WebErrors validateUpload(String root, String tplPath, MultipartFile file, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (file == null) {
            errors.addErrorString("\"error noFileToUpload\"");
            return errors;
        }
        if (isUnValidName(root, root, tplPath, errors)) {
            errors.addErrorCode("template.invalidParams");
        }
        String filename = file.getOriginalFilename();
        if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
            errors.addErrorString("\"upload error filename\"");
            return errors;
        }
        return errors;
    }

    private WebErrors validateList(String name, String tplPath, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(name, errors)) {
            return errors;
        }
        if (isUnValidName(name, name, tplPath, errors)) {
            errors.addErrorCode("template.invalidParams");
        }
        return errors;
    }

    private WebErrors validatePath(String name, String tplPath, WebErrors errors) {
        if (vldExist(name, errors)) {
            return errors;
        }
        if (isUnValidName(name, name, tplPath, errors)) {
            errors.addErrorCode("template.invalidParams");
        }
        return errors;
    }

    private WebErrors validateRename(String name, String newName, String tplPath, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(name, errors)) {
            return errors;
        }
        if (isUnValidName(name, name, tplPath, errors)) {
            errors.addErrorCode("template.invalidParams");
        }
        if (isUnValidName(newName, newName, tplPath, errors)) {
            errors.addErrorCode("template.invalidParams");
        }
        return errors;
    }

    private boolean isUnValidName(String path, String name, String tplPath, WebErrors errors) {
        return (!path.startsWith(tplPath)) || (path.contains("../")) || (path.contains("..\\")) || (name.contains("..\\")) || (name.contains("../"));
    }

    private boolean vldExist(String name, WebErrors errors) {
        if (errors.ifNull(name, "name")) {
            return true;
        }
        Tpl entity = this.tplManager.get(name);

        return errors.ifNotExist(entity, Tpl.class, name);
    }
}

