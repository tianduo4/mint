webpackJsonp([13],{"0UHm":function(t,a,e){"use strict";var i=e("Cgi1"),n=e("Uwrw");a.a={components:{imageUpload:n.a},data:function(){return{loading:!1,type:this.$route.query.type,id:this.$route.query.id,info:{},tmplateUrl:"",typeList:[],pathUrl:"",rules:{name:[{required:!0,message:"请输入名称",trigger:"blur"}],logisticsType:[{required:!0,message:"此项必填",trigger:"change"}],logisticsId:[{required:!0,type:"number",message:"此项必填",trigger:"change"}],priority:[{required:!0,type:"number",message:"请输入数字排序",trigger:"blur"}]}}},methods:{getInfo:function(t){var a=this;i.A({id:t}).then(function(t){a.info=t.body,a.type,a.loading=!1}),i.B().then(function(t){a.typeList=t.body})},getPath:function(t){this.info.logoPath=t,this.pathUrl=t},updateForm:function(){var t=this;this.$refs.form.validate(function(a){a&&(t.loading=!0,i.X(t.info).then(function(a){t.loading=!1,200==a.code&&(t.successMessage("修改成功"),setTimeout(function(){t.routerLink("/logistics","list")},1e3))}))})},saveForm:function(t){var a=this;this.$refs.form.validate(function(e){e&&(a.loading=!0,i.d(a.info).then(function(e){a.loading=!1,200==e.code&&(a.successMessage("添加成功"),t?(a.getInfo(a.id),a.pathUrl=""):setTimeout(function(){a.routerLink("/logistics","list")},1e3))}))})},restForm:function(){this.$refs.form.resetFields(),this.getInfo(this.id),this.pathUrl=""}},created:function(){this.$route.query.type?this.tmplateUrl="/api/admin/logistics/v_courierEdit?id="+this.$route.query.id+"&appId="+this.$store.state.sys.appId:this.tmplateUrl="/api/admin/logistics/v_courierAdd?id="+this.$route.query.id+"&appId="+this.$store.state.sys.appId}}},Cgi1:function(t,a,e){"use strict";function i(t){return Object(st.a)({url:rt.a.getApiInfoList,method:"post",data:t,signValidate:!0})}function n(t){return Object(st.a)({url:rt.a.deleteApiInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object(st.a)({url:rt.a.updateApiInfo,method:"post",data:t,signValidate:!0})}function s(t){return Object(st.a)({url:rt.a.addApiInfo,method:"post",data:t,signValidate:!0})}function r(t){return Object(st.a)({url:rt.a.getApiInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object(st.a)({url:rt.a.getApiAccountList,method:"post",data:t,signValidate:!0})}function u(t){return Object(st.a)({url:rt.a.getApiAccountInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(st.a)({url:rt.a.addApiAccountInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object(st.a)({url:rt.a.updateApiPassword,method:"post",data:t,signValidate:!0})}function p(t){return Object(st.a)({url:rt.a.checkApiPassword,method:"post",data:t,nonceStr:!0,signValidate:!0})}function g(t){return Object(st.a)({url:rt.a.getApiRecordList,method:"post",data:t,signValidate:!0})}function f(t){return Object(st.a)({url:rt.a.deleteApiRecordInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(st.a)({url:rt.a.getFtpList,method:"post",data:t,signValidate:!0})}function m(t){return Object(st.a)({url:rt.a.getFtpInfo,method:"post",data:t,signValidate:!0})}function b(t){return Object(st.a)({url:rt.a.addFtpInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(st.a)({url:rt.a.updateFtpInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object(st.a)({url:rt.a.deleteFtpInfo,method:"post",data:t,signValidate:!0})}function j(t){return Object(st.a)({url:rt.a.getGlobalInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object(st.a)({url:rt.a.updateGlobalInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(st.a)({url:rt.a.getBasicInfo,method:"post",data:t,signValidate:!0})}function y(t){return Object(st.a)({url:rt.a.updateBasicInfo,method:"post",data:t,signValidate:!0})}function U(t){return Object(st.a)({url:rt.a.getEmailInfo,method:"post",data:t,signValidate:!0})}function A(t){return Object(st.a)({url:rt.a.updateEmailInfo,method:"post",data:t,signValidate:!0})}function x(t){return Object(st.a)({url:rt.a.getShipList,method:"post",data:t,signValidate:!0})}function L(t){return Object(st.a)({url:rt.a.getShipInfo,method:"post",data:t,signValidate:!0})}function P(t){return Object(st.a)({url:rt.a.addShipInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(st.a)({url:rt.a.updateShipInfo,method:"post",data:t,signValidate:!0})}function M(t){return Object(st.a)({url:rt.a.priorityShipInfo,method:"post",data:t,signValidate:!0})}function $(t){return Object(st.a)({url:rt.a.deleteShipInfo,method:"post",data:t,signValidate:!0})}function _(t){return Object(st.a)({url:rt.a.getLogisticsList,method:"post",data:t,signValidate:!0})}function X(t){return Object(st.a)({url:rt.a.getLogisticsInfo,method:"post",data:t,signValidate:!0})}function k(t){return Object(st.a)({url:rt.a.addLogisticsInfo,method:"post",data:t,signValidate:!0})}function w(t){return Object(st.a)({url:rt.a.updateLogisticsInfo,method:"post",data:t,signValidate:!0})}function F(t){return Object(st.a)({url:rt.a.deleteLogisticsInfo,method:"post",data:t,signValidate:!0})}function W(t){return Object(st.a)({url:rt.a.priorityLogisticsInfo,method:"post",data:t,signValidate:!0})}function C(t){return Object(st.a)({url:rt.a.getSsoInfo,method:"post",data:t,signValidate:!0})}function T(t){return Object(st.a)({url:rt.a.updateSsoInfo,method:"post",data:t,signValidate:!0})}function q(t){return Object(st.a)({url:rt.a.getThirdApiInfo,method:"post",data:t,signValidate:!0})}function K(t){return Object(st.a)({url:rt.a.updateThirdApiInfo,method:"post",data:t,signValidate:!0})}function R(t){return Object(st.a)({url:rt.a.getPaymentPluginsList,method:"post",data:t,signValidate:!0})}function B(t){return Object(st.a)({url:rt.a.getPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function E(t){return Object(st.a)({url:rt.a.updatePaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function z(t){return Object(st.a)({url:rt.a.priorityPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function N(t){return Object(st.a)({url:rt.a.getWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function D(t){return Object(st.a)({url:rt.a.updateWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function H(t){return Object(st.a)({url:rt.a.getWeiXinMenuList,method:"post",data:t,signValidate:!0})}function J(t){return Object(st.a)({url:rt.a.getWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function G(t){return Object(st.a)({url:rt.a.addWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Z(t){return Object(st.a)({url:rt.a.updateWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Q(t){return Object(st.a)({url:rt.a.deleteWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Y(t){return Object(st.a)({url:rt.a.getWeiXinMessageList,method:"post",data:t,signValidate:!0})}function tt(t){return Object(st.a)({url:rt.a.addWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function at(t){return Object(st.a)({url:rt.a.getWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function et(t){return Object(st.a)({url:rt.a.updateWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function it(t){return Object(st.a)({url:rt.a.deleteWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function nt(t){return Object(st.a)({url:rt.a.getDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function ot(t){return Object(st.a)({url:rt.a.updateDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}a.s=i,a.i=n,a.Q=o,a.b=s,a.r=r,a.q=d,a.p=u,a.a=l,a.R=c,a.h=p,a.t=g,a.j=f,a.y=h,a.x=m,a.c=b,a.V=I,a.k=O,a.z=j,a.W=V,a.u=v,a.S=y,a.w=U,a.U=A,a.F=x,a.E=L,a.e=P,a.Z=S,a.P=M,a.m=$,a.B=_,a.A=X,a.d=k,a.X=w,a.l=F,a.N=W,a.G=C,a._0=T,a.H=q,a._1=K,a.D=R,a.C=B,a.Y=E,a.O=z,a.M=N,a._4=D,a.J=H,a.I=J,a.f=G,a._2=Z,a.n=Q,a.L=Y,a.g=tt,a.K=at,a._3=et,a.o=it,a.v=nt,a.T=ot;var st=e("yYIz"),rt=e("z+6n")},Kaxk:function(t,a,e){"use strict";var i=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[e("iframe",{attrs:{src:t.tmplateUrl,frameborder:"0",width:"100%",height:"800"}})])},n=[],o={render:i,staticRenderFns:n};a.a=o},Mlkw:function(t,a,e){"use strict";var i=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"gou-upload",staticStyle:{position:"relative"}},[e("el-upload",{staticClass:"avatar-uploader",attrs:{action:this.$store.state.sys.upLoadUrl,name:"uploadFile",data:t.upload.data,"show-file-list":!1,"on-success":t.handleAvatarSuccess,"on-progress":t.handleAvatarProgress}},[t.upload.uploading?e("div",{staticClass:"uploading"},[e("el-progress",{attrs:{type:"circle",percentage:t.upload.percent}})],1):t._e(),t._v(" "),t.upload.imageUrl?e("div",{staticClass:"upload-hover-box"},[e("img",{staticClass:"avatar",attrs:{src:t.upload.imageUrl}}),t._v(" "),t.upload.imageUrl?e("span",{staticClass:"upload-cover"},[t._v("点击修改图片")]):t._e()]):e("i",{staticClass:"el-icon-plus avatar-uploader-icon"})]),t._v(" "),t.upload.imageUrl?e("i",{staticClass:"iconfont bbs-delete pos-del",attrs:{title:"删除"},on:{click:function(a){a.preventDefault(),t.delImg(a)}}}):t._e()],1)},n=[],o={render:i,staticRenderFns:n};a.a=o},NZkp:function(t,a,e){"use strict";var i=e("2RFS"),n=e("s4F+");a.a={name:"imageUpload",props:{path:{type:String,default:""},index:{type:Number,default:0}},data:function(){var t=Object(i.a)();return{upload:{url:this.$store.state.sys.baseUrl,imageUrl:"",uploading:!1,percent:0,data:Object(n.a)({appId:this.$store.state.sys.appId,sessionKey:localStorage.getItem("sessionKey"),type:"image",nonceStr:t},this.$store.state.sys.appKey)}}},methods:{handleAvatarSuccess:function(t,a){this.upload.uploading=!1,this.upload.percent=0,200==t.code?(this.upload.imageUrl=URL.createObjectURL(a.raw),this.$emit("getPath",t.body.url,this.index)):209==t.code?(this.errorMessage("无上传权限"),this.$emit("getPath","",this.index)):(this.errorMessage("上传失败，请检查文件类型"),this.$emit("getPath","",this.index))},handleAvatarProgress:function(t,a,e){this.upload.uploading=!0,this.upload.percent=parseInt(t.percent)},delImg:function(){this.upload.imageUrl="",this.$emit("getPath","",this.index)}},created:function(){""==this.path||null==this.path?this.upload.imageUrl="":this.path.indexOf("http://")>=0?this.upload.imageUrl=this.path:this.upload.imageUrl=this.upload.url+this.path},watch:{path:function(t,a){""==this.path||null==this.path?this.upload.imageUrl="":this.upload.imageUrl=this.upload.url+this.path}}}},PKn6:function(t,a,e){var i=e("h1T2");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);e("8bSs")("6a0400fc",i,!0)},"Q/fH":function(t,a,e){"use strict";function i(t){e("PKn6")}Object.defineProperty(a,"__esModule",{value:!0});var n=e("0UHm"),o=e("Kaxk"),s=e("/Xao"),r=i,d=s(n.a,o.a,!1,r,null,null);a.default=d.exports},T9Ip:function(t,a,e){var i=e("kIg/");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);e("8bSs")("0582fcd4",i,!0)},Uwrw:function(t,a,e){"use strict";function i(t){e("T9Ip")}var n=e("NZkp"),o=e("Mlkw"),s=e("/Xao"),r=i,d=s(n.a,o.a,!1,r,"data-v-ced38416",null);a.a=d.exports},h1T2:function(t,a,e){a=t.exports=e("BkJT")(!1),a.push([t.i,"",""])},"kIg/":function(t,a,e){a=t.exports=e("BkJT")(!1),a.push([t.i,".gou-upload[data-v-ced38416]{width:178px}.pos-del[data-v-ced38416]{position:absolute;width:45px;height:45px;color:#fff;cursor:pointer;top:0;right:0;font-size:25px;display:none;z-index:15;text-align:center}.gou-upload:hover .pos-del[data-v-ced38416],.gou-upload:hover .upload-cover[data-v-ced38416]{display:block}",""])}});