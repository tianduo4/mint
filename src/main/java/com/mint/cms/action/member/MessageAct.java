package com.mint.cms.action.member;

import com.mint.cms.entity.Message;
import com.mint.cms.entity.ReceiverMessage;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.MessageMng;
import com.mint.cms.manager.ReceiverMessageMng;
import com.mint.cms.web.ShopFrontHelper;
import com.mint.cms.web.SiteUtils;
import com.mint.cms.web.threadvariable.MemberThread;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.common.web.springmvc.MessageResolver;
import com.mint.core.entity.Website;
import com.mint.core.web.WebErrors;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageAct {
    private static final Logger log = LoggerFactory.getLogger(MessageAct.class);
    public static final String MESSAGE_IN_BOX_LIST = "tpl.messageInBoxLists";
    public static final String MESSAGE_TRASH_LIST = "tpl.messageTrashLists";
    public static final String MESSAGE_MNG = "tpl.messageMng";
    public static final String MESSAGE_READ = "tpl.messageRead";

    @Autowired
    private MessageMng messageMng;

    @Autowired
    private ReceiverMessageMng receiverMessageMng;

    @RequestMapping({"/member/message_mng.jspx"})
    public String message_mng(String cl, Integer box, String msg, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        ShopFrontHelper.setCommonData(request, model, web, 1);

        ShopFrontHelper.frontPageData(request, model);

        if (member == null) {
            return "redirect:../login.jspx";
        }
        if (box != null)
            model.addAttribute("box", box);
        else {
            model.addAttribute("box", Integer.valueOf(0));
        }
        model.addAttribute("msg", msg);
        model.addAttribute("cl", cl);
        return web.getTplSys("message", MessageResolver.getMessage(request, "tpl.messageMng", new Object[0]), request);
    }

    @RequestMapping({"/member/message_list.jspx"})
    public String message_inbox(Integer pageNo, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        ShopFrontHelper.setCommonData(request, model, web, 1);

        if (member == null) {
            return "redirect:../login.jspx";
        }
        Pagination pagination = null;
        String returnPage = "tpl.messageInBoxLists";
        if (box.equals(Integer.valueOf(0))) {
            pagination = this.receiverMessageMng.getPage(null, member
                            .getId(), title, sendBeginTime, sendEndTime, status, box,
                    Boolean.valueOf(false), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
            returnPage = "tpl.messageInBoxLists";
        } else if (box.equals(Integer.valueOf(3))) {
            pagination = this.receiverMessageMng.getPage(member.getId(),
                    member.getId(), title, sendBeginTime, sendEndTime, status,
                    box, Boolean.valueOf(false), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
            returnPage = "tpl.messageTrashLists";
        }
        model.addAttribute("msg", request.getAttribute("msg"));
        model.addAttribute("pagination", pagination);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("title", title);
        model.addAttribute("sendBeginTime", sendBeginTime);
        model.addAttribute("sendEndTime", sendEndTime);
        model.addAttribute("status", status);
        model.addAttribute("box", box);
        return web.getTplSys("message", MessageResolver.getMessage(request, returnPage, new Object[0]), request);
    }

    @RequestMapping({"/member/message_read.jspx"})
    public String message_read(Long id, Integer box, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        ShopFrontHelper.setCommonData(request, model, web, 1);
        if (member == null) {
            return "redirect:../login.jspx";
        }
        ReceiverMessage message = this.receiverMessageMng.findById(id);
        if (message != null) {
            if ((!message.getMsgReceiverUser().equals(member)) &&
                    (!message.getMsgSendUser().equals(member))) {
                WebErrors errors = WebErrors.create(request);
                errors.addErrorCode("error.noPermissionsView");
                return ShopFrontHelper.showError(request, response, model, errors);
            }

            if (message.getMsgReceiverUser().equals(member)) {
                message.setMsgStatus(true);
                this.receiverMessageMng.update(message);
                log.info("member Message read Message success. id={}", id);
            }
            model.addAttribute("message", message);
        } else {
            Message msg = this.messageMng.findById(id);
            model.addAttribute("message", msg);
        }
        model.addAttribute("box", box);
        return web.getTplSys("message", MessageResolver.getMessage(request, "tpl.messageRead", new Object[0]), request);
    }

    @RequestMapping({"/member/message_trash.jspx"})
    public void message_trash(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        ShopMember member = MemberThread.get();
        JSONObject object = new JSONObject();

        if (member == null) {
            object.put("result", false);
        } else {
            for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
                Message message = this.messageMng.findById(ids[i.intValue()]);
                ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
                if ((message != null) && (message.getMsgSendUser().equals(member))) {
                    message.setMsgBox(Integer.valueOf(3));
                    receiverMessage = new ReceiverMessage();
                    receiverMessage.setMsgBox(Integer.valueOf(3));
                    receiverMessage.setMsgContent(message.getMsgContent());
                    receiverMessage.setMsgSendUser(message.getMsgSendUser());
                    receiverMessage.setMsgReceiverUser(member);
                    receiverMessage.setMsgStatus(message.getMsgStatus());
                    receiverMessage.setMsgTitle(message.getMsgTitle());
                    receiverMessage.setSendTime(message.getSendTime());
                    receiverMessage.setMessage(null);

                    this.receiverMessageMng.save(receiverMessage);

                    Set receiverMessages = message
                            .getReceiverMsgs();
                    if ((receiverMessages != null) && (receiverMessages.size() > 0)) {
                        Iterator it = receiverMessages.iterator();

                        while (it.hasNext()) {
                            ReceiverMessage tempReceiverMessage = (ReceiverMessage) it.next();
                            tempReceiverMessage.setMessage(null);
                            this.receiverMessageMng.update(tempReceiverMessage);
                        }
                    }
                    this.messageMng.deleteById(ids[i.intValue()]);
                }
                if ((receiverMessage != null) &&
                        (receiverMessage.getMsgReceiverUser().equals(member))) {
                    receiverMessage.setMsgBox(Integer.valueOf(3));
                    this.receiverMessageMng.update(receiverMessage);
                }
                log.info("member Message trash Message success. id={}",
                        ids[i.intValue()]);
            }
            object.put("result", true);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @RequestMapping({"/member/message_revert.jspx"})
    public void message_revert(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        ShopMember member = MemberThread.get();
        JSONObject object = new JSONObject();

        if (member == null) {
            object.put("result", false);
        } else {
            for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
                ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);

                if ((receiverMessage != null) &&
                        (receiverMessage.getMsgReceiverUser().equals(member))) {
                    receiverMessage.setMsgBox(Integer.valueOf(0));
                    this.receiverMessageMng.update(receiverMessage);
                }
                log.info("member Message revert Message success. id={}",
                        ids[i.intValue()]);
            }
            object.put("result", true);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @RequestMapping({"/member/message_empty.jspx"})
    public void message_empty(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        ShopMember member = MemberThread.get();
        JSONObject object = new JSONObject();

        if (member == null) {
            object.put("result", false);
        } else {
            for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
                ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
                if ((receiverMessage != null) &&
                        (receiverMessage.getMsgReceiverUser().equals(member))) {
                    this.receiverMessageMng.deleteById(ids[i.intValue()]);
                } else {
                    Message message = receiverMessage.getMessage();
                    if (receiverMessage.getMsgBox().equals(Integer.valueOf(3))) {
                        receiverMessage.setMessage(null);
                        if (message != null)
                            this.messageMng.deleteById(message.getId());
                    } else {
                        receiverMessage.setMessage(null);
                    }
                    if ((message != null) &&
                            (message.getMsgSendUser().equals(member))) {
                        this.messageMng.deleteById(message.getId());
                    }
                }
                log.info("member Message empty Message success. id={}",
                        ids[i.intValue()]);
            }
            object.put("result", true);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @RequestMapping({"/member/message_countUnreadMsg.jspx"})
    public void findUnreadMessagesByUser(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        ShopMember member = MemberThread.get();
        Website web = SiteUtils.getWeb(request);

        ShopFrontHelper.setCommonData(request, model, web, 1);
        JSONObject object = new JSONObject();
        if (member == null) {
            object.put("result", false);
        } else {
            List receiverMessages = this.receiverMessageMng
                    .getList(null, member.getId(), null, null,
                            null, Boolean.valueOf(false), Integer.valueOf(0), Boolean.valueOf(false));
            object.put("result", true);
            if ((receiverMessages != null) && (receiverMessages.size() > 0))
                object.put("count", receiverMessages.size());
            else {
                object.put("count", 0);
            }
            object.put("result", true);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @RequestMapping({"/member/message_delete.jspx"})
    public String message_delete(Long[] ids, String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();

        ShopFrontHelper.setCommonData(request, model, web, 1);
        if (member == null) {
            return "redirect:../login.jspx";
        }

        Boolean permission = Boolean.valueOf(true);
        if ((ids != null) && (ids.length > 0)) {
            for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
                Message message = this.messageMng.findById(ids[i.intValue()]);

                if ((message.getMsgReceiverUser().equals(member)) ||
                        (message.getMsgSendUser().equals(member))) continue;
                permission = Boolean.valueOf(false);
            }

            if (permission.booleanValue()) {
                this.messageMng.deleteByIds(ids);
                for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1))
                    log.info("member Message delete Message success. id={}", ids[i.intValue()]);
            } else {
                WebErrors errors = WebErrors.create(request);
                errors.addErrorCode("error.noPermissionsView");
                return ShopFrontHelper.showError(request, response, model, errors);
            }
        }
        return ShopFrontHelper.showSuccess(request, model, nextUrl);
    }
}

