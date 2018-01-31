/*    */ package com.jspgou.cms.entity;
/*    */ 
/*    */ import com.jspgou.cms.api.CommonUtils;
/*    */ import com.jspgou.cms.entity.base.BaseWebservice;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class Webservice extends BaseWebservice
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final String SERVICE_TYPE_ADD_USER = "addUser";
/*    */   public static final String SERVICE_TYPE_UPDATE_USER = "updateUser";
/*    */   public static final String SERVICE_TYPE_DELETE_USER = "deleteUser";
/*    */ 
/*    */   public void addToParams(String name, String defaultValue)
/*    */   {
/* 23 */     List list = getParams();
/* 24 */     if (list == null) {
/* 25 */       list = new ArrayList();
/* 26 */       setParams(list);
/*    */     }
/* 28 */     WebserviceParam param = new WebserviceParam();
/* 29 */     param.setParamName(name);
/* 30 */     param.setDefaultValue(defaultValue);
/* 31 */     list.add(param);
/*    */   }
/*    */ 
/*    */   public Webservice()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Webservice(Integer id)
/*    */   {
/* 43 */     super(id);
/*    */   }
/*    */ 
/*    */   public Webservice(Integer id, String address)
/*    */   {
/* 55 */     super(id, 
/* 55 */       address);
/*    */   }
/*    */ 
/*    */   public JSONObject convertToJson() throws JSONException {
/* 59 */     JSONObject json = new JSONObject();
/* 60 */     json.put("id", CommonUtils.parseId(getId()));
/* 61 */     json.put("address", CommonUtils.parseStr(getAddress()));
/* 62 */     json.put("targetNamespace", CommonUtils.parseStr(getTargetNamespace()));
/* 63 */     json.put("successResult", CommonUtils.parseStr(getSuccessResult()));
/* 64 */     json.put("type", CommonUtils.parseStr(getType()));
/* 65 */     json.put("operate", CommonUtils.parseStr(getOperate()));
/*    */ 
/* 67 */     List list = getParams();
/* 68 */     JSONArray jsonArray = new JSONArray();
/* 69 */     if ((list != null) && (list.size() > 0)) {
/* 70 */       JSONObject obj = new JSONObject();
/* 71 */       for (int i = 0; i < list.size(); i++) {
/* 72 */         obj.put("paramName", CommonUtils.parseStr(((WebserviceParam)list.get(i)).getParamName()));
/* 73 */         obj.put("defaultValue", CommonUtils.parseStr(((WebserviceParam)list.get(i)).getDefaultValue()));
/* 74 */         jsonArray.put(obj);
/*    */       }
/* 76 */       json.put("param", jsonArray);
/*    */     }
/* 78 */     return json;
/*    */   }
/*    */ }

/* Location:           G:\jee系统\jspgou\jspgouV6-ROOT\ROOT\WEB-INF\classes\
 * Qualified Name:     com.jspgou.cms.entity.Webservice
 * JD-Core Version:    0.6.0
 */