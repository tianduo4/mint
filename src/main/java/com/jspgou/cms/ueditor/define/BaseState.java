/*    */ package com.jspgou.cms.ueditor.define;
/*    */ 
/*    */ import com.jspgou.cms.ueditor.Encoder;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class BaseState
/*    */   implements State
/*    */ {
/* 11 */   private boolean state = false;
/* 12 */   private String info = null;
/*    */ 
/* 14 */   private Map<String, String> infoMap = new HashMap();
/*    */ 
/*    */   public BaseState() {
/* 17 */     this.state = true;
/*    */   }
/*    */ 
/*    */   public BaseState(boolean state) {
/* 21 */     setState(state);
/*    */   }
/*    */ 
/*    */   public BaseState(boolean state, String info) {
/* 25 */     setState(state);
/* 26 */     this.info = info;
/*    */   }
/*    */ 
/*    */   public BaseState(boolean state, int infoCode) {
/* 30 */     setState(state);
/* 31 */     this.info = AppInfo.getStateInfo(infoCode);
/*    */   }
/*    */ 
/*    */   public boolean isSuccess() {
/* 35 */     return this.state;
/*    */   }
/*    */ 
/*    */   public void setState(boolean state) {
/* 39 */     this.state = state;
/*    */   }
/*    */ 
/*    */   public void setInfo(String info) {
/* 43 */     this.info = info;
/*    */   }
/*    */ 
/*    */   public void setInfo(int infoCode) {
/* 47 */     this.info = AppInfo.getStateInfo(infoCode);
/*    */   }
/*    */ 
/*    */   public String toJSONString()
/*    */   {
/* 52 */     return toString();
/*    */   }
/*    */ 
/*    */   public String toJSONString2() {
/* 56 */     return toString();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 61 */     String key = null;
/* 62 */     String stateVal = isSuccess() ? AppInfo.getStateInfo(0) : this.info;
/*    */ 
/* 64 */     StringBuilder builder = new StringBuilder();
/*    */ 
/* 66 */     builder.append("{\"state\": \"" + stateVal + "\"");
/*    */ 
/* 68 */     Iterator iterator = this.infoMap.keySet().iterator();
/*    */ 
/* 70 */     while (iterator.hasNext())
/*    */     {
/* 72 */       key = (String)iterator.next();
/*    */ 
/* 74 */       builder.append(",\"" + key + "\": \"" + (String)this.infoMap.get(key) + "\"");
/*    */     }
/*    */ 
/* 78 */     builder.append("}");
/*    */ 
/* 80 */     return Encoder.toUnicode(builder.toString());
/*    */   }
/*    */ 
/*    */   public void putInfo(String name, String val)
/*    */   {
/* 86 */     this.infoMap.put(name, val);
/*    */   }
/*    */ 
/*    */   public void putInfo(String name, long val)
/*    */   {
/* 91 */     putInfo(name, val);
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.define.BaseState
 * JD-Core Version:    0.6.0
 */