package com.mint.cms.action.admin.main;

import com.mint.cms.entity.Message;
import com.mint.cms.entity.ReceiverMessage;
import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.ReceiverMessageMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.web.threadvariable.AdminThread;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReceiverMessageAct {
    private static final Logger log = LoggerFactory.getLogger(ReceiverMessageAct.class);

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private ReceiverMessageMng receiverMessageMng;

    @RequestMapping({"/receiverMessage/v_list.do"})
    public String list(Integer pageNo, Integer box, HttpServletRequest request, ModelMap model) {
        ShopAdmin member = AdminThread.get();
        ShopMember Receiver = this.shopMemberMng.findUsername(member.getUsername());
        Pagination pagination = this.receiverMessageMng.getPage(Receiver.getId(), SimplePage.cpn(pageNo), Integer.valueOf(0),
                CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        return "message/receiver";
    }

    @RequestMapping({"/receiverMessage/v_read.do"})
    public String read(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);

        ShopAdmin member = AdminThread.get();

        ReceiverMessage receiver = this.receiverMessageMng.findById(id);
        if (receiver != null) {
            if (receiver.getMsgReceiverUser().getUsername().equals(member.getUsername())) {
                receiver.setMsgStatus(true);
                this.receiverMessageMng.update(receiver);
            }
            model.addAttribute("message", receiver);
        } else {
            ReceiverMessage msg = this.receiverMessageMng.findById(id);
            model.addAttribute("message", msg);
        }

        return "message/view";
    }

    @RequestMapping({"/receiverMessage/o_delete.do"})
    public String delete(Long[] ids, Integer pageNo, Integer box, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateDelete(ids, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }

        ReceiverMessage[] beans = this.receiverMessageMng.deleteByIds(ids);
        for (ReceiverMessage bean : beans) {
            log.info("delete ReceiverMessage id={}", bean.getId());
        }
        return list(pageNo, box, request, model);
    }

    private WebErrors validateDelete(Long[] ids, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        errors.ifEmpty(ids, "ids");
        for (Long id : ids) {
            vldExist(id, web.getId(), errors);
        }
        return errors;
    }

    private boolean vldExist(Long id, Long webId, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        ReceiverMessage entity = this.receiverMessageMng.findById(id);

        return errors.ifNotExist(entity, Message.class, id);
    }
}

