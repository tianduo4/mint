/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BasePoster;
/*    */ import com.jspgou.common.util.DateUtils;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class Poster extends BasePoster
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public Poster()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Poster(Integer id)
/*    */   {
/* 27 */     super(id);
/*    */   }
/*    */ 
/*    */   public Poster(Integer id, String picUrl)
/*    */   {
/* 39 */     super(id, 
/* 39 */       picUrl);
/*    */   }
/*    */ 
/*    */   public JSONObject converToJson()
/*    */   {
/* 45 */     JSONObject json = new JSONObject();
/* 46 */     json.put("id", CommonUtils.parseId(getId()));
/* 47 */     json.put("picUrl", CommonUtils.parseId(getPicUrl()));
/* 48 */     json.put("time", CommonUtils.parseDate(getTime(), DateUtils.COMMON_FORMAT_STR));
/* 49 */     json.put("interUrl", CommonUtils.parseStr(getInterUrl()));
/* 50 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Poster
 * JD-Core Version:    0.6.0
 */