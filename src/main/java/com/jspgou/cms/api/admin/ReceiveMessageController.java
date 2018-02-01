package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
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
import com.jspgou.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReceiveMessageController {

    @Autowired
    private MessageMng messageMng;

    @Autowired
    private ReceiverMessageMng receiverMessageMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    @RequestMapping({"/receivemessage/list"})
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
                ShopMember member = this.shopMemberMng.findUsername(admin.getUsername());
                Pagination pagination = this.receiverMessageMng.getPage(member.getId(), SimplePage.cpn(pageNo), Integer.valueOf(0), CookieUtils.getPageSize(request));
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
    @RequestMapping({"/receivemessage/read"})
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

                ReceiverMessage receiver = this.receiverMessageMng.findById(id);
                if (receiver != null) {
                    if (receiver.getMsgReceiverUser().getUsername().equals(admin.getUsername())) {
                        receiver.setMsgStatus(true);
                        this.receiverMessageMng.update(receiver);
                    }
                } else receiver = this.receiverMessageMng.findById(id);

                body = receiver.converToJson().toString();
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

