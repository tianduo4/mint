/*    */ package com.jspgou.cms.lucene;
/*    */ 
/*    */ import com.jspgou.common.page.Pagination;
/*    */ import com.jspgou.common.web.freemarker.DirectiveUtils;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import com.jspgou.core.manager.WebsiteMng;
/*    */ import freemarker.core.Environment;
/*    */ import freemarker.template.ObjectWrapper;
/*    */ import freemarker.template.TemplateDirectiveBody;
/*    */ import freemarker.template.TemplateException;
/*    */ import freemarker.template.TemplateModel;
/*    */ import freemarker.template.TemplateNumberModel;
/*    */ import java.io.IOException;
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.ServletContext;
/*    */ import org.apache.lucene.queryParser.ParseException;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class LuceneDirectivePage extends LuceneDirectiveAbstract
/*    */ {
/*    */   public static final String TPL_NAME = "ProductPage";
/*    */ 
/*    */   @Autowired
/*    */   private LuceneProductSvc luceneProductSvc;
/*    */ 
/*    */   @Autowired
/*    */   private ServletContext servletContext;
/*    */   protected WebsiteMng websiteMng;
/*    */ 
/*    */   public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
/*    */     throws TemplateException, IOException
/*    */   {
/* 42 */     Website web = this.websiteMng.findById(Long.valueOf(1L));
/* 43 */     String query = getQuery(params);
/* 44 */     Long ctgId = getCtgId(params);
/* 45 */     Long brandId = getBrandId(params);
/*    */ 
/* 47 */     Long type = getPtypeId(params);
/* 48 */     Date start = getStartDate(params);
/* 49 */     Date end = getEndDate(params);
/* 50 */     Integer orderBy = getInt("orderBy", params);
/* 51 */     int pageNo = ((TemplateNumberModel)env.getGlobalVariable("pageNo")).getAsNumber().intValue();
/*    */     try
/*    */     {
/* 54 */       String path = this.servletContext.getRealPath("/WEB-INF/lucene");
/* 55 */       pagination = this.luceneProductSvc.search(path, query, web.getId(), ctgId, brandId, start, end, orderBy.intValue(), pageNo, getCount(params));
/*    */     }
/*    */     catch (ParseException e)
/*    */     {
/*    */       Pagination pagination;
/* 57 */       throw new RuntimeException(e);
/*    */     }
/*    */     Pagination pagination;
/* 59 */     Map paramWrap = new HashMap(
/* 60 */       params);
/* 61 */     paramWrap.put("tag_pagination", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination));
/* 62 */     paramWrap.put("tag_list", ObjectWrapper.DEFAULT_WRAPPER.wrap(pagination.getList()));
/* 63 */     Map origMap = 
/* 64 */       DirectiveUtils.addParamsToVariable(env, paramWrap);
/* 65 */     if (isInvokeTpl(params))
/* 66 */       includeTpl("shop", "ProductPage", web, params, env);
/*    */     else {
/* 68 */       renderBody(env, loopVars, body);
/*    */     }
/* 70 */     DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
/*    */   }
/*    */ 
/*    */   @Autowired
/*    */   public void setWebsiteMng(WebsiteMng websiteMng)
/*    */   {
/* 82 */     this.websiteMng = websiteMng;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.lucene.LuceneDirectivePage
 * JD-Core Version:    0.6.0
 */