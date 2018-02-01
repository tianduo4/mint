package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.service.WeixinSvc;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import com.jspgou.plug.weixin.entity.WeixinMenu;
import com.jspgou.plug.weixin.manager.WeixinMenuMng;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeixinMenuController {

    @Autowired
    private WeixinMenuMng manager;

    @Autowired
    private WeixinSvc weixinSvc;

    @RequestMapping({"/weixinMenu/list"})
    public void list(Integer parentId, Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
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
                Pagination pagination = this.manager.getPage(SiteUtils.getWebId(request), parentId, SimplePage.cpn(pageNo), pageSize.intValue());
                List<WeixinMenu> menus = (List<WeixinMenu>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (WeixinMenu menu : menus) {
                    jsons.add(menu.converToJson());
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
    @RequestMapping({"/weixinMenu/save"})
    public void save(WeixinMenu bean, Integer parentId, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getName()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                bean.setSite(SiteUtils.getWeb(request));
                if (parentId != null) {
                    WeixinMenu parent = this.manager.findById(parentId);
                    bean.setParent(parent);
                }
                this.manager.save(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/weixinMenu/get"})
    public void get(Integer id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WeixinMenu entity = new WeixinMenu();
            if ((id != null) && (id.intValue() != 0)) {
                entity = this.manager.findById(id);
            }
            body = entity.converToJson().toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/weixinMenu/update"})
    public void update(WeixinMenu bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(), bean.getName()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                this.manager.update(bean);
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
    @RequestMapping({"/weixinMenu/delete"})
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
                    id[i] = Integer.valueOf(str[i]);
                }
                this.manager.deleteByIds(id);
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/weixinMenu/menu"})
    public void menu(WeixinMenu bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getName()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                Website site = SiteUtils.getWeb(request);
                List menus = this.manager.getList(site.getId(), Integer.valueOf(100));
                this.weixinSvc.createMenu(getMenuJsonString(menus));
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    public String getMenuJsonString(List<WeixinMenu> menus) {
        String strJson = "{\"button\":[";

        for (int i = 0; i < menus.size(); i++) {
            strJson = strJson + "{\t";
            WeixinMenu menu = (WeixinMenu) menus.get(i);
            if (menu.getChild().size() > 0) {
                strJson = strJson +
                        "\"name\":\"" + menu.getName() + "\"," +
                        "\"sub_button\":[";
                Set sets = menu.getChild();
                Iterator iter = sets.iterator();
                int no = 1;
                while (iter.hasNext()) {
                    if (no > 5) {
                        break;
                    }
                    if (no == 1)
                        strJson = strJson + "{";
                    else {
                        strJson = strJson + ",{";
                    }
                    WeixinMenu child = (WeixinMenu) iter.next();
                    if (child.getType().equals("click"))
                        strJson = strJson +
                                "\"type\":\"click\"," +
                                "\"name\":\"" + child.getName() + "\"," +
                                "\"key\":\"" + child.getKey() + "\"}";
                    else {
                        strJson = strJson +
                                "\"type\":\"view\"," +
                                "\"name\":\"" + child.getName() + "\"," +
                                "\"url\":\"" + child.getUrl() + "\"}";
                    }
                    no++;
                }

                strJson = strJson + "]";
            } else if (menu.getType().equals("click")) {
                strJson = strJson +
                        "\"type\":\"click\"," +
                        "\"name\":\"" + menu.getName() + "\"," +
                        "\"key\":\"" + menu.getKey() + "\"";
            } else {
                strJson = strJson +
                        "\"type\":\"view\"," +
                        "\"name\":\"" + menu.getName() + "\"," +
                        "\"url\":\"" + menu.getUrl() + "\"";
            }
            if (i == menus.size() - 1)
                strJson = strJson + "}";
            else {
                strJson = strJson + "},";
            }
        }
        strJson = strJson + "]}";
        return strJson;
    }
}

