/*    */ package com.jspgou.cms.action.directive;
/*    */ 
/*    */ import com.jspgou.cms.action.directive.abs.WebDirective;
/*    */ import com.jspgou.cms.entity.ShopChannel;
/*    */ import com.jspgou.cms.manager.ShopChannelMng;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.ObjectWrapper;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import freemarker.template.TemplateModelException;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class ChannelListDirective extends WebDirective
/*    */ {
/*    */   public static final String TPL_NAME = "TopChannel";
/*    */   private ShopChannelMng shopChannelMng;
/*    */   private WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 40 */     Long webId = getWebId(params);
/*    */     Website web;
/* 42 */     if (webId == null)
/* 43 */       web = getWeb(env, params, this.websiteMng);
/*    */     else {
/* 45 */       web = this.websiteMng.findById(webId);
/*    */     }
/* 47 */     if (web == null) {
/* 48 */       throw new TemplateModelException("webId=" + webId + " not exist!");
/*    */     }
/* 50 */     Integer parentId = DirectiveUtils.getInt("parentId", params);
/*    */     List list;
/* 52 */     if (parentId != null) {
/* 53 */       ShopChannel channel = this.shopChannelMng.findById(parentId);
/* 54 */       if (channel != null)
/* 55 */         list = new ArrayList(channel.getChild());
/*    */       else
/* 57 */         list = new ArrayList();
/*    */     }
/*    */     else {
/* 60 */       list = this.shopChannelMng.getTopListForTag(web.getId(), Integer.valueOf(getCount(params)));
/*    */     }
/* 62 */     Map paramsWrap = new HashMap(
/* 63 */       params);
/* 64 */     paramsWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(list));
/* 65 */     Map origMap = 
/* 66 */       DirectiveUtils.addParamsToVariable(env, paramsWrap);
/* 67 */     if (isInvokeTpl(params))
/* 68 */       includeTpl("shop", "TopChannel", web, params, env);
/*    */     else {
/* 70 */       renderBody(env, loopVars, body);
/*    */     }
/* 72 */     DirectiveUtils.removeParamsFromVariable(env, paramsWrap, origMap);
/*    */   }
/*    */ 
/*    */   private void renderBody(Environment env, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException
/*    */   {
/* 77 */     body.render(env.getOut());
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setShopChannelMng(ShopChannelMng shopChannelMng)
/*    */   {
/* 85 */     this.shopChannelMng = shopChannelMng;
/*    */   }
/*    */ 
/*    */   public void setWebsiteMng(WebsiteMng websiteMng) {
/* 89 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.action.directive.ChannelListDirective
 * JD-Core Version:    0.6.0
 */