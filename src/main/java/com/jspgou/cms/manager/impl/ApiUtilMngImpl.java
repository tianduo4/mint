package com.jspgou.cms.manager.impl;

import com.jspgou.cms.Alipay;
import com.jspgou.cms.entity.ApiAccount;
import com.jspgou.cms.entity.ApiRecord;
import com.jspgou.cms.entity.ApiUserLogin;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.manager.ApiAccountMng;
import com.jspgou.cms.manager.ApiRecordMng;
import com.jspgou.cms.manager.ApiUserLoginMng;
import com.jspgou.cms.manager.ApiUtilMng;
import com.jspgou.cms.manager.ShopMemberMng;
import com.jspgou.common.util.AES128Util;
import com.jspgou.common.util.ConvertMapToJson;
import com.jspgou.common.web.RequestUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApiUtilMngImpl
        implements ApiUtilMng {

    @Autowired
    private ApiRecordMng apiRecordMng;

    @Autowired
    private ApiAccountMng apiAccountMng;

    @Autowired
    private ApiUserLoginMng apiUserLoginMng;

    @Autowired
    private ShopMemberMng shopMemberMng;

    public String getJsonStrng(String message, String url, Boolean decryptionFailure, HttpServletRequest request) {
        Map map = new HashMap();
        String result = "00";
        String appId = request.getParameter("appId");
        String sign = request.getParameter("sign");
        String callback = request.getParameter("callback");
        if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign))) {
            if (verifySign(appId, sign).booleanValue()) {
                String validateSign = getValidateSign(appId, request);
                if (verifyValidateSign(sign, validateSign).booleanValue()) {
                    if (decryptionFailure.booleanValue()) {
                        result = "01";
                        if (StringUtils.isNotBlank(message)) {
                            map.put("pd", message);
                        }
                        if (StringUtils.isNotBlank(url))
                            this.apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request), appId, url, sign);
                    } else {
                        result = "02";
                    }
                } else
                    result = "03";
            } else {
                result = "04";
            }
        }
        map.put("result", result);
        if (!StringUtils.isBlank(callback)) {
            return callback + "(" + ConvertMapToJson.buildJsonBody(map, 0, false) + ")";
        }
        return ConvertMapToJson.buildJsonBody(map, 0, false);
    }

    public String getJsonStrng(String message, String url, HttpServletRequest request) {
        Map map = new HashMap();
        String result = "00";
        String appId = request.getParameter("appId");
        String sign = request.getParameter("sign");
        String callback = request.getParameter("callback");
        if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign))) {
            if (verifySign(appId, sign).booleanValue()) {
                String validateSign = getValidateSign(appId, request);
                if (verifyValidateSign(sign, validateSign).booleanValue()) {
                    result = "01";
                    if (StringUtils.isNotBlank(message)) {
                        map.put("pd", message);
                    }
                    if (StringUtils.isNotBlank(url))
                        this.apiRecordMng.callApiRecord(RequestUtils.getIpAddr(request), appId, url, sign);
                } else {
                    result = "03";
                }
            } else {
                result = "04";
            }
        }
        map.put("result", result);
        if (!StringUtils.isBlank(callback)) {
            return callback + "(" + ConvertMapToJson.buildJsonBody(map, 0, false) + ")";
        }
        return ConvertMapToJson.buildJsonBody(map, 0, false);
    }

    public String getEncryptpass(HttpServletRequest request) throws Exception {
        String encryptPass = null;
        String appId = request.getParameter("appId");
        String sign = request.getParameter("sign");
        String aesPassword = request.getParameter("aesPassword");
        if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(aesPassword)) && (StringUtils.isNotBlank(sign)) &&
                (verifySign(appId, sign).booleanValue())) {
            String validateSign = getValidateSign(appId, request);
            if (verifyValidateSign(sign, validateSign).booleanValue()) {
                ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);
                String aesKey = apiAccount.getAesKey();
                String ivVal = apiAccount.getIvKey();
                encryptPass = AES128Util.decrypt(aesPassword, aesKey, ivVal);
            }
        }

        return encryptPass;
    }

    public ShopMember findbysessionKey(HttpServletRequest request) {
        ShopMember user = null;
        String appId = request.getParameter("appId");
        String sign = request.getParameter("sign");
        String sessionKey = request.getParameter("sessionKey");
        if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sessionKey)) && (StringUtils.isNotBlank(sign)) &&
                (verifySign(appId, sign).booleanValue())) {
            String validateSign = getValidateSign(appId, request);
            if (verifyValidateSign(sign, validateSign).booleanValue()) {
                ApiUserLogin apiUserLogin = this.apiUserLoginMng.findBySessionKey(sessionKey);
                if (apiUserLogin != null) {
                    String username = apiUserLogin.getUsername();
                    user = this.shopMemberMng.findUsername(username);
                }
            }
        }

        return user;
    }

    public Boolean verify(HttpServletRequest request) {
        Boolean verify = Boolean.valueOf(false);
        String appId = request.getParameter("appId");
        String sign = request.getParameter("sign");
        if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign)) &&
                (verifySign(appId, sign).booleanValue())) {
            String validateSign = getValidateSign(appId, request);
            if (verifyValidateSign(sign, validateSign).booleanValue()) {
                verify = Boolean.valueOf(true);
            }
        }

        return verify;
    }

    public Boolean verifySign(String appId, String sign) {
        Boolean verify = Boolean.valueOf(false);
        if ((StringUtils.isNotBlank(appId)) && (StringUtils.isNotBlank(sign))) {
            ApiRecord apirecord = this.apiRecordMng.findBySign(sign, appId);
            if (apirecord == null) {
                verify = Boolean.valueOf(true);
            }
        }
        return verify;
    }

    public Boolean verifyValidateSign(String sign, String validateSign) {
        Boolean verify = Boolean.valueOf(false);
        if ((StringUtils.isNotBlank(validateSign)) && (StringUtils.isNotBlank(sign)) &&
                (sign.equals(validateSign))) {
            verify = Boolean.valueOf(true);
        }

        return verify;
    }

    private String getValidateSign(String appId, HttpServletRequest request) {
        ApiAccount apiAccount = this.apiAccountMng.findByAppId(appId);
        String appKey = apiAccount.getAppKey();
        Map param = new HashMap();
        Enumeration penum = request.getParameterNames();
        while (penum.hasMoreElements()) {
            String pKey = (String) penum.nextElement();
            String value = request.getParameter(pKey);

            if ((!pKey.equals("sign")) && (StringUtils.isNotBlank(value))) {
                param.put(pKey, value);
            }
        }
        String validateSign = Alipay.createSign(param, appKey);
        return validateSign;
    }
}

