package com.jspgou.cms.action.member;

import com.jspgou.cms.entity.Address;
import com.jspgou.cms.entity.ShopMember;
import com.jspgou.cms.entity.ShopMemberAddress;
import com.jspgou.cms.manager.AddressMng;
import com.jspgou.cms.manager.OrderMng;
import com.jspgou.cms.manager.ShopMemberAddressMng;
import com.jspgou.cms.web.FrontUtils;
import com.jspgou.cms.web.ShopFrontHelper;
import com.jspgou.cms.web.threadvariable.MemberThread;
import com.jspgou.common.web.ResponseUtils;
import com.jspgou.common.web.springmvc.MessageResolver;
import com.jspgou.core.entity.Website;
import com.jspgou.core.web.SiteUtils;
import com.jspgou.core.web.WebErrors;
import com.jspgou.core.web.front.FrontHelper;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopMenberAddressAct {
    private static final Logger log = LoggerFactory.getLogger(ShopMenberAddressAct.class);
    private static final String MEMBER_ADDRESS = "tpl.memberAddress";
    private static final String MEMBER_ADDRESS_EDIT = "tpl.memberAddressEdit";

    @Autowired
    private OrderMng orderMng;

    @Autowired
    private AddressMng addressMng;

    @Autowired
    private ShopMemberAddressMng shopMemberAddressMng;

    @RequestMapping(value = {"/shopMemberAddress/address_list.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String list(HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        List list = this.shopMemberAddressMng.getList(member.getId());
        model.addAttribute("list", list);
        List plist = this.addressMng.getListById(null);
        model.addAttribute("plist", plist);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberAddress", new Object[0]), request);
    }

    @RequestMapping(value = {"/shopMemberAddress/address_save.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String save(ShopMemberAddress bean, Long provinceId, Long cityId, Long countryId, String returnUrl, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        List list = this.shopMemberAddressMng.getList(member.getId());
        model.addAttribute("list", list);
        ShopMemberAddress entity = null;
        if (bean.getIsDefault()) {
            int i = 0;
            for (int j = list.size(); i < j; i++) {
                entity = (ShopMemberAddress) list.get(i);
                entity.setIsDefault(false);
                this.shopMemberAddressMng.updateByUpdater(entity);
            }
        }
        if ((provinceId == null) || (cityId == null) || (countryId == null)) {
            ShopFrontHelper.setCommonData(request, model, web, 1);
            return FrontUtils.showMessage(request, model, "收货地址有误，请重新选择收货地址");
        }
        bean.setProvince(this.addressMng.findById(provinceId).getName());
        bean.setCity(this.addressMng.findById(cityId).getName());
        bean.setCountry(this.addressMng.findById(countryId).getName());
        bean.setProvinceId(this.addressMng.findById(provinceId).getId());
        bean.setCityId(this.addressMng.findById(cityId).getId());
        bean.setCountryId(this.addressMng.findById(countryId).getId());
        bean.setMember(member);
        this.shopMemberAddressMng.save(bean);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        log.info("ShopMemberAddress save success, id= {}", bean.getId());
        if (returnUrl != null) {
            return "redirect:" + returnUrl;
        }
        return "redirect:address_list.jspx";
    }

    @RequestMapping(value = {"/shopMemberAddress/address_edit.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String edit(Long id, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        WebErrors errors = validateEdit(id, member.getId(), request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        List list = this.shopMemberAddressMng.getList(member.getId());
        model.addAttribute("list", list);
        ShopMemberAddress bean = this.shopMemberAddressMng.findById(id);
        model.addAttribute("bean", bean);
        List plist = this.addressMng.getListById(null);
        model.addAttribute("plist", plist);
        List clist = this.addressMng.getListById(bean.getProvinceId());
        model.addAttribute("clist", clist);
        List alist = this.addressMng.getListById(bean.getCityId());
        model.addAttribute("alist", alist);
        model.addAttribute("id", id);
        ShopFrontHelper.setCommonData(request, model, web, 1);
        return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberAddressEdit", new Object[0]), request);
    }

    @RequestMapping(value = {"/shopMemberAddress/address_update.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String update(ShopMemberAddress bean, Long provinceId, Long cityId, Long countryId, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        List list = this.shopMemberAddressMng.getList(member.getId());
        ShopMemberAddress entity = null;
        if (bean.getIsDefault()) {
            int i = 0;
            for (int j = list.size(); i < j; i++) {
                entity = (ShopMemberAddress) list.get(i);
                entity.setIsDefault(false);
                this.shopMemberAddressMng.updateByUpdater(entity);
            }
        }
        if ((provinceId == null) || (cityId == null) || (countryId == null)) {
            ShopFrontHelper.setCommonData(request, model, web, 1);
            return FrontUtils.showMessage(request, model, "收货地址有误，请重新选择收货地址");
        }
        bean.setProvince(this.addressMng.findById(provinceId).getName());
        bean.setCity(this.addressMng.findById(cityId).getName());
        bean.setCountry(this.addressMng.findById(countryId).getName());
        this.shopMemberAddressMng.updateByUpdater(bean);
        log.info("ShopMemberAddress update success, id= {}", bean.getId());
        return "redirect:address_list.jspx";
    }

    @RequestMapping(value = {"/shopMemberAddress/address_default.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String isDefault(Long id, String returnUrl, Integer count, HttpServletRequest request, ModelMap model) {
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        List list = this.shopMemberAddressMng.getList(member.getId());
        ShopMemberAddress bean = this.shopMemberAddressMng.findById(id);
        ShopMemberAddress entity = null;
        int i = 0;
        for (int j = list.size(); i < j; i++) {
            entity = (ShopMemberAddress) list.get(i);
            entity.setIsDefault(false);
            this.shopMemberAddressMng.updateByUpdater(entity);
        }
        bean.setIsDefault(true);
        this.shopMemberAddressMng.updateByUpdater(bean);
        log.info("ShopMemberAddress default success, id= {}", bean.getId());
        if (returnUrl != null) {
            if (count != null) {
                return "redirect:" + returnUrl + "&count=" + count;
            }
            return "redirect:" + returnUrl;
        }

        return "redirect:address_list.jspx";
    }

    @RequestMapping(value = {"/shopMemberAddress/address_delete.jspx"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String delete(Long id, String returnUrl, Integer count, HttpServletRequest request, ModelMap model) {
        Website web = SiteUtils.getWeb(request);
        ShopMember member = MemberThread.get();
        if (member == null) {
            return "redirect:../login.jspx";
        }
        WebErrors errors = validateDelete(id, member.getId(), request);
        if (errors.hasErrors()) {
            return FrontHelper.showError(errors, web, model, request);
        }
        this.shopMemberAddressMng.deleteById(id, member.getId());
        log.info("ShopMemberAddress delete success, id= {}", id);
        if (returnUrl != null) {
            if (count != null) {
                return "redirect:" + returnUrl + "&count=" + count;
            }
            return "redirect:" + returnUrl;
        }

        return "redirect:address_list.jspx";
    }

    @RequestMapping({"/shopMemberAddress/findAllCity.jspx"})
    public void findAllCity(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List clist = this.addressMng.getListById(id);
        Long[] ids = new Long[clist.size()];
        String[] citys = new String[clist.size()];
        int i = 0;
        for (int j = clist.size(); i < j; i++) {
            Address city = (Address) clist.get(i);
            ids[i] = city.getId();
            citys[i] = city.getName();
        }
        JSONObject json = new JSONObject();
        try {
            json.put("ids", ids);
            json.put("citys", citys);
            json.put("success", true);
        } catch (JSONException e) {
            try {
                json.put("success", false);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    @RequestMapping({"/shopMemberAddress/findAllCountry.jspx"})
    public void findAllArea(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List alist = this.addressMng.getListById(id);
        Long[] ids = new Long[alist.size()];
        String[] areas = new String[alist.size()];
        int i = 0;
        for (int j = alist.size(); i < j; i++) {
            Address area = (Address) alist.get(i);
            ids[i] = area.getId();
            areas[i] = area.getName();
        }
        JSONObject json = new JSONObject();
        try {
            json.put("ids", ids);
            json.put("areas", areas);
            json.put("success", true);
        } catch (JSONException e) {
            try {
                json.put("success", false);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        ResponseUtils.renderJson(response, json.toString());
    }

    private WebErrors validateEdit(Long addressId, Long memberId, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldAddress(addressId, memberId, errors)) {
            return errors;
        }
        return errors;
    }

    private WebErrors validateDelete(Long addressId, Long memberId, HttpServletRequest request) {
        WebErrors errors = WebErrors.create(request);
        if (vldAddress(addressId, memberId, errors)) {
            return errors;
        }
        return errors;
    }

    private boolean vldAddress(Long addressId, Long memberId, WebErrors errors) {
        if (errors.ifNull(addressId, "id")) {
            return true;
        }
        ShopMemberAddress address = this.shopMemberAddressMng.findById(addressId);
        if (errors.ifNotExist(address, ShopMemberAddress.class, addressId)) {
            return true;
        }
        if (!memberId.equals(address.getMember().getId())) {
            errors.noPermission(ShopMemberAddress.class, addressId);
        }
        return false;
    }
}

