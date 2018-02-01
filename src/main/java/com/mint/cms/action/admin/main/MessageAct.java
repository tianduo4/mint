package com.mint.cms.action.admin.main;

import com.mint.cms.entity.Message;
import com.mint.cms.entity.ReceiverMessage;
import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.MessageMng;
import com.mint.cms.manager.ReceiverMessageMng;
import com.mint.cms.manager.ShopMemberGroupMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.service.LoginSvc;
import com.mint.cms.web.ShopFrontHelper;
import com.mint.cms.web.threadvariable.AdminThread;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.core.entity.Website;
import com.mint.core.web.SiteUtils;
import com.mint.core.web.WebErrors;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private LoginSvc loginSvc;

    @Autowired
    private MessageMng manager;

    @Autowired
    private MessageMng messageMng;

    @Autowired
    private ShopMemberGroupMng groupMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @Autowired
    private ReceiverMessageMng receiverMessageMng;

    @RequestMapping({"/message/v_list.do"})
    public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
        ShopAdmin member = AdminThread.get();
        Pagination pagination = this.messageMng.getPage(member.getId(), SimplePage.cpn(pageNo),
                CookieUtils.getPageSize(request));

        model.addAttribute("pagination", pagination);
        return "message/list";
    }

    @RequestMapping({"/message/v_add.do"})
    public String add(ModelMap model) {
        List groups = this.groupMng.getList();
        model.addAttribute("groupList", groups);
        return "message/add";
    }

    @RequestMapping({"/message/v_trashbox.do"})
    public String trashbox(Integer pageNo, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        ShopAdmin member = AdminThread.get();
        Pagination pagination = this.receiverMessageMng.getPage(member.getId(),
                member.getAdmin().getUser().getId(), title, sendBeginTime, sendEndTime, status,
                Integer.valueOf(3), Boolean.valueOf(false), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
        model.addAttribute("pagination", pagination);
        model.addAttribute("msg", request.getAttribute("msg"));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("title", title);
        model.addAttribute("sendBeginTime", sendBeginTime);
        model.addAttribute("sendEndTime", sendEndTime);
        model.addAttribute("status", status);
        model.addAttribute("box", box);
        return "message/trashbox";
    }

    @RequestMapping({"/message/v_send.do"})
    public String send(Message message, String username, Long groupId, Integer pageNo, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Website web = SiteUtils.getWeb(request);

        ShopAdmin member = AdminThread.get();

        Date now = new Date();
        ReceiverMessage receiverMessage = new ReceiverMessage();
        ShopMember msgReceiverUser = null;
        ShopMember Receiver = null;
        if (StringUtils.isNotBlank(username)) {
            Receiver = this.shopMemberMng.findUsername(username);
        }

        if (Receiver != null) {
            messageInfoSet(message, receiverMessage, member, Receiver, now,
                    request);
        }

        if ((groupId != null) && (groupId.longValue() != -1L)) {
            if (groupId.longValue() == 0L) {
                List users = this.shopMemberMng.getList(null, null);
                if ((users != null) && (users.size() > 0)) {
                    for (int i = 0; i < users.size(); i++) {
                        ShopMember tempUser = (ShopMember) users.get(i);
                        Message tempMsg = new Message();
                        tempMsg.setMsgTitle(message.getMsgTitle());
                        tempMsg.setMsgContent(message.getMsgContent());
                        ReceiverMessage tempReceiverMsg = new ReceiverMessage();
                        if (msgReceiverUser != null) {
                            if (!tempUser.equals(msgReceiverUser))
                                messageInfoSet(tempMsg, tempReceiverMsg,
                                        member, tempUser, now, request);
                        } else
                            messageInfoSet(tempMsg, tempReceiverMsg, member,
                                    tempUser, now, request);
                    }
                }
            } else {
                List users = this.shopMemberMng.getList(null, groupId);
                if ((users != null) && (users.size() > 0)) {
                    for (int i = 0; i < users.size(); i++) {
                        ShopMember tempUser = (ShopMember) users.get(i);
                        Message tempMsg = new Message();
                        tempMsg.setMsgTitle(message.getMsgTitle());
                        tempMsg.setMsgContent(message.getMsgContent());
                        ReceiverMessage tempReceiverMsg = new ReceiverMessage();
                        if (msgReceiverUser != null) {
                            if (!tempUser.equals(msgReceiverUser))
                                messageInfoSet(tempMsg, tempReceiverMsg,
                                        member, tempUser, now, request);
                        } else {
                            messageInfoSet(tempMsg, tempReceiverMsg, member,
                                    tempUser, now, request);
                        }
                    }
                }
            }
        }

        return list(pageNo, request, model);
    }

    @RequestMapping({"/message/v_edit.do"})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("message", this.manager.findById(id));
        return "message/edit";
    }

    @RequestMapping({"/message/o_save.do"})
    public String save(Message bean, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateSave(bean, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.save(bean);
        log.info("save Message id={}", bean.getId());
        return "redirect:v_list.do";
    }

    @RequestMapping({"/message/o_update.do"})
    public String update(Message bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
        bean = this.manager.update(bean);
        log.info("update Message id={}.", bean.getId());
        return list(pageNo, request, model);
    }

    @RequestMapping({"/message/v_read.do"})
    public String read(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopAdmin member = AdminThread.get();

        Message message = this.messageMng.findById(id);
        ReceiverMessage receiverMessage = this.receiverMessageMng.findById(id);
        if (message != null) {
            if (message.getMsgSendUser().equals(member)) {
                message.setMsgStatus(true);
                this.messageMng.update(message);
            }
            model.addAttribute("message", message);
        } else if (receiverMessage != null) {
            if ((!receiverMessage.getMsgReceiverUser().getUsername().equals(member.getUsername())) &&
                    (!receiverMessage.getMsgSendUser().getUsername().equals(member.getUsername()))) {
                WebErrors errors = WebErrors.create(request);
                errors.addErrorCode("error.noPermissionsView");
                return ShopFrontHelper.showError(request, response, model, errors);
            }

            if (receiverMessage.getMsgReceiverUser().getUsername().equals(member.getUsername())) {
                receiverMessage.setMsgStatus(true);
                this.receiverMessageMng.update(receiverMessage);
                log.info("member Message read Message success. id={}", id);
            }
            model.addAttribute("message", receiverMessage);
        } else {
            Message msg = this.messageMng.findById(id);
            model.addAttribute("message", msg);
        }
        return "message/read";
    }

    @RequestMapping({"/message/v_trash.do"})
    public void trash(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        Website web = SiteUtils.getWeb(request);
        ShopAdmin member = AdminThread.get();
        JSONObject object = new JSONObject();

        if (member == null) {
            object.put("result", false);
        } else {
            for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
                Message message = this.messageMng.findById(ids[i.intValue()]);
                ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);
                if ((message != null) && (message.getMsgSendUser().equals(member))) {
                    receiverMessage = new ReceiverMessage();
                    receiverMessage.setMsgBox(Integer.valueOf(3));
                    receiverMessage.setMsgContent(message.getMsgContent());
                    receiverMessage.setMsgSendUser(message.getMsgSendUser());
                    receiverMessage.setMsgReceiverUser(message
                            .getMsgReceiverUser());
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
                        (receiverMessage.getMsgReceiverUser().getMember().getUsername().equals(member.getUsername()))) {
                    receiverMessage.setMsgBox(Integer.valueOf(3));
                    this.receiverMessageMng.update(receiverMessage);
                }
                log.info("member Message trash Message success. id={}", ids[i.intValue()]);
            }
            object.put("result", true);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @RequestMapping({"/message/v_revert.do"})
    public void revert(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        Website web = SiteUtils.getWeb(request);
        ShopAdmin member = AdminThread.get();
        JSONObject object = new JSONObject();

        if (member == null) {
            object.put("result", false);
        } else {
            for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
                ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);

                if ((receiverMessage != null) && (receiverMessage.getMsgSendUser().equals(member))) {
                    receiverMessage.setMsgBox(Integer.valueOf(1));
                    this.receiverMessageMng.update(receiverMessage);
                    if (receiverMessage.getMsgBox().intValue() == 1) {
                        Message message = new Message();
                        message.setMsgBox(receiverMessage.getMsgBox());
                        message.setId(receiverMessage.getId());
                        message.setMsgSendUser(receiverMessage.getMsgSendUser());
                        message.setMsgReceiverUser(receiverMessage
                                .getMsgReceiverUser());
                        message.setMsgStatus(false);
                        message.setMsgTitle(receiverMessage.getTitleHtml());
                        message.setMsgContent(receiverMessage.getContentHtml());
                        message.setSendTime(receiverMessage.getSendTime());
                        this.messageMng.save(message);
                    }
                    this.receiverMessageMng.deleteById(ids[i.intValue()]);
                }

                if ((receiverMessage != null) &&
                        (receiverMessage.getMsgReceiverUser().getUsername().equals(member.getUsername()))) {
                    receiverMessage.setMsgBox(Integer.valueOf(0));
                    this.receiverMessageMng.update(receiverMessage);
                }
                log.info("member Message revert Message success. id={}", ids[i.intValue()]);
            }
            object.put("result", true);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @RequestMapping({"/message/v_empty.do"})
    public void empty(Long[] ids, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        Website web = SiteUtils.getWeb(request);
        ShopAdmin member = AdminThread.get();
        JSONObject object = new JSONObject();

        if (member == null) {
            object.put("result", false);
        } else {
            for (Integer i = Integer.valueOf(0); i.intValue() < ids.length; i = Integer.valueOf(i.intValue() + 1)) {
                ReceiverMessage receiverMessage = this.receiverMessageMng.findById(ids[i.intValue()]);

                if ((receiverMessage != null) &&
                        (receiverMessage.getMsgSendUser().equals(member))) {
                    this.receiverMessageMng.deleteById(ids[i.intValue()]);
                } else if ((receiverMessage != null) &&
                        (receiverMessage.getMsgReceiverUser().getUsername().equals(member.getUsername()))) {
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
                log.info("member Message empty Message success. id={}", ids[i.intValue()]);
            }
            object.put("result", true);
        }
        ResponseUtils.renderJson(response, object.toString());
    }

    @RequestMapping({"/message/v_findUser.do"})
    public void findUserByUserName(String username, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
        Website web = SiteUtils.getWeb(request);
        JSONObject object = new JSONObject();

        ShopAdmin member = AdminThread.get();
        if (member == null) {
            object.put("result", false);
        }
        Boolean exist = Boolean.valueOf(this.shopMemberMng.usernameNotExist(username));
        ShopMember user = this.shopMemberMng.findUsername(username);
        if (user != null) {
            if (member.getUsername().equals(user.getUsername())) {
                object.put("result", true);
                object.put("frontuser", user.getUsername());
            }
        } else if (exist.booleanValue()) {
            object.put("result", true);
            object.put("exist", exist);
        }

        ResponseUtils.renderJson(response, object.toString());
    }

    private WebErrors validateSave(Message bean, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);

        return errors;
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateUpdate(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        Website web = SiteUtils.getWeb(request);
        if (vldExist(id, web.getId(), errors)) {
            return errors;
        }
        return errors;
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
        Message entity = this.manager.findById(id);

        return errors.ifNotExist(entity, Message.class, id);
    }

    private void messageInfoSet(Message message, ReceiverMessage receiverMessage, ShopAdmin sendUser, ShopMember receiverUser, Date sendTime, HttpServletRequest request) {
        message.setMsgBox(Integer.valueOf(1));
        message.setMsgSendUser(sendUser);
        message.setMsgReceiverUser(receiverUser);
        message.setMsgStatus(false);
        message.setSendTime(sendTime);
        this.messageMng.save(message);
        receiverMessage.setMsgBox(Integer.valueOf(0));
        receiverMessage.setMsgContent(message.getMsgContent());
        receiverMessage.setMsgSendUser(sendUser);
        receiverMessage.setMsgReceiverUser(receiverUser);
        receiverMessage.setMsgStatus(false);
        receiverMessage.setMsgTitle(message.getMsgTitle());
        receiverMessage.setSendTime(sendTime);
        receiverMessage.setMessage(message);

        this.receiverMessageMng.save(receiverMessage);
        log.info("member Message send Message success. id={}", message.getId());
    }
}

