package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.entity.ShopChannel;
import com.jspgou.cms.manager.ShopChannelMng;
import com.jspgou.cms.web.CmsThreadVariable;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.WebErrors;

import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChannelController {

    @Autowired
    private ShopChannelMng shopChannelMng;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping({"/channel/tree"})
    public void tree(Integer pid, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Website site = CmsThreadVariable.getSite();
            List<ShopChannel> list = null;
            if ((pid != null) && (pid.intValue() == 0))
                list = this.shopChannelMng.getTopList(site.getId());
            else {
                list = this.shopChannelMng.getChildList(site.getId(), pid);
            }
            JSONArray jsons = new JSONArray();
            for (ShopChannel channel : list) {
                jsons.add(channel.converToTreeJson());
            }
            body = jsons.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/channel/list"})
    public void list(Integer pid, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            Website site = CmsThreadVariable.getSite();
            List<ShopChannel> list = null;
            if ((pid != null) && (pid.intValue() == 0))
                list = this.shopChannelMng.getTopList(site.getId());
            else {
                list = this.shopChannelMng.getChildList(site.getId(), pid);
            }
            JSONArray jsons = new JSONArray();
            for (ShopChannel channel : list) {
                jsons.add(channel.converToJson());
            }
            body = jsons.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/channel/save"})
    public void save(ShopChannel channel, Integer pid, String content, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{channel.getName(),
                    channel.getPriority(), channel.getDisplay(), channel.getBlank()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else if (this.shopChannelMng.getByPath(CmsThreadVariable.getSite().getId(), channel.getPath()) == null) {
                channel.setWebsite(CmsThreadVariable.getSite());
                this.shopChannelMng.save(channel, pid, content);
            } else {
                code = 502;
                message = "\"path not repeate\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/channel/getTpl"})
    public void getTpl(Integer type, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{type});
            if (errors.hasErrors()) {
                message = "\"param error\"";
                code = 202;
            } else {
                JSONObject json = new JSONObject();
                Website site = new Website();
                if ((type != null) && ((type.intValue() == 1) || (type.intValue() == 2))) {
                    String tplChannelDirRel = ShopChannel.getChannelTplDirRel(site);
                    String realChannelDir = this.servletContext.getRealPath(tplChannelDirRel);
                    String relChannelPath = tplChannelDirRel.substring(site.getTemplateRel(request).length());
                    String[] channelTpls = ShopChannel.getChannelTpls(type, realChannelDir, relChannelPath);
                    json.put("channelTpls", channelTpls);

                    if (type.intValue() == 2) {
                        String tplContentDirRel = ShopChannel.getContentTplDirRel(site);
                        String realContentDir = this.servletContext.getRealPath(tplContentDirRel);
                        String relContentPath = tplContentDirRel.substring(site.getTemplateRel(request).length());
                        String[] contentTpls = ShopChannel.getContentTpls(type, realContentDir, relContentPath);
                        json.put("contentTpls", contentTpls);
                    }
                    body = json.toString();
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

    @RequestMapping({"/channel/get"})
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
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
                ShopChannel shopChannel = new ShopChannel();
                if ((id != null) && (id.intValue() != 0)) {
                    shopChannel = this.shopChannelMng.findById(id);
                }
                if (shopChannel != null) {
                    body = shopChannel.converToJson().toString();
                } else {
                    code = 206;
                    message = "\"object not found\"";
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

    @RequestMapping({"/channel/getParentList"})
    public void getParentList(Integer id, HttpServletRequest request, HttpServletResponse response) {
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
                Website site = CmsThreadVariable.getSite();
                List<ShopChannel> list = null;
                if ((id != null) && (id.intValue() == 0))
                    list = this.shopChannelMng.getTopList(CmsThreadVariable.getSite().getId());
                else {
                    list = this.shopChannelMng.getListForParent(site.getId(), id);
                }
                JSONArray jsons = new JSONArray();
                for (ShopChannel channel : list) {
                    jsons.add(channel.converToTreeJson());
                }
                body = jsons.toString();
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
    @RequestMapping({"/channel/update"})
    public void update(ShopChannel channel, Integer pid, String content, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{channel.getName(), channel.getPath(),
                    channel.getPriority(), channel.getDisplay(), channel.getBlank()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else if (this.shopChannelMng.getByPath(CmsThreadVariable.getSite().getId(), channel.getPath()) == null) {
                this.shopChannelMng.update(channel, pid, content);
            } else {
                code = 502;
                message = "\"path not repeate\"";
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
    @RequestMapping({"/channel/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            if (!StringUtils.isNotBlank(ids)) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str = ids.split(",");
                Integer[] id = new Integer[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Integer.valueOf(Integer.parseInt(str[i]));
                }
                this.shopChannelMng.deleteByIds(id);
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/channel/updateSort"})
    public void updateSort(String ids, String prioritys, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids, prioritys});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                String[] str = ids.split(",");
                String[] pro = prioritys.split(",");
                Integer[] id = new Integer[str.length];
                Integer[] priority = new Integer[pro.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Integer.valueOf(Integer.parseInt(str[i]));
                    priority[i] = Integer.valueOf(Integer.parseInt(pro[i]));
                }
                this.shopChannelMng.updatePriority(id, priority);
            }
        } catch (IndexOutOfBoundsException e) {
            code = 202;
            message = "\"param error\"";
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

