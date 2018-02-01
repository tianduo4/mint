package com.mint.cms.action.admin.main;

import com.mint.cms.manager.ResourceMng;
import com.mint.common.file.FileWrap;
import com.mint.common.web.RequestUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.common.web.springmvc.RealPathResolver;
import com.mint.core.entity.Website;
import com.mint.core.manager.TemplateMng;
import com.mint.core.web.SiteUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class ResourceAct
        implements ServletContextAware {
    private static final Logger log = LoggerFactory.getLogger(ResourceAct.class);
    private static final String REL_PATH = "relPath";
    private String root;

    @Autowired
    private TemplateMng manager;

    @Autowired
    private ResourceMng resourceMng;
    private ServletContext servletContext;
    private RealPathResolver realPathResolver;

    @RequestMapping({"/resource/v_left.do"})
    public String left(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String resReal = this.servletContext.getRealPath(web.getResBaseRel());
        String resName =
                MessageResolver.getMessage(request, "resource.resName", new Object[0]);
        FileWrap wrap = this.manager.getResFileWrap(resReal, resName);
        model.addAttribute("treeRoot", wrap);
        return "resource/left";
    }

    @RequestMapping({"/resource/v_tree.do"})
    public String tree(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String root = RequestUtils.getQueryParam(request, "root");
        log.debug("tree path={}", root);

        if ((StringUtils.isBlank(root)) || ("source".equals(root))) {
            root = web.getResBaseRel();
            model.addAttribute("isRoot", Boolean.valueOf(true));
        } else {
            model.addAttribute("isRoot", Boolean.valueOf(false));
        }
        List resList = this.resourceMng.listFile(root, true);
        model.addAttribute("resList", resList);
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=UTF-8");
        return "resource/tree";
    }

    @RequestMapping({"/resource/v_list.do"})
    public String list(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String root = (String) model.get("root");
        if (root == null) {
            root = RequestUtils.getQueryParam(request, "root");
        }
        log.debug("list Resource root: {}", root);
        if (StringUtils.isBlank(root)) {
            root = web.getResBaseRel();
        }
        String rel = root.substring(web.getResBaseRel().length());
        if (rel.length() == 0) {
            rel = "/";
        }
        model.addAttribute("root", root);
        model.addAttribute("rel", rel);
        model.addAttribute("resBase", web.getResBaseUrl());
        model.addAttribute("list", this.resourceMng.listFile(root, false));
        return "resource/list";
    }

    @RequestMapping({"/resource/o_create_dir.do"})
    public String createDir(String root, String dirName, HttpServletRequest request, ModelMap model) {
        this.resourceMng.createDir(root, dirName);
        model.addAttribute("root", root);
        return list(request, model);
    }

    @RequestMapping({"/resource/v_rename.do"})
    public String renameInput(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String root = RequestUtils.getQueryParam(request, "root");
        String name = RequestUtils.getQueryParam(request, "name");
        String origName = name.substring(web.getResBaseRel().length());
        model.addAttribute("origName", origName);
        model.addAttribute("root", root);
        return "resource/rename";
    }

    @RequestMapping({"/resource/o_rename.do"})
    public String renameUpdate(String relPath, String origName, String newName, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String real = this.servletContext.getRealPath(web.getResBaseRel(relPath));
        File origFile = new File(real, origName);

        File newFile = new File(real, newName);
        origFile.renameTo(newFile);
        log.info("rename resource dir {} to {}", origFile.getAbsolutePath(),
                newFile.getAbsolutePath());
        model.addAttribute("relPath", relPath);
        return list(request, model);
    }

    @RequestMapping({"/resource/v_add.do"})
    public String add(String relPath, HttpServletRequest request, ModelMap model) {
        model.addAttribute("relPath", relPath);
        return "resource/add";
    }

    @RequestMapping({"/resource/o_save.do"})
    public String save(String relPath, String filename, String extension, String content, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String real = this.servletContext.getRealPath(web.getResBaseRel(relPath));
        try {
            File file = new File(real, filename + extension);
            FileUtils.writeStringToFile(file, content, "UTF-8");
            log.info("save resource file success: {}", file.getAbsolutePath());
        } catch (IOException e) {
            log.error("write resource file error", e);
        }
        model.addAttribute("relPath", relPath);
        return list(request, model);
    }

    @RequestMapping({"/resource/v_edit.do"})
    public String edit(HttpServletRequest request, ModelMap model) {
        String root = RequestUtils.getQueryParam(request, "root");
        String name = RequestUtils.getQueryParam(request, "name");
        Website web = SiteUtils.getWeb(request);
        String real = this.servletContext.getRealPath(name);
        File file = new File(real);
        String filename = file.getName();
        try {
            String content = FileUtils.readFileToString(file, "UTF-8");
            model.addAttribute("content", content);
        } catch (IOException e) {
            log.error("read resource file error", e);
        }
        model.addAttribute("filename", filename);
        model.addAttribute("root", root);
        model.addAttribute("name", name);
        return "resource/edit";
    }

    @RequestMapping({"/resource/o_update.do"})
    public String update(String name, String content, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String real = this.realPathResolver.get(name);
        File file = new File(real);
        try {
            FileUtils.writeStringToFile(file, content, "UTF-8");
        } catch (IOException e) {
            log.error("write resource file error", e);
        }
        ResponseUtils.renderJson(response, "true");

        return null;
    }

    @RequestMapping({"/resource/o_delete.do"})
    public String delete(String relPath, String[] names, HttpServletRequest request, ModelMap model) {
        String root = RequestUtils.getQueryParam(request, "root");
        for (String name : names) {
            int count = this.resourceMng.delete(new String[]{name});
            log.info("delete Resource {}, count {}", name, Integer.valueOf(count));
        }
        model.addAttribute("root", root);
        return list(request, model);
    }

    @RequestMapping({"/resource/o_delete_single.do"})
    public String deleteSingle(HttpServletRequest request, ModelMap model) {
        String root = RequestUtils.getQueryParam(request, "root");
        String name = RequestUtils.getQueryParam(request, "name");
        int count = this.resourceMng.delete(new String[]{name});
        log.info("delete Resource {}, count {}", name, Integer.valueOf(count));
        model.addAttribute("root", root);
        return list(request, model);
    }

    @RequestMapping({"/resource/v_upload.do"})
    public String uploadInput(String relPath, HttpServletRequest request, ModelMap model) {
        model.addAttribute("relPath", relPath);
        return "resource/upload";
    }

    @RequestMapping({"/resource/o_upload.do"})
    public String uploadSubmit(String relPath, HttpServletRequest request, ModelMap model) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map map = multipartRequest.getFileMap();
        MultipartFile[] files = new MultipartFile[map.size()];
        map.values().toArray(files);
        if (files.length > 0) {
            Website web = SiteUtils.getWeb(request);
            String realPath = this.servletContext.getRealPath(web
                    .getResBaseRel(relPath));
            this.manager.uploadResourceFile(realPath, files);
        }
        model.addAttribute("relPath", relPath);
        return list(request, model);
    }

    @RequestMapping(value = {"/resource/o_swfupload.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public void swfUpload(String root, @RequestParam(value = "Filedata", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IllegalStateException, IOException {
        this.resourceMng.saveFile(root, file);
        model.addAttribute("root", root);
        log.info("file upload seccess: {}, size:{}.", file
                .getOriginalFilename(), Long.valueOf(file.getSize()));
        ResponseUtils.renderText(response, "");
    }

    @Autowired
    public void setRealPathResolver(RealPathResolver realPathResolver) {
        this.realPathResolver = realPathResolver;
        this.root = realPathResolver.get("");
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

