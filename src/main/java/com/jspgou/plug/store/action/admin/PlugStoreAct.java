package com.jspgou.plug.store.action.admin;

import com.jspgou.common.page.Pagination;
import com.jspgou.common.page.SimplePage;
import com.jspgou.common.security.encoder.Md5PwdEncoder;
import com.jspgou.common.web.CookieUtils;
import com.jspgou.common.web.session.SessionProvider;
import com.jspgou.core.web.WebErrors;
import com.jspgou.plug.store.entity.PlugStoreConfig;
import com.jspgou.plug.store.entity.StorePlug;
import com.jspgou.plug.store.manager.PlugStoreConfigMng;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlugStoreAct {
    private static final Logger log = LoggerFactory.getLogger(PlugStoreAct.class);
    private static final String STORE_LOGIN = "store_login";
    private Integer totalCount;

    @Autowired
    private PlugStoreConfigMng manager;

    @Autowired
    private SessionProvider session;

    @Autowired
    private Md5PwdEncoder pwdEncoder;

    @RequestMapping({"/store/v_center.do"})
    public String list(Integer productType, Integer pageNo, HttpServletRequest request, ModelMap model) {
        if ((this.totalCount == null) || (productType != null)) {
            this.totalCount = getPlugTotal(productType);
        }
        int pageSize = CookieUtils.getPageSize(request);
        Pagination p = new Pagination(SimplePage.cpn(pageNo), pageSize, this.totalCount.intValue());
        if (this.totalCount.intValue() < 1)
            p.setList(new ArrayList());
        else {
            p.setList(getPlugs(productType, Integer.valueOf(pageSize * (SimplePage.cpn(pageNo) - 1)),
                    Integer.valueOf(pageSize)));
        }
        String plugUrlPrefix = this.manager.getDefault().getServerUrl() + "/plug";
        model.addAttribute("pagination", p);
        model.addAttribute("pageNo", Integer.valueOf(p.getPageNo()));
        model.addAttribute("plugUrlPrefix", plugUrlPrefix);
        model.addAttribute("productType", productType);
        return "plugStore/list";
    }

    @RequestMapping({"/store/v_config.do"})
    public String config(HttpServletRequest request, ModelMap model) {
        Boolean is_login = (Boolean) this.session.getAttribute(request, "store_login");
        if ((is_login != null) && (is_login.booleanValue())) {
            model.addAttribute("plugStoreConfig", this.manager.getDefault());
            return "plugStore/config";
        }
        return "plugStore/login";
    }

    @RequestMapping({"/store/o_login.do"})
    public String o_login(String password, ModelMap model, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (this.pwdEncoder.encodePassword(password).equals(
                this.manager.getDefault().getPassword())) {
            this.session.setAttribute(request, response, "store_login", Boolean.valueOf(true));
            return config(request, model);
        }
        return "plugStore/login_error";
    }

    @RequestMapping({"/store/config_update.do"})
    public String update(PlugStoreConfig bean, String password, Integer pageNo, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateUpdate(bean.getId(), request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        bean = this.manager.update(bean, password);
        log.info("update PlugStoreConfig id={}.", bean.getId());
        return config(request, model);
    }

    private Integer getPlugTotal(Integer productType) {
        String serverUrl = this.manager.getDefault().getServerUrl();
        String url = serverUrl + "/json/plug_sum.jspx?productId=1";
        if (productType != null) {
            url = url + "&productType=" + productType;
        }
        CharsetHandler handler = new CharsetHandler("utf-8");
        HttpClient client = new DefaultHttpClient();
        String total = "0";
        try {
            HttpGet httpget = new HttpGet(new URI(url));
            total = (String) client.execute(httpget, handler);
        } catch (Exception localException) {
        }
        return Integer.valueOf(Integer.parseInt(total));
    }

    private List<StorePlug> getPlugs(Integer productType, Integer first, Integer count) {
        String serverUrl = this.manager.getDefault().getServerUrl();
        String url = serverUrl + "/json/plug_list.jspx?productId=4" + "&first=" +
                first + "&count=" + count;
        if (productType != null) {
            url = url + "&productType=" + productType;
        }
        CharsetHandler handler = new CharsetHandler("utf-8");
        HttpClient client = new DefaultHttpClient();
        String json = "";
        try {
            HttpGet httpget = new HttpGet(new URI(url));
            json = (String) client.execute(httpget, handler);
        } catch (Exception localException) {
        }
        List list = new ArrayList();
        try {
            JSONArray jarray = new JSONArray(json);
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jobject = (JSONObject) jarray.get(i);
                StorePlug plug = new StorePlug();
                plug.setId(Integer.valueOf(jobject.getInt("id")));
                plug.setChargeAmount(Double.valueOf(jobject.getDouble("chargeAmount")));
                plug.setShortDesc(jobject.getString("shortDesc"));
                plug.setIsCharge(Boolean.valueOf(jobject.getBoolean("isCharge")));
                plug.setProductId(Integer.valueOf(jobject.getInt("productId")));
                plug.setProductName(jobject.getString("productName"));
                plug.setReleaseDate(jobject.getString("releaseDate"));
                plug.setTitle(jobject.getString("title"));
                plug.setType(Integer.valueOf(jobject.getInt("type")));
                list.add(plug);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldExist(id, errors)) {
            return errors;
        }
        return errors;
    }

    private boolean vldExist(Integer id, WebErrors errors) {
        if (errors.ifNull(id, "id")) {
            return true;
        }
        PlugStoreConfig entity = this.manager.findById(id);

        return errors.ifNotExist(entity, PlugStoreConfig.class, id);
    }

    private class CharsetHandler
            implements ResponseHandler<String> {
        private String charset;

        public CharsetHandler(String charset) {
            this.charset = charset;
        }

        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                if (!StringUtils.isBlank(this.charset)) {
                    return EntityUtils.toString(entity, this.charset);
                }
                return EntityUtils.toString(entity);
            }

            return null;
        }
    }
}

