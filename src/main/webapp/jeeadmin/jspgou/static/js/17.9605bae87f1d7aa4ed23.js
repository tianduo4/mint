webpackJsonp([17],{"9cr9":function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,"",""])},CY7R:function(t,e,a){"use strict";var i=a("simw"),o=a("Uwrw");e.a={components:{imageUpload:o.a},data:function(){return{loading:!0,type:this.$route.query.type,id:this.$route.query.id,info:{},categoryIdList:[],pathUrl:"",rules:{name:[{required:!0,message:"请输入版位名称",trigger:"blur"}],adspaceId:[{required:!0,message:"版位信息必填",trigger:"blur"}]}}},methods:{getInfo:function(t){var e=this;i.r({id:t}).then(function(t){e.info=t.body,e.type&&(e.info.displayCount=0,e.info.clickCount=0),e.info.enabled=!0,e.loading=!1}),i.q().then(function(t){e.categoryIdList=t.body})},getPath:function(t){this.info.attr_image_url=t,this.pathUrl=t},updateForm:function(){var t=this;this.$refs.form.validate(function(e){e&&(t.loading=!0,i.E(t.info).then(function(e){t.loading=!1,200==e.code&&(t.successMessage("修改成功"),setTimeout(function(){t.routerLink("/advertise","list")},1e3))}))})},saveForm:function(t){var e=this;this.$refs.form.validate(function(a){a&&(e.loading=!0,i.c(e.info).then(function(a){e.loading=!1,200==a.code&&(e.successMessage("添加成功"),t?(e.getInfo(e.id),e.pathUrl=""):setTimeout(function(){e.routerLink("/advertise","list")},1e3))}))})},restForm:function(){this.$refs.form.resetFields(),this.getInfo(this.id),this.pathUrl=""}},created:function(){this.getInfo(this.id)}}},Mlkw:function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"gou-upload",staticStyle:{position:"relative"}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{action:this.$store.state.sys.upLoadUrl,name:"uploadFile",data:t.upload.data,"show-file-list":!1,"on-success":t.handleAvatarSuccess,"on-progress":t.handleAvatarProgress}},[t.upload.uploading?a("div",{staticClass:"uploading"},[a("el-progress",{attrs:{type:"circle",percentage:t.upload.percent}})],1):t._e(),t._v(" "),t.upload.imageUrl?a("div",{staticClass:"upload-hover-box"},[a("img",{staticClass:"avatar",attrs:{src:t.upload.imageUrl}}),t._v(" "),t.upload.imageUrl?a("span",{staticClass:"upload-cover"},[t._v("点击修改图片")]):t._e()]):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"})]),t._v(" "),t.upload.imageUrl?a("i",{staticClass:"iconfont bbs-delete pos-del",attrs:{title:"删除"},on:{click:function(e){e.preventDefault(),t.delImg(e)}}}):t._e()],1)},o=[],n={render:i,staticRenderFns:o};e.a=n},NZkp:function(t,e,a){"use strict";var i=a("2RFS"),o=a("s4F+");e.a={name:"imageUpload",props:{path:{type:String,default:""},index:{type:Number,default:0}},data:function(){var t=Object(i.a)();return{upload:{url:this.$store.state.sys.baseUrl,imageUrl:"",uploading:!1,percent:0,data:Object(o.a)({appId:this.$store.state.sys.appId,sessionKey:localStorage.getItem("sessionKey"),type:"image",nonceStr:t},this.$store.state.sys.appKey)}}},methods:{handleAvatarSuccess:function(t,e){this.upload.uploading=!1,this.upload.percent=0,200==t.code?(this.upload.imageUrl=URL.createObjectURL(e.raw),this.$emit("getPath",t.body.url,this.index)):209==t.code?(this.errorMessage("无上传权限"),this.$emit("getPath","",this.index)):(this.errorMessage("上传失败，请检查文件类型"),this.$emit("getPath","",this.index))},handleAvatarProgress:function(t,e,a){this.upload.uploading=!0,this.upload.percent=parseInt(t.percent)},delImg:function(){this.upload.imageUrl="",this.$emit("getPath","",this.index)}},created:function(){""==this.path||null==this.path?this.upload.imageUrl="":this.path.indexOf("http://")>=0?this.upload.imageUrl=this.path:this.upload.imageUrl=this.upload.url+this.path},watch:{path:function(t,e){""==this.path||null==this.path?this.upload.imageUrl="":this.upload.imageUrl=this.upload.url+this.path}}}},PHIU:function(t,e,a){"use strict";function i(t){a("aJy9")}Object.defineProperty(e,"__esModule",{value:!0});var o=a("CY7R"),n=a("lad3"),s=a("/Xao"),r=i,l=s(o.a,n.a,!1,r,null,null);e.default=l.exports},T9Ip:function(t,e,a){var i=a("kIg/");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a("8bSs")("0582fcd4",i,!0)},Uwrw:function(t,e,a){"use strict";function i(t){a("T9Ip")}var o=a("NZkp"),n=a("Mlkw"),s=a("/Xao"),r=i,l=s(o.a,n.a,!1,r,"data-v-ced38416",null);e.a=l.exports},aJy9:function(t,e,a){var i=a("9cr9");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a("8bSs")("7112b14d",i,!0)},"kIg/":function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,".gou-upload[data-v-ced38416]{width:178px}.pos-del[data-v-ced38416]{position:absolute;width:45px;height:45px;color:#fff;cursor:pointer;top:0;right:0;font-size:25px;display:none;z-index:15;text-align:center}.gou-upload:hover .pos-del[data-v-ced38416],.gou-upload:hover .upload-cover[data-v-ced38416]{display:block}",""])},lad3:function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v(t._s(t.$route.name))]),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-button",{staticClass:"back",on:{click:function(e){t.routerLink("/advertise","list")}}},[t._v("返回列表")])],1)]),t._v(" "),a("el-form",{ref:"form",staticClass:"table-body",attrs:{"label-width":"160px",model:t.info,rules:t.rules}},[a("el-row",{staticClass:"form-group"},[a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"广告名称",prop:"name"}},[a("el-input",{staticClass:"w192",model:{value:t.info.name,callback:function(e){t.$set(t.info,"name",e)},expression:"info.name"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"所属版位",prop:"adspaceId"}},[a("el-select",{model:{value:t.info.adspaceId,callback:function(e){t.$set(t.info,"adspaceId",e)},expression:"info.adspaceId"}},t._l(t.categoryIdList,function(t,e){return a("el-option",{key:e,attrs:{label:t.name,value:t.id}})}))],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"开始时间",prop:"startTime"}},[a("el-date-picker",{staticClass:"w192",attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss",placeholder:"选择\b开始时间"},model:{value:t.info.startTime,callback:function(e){t.$set(t.info,"startTime",e)},expression:"info.startTime"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"结束时间",prop:"endTime"}},[a("el-date-picker",{staticClass:"w192",attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss",placeholder:"选择\b结束时间"},model:{value:t.info.endTime,callback:function(e){t.$set(t.info,"endTime",e)},expression:"info.endTime"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"广告图片"}},[a("imageUpload",{attrs:{path:t.info.attr_image_url},on:{getPath:t.getPath}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-col",{staticClass:"border-none",attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"链接地址"}},[a("el-input",{staticClass:"w192",model:{value:t.info.attr_image_link,callback:function(e){t.$set(t.info,"attr_image_link",e)},expression:"info.attr_image_link"}})],1)],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"权重",required:!0}},[a("el-input",{staticClass:"w192",attrs:{type:"number",min:"0"},model:{value:t.info.weight,callback:function(e){t.$set(t.info,"weight",t._n(e))},expression:"info.weight"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"是否启用",required:!0}},[a("el-radio",{attrs:{label:!0},model:{value:t.info.enabled,callback:function(e){t.$set(t.info,"enabled",e)},expression:"info.enabled"}},[t._v("是")]),t._v(" "),a("el-radio",{attrs:{label:!1},model:{value:t.info.enabled,callback:function(e){t.$set(t.info,"enabled",e)},expression:"info.enabled"}},[t._v("否")])],1)],1)],1)],1),t._v(" "),a("div",{staticClass:"form-bottom-bar"},["save"==t.type?a("el-button",{attrs:{type:"warning"},on:{click:function(e){t.saveForm(!0)}}},[t._v("提交并继续添加")]):t._e(),t._v(" "),"save"==t.type?a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.saveForm(!1)}}},[t._v("添加")]):t._e(),t._v(" "),"update"==t.type?a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.updateForm()}}},[t._v("修改")]):t._e(),t._v(" "),a("el-button",{attrs:{type:"info"},on:{click:function(e){t.restForm()}}},[t._v("重置")]),t._v(" "),"save"==t.type?a("div",{staticClass:"help-info"},[t._v("选择“提交并继续添加”按钮会停留在添加页面；选择“添加”按钮后将跳转到对应的列表页。")]):t._e()],1)],1)},o=[],n={render:i,staticRenderFns:o};e.a=n},simw:function(t,e,a){"use strict";function i(t){return Object(R.a)({url:M.a.getAdminList,method:"post",data:t,signValidate:!0})}function o(t){return Object(R.a)({url:M.a.getAdminInfo,method:"post",data:t,signValidate:!0})}function n(t){return Object(R.a)({url:M.a.addAdminInfo,method:"post",data:t,signValidate:!0,nonceStr:!0})}function s(t){return Object(R.a)({url:M.a.deleteAdminInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function r(t){return Object(R.a)({url:M.a.updateAdminInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(R.a)({url:M.a.getRoleList,method:"post",data:t,signValidate:!0})}function d(t){return Object(R.a)({url:M.a.getRoleInfo,method:"post",data:t,signValidate:!0})}function u(t){return Object(R.a)({url:M.a.addRoleInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object(R.a)({url:M.a.updateRoleInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function p(t){return Object(R.a)({url:M.a.deleteRoleInfo,method:"post",data:t,signValidate:!0})}function f(t){return Object(R.a)({url:M.a.getCouponList,method:"post",data:t,signValidate:!0})}function m(t){return Object(R.a)({url:M.a.getCouponInfo,method:"post",data:t,signValidate:!0})}function g(t){return Object(R.a)({url:M.a.addCouponInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(R.a)({url:M.a.deleteCouponInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(R.a)({url:M.a.disabledCouponInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function b(t){return Object(R.a)({url:M.a.getCustomerServiceList,method:"post",data:t,signValidate:!0})}function y(t){return Object(R.a)({url:M.a.getCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function _(t){return Object(R.a)({url:M.a.addCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(R.a)({url:M.a.updateCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function j(t){return Object(R.a)({url:M.a.deleteCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function x(t){return Object(R.a)({url:M.a.priorityCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function C(t){return Object(R.a)({url:M.a.getPosterList,method:"post",data:t,signValidate:!0})}function O(t){return Object(R.a)({url:M.a.updatePosterInfo,method:"post",data:t,signValidate:!0})}function k(t){return Object(R.a)({url:M.a.getLogList,method:"post",data:t,signValidate:!0})}function V(t){return Object(R.a)({url:M.a.getAdspaceList,method:"post",data:t,signValidate:!0})}function w(t){return Object(R.a)({url:M.a.getAdspaceInfo,method:"post",data:t,signValidate:!0})}function U(t){return Object(R.a)({url:M.a.addAdspaceInfo,method:"post",data:t,signValidate:!0})}function $(t){return Object(R.a)({url:M.a.updateAdspaceInfo,method:"post",data:t,signValidate:!0})}function A(t){return Object(R.a)({url:M.a.deleteAdspaceInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(R.a)({url:M.a.getAdvertiseList,method:"post",data:t,signValidate:!0})}function L(t){return Object(R.a)({url:M.a.getAdvertiseInfo,method:"post",data:t,signValidate:!0})}function F(t){return Object(R.a)({url:M.a.addAdvertiseInfo,method:"post",data:t,signValidate:!0})}function T(t){return Object(R.a)({url:M.a.updateAdvertiseInfo,method:"post",data:t,signValidate:!0})}function P(t){return Object(R.a)({url:M.a.deleteAdvertiseInfo,method:"post",data:t,signValidate:!0})}e.o=i,e.n=o,e.a=n,e.g=s,e.C=r,e.A=l,e.z=d,e.f=u,e.H=c,e.l=p,e.u=f,e.t=m,e.d=g,e.j=h,e.m=v,e.w=b,e.v=y,e.e=_,e.F=I,e.k=j,e.B=x,e.y=C,e.G=O,e.x=k,e.q=V,e.p=w,e.b=U,e.D=$,e.h=A,e.s=S,e.r=L,e.c=F,e.E=T,e.i=P;var R=a("yYIz"),M=a("z+6n")}});