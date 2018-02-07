package com.mint.cms.action.admin.main;

import com.mint.cms.manager.ResourceMng;
import com.mint.common.file.FileWrap;
import com.mint.common.util.Zipper;
import com.mint.common.web.RequestUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.Website;
import com.mint.core.manager.TemplateMng;
import com.mint.core.tpl.TplManager;
import com.mint.core.web.SiteUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@Controller
public class TemplateController
        implements ServletContextAware {
    private static final Logger log = LoggerFactory.getLogger(TemplateController.class);
    private static final String REL_PATH = "relPath";

    @Autowired
    private TemplateMng manager;
    private ServletContext servletContext;
    private ResourceMng resourceMng;
    private TplManager tplManager;

    @RequestMapping({"/template/v_left.do"})
    public String left(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String tplReal = this.servletContext.getRealPath(web.getTemplateRel(request));
        String tplName = MessageResolver.getMessage(request, "template.tplName", new Object[0]);
        FileWrap wrap = this.manager.getTplFileWrap(tplReal, tplName);
        model.addAttribute("treeRoot", wrap);
        return "template/left";
    }

    @RequestMapping(value = {"/template/o_swfupload.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public void swfUpload(String root, @RequestParam(value = "Filedata", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IllegalStateException, IOException {
        this.tplManager.save(root, file);
        model.addAttribute("root", root);
        log.info("file upload seccess: {}, size:{}.", file
                .getOriginalFilename(), Long.valueOf(file.getSize()));
        ResponseUtils.renderText(response, "");
    }

    @RequestMapping(value = {"/template/v_tree.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String tree(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String root = RequestUtils.getQueryParam(request, "root");
        log.debug("tree path={}", root);

        if ((StringUtils.isBlank(root)) || ("source".equals(root))) {
            root = web.getTemplateRel1();
            model.addAttribute("isRoot", Boolean.valueOf(true));
        } else {
            model.addAttribute("isRoot", Boolean.valueOf(false));
        }
        List tplList = this.tplManager.getChild(root);
        model.addAttribute("tplList", tplList);
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=UTF-8");
        return "template/tree";
    }

    @RequestMapping(value = {"/template/v_list.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String list(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String root = (String) model.get("root");
        if (root == null) {
            root = RequestUtils.getQueryParam(request, "root");
        }
        log.debug("list Template root: {}", root);
        if (StringUtils.isBlank(root)) {
            root = new String(web.getTemplateRelBuff());
        }
        String rel = root.substring(new String(web.getTemplateRelBuff()).length());
        if (rel.length() == 0) {
            rel = "/";
        }
        model.addAttribute("root", root);
        model.addAttribute("rel", rel);
        model.addAttribute("list", this.tplManager.getChild(root));
        return "template/list";
    }

    @RequestMapping({"/template/o_create_dir.do"})
    public String createDir(String relPath, String dirName, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String real = this.servletContext.getRealPath(web.getTemplateRel(relPath, request));
        File file = new File(real, dirName);

        if (!file.exists()) {
            file.mkdirs();
        }
        model.addAttribute("relPath", relPath);
        return list(request, model);
    }

    @RequestMapping(value = {"/template/v_rename.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String renameInput(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String root = RequestUtils.getQueryParam(request, "root");
        String name = RequestUtils.getQueryParam(request, "name");
        String origName = name.substring(web.getTemplateRel(request).length());
        model.addAttribute("origName", origName);
        model.addAttribute("root", root);
        return "template/rename";
    }

    @RequestMapping({"/template/o_rename.do"})
    public String renameUpdate(String relPath, String origName, String newName, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        String real = this.servletContext.getRealPath(web.getTemplateRel(relPath, request));
        File origFile = new File(real, origName);

        File newFile = new File(real, newName);
        origFile.renameTo(newFile);
        log.info("rename template dir {} to {}", origFile.getAbsolutePath(),
                newFile.getAbsolutePath());
        model.addAttribute("relPath", relPath);
        return list(request, model);
    }

    @RequestMapping(value = {"/template/v_add.do"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String add(HttpServletRequest request, ModelMap model) {
        String root = RequestUtils.getQueryParam(request, "root");
        model.addAttribute("root", root);
        return "template/add";
    }

    @RequestMapping({"/template/v_edit.do"})
    public String edit(HttpServletRequest request, ModelMap model) {
        String root = RequestUtils.getQueryParam(request, "root");
        String name = RequestUtils.getQueryParam(request, "name");
        model.addAttribute("template", this.tplManager.get(name));
        model.addAttribute("root", root);
        return "template/edit";
    }

    @RequestMapping({"/template/o_save.do"})
    public String save(String root, String filename, String source, HttpServletRequest request, ModelMap model) {
        String name = root + "/" + filename + ".html";
        this.tplManager.save(name, source, false);
        model.addAttribute("root", root);
        log.info("save Template name={}", filename);
        return "redirect:v_list.do";
    }

    @RequestMapping({"/template/o_update.do"})
    public void update(String root, String name, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        this.tplManager.update(name, source);
        log.info("update Template name={}.", name);
        model.addAttribute("root", root);
        ResponseUtils.renderJson(response, "{success:true}");
    }

    @RequestMapping({"/template/o_delete.do"})
    public String delete(String root, String[] names, HttpServletRequest request, ModelMap model) {
        int count = this.tplManager.delete(names);
        log.info("delete Template count: {}", Integer.valueOf(count));
        for (String name : names) {
            log.info("delete Template name={}", name);
        }
        model.addAttribute("root", root);
        return list(request, model);
    }

    @RequestMapping({"/template/o_delete_single.do"})
    public String deleteSingle(HttpServletRequest request, ModelMap model) {
        String root = RequestUtils.getQueryParam(request, "root");
        String name = RequestUtils.getQueryParam(request, "name");
        int count = this.tplManager.delete(new String[]{name});
        log.info("delete Template {}, count {}", name, Integer.valueOf(count));
        model.addAttribute("root", root);
        return list(request, model);
    }

    @RequestMapping({"/template/v_solution.do"})
    public String solutionInput(HttpServletRequest request, ModelMap model) {
        Website site = SiteUtils.getWeb(request);
        String[] solutions = this.resourceMng.getSolutions(site.getTplPath());
        model.addAttribute("solutions", solutions);
        model.addAttribute("defSolution", site.getTplSolution());
        model.addAttribute("defMobileSolution", site.getTplMobileSolution());
        return "template/solution";
    }

    @RequestMapping({"/template/o_export.do"})
    public void templateExport(HttpServletRequest request, HttpServletResponse response) {
        String solution = request.getParameter("solution");
        Website site = SiteUtils.getWeb(request);
        List fileEntrys = this.resourceMng.export(site, solution);
        response.setContentType("application/x-download;charset=UTF-8");
        response.addHeader("Content-disposition", "filename=template-" +
                solution + ".zip");
        try {
            Zipper.zip(response.getOutputStream(), fileEntrys, "GBK");
        } catch (IOException e) {
            log.error("export template error!", e);
        }
    }

    @RequestMapping({"/template/o_solution.do"})
    public String solutionUpdate(HttpServletRequest request, ModelMap model) {
        return solutionInput(request, model);
    }

    @RequestMapping({"/template/o_import.do"})
    public String importSubmit(@RequestParam(value = "tplZip", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IOException {
        Website site = SiteUtils.getWeb(request);
        File tempFile = File.createTempFile("tplZip", "temp");
        file.transferTo(tempFile);
        this.resourceMng.imoport(tempFile, site);
        tempFile.delete();
        return solutionInput(request, model);
    }

    public void setTplManager(TplManager tplManager) {
        this.tplManager = tplManager;
    }

    @Autowired
    public void setResourceMng(ResourceMng resourceMng) {
        this.resourceMng = resourceMng;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

