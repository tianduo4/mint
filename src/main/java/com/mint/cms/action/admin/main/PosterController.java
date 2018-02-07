package com.mint.cms.action.admin.main;

import com.mint.cms.entity.Poster;
import com.mint.cms.manager.PosterMng;
import com.mint.common.web.ResponseUtils;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PosterController {

    @Autowired
    private PosterMng manager;

    @RequestMapping({"/poster/v_list.do"})
    public String list(HttpServletRequest request, ModelMap model) {
        List listPoster = this.manager.getPage();
        model.addAttribute("listPoster", listPoster);
        return "poster/list";
    }

    @RequestMapping({"/poster/o_updateCare.do"})
    public String o_updateCare(String val, Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Poster bean = this.manager.findById(id);
        bean.setInterUrl(val);
        this.manager.update(bean);
        ResponseUtils.renderJson(response, "success");
        return null;
    }

    @RequestMapping({"/poster/o_updateImageAddres.do"})
    public String updateImageAddres(String val, Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Poster bean = this.manager.findById(id);
        bean.setPicUrl(val);
        this.manager.update(bean);
        ResponseUtils.renderJson(response, "success");
        return null;
    }

    @RequestMapping({"/poster/o_update.do"})
    public String edit(String[] picUrl, String[] interUrl, Integer pageNo, HttpServletRequest request, ModelMap model) {
        if ((picUrl != null) && (picUrl.length > 0)) {
            for (int i = 0; i < picUrl.length; i++) {
                Poster p = new Poster();
                p.setTime(new Date());
                p.setPicUrl(picUrl[i]);
                p.setInterUrl(interUrl[i]);
                this.manager.saveOrUpdate(p);
            }
        }

        return "redirect:v_list.do";
    }

    @RequestMapping({"/poster/o_delPoster.do"})
    public String delete(Integer id, Integer pageNo, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        this.manager.deleteById(id);
        ResponseUtils.renderJson(response, "success");
        return null;
    }
}

