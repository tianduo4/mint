package com.mint.cms.api.admin;

import com.mint.cms.api.ApiResponse;
import com.mint.cms.api.ApiValidate;
import com.mint.cms.entity.ReceiverMessage;
import com.mint.cms.entity.ShopAdmin;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.manager.MessageMng;
import com.mint.cms.manager.ReceiverMessageMng;
import com.mint.cms.manager.ShopMemberMng;
import com.mint.cms.web.CmsThreadVariable;
import com.mint.cms.web.SignValidate;
import com.mint.common.page.Pagination;
import com.mint.common.page.SimplePage;
import com.mint.common.web.CookieUtils;
import com.mint.common.web.ResponseUtils;
import com.mint.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReceiveMessageApiController {

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

