webpackJsonp([2],{"9q8C":function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name tab-name active",on:{click:function(e){t.routerLink("/wechatset","list")}}},[t._v("公众号设置")]),t._v(" "),a("span",{staticClass:"forum-name tab-name  ",on:{click:function(e){t.routerLink("/wechatset/wechatmenu","list")}}},[t._v("自定义菜单")]),t._v(" "),a("span",{staticClass:"forum-name tab-name",on:{click:function(e){t.routerLink("/wechatset/wechatmessage","list")}}},[t._v("自定义回复")]),t._v(" "),a("span",{staticClass:"forum-name tab-name",on:{click:function(e){t.routerLink("/wechatset/wechatdefaultMessage","list")}}},[t._v("默认回复")])]),t._v(" "),a("el-form",{ref:"form",staticClass:"table-body",attrs:{"label-width":"160px",model:t.info,rules:t.rules}},[a("el-row",{staticClass:"form-group"},[a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"App Key:",prop:"appKey"}},[a("el-input",{staticClass:"w192",model:{value:t.info.appKey,callback:function(e){t.$set(t.info,"appKey",e)},expression:"info.appKey"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"App Secret:",prop:"appSecret"}},[a("el-input",{staticClass:"w192",model:{value:t.info.appSecret,callback:function(e){t.$set(t.info,"appSecret",e)},expression:"info.appSecret"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"App Token:",prop:"token"}},[a("el-input",{staticClass:"w192",model:{value:t.info.token,callback:function(e){t.$set(t.info,"token",e)},expression:"info.token"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"关注欢迎语",prop:"welcome"}},[a("el-input",{staticClass:"w192",attrs:{type:"textarea"},model:{value:t.info.welcome,callback:function(e){t.$set(t.info,"welcome",e)},expression:"info.welcome"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"微信二维码"}},[a("imageUpload",{attrs:{path:t.imagePath},on:{getPath:t.getPath}})],1)],1)],1)],1),t._v(" "),a("div",{staticClass:"form-bottom-bar"},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.updateForm()}}},[t._v("修改")]),t._v(" "),a("el-button",{attrs:{type:"info"},on:{click:function(e){t.restForm()}}},[t._v("重置")])],1)],1)},n=[],o={render:i,staticRenderFns:n};e.a=o},Cgi1:function(t,e,a){"use strict";function i(t){return Object(st.a)({url:rt.a.getApiInfoList,method:"post",data:t,signValidate:!0})}function n(t){return Object(st.a)({url:rt.a.deleteApiInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object(st.a)({url:rt.a.updateApiInfo,method:"post",data:t,signValidate:!0})}function s(t){return Object(st.a)({url:rt.a.addApiInfo,method:"post",data:t,signValidate:!0})}function r(t){return Object(st.a)({url:rt.a.getApiInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object(st.a)({url:rt.a.getApiAccountList,method:"post",data:t,signValidate:!0})}function u(t){return Object(st.a)({url:rt.a.getApiAccountInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(st.a)({url:rt.a.addApiAccountInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object(st.a)({url:rt.a.updateApiPassword,method:"post",data:t,signValidate:!0})}function p(t){return Object(st.a)({url:rt.a.checkApiPassword,method:"post",data:t,nonceStr:!0,signValidate:!0})}function f(t){return Object(st.a)({url:rt.a.getApiRecordList,method:"post",data:t,signValidate:!0})}function g(t){return Object(st.a)({url:rt.a.deleteApiRecordInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(st.a)({url:rt.a.getFtpList,method:"post",data:t,signValidate:!0})}function m(t){return Object(st.a)({url:rt.a.getFtpInfo,method:"post",data:t,signValidate:!0})}function b(t){return Object(st.a)({url:rt.a.addFtpInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(st.a)({url:rt.a.updateFtpInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(st.a)({url:rt.a.deleteFtpInfo,method:"post",data:t,signValidate:!0})}function y(t){return Object(st.a)({url:rt.a.getGlobalInfo,method:"post",data:t,signValidate:!0})}function j(t){return Object(st.a)({url:rt.a.updateGlobalInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object(st.a)({url:rt.a.getBasicInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object(st.a)({url:rt.a.updateBasicInfo,method:"post",data:t,signValidate:!0})}function w(t){return Object(st.a)({url:rt.a.getEmailInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(st.a)({url:rt.a.updateEmailInfo,method:"post",data:t,signValidate:!0})}function x(t){return Object(st.a)({url:rt.a.getShipList,method:"post",data:t,signValidate:!0})}function _(t){return Object(st.a)({url:rt.a.getShipInfo,method:"post",data:t,signValidate:!0})}function C(t){return Object(st.a)({url:rt.a.addShipInfo,method:"post",data:t,signValidate:!0})}function k(t){return Object(st.a)({url:rt.a.updateShipInfo,method:"post",data:t,signValidate:!0})}function U(t){return Object(st.a)({url:rt.a.priorityShipInfo,method:"post",data:t,signValidate:!0})}function E(t){return Object(st.a)({url:rt.a.deleteShipInfo,method:"post",data:t,signValidate:!0})}function A(t){return Object(st.a)({url:rt.a.getLogisticsList,method:"post",data:t,signValidate:!0})}function M(t){return Object(st.a)({url:rt.a.getLogisticsInfo,method:"post",data:t,signValidate:!0})}function P(t){return Object(st.a)({url:rt.a.addLogisticsInfo,method:"post",data:t,signValidate:!0})}function $(t){return Object(st.a)({url:rt.a.updateLogisticsInfo,method:"post",data:t,signValidate:!0})}function L(t){return Object(st.a)({url:rt.a.deleteLogisticsInfo,method:"post",data:t,signValidate:!0})}function T(t){return Object(st.a)({url:rt.a.priorityLogisticsInfo,method:"post",data:t,signValidate:!0})}function K(t){return Object(st.a)({url:rt.a.getSsoInfo,method:"post",data:t,signValidate:!0})}function X(t){return Object(st.a)({url:rt.a.updateSsoInfo,method:"post",data:t,signValidate:!0})}function F(t){return Object(st.a)({url:rt.a.getThirdApiInfo,method:"post",data:t,signValidate:!0})}function W(t){return Object(st.a)({url:rt.a.updateThirdApiInfo,method:"post",data:t,signValidate:!0})}function R(t){return Object(st.a)({url:rt.a.getPaymentPluginsList,method:"post",data:t,signValidate:!0})}function q(t){return Object(st.a)({url:rt.a.getPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function N(t){return Object(st.a)({url:rt.a.updatePaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function B(t){return Object(st.a)({url:rt.a.priorityPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function G(t){return Object(st.a)({url:rt.a.getWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function J(t){return Object(st.a)({url:rt.a.updateWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function z(t){return Object(st.a)({url:rt.a.getWeiXinMenuList,method:"post",data:t,signValidate:!0})}function D(t){return Object(st.a)({url:rt.a.getWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function H(t){return Object(st.a)({url:rt.a.addWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Z(t){return Object(st.a)({url:rt.a.updateWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Y(t){return Object(st.a)({url:rt.a.deleteWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Q(t){return Object(st.a)({url:rt.a.getWeiXinMessageList,method:"post",data:t,signValidate:!0})}function tt(t){return Object(st.a)({url:rt.a.addWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function et(t){return Object(st.a)({url:rt.a.getWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function at(t){return Object(st.a)({url:rt.a.updateWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function it(t){return Object(st.a)({url:rt.a.deleteWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function nt(t){return Object(st.a)({url:rt.a.getDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function ot(t){return Object(st.a)({url:rt.a.updateDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}e.s=i,e.i=n,e.Q=o,e.b=s,e.r=r,e.q=d,e.p=u,e.a=l,e.R=c,e.h=p,e.t=f,e.j=g,e.y=h,e.x=m,e.c=b,e.V=I,e.k=v,e.z=y,e.W=j,e.u=O,e.S=V,e.w=w,e.U=S,e.F=x,e.E=_,e.e=C,e.Z=k,e.P=U,e.m=E,e.B=A,e.A=M,e.d=P,e.X=$,e.l=L,e.N=T,e.G=K,e._0=X,e.H=F,e._1=W,e.D=R,e.C=q,e.Y=N,e.O=B,e.M=G,e._4=J,e.J=z,e.I=D,e.f=H,e._2=Z,e.n=Y,e.L=Q,e.g=tt,e.K=et,e._3=at,e.o=it,e.v=nt,e.T=ot;var st=a("yYIz"),rt=a("z+6n")},H5Jq:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,"",""])},Kgci:function(t,e,a){"use strict";function i(t){a("qHNg")}Object.defineProperty(e,"__esModule",{value:!0});var n=a("Vj9p"),o=a("9q8C"),s=a("/Xao"),r=i,d=s(n.a,o.a,!1,r,null,null);e.default=d.exports},Mlkw:function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"gou-upload",staticStyle:{position:"relative"}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{action:this.$store.state.sys.upLoadUrl,name:"uploadFile",data:t.upload.data,"show-file-list":!1,"on-success":t.handleAvatarSuccess,"on-progress":t.handleAvatarProgress}},[t.upload.uploading?a("div",{staticClass:"uploading"},[a("el-progress",{attrs:{type:"circle",percentage:t.upload.percent}})],1):t._e(),t._v(" "),t.upload.imageUrl?a("div",{staticClass:"upload-hover-box"},[a("img",{staticClass:"avatar",attrs:{src:t.upload.imageUrl}}),t._v(" "),t.upload.imageUrl?a("span",{staticClass:"upload-cover"},[t._v("点击修改图片")]):t._e()]):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"})]),t._v(" "),t.upload.imageUrl?a("i",{staticClass:"iconfont bbs-delete pos-del",attrs:{title:"删除"},on:{click:function(e){e.preventDefault(),t.delImg(e)}}}):t._e()],1)},n=[],o={render:i,staticRenderFns:n};e.a=o},NZkp:function(t,e,a){"use strict";var i=a("2RFS"),n=a("s4F+");e.a={name:"imageUpload",props:{path:{type:String,default:""},index:{type:Number,default:0}},data:function(){var t=Object(i.a)();return{upload:{url:this.$store.state.sys.baseUrl,imageUrl:"",uploading:!1,percent:0,data:Object(n.a)({appId:this.$store.state.sys.appId,sessionKey:localStorage.getItem("sessionKey"),type:"image",nonceStr:t},this.$store.state.sys.appKey)}}},methods:{handleAvatarSuccess:function(t,e){this.upload.uploading=!1,this.upload.percent=0,200==t.code?(this.upload.imageUrl=URL.createObjectURL(e.raw),this.$emit("getPath",t.body.url,this.index)):209==t.code?(this.errorMessage("无上传权限"),this.$emit("getPath","",this.index)):(this.errorMessage("上传失败，请检查文件类型"),this.$emit("getPath","",this.index))},handleAvatarProgress:function(t,e,a){this.upload.uploading=!0,this.upload.percent=parseInt(t.percent)},delImg:function(){this.upload.imageUrl="",this.$emit("getPath","",this.index)}},created:function(){""==this.path||null==this.path?this.upload.imageUrl="":this.path.indexOf("http://")>=0?this.upload.imageUrl=this.path:this.upload.imageUrl=this.upload.url+this.path},watch:{path:function(t,e){""==this.path||null==this.path?this.upload.imageUrl="":this.upload.imageUrl=this.upload.url+this.path}}}},T9Ip:function(t,e,a){var i=a("kIg/");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a("8bSs")("0582fcd4",i,!0)},Uwrw:function(t,e,a){"use strict";function i(t){a("T9Ip")}var n=a("NZkp"),o=a("Mlkw"),s=a("/Xao"),r=i,d=s(n.a,o.a,!1,r,"data-v-ced38416",null);e.a=d.exports},Vj9p:function(t,e,a){"use strict";var i=(a("b1Wg"),a("Uwrw")),n=a("Cgi1");e.a={components:{imageUpload:i.a},data:function(){return{loading:!0,info:{},UE:"",imagePath:"",type:this.$route.query.type,id:this.$route.query.id,rules:{}}},methods:{getInfo:function(){var t=this;n.M().then(function(e){t.info=e.body,t.imagePath=e.body.pic}),this.loading=!1},getPath:function(t){this.info.pic=t},updateForm:function(){var t=this;this.$refs.form.validate(function(e){e&&(t.loading=!0,n._4(t.info).then(function(e){t.loading=!1,200==e.code&&t.successMessage("修改成功")}).catch(function(e){t.errorMessage("执行异常"),t.loading=!1}))})},restForm:function(){this.$refs.form.resetFields(),this.getInfo()}},created:function(){this.getInfo()}}},b1Wg:function(t,e,a){"use strict";var i=a("xeux"),n=a("oGsl"),o=a("/Xao"),s=o(i.a,n.a,!1,null,null,null);e.a=s.exports},"kIg/":function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,".gou-upload[data-v-ced38416]{width:178px}.pos-del[data-v-ced38416]{position:absolute;width:45px;height:45px;color:#fff;cursor:pointer;top:0;right:0;font-size:25px;display:none;z-index:15;text-align:center}.gou-upload:hover .pos-del[data-v-ced38416],.gou-upload:hover .upload-cover[data-v-ced38416]{display:block}",""])},oGsl:function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement;return(t._self._c||e)("script",{attrs:{id:t.randomId,name:"content",type:"text/plain"}})},n=[],o={render:i,staticRenderFns:n};e.a=o},qHNg:function(t,e,a){var i=a("H5Jq");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a("8bSs")("4479bbe5",i,!0)},xeux:function(t,e,a){"use strict";e.a={name:"VueUEditor",props:{ueditorPath:{type:String,default:"./static/ueditor/"},defaultMsg:{type:String,default:""},ueditorConfig:{type:Object,default:function(){return{}}},index:{default:0}},data:function(){return{randomId:"editor_"+1e4*Math.random(),instance:null,scriptTagStatus:0,appId:this.$store.state.sys.appId}},created:function(){void 0!==window.UE?(this.scriptTagStatus=2,this.initEditor()):this.insertScriptTag()},mounted:function(){},beforeDestroy:function(){null!==this.instance&&this.instance.destroy&&this.instance.destroy()},watch:{defaultMsg:function(t,e){this.instance=window.UE.getEditor(this.randomId,this.ueditorConfig),null!==t&&this.instance.ready(function(){this.setContent(t)})}},methods:{insertScriptTag:function(){var t=this,e=document.getElementById("editorScriptTag"),a=document.getElementById("configScriptTag");if(null===e){a=document.createElement("script"),a.type="text/javascript",a.src=this.ueditorPath+"ueditor.config.js",a.id="configScriptTag",e=document.createElement("script"),e.type="text/javascript",e.src=this.ueditorPath+"ueditor.all.js",e.id="editorScriptTag";var i=document.getElementsByTagName("head")[0];i.appendChild(a),i.appendChild(e)}a.loaded?this.scriptTagStatus++:a.addEventListener("load",function(){t.scriptTagStatus++,a.loaded=!0,t.initEditor()}),e.loaded?this.scriptTagStatus++:e.addEventListener("load",function(){t.scriptTagStatus++,e.loaded=!0,t.initEditor()}),this.initEditor()},getUEContent:function(){return this.instance.getContent()},initEditor:function(){var t=this,e=this;2===this.scriptTagStatus&&null===this.instance&&this.$nextTick(function(){t.instance=window.UE.getEditor(t.randomId,t.ueditorConfig),t.instance.addListener("ready",function(){window.UE.Editor.prototype._bkGetActionUrl=window.UE.Editor.prototype.getActionUrl,window.UE.Editor.prototype.getActionUrl=function(t){return"uploadimage"==t?"http://192.168.0.173:8080/jeecmsv9/api/admin/ueditor/upload?sessionKey=41e913c000de6b32c2bfb9ab98d67ab5f2377b7c2b605d9c599a688f3bfdd189454b68b238d570cf5b0ad835c5710337&appId=1580387213331704":"uploadvideo"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/upload?sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"uploadfile"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/upload?sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"catchimage"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/getRemoteImage?sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"uploadscrawl"==t?"http://192.168.0.199:12000/newjspgou/api/admin//ueditor/scrawlImage?Type=Image&sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"listimage"==t||"listfile"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/imageManager?picNum=50&insite=false&sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"config"==t?"/static/ueditor/config.json":void 0},t.$emit("ready",t.instance,t.index),t.instance.setContent(t.defaultMsg)})})}}}}});