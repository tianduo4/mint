webpackJsonp([77],{"+iHN":function(t,a,e){"use strict";var r=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("div",{staticClass:"table-body table-responsive"},[e("el-form",{ref:"topicInfo",attrs:{model:t.params,rules:t.rules}},[e("el-form-item",{staticClass:"form-group",staticStyle:{"margin-top":"20px","margin-bottom":"0"},attrs:{label:"原名称"}},[e("el-col",{attrs:{span:6}},[t._v("\n                    "+t._s(t.params.origName)+"\n                ")]),t._v(" "),e("el-col",{staticClass:"required",attrs:{span:1}},[t._v("  ")]),t._v(" "),e("el-col",{attrs:{span:7}})],1),t._v(" "),e("el-form-item",{staticClass:"form-group",attrs:{label:"新名称",prop:"distName"}},[e("el-col",{attrs:{span:6}},[e("el-input",{model:{value:t.params.distName,callback:function(a){t.$set(t.params,"distName",a)},expression:"params.distName"}})],1),t._v(" "),e("el-col",{staticClass:"required",attrs:{span:1}},[t._v("*")]),t._v(" "),e("el-col",{attrs:{span:7}})],1)],1),t._v(" "),e("div",{staticClass:"margin-md"},[e("input",{staticClass:"bbs-submit",attrs:{type:"button",value:"提交"},on:{click:function(a){t.reName("topicInfo")}}}),t._v(" "),e("input",{staticClass:"bbs-reset",attrs:{type:"reset",value:"重置"}})])],1)])},n=[],i={render:r,staticRenderFns:n};a.a=i},PHrY:function(t,a,e){"use strict";function r(t){return Object(S.a)({url:"/api/admin/resource/tree",method:"post",data:t})}function n(t){return Object(S.a)({url:"/api/admin/resource/list",method:"post",data:t})}function i(t){return Object(S.a)({url:"/api/admin/template/list",method:"post",data:t})}function o(t){return Object(S.a)({url:"/api/admin/resource/get",method:"post",data:t})}function s(t){return Object(S.a)({url:"/api/admin/template/get",method:"post",data:t})}function u(t){return Object(S.a)({url:"/api/admin/resource/delete",method:"post",signValidate:!0,data:t})}function l(t){return Object(S.a)({url:"/api/admin/template/delete",method:"post",signValidate:!0,data:t})}function d(t){return Object(S.a)({url:"api/admin/resource/dir_save",method:"post",signValidate:!0,data:t})}function c(t){return Object(S.a)({url:"api/admin/template/dir_save",method:"post",signValidate:!0,data:t})}function m(t){return Object(S.a)({url:"/api/admin/resource/rename",method:"post",signValidate:!0,data:t})}function p(t){return Object(S.a)({url:"/api/admin/template/rename",method:"post",signValidate:!0,data:t})}function f(t){return Object(S.a)({url:"/api/admin/template/update",method:"post",signValidate:!0,data:t})}function h(t){return Object(S.a)({url:"/api/admin/resource/save",method:"post",signValidate:!0,data:t})}function g(t){return Object(S.a)({url:"/api/admin/template/save",method:"post",signValidate:!0,data:t})}function b(t){return Object(S.a)({url:"/api/admin/template/tree",method:"post",data:t})}function j(t){return Object(S.a)({url:"/api/admin/template/getSolutions",method:"post",data:t})}function v(t){return Object(S.a)({url:"/api/admin/template/solutionupdate",method:"post",signValidate:!0,data:t})}function O(t){return Object(S.a)({url:T.a.getChannelTree,method:"post",signValidate:!0,data:t})}function V(t){return Object(S.a)({url:T.a.getChannelList,method:"post",signValidate:!0,data:t})}function C(t){return Object(S.a)({url:T.a.getChannelInfo,method:"post",signValidate:!0,data:t})}function y(t){return Object(S.a)({url:T.a.addChannelInfo,method:"post",signValidate:!0,data:t})}function _(t){return Object(S.a)({url:T.a.updateChannelInfo,method:"post",signValidate:!0,data:t})}function N(t){return Object(S.a)({url:T.a.deleteChannelInfo,method:"post",signValidate:!0,data:t})}function q(t){return Object(S.a)({url:T.a.priorityChannelInfo,method:"post",signValidate:!0,data:t})}function I(t){return Object(S.a)({url:T.a.getChannelTpl,method:"post",signValidate:!0,data:t})}function $(t){return Object(S.a)({url:T.a.getParentChannelList,method:"post",signValidate:!0,data:t})}function A(t){return Object(S.a)({url:T.a.getArticleList,method:"post",signValidate:!0,data:t})}function k(t){return Object(S.a)({url:T.a.getArticleInfo,method:"post",signValidate:!0,data:t})}function x(t){return Object(S.a)({url:T.a.addArticleInfo,method:"post",signValidate:!0,data:t})}function L(t){return Object(S.a)({url:T.a.updateArticleInfo,method:"post",signValidate:!0,data:t})}function z(t){return Object(S.a)({url:T.a.deleteArticleInfo,method:"post",signValidate:!0,data:t})}function E(t){return Object(S.a)({url:T.a.getArticleChannelList,method:"post",signValidate:!0,data:t})}a.u=r,a.t=n,a.x=i,a.s=o,a.w=s,a.i=u,a.j=l,a.e=d,a.f=c,a.A=m,a.C=p,a.F=f,a.c=h,a.d=g,a.y=b,a.v=j,a.B=v,a.q=O,a.o=V,a.n=C,a.b=y,a.E=_,a.h=N,a.z=q,a.p=I,a.r=$,a.m=A,a.l=k,a.a=x,a.D=L,a.g=z,a.k=E;var S=e("yYIz"),T=e("z+6n"),w=e("DEjr");e.n(w)},mEug:function(t,a,e){"use strict";var r=e("PHrY");a.a={data:function(){return{loading:!0,rules:{distName:[{required:!0,message:"此项必填",trigger:"blur"}]},params:{origName:"",distName:"",root:""}}},methods:{reName:function(t){var a=this;this.$refs[t].validate(function(t){if(document.querySelector("#main").scrollTop=0,!t)return document.querySelector("#main").scrollTop=0,!1;var e=a.params;r.A(e).then(function(t){a.loading=!1,200==t.code?(a.$message.success("修改成功"),a.$router.push({path:"/resourcelist",query:{name:a.params.root}})):(a.$message.error(t.message),a.$router.push({path:"/resourcelist",query:{name:a.params.root}}))}).catch(function(t){a.loading=!1,a.$message.error("修改失败"),a.$router.push({path:"/resourcelist",query:{name:a.params.root}})})})},back:function(){this.$router.push({path:"/resourcelist",query:{name:this.params.root}})}},created:function(){var t=this.$route.query.name,a=this.$route.query.root;this.params.origName=t,this.params.distName=t,this.params.root=a}}},mXLz:function(t,a,e){a=t.exports=e("BkJT")(!1),a.push([t.i,"",""])},vGwR:function(t,a,e){var r=e("mXLz");"string"==typeof r&&(r=[[t.i,r,""]]),r.locals&&(t.exports=r.locals);e("8bSs")("2d43e03a",r,!0)},"y4/j":function(t,a,e){"use strict";function r(t){e("vGwR")}Object.defineProperty(a,"__esModule",{value:!0});var n=e("mEug"),i=e("+iHN"),o=e("46Yf"),s=r,u=o(n.a,i.a,!1,s,null,null);a.default=u.exports}});