package com.mint.cms.manager.impl;

import com.mint.cms.dao.ApiRecordDao;
import com.mint.cms.entity.ApiInfo;
import com.mint.cms.entity.ApiRecord;
import com.mint.cms.manager.ApiAccountMng;
import com.mint.cms.manager.ApiInfoMng;
import com.mint.cms.manager.ApiRecordMng;
import com.mint.common.hibernate4.Updater;
import com.mint.common.page.Pagination;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApiRecordMngImpl
        implements ApiRecordMng {
    private ApiRecordDao dao;

    @Autowired
    private ApiAccountMng apiAccountMng;

    @Autowired
    private ApiInfoMng apiInfoMng;

    @Transactional(readOnly = true)
    public Pagination getPage(int pageNo, int pageSize) {
        Pagination page = this.dao.getPage(pageNo, pageSize);
        return page;
    }

    @Transactional(readOnly = true)
    public ApiRecord findById(Long id) {
        ApiRecord entity = this.dao.findById(id);
        return entity;
    }

    public ApiRecord save(ApiRecord bean) {
        this.dao.save(bean);
        return bean;
    }

    public ApiRecord update(ApiRecord bean) {
        Updater updater = new Updater(bean);
        ApiRecord entity = this.dao.updateByUpdater(updater);
        return entity;
    }

    public ApiRecord deleteById(Long id) {
        ApiRecord bean = this.dao.deleteById(id);
        return bean;
    }

    public ApiRecord[] deleteByIds(Long[] ids) {
        ApiRecord[] beans = new ApiRecord[ids.length];
        int i = 0;
        for (int len = ids.length; i < len; i++) {
            beans[i] = deleteById(ids[i]);
        }
        return beans;
    }

    public void callApiRecord(String ipAddr, String appId, String apiUrl, String sign) {
        ApiRecord apiRecord = new ApiRecord();
        apiRecord.setApiAccount(this.apiAccountMng.findByAppId(appId));
        if (this.apiInfoMng.findByApiUrl(apiUrl) != null) {
            apiRecord.setApiInfo(this.apiInfoMng.findByApiUrl(apiUrl));
        } else {
            ApiInfo apiInfo = new ApiInfo();
            apiInfo.setApiName("接口");
            apiInfo.setApiUrl(apiUrl);
            apiInfo.setApiCode("ApiCode");
            apiInfo.setDisabled(Boolean.valueOf(false));
            apiInfo.setLimitCallDay(Integer.valueOf(0));
            apiRecord.setApiInfo(this.apiInfoMng.save(apiInfo));
        }
        Date now = new Timestamp(System.currentTimeMillis());
        apiRecord.setApiCallTime(now);
        apiRecord.setApiIp(ipAddr);
        apiRecord.setSign(sign);
        apiRecord.setCallTimeStamp(Long.valueOf(System.currentTimeMillis()));
        save(apiRecord);
    }

    public ApiRecord findBySign(String sign, String appId) {
        return this.dao.findBySign(sign, appId);
    }

    @Autowired
    public void setDao(ApiRecordDao dao) {
        this.dao = dao;
    }
}

