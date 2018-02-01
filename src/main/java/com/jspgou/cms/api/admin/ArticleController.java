package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.entity.ShopArticle;
import com.jspgou.cms.entity.ShopChannel;
import com.jspgou.cms.manager.ShopArticleMng;
import com.jspgou.cms.manager.ShopChannelMng;
import com.jspgou.cms.web.CmsThreadVariable;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.WebErrors;

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
public class ArticleController {

    @Autowired
    private ShopArticleMng shopArticleMng;

    @Autowired
    private ShopChannelMng shopChannelMng;

    @RequestMapping({"/article/list"})
    public void list(Integer cid, Integer pageSize, Integer pageNo, HttpServletRequest request, HttpServletResponse response) {
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
                Pagination pagination = this.shopArticleMng.getPage(cid, CmsThreadVariable.getSite().getId(), pageNo.intValue(), pageSize.intValue());
                List<ShopArticle> articles = (List<ShopArticle>) pagination.getList();
                JSONArray jsons = new JSONArray();
                for (ShopArticle article : articles) {
                    jsons.add(article.converToJson());
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

    @RequestMapping({"/article/channelList"})
    public void getParentChannel(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            List<ShopChannel> list = this.shopChannelMng.getArticleList(CmsThreadVariable.getSite().getId());
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

    @SignValidate
    @RequestMapping({"/article/save"})
    public void save(ShopArticle article, Integer channelId, String content, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{channelId, article.getTitle(), article.getDisabled()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                article.setWebsite(CmsThreadVariable.getSite());
                this.shopArticleMng.save(article, channelId, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/article/get"})
    public void get(Long id, HttpServletRequest request, HttpServletResponse response) {
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
                ShopArticle article = new ShopArticle();
                if ((id != null) && (id.longValue() != 0L)) {
                    article = this.shopArticleMng.findById(id);
                }
                if (article != null) {
                    body = article.converToJson().toString();
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

    @SignValidate
    @RequestMapping({"/article/update"})
    public void update(ShopArticle article, Integer channelId, String content, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{channelId, article.getTitle(), article.getDisabled()});

            if (errors.hasErrors()) {
                code = 202;
                message = "\"param error\"";
            } else {
                this.shopArticleMng.update(article, channelId, content);
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
    @RequestMapping({"/article/delete"})
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
                Long[] id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                this.shopArticleMng.deleteByIds(id);
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }
}

