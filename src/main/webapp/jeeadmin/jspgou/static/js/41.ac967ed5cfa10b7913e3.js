webpackJsonp([41],{"4Paw":function(t,a,e){"use strict";var n=e("Cgi1");a.a={data:function(){return{loading:!0,systemInfo:{},groupList:[],rules:{exchangetax:[{required:!0,type:"number",message:"请输入一个数字",trigger:"blur"}],miniBalance:[{required:!0,type:"number",message:"此项必填",trigger:"blur"}]},params:""}},methods:{getSsoInfo:function(){var t=this;n.G().then(function(a){"200"==a.code&&(t.loading=!1,t.systemInfo=a.body)}).catch(function(a){t.loading=!1})},updateSsoInfo:function(t){var a=this;this.$refs[t].validate(function(t){if(document.querySelector("#main").scrollTop=0,!t)return document.querySelector("#main").scrollTop=0,!1;a.loading=!0;var e=a.params;n._0(e).then(function(t){a.loading=!1,"200"==t.code&&a.$message.success("修改成功")}).catch(function(t){a.loading=!1,a.$message.error("修改异常")})})},deledeItems:function(t){this.systemInfo.attr.splice(t,1)},addSso:function(){var t=this.systemInfo.attr.length+1,a={value:"",key:"attr_sso_"+t};this.systemInfo.attr.push(a)}},created:function(){this.getSsoInfo()},watch:{$route:function(t,a){this.loading=!0,this.getSsoInfo()},systemInfo:{handler:function(t,a){var e=t.attr;this.params={attr_ssoEnable:t.attr_ssoEnable};for(var n in e)this.params[e[n].key]=e[n].value},deep:!0}}}},"8Qir":function(t,a,e){"use strict";var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"forum-module"},[t._m(0,!1,!1),t._v(" "),e("el-form",{ref:"topicInfo",staticClass:"table-body",attrs:{model:t.systemInfo,rules:t.rules,"label-width":"160px"}},[e("el-row",{staticClass:"form-group"},[e("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[e("el-form-item",{staticClass:"form-group",attrs:{label:"是否开启单点认证"}},[e("el-radio",{attrs:{label:!0},model:{value:t.systemInfo.attr_ssoEnable,callback:function(a){t.$set(t.systemInfo,"attr_ssoEnable",a)},expression:"systemInfo.attr_ssoEnable"}},[t._v("是")]),t._v(" "),e("el-radio",{attrs:{label:!1},model:{value:t.systemInfo.attr_ssoEnable,callback:function(a){t.$set(t.systemInfo,"attr_ssoEnable",a)},expression:"systemInfo.attr_ssoEnable"}},[t._v("否")])],1)],1),t._v(" "),e("el-col",{directives:[{name:"show",rawName:"v-show",value:t.systemInfo.attr_ssoEnable,expression:"systemInfo.attr_ssoEnable"}],attrs:{xs:24,sm:24,md:24,lg:12}},[e("el-form-item",{staticClass:"form-group",attrs:{label:"认证地址"}},[e("el-col",{attrs:{span:20}},[t._l(t.systemInfo.attr,function(a,n){return e("el-row",{key:a.key,staticClass:"mb10",attrs:{gutter:15}},[e("el-col",{attrs:{span:10}},[e("el-input",{model:{value:a.value,callback:function(e){t.$set(a,"value",e)},expression:"item.value"}})],1),t._v(" "),e("el-col",{staticClass:"required",attrs:{span:1}},[e("el-button",{attrs:{type:"danger"},on:{click:function(a){t.deledeItems(n)}}},[t._v("删除")])],1)],1)}),t._v(" "),e("el-col",{staticClass:"required",attrs:{span:1}},[e("el-button",{attrs:{type:"warning"},on:{click:t.addSso}},[t._v("添加")])],1)],2)],1)],1)],1)],1),t._v(" "),e("div",{staticClass:"form-bottom-bar"},[e("el-button",{attrs:{type:"primary"},on:{click:function(a){t.updateSsoInfo("topicInfo")}}},[t._v("修改")]),t._v(" "),e("el-button",{attrs:{type:"info"},on:{click:function(a){t.restForm()}}},[t._v("重置")])],1)],1)},o=[function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"forum-header"},[e("span",{staticClass:"forum-name"},[t._v("单点登录设置")])])}],i={render:n,staticRenderFns:o};a.a=i},Cgi1:function(t,a,e){"use strict";function n(t){return Object(st.a)({url:rt.a.getApiInfoList,method:"post",data:t,signValidate:!0})}function o(t){return Object(st.a)({url:rt.a.deleteApiInfo,method:"post",data:t,signValidate:!0})}function i(t){return Object(st.a)({url:rt.a.updateApiInfo,method:"post",data:t,signValidate:!0})}function s(t){return Object(st.a)({url:rt.a.addApiInfo,method:"post",data:t,signValidate:!0})}function r(t){return Object(st.a)({url:rt.a.getApiInfo,method:"post",data:t,signValidate:!0})}function u(t){return Object(st.a)({url:rt.a.getApiAccountList,method:"post",data:t,signValidate:!0})}function d(t){return Object(st.a)({url:rt.a.getApiAccountInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(st.a)({url:rt.a.addApiAccountInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object(st.a)({url:rt.a.updateApiPassword,method:"post",data:t,signValidate:!0})}function f(t){return Object(st.a)({url:rt.a.checkApiPassword,method:"post",data:t,nonceStr:!0,signValidate:!0})}function p(t){return Object(st.a)({url:rt.a.getApiRecordList,method:"post",data:t,signValidate:!0})}function m(t){return Object(st.a)({url:rt.a.deleteApiRecordInfo,method:"post",data:t,signValidate:!0})}function g(t){return Object(st.a)({url:rt.a.getFtpList,method:"post",data:t,signValidate:!0})}function b(t){return Object(st.a)({url:rt.a.getFtpInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(st.a)({url:rt.a.addFtpInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(st.a)({url:rt.a.updateFtpInfo,method:"post",data:t,signValidate:!0})}function j(t){return Object(st.a)({url:rt.a.deleteFtpInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object(st.a)({url:rt.a.getGlobalInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object(st.a)({url:rt.a.updateGlobalInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(st.a)({url:rt.a.getBasicInfo,method:"post",data:t,signValidate:!0})}function y(t){return Object(st.a)({url:rt.a.updateBasicInfo,method:"post",data:t,signValidate:!0})}function _(t){return Object(st.a)({url:rt.a.getEmailInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(st.a)({url:rt.a.updateEmailInfo,method:"post",data:t,signValidate:!0})}function A(t){return Object(st.a)({url:rt.a.getShipList,method:"post",data:t,signValidate:!0})}function L(t){return Object(st.a)({url:rt.a.getShipInfo,method:"post",data:t,signValidate:!0})}function X(t){return Object(st.a)({url:rt.a.addShipInfo,method:"post",data:t,signValidate:!0})}function k(t){return Object(st.a)({url:rt.a.updateShipInfo,method:"post",data:t,signValidate:!0})}function E(t){return Object(st.a)({url:rt.a.priorityShipInfo,method:"post",data:t,signValidate:!0})}function W(t){return Object(st.a)({url:rt.a.deleteShipInfo,method:"post",data:t,signValidate:!0})}function w(t){return Object(st.a)({url:rt.a.getLogisticsList,method:"post",data:t,signValidate:!0})}function x(t){return Object(st.a)({url:rt.a.getLogisticsInfo,method:"post",data:t,signValidate:!0})}function C(t){return Object(st.a)({url:rt.a.addLogisticsInfo,method:"post",data:t,signValidate:!0})}function M(t){return Object(st.a)({url:rt.a.updateLogisticsInfo,method:"post",data:t,signValidate:!0})}function P(t){return Object(st.a)({url:rt.a.deleteLogisticsInfo,method:"post",data:t,signValidate:!0})}function F(t){return Object(st.a)({url:rt.a.priorityLogisticsInfo,method:"post",data:t,signValidate:!0})}function $(t){return Object(st.a)({url:rt.a.getSsoInfo,method:"post",data:t,signValidate:!0})}function q(t){return Object(st.a)({url:rt.a.updateSsoInfo,method:"post",data:t,signValidate:!0})}function T(t){return Object(st.a)({url:rt.a.getThirdApiInfo,method:"post",data:t,signValidate:!0})}function B(t){return Object(st.a)({url:rt.a.updateThirdApiInfo,method:"post",data:t,signValidate:!0})}function G(t){return Object(st.a)({url:rt.a.getPaymentPluginsList,method:"post",data:t,signValidate:!0})}function R(t){return Object(st.a)({url:rt.a.getPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function Y(t){return Object(st.a)({url:rt.a.updatePaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function z(t){return Object(st.a)({url:rt.a.priorityPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function D(t){return Object(st.a)({url:rt.a.getWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function H(t){return Object(st.a)({url:rt.a.updateWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function J(t){return Object(st.a)({url:rt.a.getWeiXinMenuList,method:"post",data:t,signValidate:!0})}function Q(t){return Object(st.a)({url:rt.a.getWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Z(t){return Object(st.a)({url:rt.a.addWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function N(t){return Object(st.a)({url:rt.a.updateWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function U(t){return Object(st.a)({url:rt.a.deleteWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function K(t){return Object(st.a)({url:rt.a.getWeiXinMessageList,method:"post",data:t,signValidate:!0})}function tt(t){return Object(st.a)({url:rt.a.addWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function at(t){return Object(st.a)({url:rt.a.getWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function et(t){return Object(st.a)({url:rt.a.updateWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function nt(t){return Object(st.a)({url:rt.a.deleteWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function ot(t){return Object(st.a)({url:rt.a.getDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function it(t){return Object(st.a)({url:rt.a.updateDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}a.s=n,a.i=o,a.Q=i,a.b=s,a.r=r,a.q=u,a.p=d,a.a=l,a.R=c,a.h=f,a.t=p,a.j=m,a.y=g,a.x=b,a.c=h,a.V=I,a.k=j,a.z=O,a.W=V,a.u=v,a.S=y,a.w=_,a.U=S,a.F=A,a.E=L,a.e=X,a.Z=k,a.P=E,a.m=W,a.B=w,a.A=x,a.d=C,a.X=M,a.l=P,a.N=F,a.G=$,a._0=q,a.H=T,a._1=B,a.D=G,a.C=R,a.Y=Y,a.O=z,a.M=D,a._4=H,a.J=J,a.I=Q,a.f=Z,a._2=N,a.n=U,a.L=K,a.g=tt,a.K=at,a._3=et,a.o=nt,a.v=ot,a.T=it;var st=e("yYIz"),rt=e("z+6n")},Yhyt:function(t,a,e){a=t.exports=e("BkJT")(!1),a.push([t.i,".mb10[data-v-2ce19cae]{margin-bottom:10px}",""])},aHpZ:function(t,a,e){var n=e("Yhyt");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);e("8bSs")("0be35316",n,!0)},yUbF:function(t,a,e){"use strict";function n(t){e("aHpZ")}Object.defineProperty(a,"__esModule",{value:!0});var o=e("4Paw"),i=e("8Qir"),s=e("/Xao"),r=n,u=s(o.a,i.a,!1,r,"data-v-2ce19cae",null);a.default=u.exports}});