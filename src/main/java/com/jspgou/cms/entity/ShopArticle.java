/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseShopArticle;
/*    */ import com.jspgou.core.entity.Website;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ import net.sf.json.JSONObject;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class ShopArticle extends BaseShopArticle
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public String getUrl()
/*    */   {
/* 27 */     if (!StringUtils.isBlank(getLink())) {
/* 28 */       return getLink();
/*    */     }
/* 30 */     return "/" + 
/* 31 */       getChannel().getPath() + "/" + getId() + 
/* 32 */       getWebsite().getSuffix();
/*    */   }
/*    */ 
/*    */   public String getContent()
/*    */   {
/* 41 */     ShopArticleContent content = getArticleContent();
/* 42 */     if (content != null) {
/* 43 */       return content.getContent();
/*    */     }
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 50 */     Date d = getPublishTime();
/* 51 */     if (d == null)
/* 52 */       setPublishTime(new Timestamp(System.currentTimeMillis()));
/*    */   }
/*    */ 
/*    */   public ShopArticle()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShopArticle(Long id)
/*    */   {
/* 65 */     super(id);
/*    */   }
/*    */ 
/*    */   public ShopArticle(Long id, Website website, ShopChannel channel, String title, Date publishTime, Boolean disabled)
/*    */   {
/* 77 */     super(id, website, channel, title, publishTime, disabled);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 84 */     JSONObject json = new JSONObject();
/* 85 */     json.put("id", CommonUtils.parseId(getId()));
/* 86 */     json.put("channelId", getChannel() != null ? Integer.valueOf(CommonUtils.parseInteger(getChannel().getId())) : "");
/* 87 */     json.put("channelName", getChannel() != null ? CommonUtils.parseStr(getChannel().getName()) : "");
/* 88 */     json.put("title", CommonUtils.parseStr(getTitle()));
/* 89 */     json.put("disabled", CommonUtils.parseBoolean(getDisabled()));
/* 90 */     json.put("content", CommonUtils.parseStr(getContent()));
/* 91 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.ShopArticle
 * JD-Core Version:    0.6.0
 */