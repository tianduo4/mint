package com.jspgou.cms.action.directive;

import com.jspgou.cms.action.directive.abs.WebDirective;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.ShopScoreMng;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.page.Pagination;
import com.jspgou.common.web.freemarker.DirectiveUtils;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ScorePageDirective extends WebDirective {
    public static final String TPL_NAME = "ShopScorePage";
    private ShopScoreMng shopScoreMng;
    private WebsiteMng websiteMng;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        ShopMember member = MemberThread.get();
        Website web = getWeb(env, params, this.websiteMng);
        Integer count = Integer.valueOf(getCount(params));
        Boolean status = getBool("status", params);
        Boolean useStatus = getBool("useStatus", params);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = getString("startTime", params);
        String endTime = getString("endTime", params);
        Date start = null;
        Date end = null;
        try {
            if (!StringUtils.isBlank(startTime))
                start = df.parse(startTime);
            else {
                start = null;
            }
            if (!StringUtils.isBlank(endTime))
                end = df.parse(endTime);
            else
                end = null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Pagination pagination = this.shopScoreMng.getPage(member.getId(), status, useStatus,
                start, end, Integer.valueOf(getPageNo(env)), count);
        Map paramWrap = new HashMap(
                params);
        paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER
                .wrap(pagination));
        paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
        Map origMap =
                DirectiveUtils.addParamsToVariable(env, paramWrap);
        if (isInvokeTpl(params))
            includeTpl("shop", "ShopScorePage", web, params, env);
        else {
            renderBody(env, loopVars, body);
        }
        DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
    }

    private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        body.render(env.getOut());
    }

    @Autowired
    public void setShopScoreMng(ShopScoreMng shopScoreMng) {
        this.shopScoreMng = shopScoreMng;
    }

    @Autowired
    public void setWebsiteMng(WebsiteMng websiteMng) {
        this.websiteMng = websiteMng;
    }
}

