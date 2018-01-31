/*     */ package com.jspgou.cms.ueditor.define;
/*     */ 
/*     */ import com.jspgou.cms.ueditor.Encoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class MultiState
/*     */   implements State
/*     */ {
/*  23 */   private boolean state = false;
/*  24 */   private String info = null;
/*  25 */   private Map<String, Long> intMap = new HashMap();
/*  26 */   private Map<String, String> infoMap = new HashMap();
/*  27 */   private List<String> stateList = new ArrayList();
/*     */ 
/*     */   public MultiState(boolean state) {
/*  30 */     this.state = state;
/*     */   }
/*     */ 
/*     */   public MultiState(boolean state, String info) {
/*  34 */     this.state = state;
/*  35 */     this.info = info;
/*     */   }
/*     */ 
/*     */   public MultiState(boolean state, int infoKey) {
/*  39 */     this.state = state;
/*  40 */     this.info = AppInfo.getStateInfo(infoKey);
/*     */   }
/*     */ 
/*     */   public boolean isSuccess()
/*     */   {
/*  45 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void addState(State state) {
/*  49 */     this.stateList.add(state.toJSONString());
/*     */   }
/*     */ 
/*     */   public void putInfo(String name, String val)
/*     */   {
/*  57 */     this.infoMap.put(name, val);
/*     */   }
/*     */ 
/*     */   public String toJSONString()
/*     */   {
/*  63 */     String stateVal = isSuccess() ? AppInfo.getStateInfo(0) : this.info;
/*     */ 
/*  65 */     StringBuilder builder = new StringBuilder();
/*     */ 
/*  67 */     builder.append("{\"state\":\"" + stateVal + "\"");
/*     */ 
/*  70 */     Iterator iterator = this.intMap.keySet().iterator();
/*     */ 
/*  72 */     while (iterator.hasNext())
/*     */     {
/*  74 */       stateVal = (String)iterator.next();
/*     */ 
/*  76 */       builder.append(",\"" + stateVal + "\":" + this.intMap.get(stateVal));
/*     */     }
/*     */ 
/*  80 */     iterator = this.infoMap.keySet().iterator();
/*     */ 
/*  82 */     while (iterator.hasNext())
/*     */     {
/*  84 */       stateVal = (String)iterator.next();
/*     */ 
/*  86 */       builder.append(",\"" + stateVal + "\":\"" + (String)this.infoMap.get(stateVal) + "\"");
/*     */     }
/*     */ 
/*  90 */     builder.append(",\"list\":[");
/*     */ 
/*  93 */     iterator = this.stateList.iterator();
/*     */ 
/*  95 */     while (iterator.hasNext())
/*     */     {
/*  97 */       builder.append((String)iterator.next() + ",");
/*     */     }
/*     */ 
/* 101 */     if (this.stateList.size() > 0) {
/* 102 */       builder.deleteCharAt(builder.length() - 1);
/*     */     }
/*     */ 
/* 105 */     builder.append("]}");
/*     */ 
/* 107 */     return Encoder.toUnicode(builder.toString());
/*     */   }
/*     */ 
/*     */   public String toJSONString2()
/*     */   {
/* 113 */     JSONObject json = new JSONObject();
/*     */     try {
/* 115 */       String stateVal = isSuccess() ? AppInfo.getStateInfo(0) : this.info;
/*     */ 
/* 117 */       StringBuilder builder = new StringBuilder();
/*     */ 
/* 119 */       builder.append("{\"state\":\"" + stateVal + "\"");
/*     */ 
/* 121 */       json.put("state", stateVal);
/*     */ 
/* 125 */       Iterator iterator = this.intMap.keySet().iterator();
/*     */ 
/* 127 */       while (iterator.hasNext())
/*     */       {
/* 129 */         stateVal = (String)iterator.next();
/*     */ 
/* 131 */         builder.append(",\"" + stateVal + "\":" + this.intMap.get(stateVal));
/*     */       }
/*     */ 
/* 135 */       iterator = this.infoMap.keySet().iterator();
/*     */ 
/* 137 */       while (iterator.hasNext())
/*     */       {
/* 139 */         stateVal = (String)iterator.next();
/*     */ 
/* 141 */         builder.append(",\"" + stateVal + "\":\"" + (String)this.infoMap.get(stateVal) + "\"");
/*     */       }
/*     */ 
/* 145 */       builder.append(",list:[");
/*     */ 
/* 147 */       JSONArray array = new JSONArray();
/* 148 */       int i = 0;
/* 149 */       iterator = this.stateList.iterator();
/* 150 */       while (iterator.hasNext())
/*     */       {
/* 153 */         JSONObject jobj = new JSONObject();
/* 154 */         jobj.put(stateVal, iterator.next());
/* 155 */         array.put(i++, jobj);
/*     */       }
/* 157 */       json.put("list", array);
/*     */ 
/* 159 */       if (this.stateList.size() > 0) {
/* 160 */         builder.deleteCharAt(builder.length() - 1);
/*     */       }
/*     */ 
/* 163 */       builder.append("]}");
/*     */     }
/*     */     catch (JSONException e) {
/* 166 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 169 */     return json.toString();
/*     */   }
/*     */ 
/*     */   public void putInfo(String name, long val)
/*     */   {
/* 174 */     this.intMap.put(name, Long.valueOf(val));
/*     */   }
/*     */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.ueditor.define.MultiState
 * JD-Core Version:    0.6.0
 */