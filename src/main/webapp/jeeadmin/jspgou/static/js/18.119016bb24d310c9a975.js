webpackJsonp([18],{"6URI":function(t,e,a){var n=a("LdaQ");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a("8bSs")("03ac9141",n,!0)},Cgi1:function(t,e,a){"use strict";function n(t){return Object(st.a)({url:rt.a.getApiInfoList,method:"post",data:t,signValidate:!0})}function i(t){return Object(st.a)({url:rt.a.deleteApiInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object(st.a)({url:rt.a.updateApiInfo,method:"post",data:t,signValidate:!0})}function s(t){return Object(st.a)({url:rt.a.addApiInfo,method:"post",data:t,signValidate:!0})}function r(t){return Object(st.a)({url:rt.a.getApiInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object(st.a)({url:rt.a.getApiAccountList,method:"post",data:t,signValidate:!0})}function l(t){return Object(st.a)({url:rt.a.getApiAccountInfo,method:"post",data:t,signValidate:!0})}function u(t){return Object(st.a)({url:rt.a.addApiAccountInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object(st.a)({url:rt.a.updateApiPassword,method:"post",data:t,signValidate:!0})}function f(t){return Object(st.a)({url:rt.a.checkApiPassword,method:"post",data:t,nonceStr:!0,signValidate:!0})}function p(t){return Object(st.a)({url:rt.a.getApiRecordList,method:"post",data:t,signValidate:!0})}function g(t){return Object(st.a)({url:rt.a.deleteApiRecordInfo,method:"post",data:t,signValidate:!0})}function m(t){return Object(st.a)({url:rt.a.getFtpList,method:"post",data:t,signValidate:!0})}function b(t){return Object(st.a)({url:rt.a.getFtpInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(st.a)({url:rt.a.addFtpInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(st.a)({url:rt.a.updateFtpInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(st.a)({url:rt.a.deleteFtpInfo,method:"post",data:t,signValidate:!0})}function j(t){return Object(st.a)({url:rt.a.getGlobalInfo,method:"post",data:t,signValidate:!0})}function y(t){return Object(st.a)({url:rt.a.updateGlobalInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object(st.a)({url:rt.a.getBasicInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object(st.a)({url:rt.a.updateBasicInfo,method:"post",data:t,signValidate:!0})}function E(t){return Object(st.a)({url:rt.a.getEmailInfo,method:"post",data:t,signValidate:!0})}function x(t){return Object(st.a)({url:rt.a.updateEmailInfo,method:"post",data:t,signValidate:!0})}function _(t){return Object(st.a)({url:rt.a.getShipList,method:"post",data:t,signValidate:!0})}function w(t){return Object(st.a)({url:rt.a.getShipInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(st.a)({url:rt.a.addShipInfo,method:"post",data:t,signValidate:!0})}function q(t){return Object(st.a)({url:rt.a.updateShipInfo,method:"post",data:t,signValidate:!0})}function A(t){return Object(st.a)({url:rt.a.priorityShipInfo,method:"post",data:t,signValidate:!0})}function C(t){return Object(st.a)({url:rt.a.deleteShipInfo,method:"post",data:t,signValidate:!0})}function $(t){return Object(st.a)({url:rt.a.getLogisticsList,method:"post",data:t,signValidate:!0})}function K(t){return Object(st.a)({url:rt.a.getLogisticsInfo,method:"post",data:t,signValidate:!0})}function k(t){return Object(st.a)({url:rt.a.addLogisticsInfo,method:"post",data:t,signValidate:!0})}function T(t){return Object(st.a)({url:rt.a.updateLogisticsInfo,method:"post",data:t,signValidate:!0})}function L(t){return Object(st.a)({url:rt.a.deleteLogisticsInfo,method:"post",data:t,signValidate:!0})}function M(t){return Object(st.a)({url:rt.a.priorityLogisticsInfo,method:"post",data:t,signValidate:!0})}function P(t){return Object(st.a)({url:rt.a.getSsoInfo,method:"post",data:t,signValidate:!0})}function W(t){return Object(st.a)({url:rt.a.updateSsoInfo,method:"post",data:t,signValidate:!0})}function X(t){return Object(st.a)({url:rt.a.getThirdApiInfo,method:"post",data:t,signValidate:!0})}function U(t){return Object(st.a)({url:rt.a.updateThirdApiInfo,method:"post",data:t,signValidate:!0})}function D(t){return Object(st.a)({url:rt.a.getPaymentPluginsList,method:"post",data:t,signValidate:!0})}function F(t){return Object(st.a)({url:rt.a.getPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function R(t){return Object(st.a)({url:rt.a.updatePaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function B(t){return Object(st.a)({url:rt.a.priorityPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function G(t){return Object(st.a)({url:rt.a.getWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function Q(t){return Object(st.a)({url:rt.a.updateWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function N(t){return Object(st.a)({url:rt.a.getWeiXinMenuList,method:"post",data:t,signValidate:!0})}function z(t){return Object(st.a)({url:rt.a.getWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function J(t){return Object(st.a)({url:rt.a.addWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Y(t){return Object(st.a)({url:rt.a.updateWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function H(t){return Object(st.a)({url:rt.a.deleteWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Z(t){return Object(st.a)({url:rt.a.getWeiXinMessageList,method:"post",data:t,signValidate:!0})}function tt(t){return Object(st.a)({url:rt.a.addWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function et(t){return Object(st.a)({url:rt.a.getWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function at(t){return Object(st.a)({url:rt.a.updateWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function nt(t){return Object(st.a)({url:rt.a.deleteWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function it(t){return Object(st.a)({url:rt.a.getDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function ot(t){return Object(st.a)({url:rt.a.updateDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}e.s=n,e.i=i,e.Q=o,e.b=s,e.r=r,e.q=d,e.p=l,e.a=u,e.R=c,e.h=f,e.t=p,e.j=g,e.y=m,e.x=b,e.c=h,e.V=I,e.k=v,e.z=j,e.W=y,e.u=O,e.S=V,e.w=E,e.U=x,e.F=_,e.E=w,e.e=S,e.Z=q,e.P=A,e.m=C,e.B=$,e.A=K,e.d=k,e.X=T,e.l=L,e.N=M,e.G=P,e._0=W,e.H=X,e._1=U,e.D=D,e.C=F,e.Y=R,e.O=B,e.M=G,e._4=Q,e.J=N,e.I=z,e.f=J,e._2=Y,e.n=H,e.L=Z,e.g=tt,e.K=et,e._3=at,e.o=nt,e.v=it,e.T=ot;var st=a("yYIz"),rt=a("z+6n")},LdaQ:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,"",""])},"T+4a":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v(t._s(t.$route.name))])]),t._v(" "),a("el-form",{ref:"form",staticClass:"table-body",attrs:{"label-width":"160px",model:t.info,rules:t.rules}},[a("el-row",{staticClass:"form-group"},[a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"QQ登陆:"}},[a("el-radio",{attrs:{label:!0},model:{value:t.info.qqEnable,callback:function(e){t.$set(t.info,"qqEnable",e)},expression:"info.qqEnable"}},[t._v("是")]),t._v(" "),a("el-radio",{attrs:{label:!1},model:{value:t.info.qqEnable,callback:function(e){t.$set(t.info,"qqEnable",e)},expression:"info.qqEnable"}},[t._v("否")]),t._v(" "),a("span",{staticClass:"gray"},[t._v("(为了安全起见，填写完成提交后，密钥不可见)")])],1)],1),t._v(" "),t.info.qqEnable?a("el-col",{attrs:{xs:24,sm:24,md:24,lg:12}},[a("el-form-item",{attrs:{label:"APP ID:",prop:"name"}},[a("el-input",{staticClass:"w192",model:{value:t.info.qqID,callback:function(e){t.$set(t.info,"qqID",e)},expression:"info.qqID"}})],1)],1):t._e(),t._v(" "),t.info.qqEnable?a("el-col",{attrs:{xs:24,sm:24,md:24,lg:12}},[a("el-form-item",{attrs:{label:"APP KEY:",prop:"name"}},[a("el-input",{staticClass:"w192",model:{value:t.info.qqKey,callback:function(e){t.$set(t.info,"qqKey",e)},expression:"info.qqKey"}})],1)],1):t._e(),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"新浪微博登陆:"}},[a("el-radio",{attrs:{label:!0},model:{value:t.info.sinaEnable,callback:function(e){t.$set(t.info,"sinaEnable",e)},expression:"info.sinaEnable"}},[t._v("是")]),t._v(" "),a("el-radio",{attrs:{label:!1},model:{value:t.info.sinaEnable,callback:function(e){t.$set(t.info,"sinaEnable",e)},expression:"info.sinaEnable"}},[t._v("否")]),t._v(" "),a("span",{staticClass:"gray"},[t._v("(为了安全起见，填写完成提交后，密钥不可见)")])],1)],1),t._v(" "),t.info.sinaEnable?a("el-col",{attrs:{xs:24,sm:24,md:24,lg:12}},[a("el-form-item",{attrs:{label:"App Key:"}},[a("el-input",{staticClass:"w192",model:{value:t.info.sinaID,callback:function(e){t.$set(t.info,"sinaID",e)},expression:"info.sinaID"}})],1)],1):t._e(),t._v(" "),t.info.sinaEnable?a("el-col",{attrs:{xs:24,sm:24,md:24,lg:12}},[a("el-form-item",{attrs:{label:"App Secret:",prop:"name"}},[a("el-input",{staticClass:"w192",model:{value:t.info.sinaKey,callback:function(e){t.$set(t.info,"sinaKey",e)},expression:"info.sinaKey"}})],1)],1):t._e(),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"微信登录:",prop:"relativePath"}},[a("el-radio",{attrs:{label:!0},model:{value:t.info.weixinEnable,callback:function(e){t.$set(t.info,"weixinEnable",e)},expression:"info.weixinEnable"}},[t._v("是")]),t._v(" "),a("el-radio",{attrs:{label:!1},model:{value:t.info.weixinEnable,callback:function(e){t.$set(t.info,"weixinEnable",e)},expression:"info.weixinEnable"}},[t._v("否")]),t._v(" "),a("span",{staticClass:"gray"},[t._v("(为了安全起见，填写完成提交后，密钥不可见)")])],1)],1),t._v(" "),t.info.weixinEnable?a("el-col",{attrs:{xs:24,sm:24,md:24,lg:12}},[a("el-form-item",{attrs:{label:"App Key:",prop:"name"}},[a("el-input",{staticClass:"w192",model:{value:t.info.weixinID,callback:function(e){t.$set(t.info,"weixinID",e)},expression:"info.weixinID"}})],1)],1):t._e(),t._v(" "),t.info.weixinEnable?a("el-col",{attrs:{xs:24,sm:24,md:24,lg:12}},[a("el-form-item",{attrs:{label:"App Secret:",prop:"name"}},[a("el-input",{staticClass:"w192",model:{value:t.info.weixinKey,callback:function(e){t.$set(t.info,"weixinKey",e)},expression:"info.weixinKey"}})],1)],1):t._e()],1)],1),t._v(" "),a("div",{staticClass:"form-bottom-bar"},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.updateForm()}}},[t._v("修改")]),t._v(" "),a("el-button",{attrs:{type:"info"},on:{click:function(e){t.restForm()}}},[t._v("重置")])],1)],1)},i=[],o={render:n,staticRenderFns:i};e.a=o},b1Wg:function(t,e,a){"use strict";var n=a("xeux"),i=a("oGsl"),o=a("/Xao"),s=o(n.a,i.a,!1,null,null,null);e.a=s.exports},kuv1:function(t,e,a){"use strict";var n=a("b1Wg"),i=a("Cgi1");e.a={components:{VueUEditor:n.a},data:function(){return{loading:!0,info:{},UE:"",rules:{}}},methods:{getInfo:function(){var t=this;i.H().then(function(e){t.info=e.body[0]}),this.loading=!1},updateForm:function(){var t=this;this.$refs.form.validate(function(e){e&&(t.loading=!0,i._1(t.info).then(function(e){t.loading=!1,200==e.code&&t.successMessage("修改成功")}).catch(function(e){t.errorMessage("执行异常"),t.loading=!1}))})},restForm:function(){this.$refs.form.resetFields(),this.getInfo()}},created:function(){this.getInfo()}}},oGsl:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement;return(t._self._c||e)("script",{attrs:{id:t.randomId,name:"content",type:"text/plain"}})},i=[],o={render:n,staticRenderFns:i};e.a=o},vbd6:function(t,e,a){"use strict";function n(t){a("6URI")}Object.defineProperty(e,"__esModule",{value:!0});var i=a("kuv1"),o=a("T+4a"),s=a("/Xao"),r=n,d=s(i.a,o.a,!1,r,null,null);e.default=d.exports},xeux:function(t,e,a){"use strict";e.a={name:"VueUEditor",props:{ueditorPath:{type:String,default:"./static/ueditor/"},defaultMsg:{type:String,default:""},ueditorConfig:{type:Object,default:function(){return{}}},index:{default:0}},data:function(){return{randomId:"editor_"+1e4*Math.random(),instance:null,scriptTagStatus:0,appId:this.$store.state.sys.appId}},created:function(){void 0!==window.UE?(this.scriptTagStatus=2,this.initEditor()):this.insertScriptTag()},mounted:function(){},beforeDestroy:function(){null!==this.instance&&this.instance.destroy&&this.instance.destroy()},watch:{defaultMsg:function(t,e){this.instance=window.UE.getEditor(this.randomId,this.ueditorConfig),null!==t&&this.instance.ready(function(){this.setContent(t)})}},methods:{insertScriptTag:function(){var t=this,e=document.getElementById("editorScriptTag"),a=document.getElementById("configScriptTag");if(null===e){a=document.createElement("script"),a.type="text/javascript",a.src=this.ueditorPath+"ueditor.config.js",a.id="configScriptTag",e=document.createElement("script"),e.type="text/javascript",e.src=this.ueditorPath+"ueditor.all.js",e.id="editorScriptTag";var n=document.getElementsByTagName("head")[0];n.appendChild(a),n.appendChild(e)}a.loaded?this.scriptTagStatus++:a.addEventListener("load",function(){t.scriptTagStatus++,a.loaded=!0,t.initEditor()}),e.loaded?this.scriptTagStatus++:e.addEventListener("load",function(){t.scriptTagStatus++,e.loaded=!0,t.initEditor()}),this.initEditor()},getUEContent:function(){return this.instance.getContent()},initEditor:function(){var t=this,e=this;2===this.scriptTagStatus&&null===this.instance&&this.$nextTick(function(){t.instance=window.UE.getEditor(t.randomId,t.ueditorConfig),t.instance.addListener("ready",function(){window.UE.Editor.prototype._bkGetActionUrl=window.UE.Editor.prototype.getActionUrl,window.UE.Editor.prototype.getActionUrl=function(t){return"uploadimage"==t?"http://192.168.0.173:8080/jeecmsv9/api/admin/ueditor/upload?sessionKey=41e913c000de6b32c2bfb9ab98d67ab5f2377b7c2b605d9c599a688f3bfdd189454b68b238d570cf5b0ad835c5710337&appId=1580387213331704":"uploadvideo"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/upload?sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"uploadfile"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/upload?sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"catchimage"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/getRemoteImage?sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"uploadscrawl"==t?"http://192.168.0.199:12000/newjspgou/api/admin//ueditor/scrawlImage?Type=Image&sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"listimage"==t||"listfile"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/imageManager?picNum=50&insite=false&sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"config"==t?"/static/ueditor/config.json":void 0},t.$emit("ready",t.instance,t.index),t.instance.setContent(t.defaultMsg)})})}}}}});