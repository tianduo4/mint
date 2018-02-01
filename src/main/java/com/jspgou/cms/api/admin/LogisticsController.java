package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiResponse;
import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.api.ExceptionUtil;
import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.manager.LogisticsMng;
import com.jspgou.cms.web.SignValidate;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.core.web.WebErrors;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogisticsController {

    @Autowired
    private LogisticsMng logisticsMng;

    @RequestMapping({"/logistics/list"})
    public void list(HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            List list = this.logisticsMng.getAllList();
            org.json.JSONArray jsonArray = new org.json.JSONArray();
            if (((list != null ? 1 : 0) & (list.size() > 0 ? 1 : 0)) != 0) {
                for (int i = 0; i < list.size(); i++) {
                    jsonArray.put(i, ((Logistics) list.get(i)).convertToJson());
                }
            }
            body = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @SignValidate
    @RequestMapping({"/logistics/save"})
    public void save(Logistics logistics, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{
                    logistics.getName(), logistics.getPriority()});
            if (!errors.hasErrors()) {
                logistics.setCourier(Boolean.valueOf(false));
                this.logisticsMng.save(logistics, null);
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = 100;
            message = "\"system exception\"";
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/logistics/get"})
    public void getLogistics(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                Logistics logistics = new Logistics();
                if (id.longValue() != 0L) {
                    logistics = this.logisticsMng.findById(id);
                }
                if (logistics != null) {
                    body = logistics.convertToJson().toString();
                } else {
                    code = 206;
                    message = "\"object not found\"";
                }
            } else {
                code = 202;
                message = "\"param error\"";
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
    @RequestMapping({"/logistics/priority"})
    public void priority(String ids, String priority, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids, priority});
            if (!errors.hasErrors()) {
                Long[] id = null;
                Integer[] prioritys = null;
                String[] str = ids.split(",");
                id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }

                String[] str1 = priority.split(",");
                prioritys = new Integer[str1.length];
                for (int i = 0; i < str1.length; i++) {
                    prioritys[i] = Integer.valueOf(Integer.parseInt(str1[i]));
                }
                this.logisticsMng.updatePriority(id, prioritys);
            } else {
                code = 202;
                message = "\"param error\"";
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
    @RequestMapping({"/logistics/update"})
    public void update(Logistics bean, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(), bean.getName(), bean.getPriority()});
            if (!errors.hasErrors()) {
                bean = this.logisticsMng.update(bean, null);
            } else {
                code = 202;
                message = "\"param error\"";
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
    @RequestMapping({"/logistics/delete"})
    public void delete(String ids, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{ids});
            if (!errors.hasErrors()) {
                String[] str = ids.split(",");
                Long[] id = new Long[str.length];
                for (int i = 0; i < str.length; i++) {
                    id[i] = Long.valueOf(Long.parseLong(str[i]));
                }
                this.logisticsMng.deleteByIds(id);
            } else {
                code = 202;
                message = "\"param error\"";
            }
        } catch (Exception e) {
            ExceptionUtil.convertException(response, request, e);
            return;
        }
        ApiResponse apiResponse = new ApiResponse(body, message, code);
        ResponseUtils.renderApiJson(response, request, apiResponse);
    }

    @RequestMapping({"/logistics/getCourier"})
    public void getCourier(Long id, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{id});
            if (!errors.hasErrors()) {
                Logistics logistics = new Logistics();
                if (id.longValue() != 0L) {
                    logistics = this.logisticsMng.findById(id);
                }
                if (logistics != null) {
                    body = logistics.convertToJson1().toString();
                } else {
                    code = 206;
                    message = "\"object not found\"";
                }
            } else {
                code = 202;
                message = "\"param error\"";
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
    @RequestMapping({"/courier/update"})
    public void updateCourier(Logistics bean, String courier, String imgUrl, Integer evenSpacing, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[]{bean.getId(), bean.getImgUrl()});
            if (!errors.hasErrors()) {
                net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(courier);
                net.sf.json.JSONObject undefined = json.getJSONObject("undefined");
                net.sf.json.JSONArray results = undefined.getJSONArray("elements");
                bean.setFnt(Double.valueOf(((String) results.getJSONObject(0).get("top")).replace("px", "")));
                bean.setFnz(Double.valueOf(((String) results.getJSONObject(0).get("left")).replace("px", "")));
                bean.setFnw(Double.valueOf(((String) results.getJSONObject(0).get("width")).replace("px", "")));
                bean.setFnh(Double.valueOf(((String) results.getJSONObject(0).get("height")).replace("px", "")));
                bean.setFnwh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setFat(Double.valueOf(((String) results.getJSONObject(1).get("top")).replace("px", "")));
                bean.setFaz(Double.valueOf(((String) results.getJSONObject(1).get("left")).replace("px", "")));
                bean.setFaw(Double.valueOf(((String) results.getJSONObject(1).get("width")).replace("px", "")));
                bean.setFah(Double.valueOf(((String) results.getJSONObject(1).get("height")).replace("px", "")));
                bean.setFawh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setFpt(Double.valueOf(((String) results.getJSONObject(2).get("top")).replace("px", "")));
                bean.setFpz(Double.valueOf(((String) results.getJSONObject(2).get("left")).replace("px", "")));
                bean.setFpw(Double.valueOf(((String) results.getJSONObject(2).get("width")).replace("px", "")));
                bean.setFph(Double.valueOf(((String) results.getJSONObject(2).get("height")).replace("px", "")));
                bean.setFpwh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setSnt(Double.valueOf(((String) results.getJSONObject(3).get("top")).replace("px", "")));
                bean.setSnz(Double.valueOf(((String) results.getJSONObject(3).get("left")).replace("px", "")));
                bean.setSnw(Double.valueOf(((String) results.getJSONObject(3).get("width")).replace("px", "")));
                bean.setSnh(Double.valueOf(((String) results.getJSONObject(3).get("height")).replace("px", "")));
                bean.setSnwh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setSat(Double.valueOf(((String) results.getJSONObject(4).get("top")).replace("px", "")));
                bean.setSaz(Double.valueOf(((String) results.getJSONObject(4).get("left")).replace("px", "")));
                bean.setSaw(Double.valueOf(((String) results.getJSONObject(4).get("width")).replace("px", "")));
                bean.setSah(Double.valueOf(((String) results.getJSONObject(4).get("height")).replace("px", "")));
                bean.setSawh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setSpt(Double.valueOf(((String) results.getJSONObject(5).get("top")).replace("px", "")));
                bean.setSpz(Double.valueOf(((String) results.getJSONObject(5).get("left")).replace("px", "")));
                bean.setSpw(Double.valueOf(((String) results.getJSONObject(5).get("width")).replace("px", "")));
                bean.setSph(Double.valueOf(((String) results.getJSONObject(5).get("height")).replace("px", "")));
                bean.setSpwh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setEvenSpacing(evenSpacing);
                bean.setImgUrl(imgUrl);
                this.logisticsMng.update(bean, null);
            } else {
                code = 202;
                message = "\"param error\"";
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
    @RequestMapping({"/courier/save"})
    public void saveCourier(Long id, String courier, String imgUrl, Integer evenSpacing, HttpServletRequest request, HttpServletResponse response) {
        String body = "\"\"";
        String message = "\"success\"";
        int code = 200;
        try {
            WebErrors errors = WebErrors.create(request);
            errors = ApiValidate.validateRequiredParams(errors, new Object[0]);
            if (!errors.hasErrors()) {
                Logistics bean = this.logisticsMng.findById(id);
                net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(courier);
                net.sf.json.JSONObject undefined = json.getJSONObject("undefined");
                net.sf.json.JSONArray results = undefined.getJSONArray("elements");
                bean.setFnt(Double.valueOf(((String) results.getJSONObject(0).get("top")).replace("px", "")));
                bean.setFnz(Double.valueOf(((String) results.getJSONObject(0).get("left")).replace("px", "")));
                bean.setFnw(Double.valueOf(((String) results.getJSONObject(0).get("width")).replace("px", "")));
                bean.setFnh(Double.valueOf(((String) results.getJSONObject(0).get("height")).replace("px", "")));
                bean.setFnwh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setFat(Double.valueOf(((String) results.getJSONObject(1).get("top")).replace("px", "")));
                bean.setFaz(Double.valueOf(((String) results.getJSONObject(1).get("left")).replace("px", "")));
                bean.setFaw(Double.valueOf(((String) results.getJSONObject(1).get("width")).replace("px", "")));
                bean.setFah(Double.valueOf(((String) results.getJSONObject(1).get("height")).replace("px", "")));
                bean.setFawh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setFpt(Double.valueOf(((String) results.getJSONObject(2).get("top")).replace("px", "")));
                bean.setFpz(Double.valueOf(((String) results.getJSONObject(2).get("left")).replace("px", "")));
                bean.setFpw(Double.valueOf(((String) results.getJSONObject(2).get("width")).replace("px", "")));
                bean.setFph(Double.valueOf(((String) results.getJSONObject(2).get("height")).replace("px", "")));
                bean.setFpwh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setSnt(Double.valueOf(((String) results.getJSONObject(3).get("top")).replace("px", "")));
                bean.setSnz(Double.valueOf(((String) results.getJSONObject(3).get("left")).replace("px", "")));
                bean.setSnw(Double.valueOf(((String) results.getJSONObject(3).get("width")).replace("px", "")));
                bean.setSnh(Double.valueOf(((String) results.getJSONObject(3).get("height")).replace("px", "")));
                bean.setSnwh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setSat(Double.valueOf(((String) results.getJSONObject(4).get("top")).replace("px", "")));
                bean.setSaz(Double.valueOf(((String) results.getJSONObject(4).get("left")).replace("px", "")));
                bean.setSaw(Double.valueOf(((String) results.getJSONObject(4).get("width")).replace("px", "")));
                bean.setSah(Double.valueOf(((String) results.getJSONObject(4).get("height")).replace("px", "")));
                bean.setSawh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setSpt(Double.valueOf(((String) results.getJSONObject(5).get("top")).replace("px", "")));
                bean.setSpz(Double.valueOf(((String) results.getJSONObject(5).get("left")).replace("px", "")));
                bean.setSpw(Double.valueOf(((String) results.getJSONObject(5).get("width")).replace("px", "")));
                bean.setSph(Double.valueOf(((String) results.getJSONObject(5).get("height")).replace("px", "")));
                bean.setSpwh((String) results.getJSONObject(0).get("fontWeight"));
                bean.setEvenSpacing(evenSpacing);
                if (StringUtils.isNotBlank(imgUrl))
                    bean.setImgUrl(imgUrl);
                else {
                    bean.setImgUrl("res/common/img/kd/ems.jpg");
                }
                bean.setCourier(Boolean.valueOf(true));
                this.logisticsMng.update(bean, null);
            } else {
                code = 202;
                message = "\"param error\"";
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

