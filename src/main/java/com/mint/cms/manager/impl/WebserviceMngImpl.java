package com.mint.cms.manager.impl;

import com.mint.cms.dao.WebserviceDao;
import com.mint.cms.entity.ShopMember;
import com.mint.cms.entity.Webservice;
import com.mint.cms.entity.WebserviceParam;
import com.mint.cms.manager.WebserviceMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Transactional
public class WebserviceMngImpl
        implements WebserviceMng {
    private WebserviceDao dao;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public List<Webservice> getList(String type) {
        return this.dao.getList(type);
    }

    @Transactional(readOnly = true)
    public boolean hashWebservice(String type) {
        return getList(type).size() > 0;
    }

    public String callWebService(Webservice webservice, Map<String, String> paramsValues) {
        String endpoint = webservice.getAddress();
        org.apache.axis.client.Service service = new org.apache.axis.client.Service();

        String res = null;
        try {
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName(new QName(webservice.getTargetNamespace(), webservice.getOperate()));
            List params = webservice.getParams();
            Object[] values = new Object[params.size()];
            for (int i = 0; i < params.size(); i++) {
                WebserviceParam p = (WebserviceParam) params.get(i);
                String defaultValue = p.getDefaultValue();
                String pValue = (String) paramsValues.get(p.getParamName());
                if (StringUtils.isBlank(pValue))
                    values[i] = defaultValue;
                else {
                    values[i] = pValue;
                }
            }
            res = (String) call.invoke(values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Transactional(readOnly = true)
    public Webservice findById(Integer id) {
        Webservice entity = this.dao.findById(id);
        return entity;
    }

    public Webservice save(Webservice bean, String[] paramName, String[] defaultValue) {
        bean = this.dao.save(bean);

        if ((paramName != null) && (paramName.length > 0)) {
            int i = 0;
            for (int len = paramName.length; i < len; i++) {
                if (!StringUtils.isBlank(paramName[i])) {
                    bean.addToParams(paramName[i], defaultValue[i]);
                }
            }
        }
        return null;
    }

    public Webservice update(Webservice bean, String[] paramName, String[] defaultValue) {
        Updater updater = new Updater(bean);
        Webservice entity = this.dao.updateByUpdater(updater);
        entity.getParams().clear();
        if ((paramName != null) && (paramName.length > 0)) {
            int i = 0;
            for (int len = paramName.length; i < len; i++) {
                if (!StringUtils.isBlank(paramName[i])) {
                    entity.addToParams(paramName[i], defaultValue[i]);
                }
            }
        }
        return entity;
    }

    public Webservice deleteById(Integer id) {
        Webservice bean = this.dao.deleteById(id);
        return bean;
    }

    public Webservice[] deleteByIds(Integer[] ids) {
        Webservice[] beans = new Webservice[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    @Autowired
    public void setDao(WebserviceDao dao) {
        this.dao = dao;
    }

    public void callWebService(String operate, Map<String, String> params) {
        List<Webservice> list = getList(operate);
        for (Webservice s : list)
            callWebService(s, params);
    }

    public void callWebService(String admin, String username, String password, String email, ShopMember shopmember, String operate) {
        if (hashWebservice(operate)) {
            Map paramsValues = new HashMap();
            paramsValues.put("username", username);
            paramsValues.put("password", password);
            paramsValues.put("admin", admin);
            if (StringUtils.isNotBlank(email)) {
                paramsValues.put("email", email);
            }
            if (shopmember != null) {
                if (StringUtils.isNotBlank(shopmember.getRealName())) {
                    paramsValues.put("realname", shopmember.getRealName());
                }
                if (shopmember.getGender() != null) {
                    paramsValues.put("sex", shopmember.getGender().toString());
                }
                if (StringUtils.isNotBlank(shopmember.getMobile())) {
                    paramsValues.put("tel", shopmember.getMobile());
                }
            }
            callWebService(operate, paramsValues);
        }
    }
}

