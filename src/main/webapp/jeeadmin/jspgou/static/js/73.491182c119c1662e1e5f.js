webpackJsonp([73],{"31ug":function(t,a,e){var n=e("sS7/");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);e("8bSs")("146ec9da",n,!0)},MOuR:function(t,a,e){"use strict";function n(t){e("31ug")}Object.defineProperty(a,"__esModule",{value:!0});var r=e("QaMK"),i=e("u6M0"),s=e("46Yf"),o=n,u=s(r.a,i.a,!1,o,null,null);a.default=u.exports},PHrY:function(t,a,e){"use strict";function n(t){return Object(L.a)({url:"/api/admin/resource/tree",method:"post",data:t})}function r(t){return Object(L.a)({url:"/api/admin/resource/list",method:"post",data:t})}function i(t){return Object(L.a)({url:"/api/admin/template/list",method:"post",data:t})}function s(t){return Object(L.a)({url:"/api/admin/resource/get",method:"post",data:t})}function o(t){return Object(L.a)({url:"/api/admin/template/get",method:"post",data:t})}function u(t){return Object(L.a)({url:"/api/admin/resource/delete",method:"post",signValidate:!0,data:t})}function l(t){return Object(L.a)({url:"/api/admin/template/delete",method:"post",signValidate:!0,data:t})}function d(t){return Object(L.a)({url:"api/admin/resource/dir_save",method:"post",signValidate:!0,data:t})}function c(t){return Object(L.a)({url:"api/admin/template/dir_save",method:"post",signValidate:!0,data:t})}function m(t){return Object(L.a)({url:"/api/admin/resource/rename",method:"post",signValidate:!0,data:t})}function p(t){return Object(L.a)({url:"/api/admin/template/rename",method:"post",signValidate:!0,data:t})}function f(t){return Object(L.a)({url:"/api/admin/template/update",method:"post",signValidate:!0,data:t})}function h(t){return Object(L.a)({url:"/api/admin/resource/save",method:"post",signValidate:!0,data:t})}function g(t){return Object(L.a)({url:"/api/admin/template/save",method:"post",signValidate:!0,data:t})}function b(t){return Object(L.a)({url:"/api/admin/template/tree",method:"post",data:t})}function v(t){return Object(L.a)({url:"/api/admin/template/getSolutions",method:"post",data:t})}function j(t){return Object(L.a)({url:"/api/admin/template/solutionupdate",method:"post",signValidate:!0,data:t})}function O(t){return Object(L.a)({url:P.a.getChannelTree,method:"post",signValidate:!0,data:t})}function V(t){return Object(L.a)({url:P.a.getChannelList,method:"post",signValidate:!0,data:t})}function C(t){return Object(L.a)({url:P.a.getChannelInfo,method:"post",signValidate:!0,data:t})}function _(t){return Object(L.a)({url:P.a.addChannelInfo,method:"post",signValidate:!0,data:t})}function y(t){return Object(L.a)({url:P.a.updateChannelInfo,method:"post",signValidate:!0,data:t})}function q(t){return Object(L.a)({url:P.a.deleteChannelInfo,method:"post",signValidate:!0,data:t})}function I(t){return Object(L.a)({url:P.a.priorityChannelInfo,method:"post",signValidate:!0,data:t})}function N(t){return Object(L.a)({url:P.a.getChannelTpl,method:"post",signValidate:!0,data:t})}function $(t){return Object(L.a)({url:P.a.getParentChannelList,method:"post",signValidate:!0,data:t})}function A(t){return Object(L.a)({url:P.a.getArticleList,method:"post",signValidate:!0,data:t})}function k(t){return Object(L.a)({url:P.a.getArticleInfo,method:"post",signValidate:!0,data:t})}function M(t){return Object(L.a)({url:P.a.addArticleInfo,method:"post",signValidate:!0,data:t})}function S(t){return Object(L.a)({url:P.a.updateArticleInfo,method:"post",signValidate:!0,data:t})}function x(t){return Object(L.a)({url:P.a.deleteArticleInfo,method:"post",signValidate:!0,data:t})}function T(t){return Object(L.a)({url:P.a.getArticleChannelList,method:"post",signValidate:!0,data:t})}a.u=n,a.t=r,a.x=i,a.s=s,a.w=o,a.i=u,a.j=l,a.e=d,a.f=c,a.A=m,a.C=p,a.F=f,a.c=h,a.d=g,a.y=b,a.v=v,a.B=j,a.q=O,a.o=V,a.n=C,a.b=_,a.E=y,a.h=q,a.z=I,a.p=N,a.r=$,a.m=A,a.l=k,a.a=M,a.D=S,a.g=x,a.k=T;var L=e("yYIz"),P=e("z+6n"),Y=e("DEjr");e.n(Y)},QaMK:function(t,a,e){"use strict";var n=e("PHrY");a.a={data:function(){return{loading:!0,rules:{distName:[{required:!0,message:"此项必填",trigger:"blur"}]},params:{origName:"",distName:"",root:""}}},methods:{reName:function(t){var a=this;this.$refs[t].validate(function(t){if(document.querySelector("#main").scrollTop=0,!t)return document.querySelector("#main").scrollTop=0,!1;var e=a.params;n.C(e).then(function(t){a.loading=!1,200==t.code?(a.$message.success("修改成功"),a.$router.push({path:"/templatelist",query:{name:a.params.root}})):a.$router.push({path:"/templatelist",query:{name:a.params.root}})}).catch(function(t){a.loading=!1,a.$message.error("修改失败"),a.$router.push({path:"/templatelist",query:{name:a.params.root}})})})},back:function(){this.$router.push({path:"/templatelist",query:{name:this.params.root}})}},created:function(){var t=this.$route.query.name,a=this.$route.query.root;this.params.origName=t,this.params.distName=t,this.params.root=a}}},"sS7/":function(t,a,e){a=t.exports=e("BkJT")(!1),a.push([t.i,"",""])},u6M0:function(t,a,e){"use strict";var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"forum-module"},[e("div",{staticClass:"forum-header"},[e("span",{staticClass:"forum-name"},[t._v(t._s(t.$route.name))])]),t._v(" "),e("div",{staticClass:"table-body table-responsive"},[e("el-form",{ref:"topicInfo",attrs:{model:t.params,rules:t.rules}},[e("el-form-item",{staticClass:"form-group",attrs:{label:"原名称"}},[e("el-col",{attrs:{span:6}},[t._v("\n                    "+t._s(t.params.origName)+"\n                ")]),t._v(" "),e("el-col",{staticClass:"required",attrs:{span:1}},[t._v("*")]),t._v(" "),e("el-col",{attrs:{span:7}})],1),t._v(" "),e("el-form-item",{staticClass:"form-group",attrs:{label:"新名称",prop:"distName"}},[e("el-col",{attrs:{span:6}},[e("el-input",{model:{value:t.params.distName,callback:function(a){t.$set(t.params,"distName",a)},expression:"params.distName"}})],1),t._v(" "),e("el-col",{staticClass:"required",attrs:{span:1}},[t._v("  ")]),t._v(" "),e("el-col",{attrs:{span:7}})],1)],1),t._v(" "),e("div",{staticClass:"margin-md"},[e("input",{staticClass:"bbs-submit",attrs:{type:"button",value:"提交"},on:{click:function(a){t.reName("topicInfo")}}}),t._v(" "),e("input",{staticClass:"bbs-reset",attrs:{type:"reset",value:"重置"}})])],1)])},r=[],i={render:n,staticRenderFns:r};a.a=i}});