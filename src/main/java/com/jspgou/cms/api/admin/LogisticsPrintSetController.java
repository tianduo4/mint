package com.jspgou.cms.api.admin;

import com.jspgou.cms.api.ApiValidate;
import com.jspgou.cms.entity.Logistics;
import com.jspgou.cms.entity.LogisticsText;
import com.jspgou.cms.manager.LogisticsMng;
import com.jspgou.common.image.ImageUtils;
import com.jspgou.common.upload.FileRepository;
import com.jspgou.core.entity.Website;
import com.jspgou.core.manager.WebsiteMng;
import com.jspgou.core.web.WebErrors;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class LogisticsPrintSetController {

    @Autowired
    private LogisticsMng manager;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private WebsiteMng websiteMng;

    @RequestMapping({"/logistics/v_courierAdd"})
    public String courierAdd(Long id, String appId, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("logistics", this.manager.findById(id));
        model.addAttribute("base", request.getContextPath());
        model.addAttribute("appId", appId);
        return "logistics/courierAdd";
    }

    @RequestMapping({"/common/o_upload_image"})
    public String execute(String fileName, String appId, Integer uploadNum, Integer zoomWidth, Integer zoomHeight, @RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, ModelMap model)
            throws IOException {
        Website site = this.websiteMng.findById(Long.valueOf(1L));
        try {
            WebErrors errors = validate("image", file, request);
            if (!errors.hasErrors()) {
                errors = ApiValidate.validateRequiredParams(errors, new Object[]{file});
                if (!errors.hasErrors()) {
                    String origName = file.getOriginalFilename();
                    String ext = FilenameUtils.getExtension(origName).toLowerCase(
                            Locale.ENGLISH);
                    String filepath = "";
                    filepath = this.fileRepository.upload(site, ext, file);
                    if (StringUtils.isEmpty(filepath)) {
                        errors.addErrorCode(String.valueOf(400));
                        errors.addErrorString("上传错误");
                    } else {
                        model.addAttribute("uploadPath", site.getUploadResUrlBuff() + filepath);
                        model.addAttribute("uploadNum", uploadNum);
                    }
                }
            }
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("appId", appId);
        model.addAttribute("base", site.getUploadResUrlBuff());
        return "/common/iframe_upload";
    }

    private WebErrors validate(String type, MultipartFile file, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);

        if ((file == null) || (type == null)) {
            errors.addErrorCode(String.valueOf(202));
            errors.addErrorString("参数错误");
            return errors;
        }

        if ((!type.equals("image")) && (!type.equals("attach")) && (!type.equals("video"))) {
            errors.addErrorCode(String.valueOf(401));
            errors.addErrorString("上传类型文件暂不支持");
            return errors;
        }
        String filename = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(filename);

        if ((filename != null) && ((filename.contains("/")) || (filename.contains("\\")) || (filename.indexOf("") != -1))) {
            errors.addErrorCode(String.valueOf(402));
            errors.addErrorString("文件名含非法字符，如\\ /等等");
            return errors;
        }
        if (type.equals("image")) {
            if (!ImageUtils.isValidImageExt(ext)) {
                errors.addErrorCode(String.valueOf(403));
                errors.addErrorString("图片文件后缀不支持，暂时只支持(jpg, jpeg,gif, png, bmp )");
                return errors;
            }
        }
        return errors;
    }

    @RequestMapping({"/logistics/o_courierSave"})
    public String courierSave(String courier, String appId, String imgUrl, Long logisticsId, Integer evenSpacing, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEditCourier(courier, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Logistics bean = this.manager.findById(logisticsId);
        JSONObject json = JSONObject.fromObject(courier);
        JSONObject undefined = json.getJSONObject("undefined");
        JSONArray results = undefined.getJSONArray("elements");
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
        this.manager.update(bean, bean.getLogisticsText().getText());
        model.addAttribute("logistics", bean);
        return courierEdit(logisticsId, appId, request, model);
    }

    @RequestMapping({"/logistics/v_courierEdit"})
    public String courierEdit(Long id, String appId, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEdit(id, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        model.addAttribute("logistics", this.manager.findById(id));
        model.addAttribute("base", request.getContextPath());
        model.addAttribute("appId", appId);
        return "logistics/courierEdit";
    }

    @RequestMapping({"/logistics/o_courierEdit"})
    public String courierEdit(String courier, String appId, String imgUrl, Long logisticsId, Integer evenSpacing, HttpServletRequest request, ModelMap model) {
        WebErrors errors = validateEditCourier(courier, request);
        if (errors.hasErrors()) {
            return errors.showErrorPage(model);
        }
        Logistics bean = this.manager.findById(logisticsId);
        JSONObject json = JSONObject.fromObject(courier);
        JSONObject undefined = json.getJSONObject("undefined");
        JSONArray results = undefined.getJSONArray("elements");
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
        this.manager.update(bean, bean.getLogisticsText().getText());
        model.addAttribute("logistics", bean);
        return courierEdit(logisticsId, appId, request, model);
    }

    private WebErrors validateEdit(Long id, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        errors.ifNull(id, "id");
        vldExist(id, errors);
        return errors;
    }

    private WebErrors validateEditCourier(String courier, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (StringUtils.isEmpty(courier)) {
            errors.addErrorString("请先保存模板再提交");
            return errors;
        }
        return errors;
    }

    private boolean vldExist(Long id, WebErrors errors) {
        Logistics entity = this.manager.findById(id);
        return errors.ifNotExist(entity, Logistics.class, id);
    }
}

