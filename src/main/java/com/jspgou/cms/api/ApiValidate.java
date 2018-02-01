package com.jspgou.cms.api;

import com.jspgou.cms.entity.ApiAccount;
import com.jspgou.common.util.PayUtil;
import com.jspgou.common.web.RequestUtils;
import com.jspgou.core.web.WebErrors;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiValidate {
    private static final Logger log = LoggerFactory.getLogger(ApiValidate.class);

    public static WebErrors validateRequiredParams(WebErrors errors, Object[] params) {
        for (Object p : params) {
            if ((((p instanceof String)) && (StringUtils.isBlank((String) p))) || (p == null)) {
                errors.addErrorString("\"param required\"");
            }
        }
        return errors;
    }

    public static WebErrors validateApiAccount(HttpServletRequest request, WebErrors errors, ApiAccount apiAccount) {
        if ((apiAccount == null) || (apiAccount.getDisabled())) {
            errors.addErrorString("\"appId not exist or appId disabled\"");
            log.error(RequestUtils.getIpAddr(request) + "\"appId not exist or appId disabled\"");
        }
        return errors;
    }

    public static WebErrors validateSign(HttpServletRequest request, WebErrors errors, ApiAccount apiAccount, String sign) {
        if ((apiAccount != null) && (!apiAccount.getDisabled())) {
            Map param = RequestUtils.getSignMap(request);
            String appKey = apiAccount.getAppKey();
            String validateSign = PayUtil.createSign(param, appKey);
            if ((StringUtils.isBlank(sign)) || (!sign.equals(validateSign))) {
                errors.addErrorString("\"sign validate error\"");
                log.error(RequestUtils.getIpAddr(request) + " sign validate error");
            }
        } else {
            errors.addErrorString("\"appId not exist or appId disabled\"");
            log.error(RequestUtils.getIpAddr(request) + "\"appId not exist or appId disabled\"");
        }
        return errors;
    }
}

