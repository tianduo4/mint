/*     */ package com.jspgou.cms.action.member;
/*     */ 
/*     */ import com.jspgou.cms.entity.Address;
/*     */ import com.jspgou.cms.entity.ShopMember;
/*     */ import com.jspgou.cms.entity.ShopMemberAddress;
/*     */ import com.jspgou.cms.manager.AddressMng;
/*     */ import com.jspgou.cms.manager.OrderMng;
/*     */ import com.jspgou.cms.manager.ShopMemberAddressMng;
/*     */ import com.jspgou.cms.web.FrontUtils;
/*     */ import com.jspgou.cms.web.ShopFrontHelper;
/*     */ import com.jspgou.cms.web.threadvariable.MemberThread;
/*     */ import com.jspgou.common.web.ResponseUtils;
/*     */ import com.jspgou.common.web.springmvc.MessageResolver;
/*     */ import com.jspgou.core.entity.Website;
/*     */ import com.jspgou.core.web.SiteUtils;
/*     */ import com.jspgou.core.web.WebErrors;
/*     */ import com.jspgou.core.web.front.FrontHelper;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class ShopMenberAddressAct
/*     */ {
/*  43 */   private static final Logger log = LoggerFactory.getLogger(ShopMenberAddressAct.class);
/*     */   private static final String MEMBER_ADDRESS = "tpl.memberAddress";
/*     */   private static final String MEMBER_ADDRESS_EDIT = "tpl.memberAddressEdit";
/*     */ 
/*     */   @Autowired
/*     */   private OrderMng orderMng;
/*     */ 
/*     */   @Autowired
/*     */   private AddressMng addressMng;
/*     */ 
/*     */   @Autowired
/*     */   private ShopMemberAddressMng shopMemberAddressMng;
/*     */ 
/*     */   @RequestMapping(value={"/shopMemberAddress/address_list.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String list(HttpServletRequest request, ModelMap model)
/*     */   {
/*  52 */     Website web = SiteUtils.getWeb(request);
/*  53 */     ShopMember member = MemberThread.get();
/*  54 */     if (member == null) {
/*  55 */       return "redirect:../login.jspx";
/*     */     }
/*  57 */     List list = this.shopMemberAddressMng.getList(member.getId());
/*  58 */     model.addAttribute("list", list);
/*  59 */     List plist = this.addressMng.getListById(null);
/*  60 */     model.addAttribute("plist", plist);
/*  61 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  62 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberAddress", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMemberAddress/address_save.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String save(ShopMemberAddress bean, Long provinceId, Long cityId, Long countryId, String returnUrl, HttpServletRequest request, ModelMap model) {
/*  68 */     Website web = SiteUtils.getWeb(request);
/*  69 */     ShopMember member = MemberThread.get();
/*  70 */     if (member == null) {
/*  71 */       return "redirect:../login.jspx";
/*     */     }
/*  73 */     List list = this.shopMemberAddressMng.getList(member.getId());
/*  74 */     model.addAttribute("list", list);
/*  75 */     ShopMemberAddress entity = null;
/*  76 */     if (bean.getIsDefault()) {
/*  77 */       int i = 0; for (int j = list.size(); i < j; i++) {
/*  78 */         entity = (ShopMemberAddress)list.get(i);
/*  79 */         entity.setIsDefault(false);
/*  80 */         this.shopMemberAddressMng.updateByUpdater(entity);
/*     */       }
/*     */     }
/*  83 */     if ((provinceId == null) || (cityId == null) || (countryId == null)) {
/*  84 */       ShopFrontHelper.setCommonData(request, model, web, 1);
/*  85 */       return FrontUtils.showMessage(request, model, "收货地址有误，请重新选择收货地址");
/*     */     }
/*  87 */     bean.setProvince(this.addressMng.findById(provinceId).getName());
/*  88 */     bean.setCity(this.addressMng.findById(cityId).getName());
/*  89 */     bean.setCountry(this.addressMng.findById(countryId).getName());
/*  90 */     bean.setProvinceId(this.addressMng.findById(provinceId).getId());
/*  91 */     bean.setCityId(this.addressMng.findById(cityId).getId());
/*  92 */     bean.setCountryId(this.addressMng.findById(countryId).getId());
/*  93 */     bean.setMember(member);
/*  94 */     this.shopMemberAddressMng.save(bean);
/*  95 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/*  96 */     log.info("ShopMemberAddress save success, id= {}", bean.getId());
/*  97 */     if (returnUrl != null) {
/*  98 */       return "redirect:" + returnUrl;
/*     */     }
/* 100 */     return "redirect:address_list.jspx";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMemberAddress/address_edit.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String edit(Long id, HttpServletRequest request, ModelMap model) {
/* 106 */     Website web = SiteUtils.getWeb(request);
/* 107 */     ShopMember member = MemberThread.get();
/* 108 */     if (member == null) {
/* 109 */       return "redirect:../login.jspx";
/*     */     }
/* 111 */     WebErrors errors = validateEdit(id, member.getId(), request);
/* 112 */     if (errors.hasErrors()) {
/* 113 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 115 */     List list = this.shopMemberAddressMng.getList(member.getId());
/* 116 */     model.addAttribute("list", list);
/* 117 */     ShopMemberAddress bean = this.shopMemberAddressMng.findById(id);
/* 118 */     model.addAttribute("bean", bean);
/* 119 */     List plist = this.addressMng.getListById(null);
/* 120 */     model.addAttribute("plist", plist);
/* 121 */     List clist = this.addressMng.getListById(bean.getProvinceId());
/* 122 */     model.addAttribute("clist", clist);
/* 123 */     List alist = this.addressMng.getListById(bean.getCityId());
/* 124 */     model.addAttribute("alist", alist);
/* 125 */     model.addAttribute("id", id);
/* 126 */     ShopFrontHelper.setCommonData(request, model, web, 1);
/* 127 */     return web.getTplSys("member", MessageResolver.getMessage(request, "tpl.memberAddressEdit", new Object[0]), request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMemberAddress/address_update.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String update(ShopMemberAddress bean, Long provinceId, Long cityId, Long countryId, HttpServletRequest request, ModelMap model) {
/* 133 */     Website web = SiteUtils.getWeb(request);
/* 134 */     ShopMember member = MemberThread.get();
/* 135 */     if (member == null) {
/* 136 */       return "redirect:../login.jspx";
/*     */     }
/* 138 */     List list = this.shopMemberAddressMng.getList(member.getId());
/* 139 */     ShopMemberAddress entity = null;
/* 140 */     if (bean.getIsDefault()) {
/* 141 */       int i = 0; for (int j = list.size(); i < j; i++) {
/* 142 */         entity = (ShopMemberAddress)list.get(i);
/* 143 */         entity.setIsDefault(false);
/* 144 */         this.shopMemberAddressMng.updateByUpdater(entity);
/*     */       }
/*     */     }
/* 147 */     if ((provinceId == null) || (cityId == null) || (countryId == null)) {
/* 148 */       ShopFrontHelper.setCommonData(request, model, web, 1);
/* 149 */       return FrontUtils.showMessage(request, model, "收货地址有误，请重新选择收货地址");
/*     */     }
/* 151 */     bean.setProvince(this.addressMng.findById(provinceId).getName());
/* 152 */     bean.setCity(this.addressMng.findById(cityId).getName());
/* 153 */     bean.setCountry(this.addressMng.findById(countryId).getName());
/* 154 */     this.shopMemberAddressMng.updateByUpdater(bean);
/* 155 */     log.info("ShopMemberAddress update success, id= {}", bean.getId());
/* 156 */     return "redirect:address_list.jspx";
/*     */   }
/*     */   @RequestMapping(value={"/shopMemberAddress/address_default.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String isDefault(Long id, String returnUrl, Integer count, HttpServletRequest request, ModelMap model) {
/* 161 */     ShopMember member = MemberThread.get();
/* 162 */     if (member == null) {
/* 163 */       return "redirect:../login.jspx";
/*     */     }
/* 165 */     List list = this.shopMemberAddressMng.getList(member.getId());
/* 166 */     ShopMemberAddress bean = this.shopMemberAddressMng.findById(id);
/* 167 */     ShopMemberAddress entity = null;
/* 168 */     int i = 0; for (int j = list.size(); i < j; i++) {
/* 169 */       entity = (ShopMemberAddress)list.get(i);
/* 170 */       entity.setIsDefault(false);
/* 171 */       this.shopMemberAddressMng.updateByUpdater(entity);
/*     */     }
/* 173 */     bean.setIsDefault(true);
/* 174 */     this.shopMemberAddressMng.updateByUpdater(bean);
/* 175 */     log.info("ShopMemberAddress default success, id= {}", bean.getId());
/* 176 */     if (returnUrl != null) {
/* 177 */       if (count != null) {
/* 178 */         return "redirect:" + returnUrl + "&count=" + count;
/*     */       }
/* 180 */       return "redirect:" + returnUrl;
/*     */     }
/*     */ 
/* 183 */     return "redirect:address_list.jspx";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/shopMemberAddress/address_delete.jspx"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String delete(Long id, String returnUrl, Integer count, HttpServletRequest request, ModelMap model) {
/* 189 */     Website web = SiteUtils.getWeb(request);
/* 190 */     ShopMember member = MemberThread.get();
/* 191 */     if (member == null) {
/* 192 */       return "redirect:../login.jspx";
/*     */     }
/* 194 */     WebErrors errors = validateDelete(id, member.getId(), request);
/* 195 */     if (errors.hasErrors()) {
/* 196 */       return FrontHelper.showError(errors, web, model, request);
/*     */     }
/* 198 */     this.shopMemberAddressMng.deleteById(id, member.getId());
/* 199 */     log.info("ShopMemberAddress delete success, id= {}", id);
/* 200 */     if (returnUrl != null) {
/* 201 */       if (count != null) {
/* 202 */         return "redirect:" + returnUrl + "&count=" + count;
/*     */       }
/* 204 */       return "redirect:" + returnUrl;
/*     */     }
/*     */ 
/* 207 */     return "redirect:address_list.jspx";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopMemberAddress/findAllCity.jspx"})
/*     */   public void findAllCity(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 216 */     List clist = this.addressMng.getListById(id);
/* 217 */     Long[] ids = new Long[clist.size()];
/* 218 */     String[] citys = new String[clist.size()];
/* 219 */     int i = 0; for (int j = clist.size(); i < j; i++) {
/* 220 */       Address city = (Address)clist.get(i);
/* 221 */       ids[i] = city.getId();
/* 222 */       citys[i] = city.getName();
/*     */     }
/* 224 */     JSONObject json = new JSONObject();
/*     */     try {
/* 226 */       json.put("ids", ids);
/* 227 */       json.put("citys", citys);
/* 228 */       json.put("success", true);
/*     */     } catch (JSONException e) {
/*     */       try {
/* 231 */         json.put("success", false);
/*     */       } catch (JSONException e1) {
/* 233 */         e1.printStackTrace();
/*     */       }
/* 235 */       e.printStackTrace();
/*     */     }
/* 237 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/shopMemberAddress/findAllCountry.jspx"})
/*     */   public void findAllArea(Long id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/* 245 */     List alist = this.addressMng.getListById(id);
/* 246 */     Long[] ids = new Long[alist.size()];
/* 247 */     String[] areas = new String[alist.size()];
/* 248 */     int i = 0; for (int j = alist.size(); i < j; i++) {
/* 249 */       Address area = (Address)alist.get(i);
/* 250 */       ids[i] = area.getId();
/* 251 */       areas[i] = area.getName();
/*     */     }
/* 253 */     JSONObject json = new JSONObject();
/*     */     try {
/* 255 */       json.put("ids", ids);
/* 256 */       json.put("areas", areas);
/* 257 */       json.put("success", true);
/*     */     } catch (JSONException e) {
/*     */       try {
/* 260 */         json.put("success", false);
/*     */       } catch (JSONException e1) {
/* 262 */         e1.printStackTrace();
/*     */       }
/* 264 */       e.printStackTrace();
/*     */     }
/* 266 */     ResponseUtils.renderJson(response, json.toString());
/*     */   }
/*     */ 
/*     */   private WebErrors validateEdit(Long addressId, Long memberId, HttpServletRequest request)
/*     */   {
/* 271 */     WebErrors errors = WebErrors.create(request);
/* 272 */     if (vldAddress(addressId, memberId, errors)) {
/* 273 */       return errors;
/*     */     }
/* 275 */     return errors;
/*     */   }
/*     */ 
/*     */   private WebErrors validateDelete(Long addressId, Long memberId, HttpServletRequest request)
/*     */   {
/* 280 */     WebErrors errors = WebErrors.create(request);
/* 281 */     if (vldAddress(addressId, memberId, errors)) {
/* 282 */       return errors;
/*     */     }
/* 284 */     return errors;
/*     */   }
/*     */ 
/*     */   private boolean vldAddress(Long addressId, Long memberId, WebErrors errors)
/*     */   {
/* 290 */     if (errors.ifNull(addressId, "id")) {
/* 291 */       return true;
/*     */     }
/* 293 */     ShopMemberAddress address = this.shopMemberAddressMng.findById(addressId);
/* 294 */     if (errors.ifNotExist(address, ShopMemberAddress.class, addressId)) {
/* 295 */       return true;
/*     */     }
/* 297 */     if (!memberId.equals(address.getMember().getId())) {
/* 298 */       errors.noPermission(ShopMemberAddress.class, addressId);
/*     */     }
/* 300 */     return false;
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.member.ShopMenberAddressAct
 * JD-Core Version:    0.6.0
 */