package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.entity.Message;
import com.jspgou.cms.entity.ReceiverMessage;
import com.jspgou.cms.entity.ShopAdmin;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.MessageMng;
import com.jspgou.cms.manager.ReceiverMessageMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.cms.web.CmsThreadVariable;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Admin;
import com.jspgou.core.entity.User;
import com.jspgou.core.web.WebErrors;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {

    @Autowired
    private MessageMng messageMng;

    @Autowired
    private ReceiverMessageMng receiverMessageMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @RequestMapping({"/message/list"})
    public void list(Integer pageSize, Integer pageNo, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{pageNo, pageSize});
            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ShopAdmin admin = CmsThreadVariable.getAdminUser();
                Pagination pagination = this.messageMng.getPage(admin.getId(), pageNo.intValue(), pageSize.intValue());
                List<Message> mess = (List<Message>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (Message mes : mess) {
                    jsons.add(mes.converToJson());
                }
                body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/message/recycle"})
    public void recycle(Integer pageNo, Integer pageSize, String title, Date sendBeginTime, Date sendEndTime, Boolean status, Integer box, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);

            errors = ApiValidate.validateRequiredParams(errors, new Object[]{pageNo, pageSize});
            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ShopAdmin admin = CmsThreadVariable.getAdminUser();
                Pagination pagination = this.receiverMessageMng.getPage(admin.getId(),
                        admin.getAdmin().getUser().getId(), title, sendBeginTime, sendEndTime, status,
                        Integer.valueOf(3), Boolean.valueOf(false), SimplePage.cpn(pageNo), CookieUtils.getPageSize(request));
                List<ReceiverMessage> remess = (List<ReceiverMessage>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (ReceiverMessage remes : remess) {
                    jsons.add(remes.converToJson());
                }
                body = jsons.toString() + ",\"totalCount\":" + pagination.getTotalCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/message/send"})
    public void send(Message mes, String username, Long groupId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{mes.getMsgTitle(), mes.getMsgContent()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ShopAdmin admin = CmsThreadVariable.getAdminUser();
                this.messageMng.send(mes, admin, username, groupId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/message/read"})
    public void read(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ShopAdmin admin = CmsThreadVariable.getAdminUser();

                Message mes = this.messageMng.findById(id);
                ReceiverMessage receiverMessage = this.receiverMessageMng.findById(id);
                if (mes != null) {
                    if (mes.getMsgSendUser().equals(admin)) {
                        mes.setMsgStatus(true);
                        this.messageMng.update(mes);
                    }
                    body = mes.converToJson().toString();
                } else if (receiverMessage != null) {
                    if ((!receiverMessage.getMsgReceiverUser().getUsername().equals(admin.getUsername())) &&
                            (!receiverMessage.getMsgSendUser().getUsername().equals(admin.getUsername()))) {
                        code = 500;
                        message = "\"not perms reading\"";
                        ApiResponse apiResponse = new ApiResponse(body, message, code);
                        ResponseUtils.renderApiJson(response, request, apiResponse);
                        return;
                    }

                    if (receiverMessage.getMsgReceiverUser().getUsername().equals(admin.getUsername())) {
                        receiverMessage.setMsgStatus(true);
                        this.receiverMessageMng.update(receiverMessage);
                    }
                    body = receiverMessage.converToJson().toString();
                } else {
                    mes = this.messageMng.findById(id);
                    body = mes.converToJson().toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/message/trash"})
    public void trash(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (!StringUtils.isNotBlank(ids)) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str = ids.split(",");
                Long[] id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }

                ShopAdmin admin = CmsThreadVariable.getAdminUser();
                this.messageMng.trash(admin, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/message/revert"})
    public void revert(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (!StringUtils.isNotBlank(ids)) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str = ids.split(",");
                Long[] id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }

                ShopAdmin admin = CmsThreadVariable.getAdminUser();
                this.messageMng.revert(admin, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/message/clean"})
    public void clean(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (!StringUtils.isNotBlank(ids)) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str = ids.split(",");
                Long[] id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }

                ShopAdmin admin = CmsThreadVariable.getAdminUser();
                this.messageMng.clean(admin, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/message/checkUsername"})
    public void checkUsername(String username, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"Invalid recipient user or user not exist\"";
        int code = 501;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{username});
            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                ShopAdmin admin = CmsThreadVariable.getAdminUser();
                ShopMember user = this.shopMemberMng.findUsername(username);
                if ((user != null) &&
                        (!admin.getUsername().equals(user.getUsername()))) {
                    code = 200;
                    message = "\"success\"";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

