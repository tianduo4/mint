webpackJsonp([75],{MrQv:function(e,t,a){"use strict";var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[e._v(e._s(e.$route.name))]),e._v(" "),a("div",{staticClass:"pull-right"},[a("el-button",{on:{click:e.back}},[e._v("返回列表")])],1)]),e._v(" "),a("div",{directives:[{name:"loading",rawName:"v-loading.body",value:e.loading,expression:"loading",modifiers:{body:!0}}],staticClass:"table-body table-responsive"},[a("el-form",{ref:"topicInfo",attrs:{rules:e.rules,model:e.resourceInfo}},[a("el-form-item",{staticClass:"form-group left-group",staticStyle:{"margin-top":"24px"},attrs:{label:"文件名:"}},[a("el-col",{attrs:{span:6}},["edit"==e.$route.query.type?a("el-input",{staticClass:"w128",attrs:{disabled:!0},model:{value:e.showFileName,callback:function(t){e.showFileName=t},expression:"showFileName"}}):e._e(),e._v(" "),"add"==e.$route.query.type?a("el-input",{staticClass:"w128",model:{value:e.showFileName,callback:function(t){e.showFileName=t},expression:"showFileName"}}):e._e()],1)],1),e._v(" "),a("el-form-item",{staticClass:"form-group",attrs:{label:" "}},[a("el-col",{attrs:{span:24}},[a("textarea",{directives:[{name:"model",rawName:"v-model",value:e.resourceInfo.source,expression:"resourceInfo.source"}],staticStyle:{width:"100%",outline:"none"},attrs:{rows:"25",id:"tmp"},domProps:{value:e.resourceInfo.source},on:{input:function(t){t.target.composing||e.$set(e.resourceInfo,"source",t.target.value)}}})])],1)],1),e._v(" "),a("div",{staticClass:"margin-md"},["edit"==e.$route.query.type?a("el-button",{attrs:{type:"warning"},on:{click:function(t){e.updateResourceInfo("topicInfo")}}},[e._v("\n                修改\n            ")]):e._e(),e._v(" "),"add"==e.$route.query.type?a("el-button",{attrs:{value:"",type:"primary"},on:{click:function(t){e.addTemplateInfo("topicInfo")}}},[e._v("\n                添加\n            ")]):e._e(),e._v(" "),a("el-button",{attrs:{type:"reset"}},[e._v("重置")])],1)],1)])},r=[],o={render:n,staticRenderFns:r};t.a=o},OgkS:function(e,t,a){var n=a("W2dj");"string"==typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);a("8bSs")("7d386990",n,!0)},PHrY:function(e,t,a){"use strict";function n(e){return Object(A.a)({url:"/api/admin/resource/tree",method:"post",data:e})}function r(e){return Object(A.a)({url:"/api/admin/resource/list",method:"post",data:e})}function o(e){return Object(A.a)({url:"/api/admin/template/list",method:"post",data:e})}function i(e){return Object(A.a)({url:"/api/admin/resource/get",method:"post",data:e})}function s(e){return Object(A.a)({url:"/api/admin/template/get",method:"post",data:e})}function u(e){return Object(A.a)({url:"/api/admin/resource/delete",method:"post",signValidate:!0,data:e})}function l(e){return Object(A.a)({url:"/api/admin/template/delete",method:"post",signValidate:!0,data:e})}function d(e){return Object(A.a)({url:"api/admin/resource/dir_save",method:"post",signValidate:!0,data:e})}function c(e){return Object(A.a)({url:"api/admin/template/dir_save",method:"post",signValidate:!0,data:e})}function m(e){return Object(A.a)({url:"/api/admin/resource/rename",method:"post",signValidate:!0,data:e})}function p(e){return Object(A.a)({url:"/api/admin/template/rename",method:"post",signValidate:!0,data:e})}function f(e){return Object(A.a)({url:"/api/admin/template/update",method:"post",signValidate:!0,data:e})}function h(e){return Object(A.a)({url:"/api/admin/resource/save",method:"post",signValidate:!0,data:e})}function g(e){return Object(A.a)({url:"/api/admin/template/save",method:"post",signValidate:!0,data:e})}function b(e){return Object(A.a)({url:"/api/admin/template/tree",method:"post",data:e})}function v(e){return Object(A.a)({url:"/api/admin/template/getSolutions",method:"post",data:e})}function j(e){return Object(A.a)({url:"/api/admin/template/solutionupdate",method:"post",signValidate:!0,data:e})}function I(e){return Object(A.a)({url:P.a.getChannelTree,method:"post",signValidate:!0,data:e})}function O(e){return Object(A.a)({url:P.a.getChannelList,method:"post",signValidate:!0,data:e})}function y(e){return Object(A.a)({url:P.a.getChannelInfo,method:"post",signValidate:!0,data:e})}function w(e){return Object(A.a)({url:P.a.addChannelInfo,method:"post",signValidate:!0,data:e})}function V(e){return Object(A.a)({url:P.a.updateChannelInfo,method:"post",signValidate:!0,data:e})}function _(e){return Object(A.a)({url:P.a.deleteChannelInfo,method:"post",signValidate:!0,data:e})}function $(e){return Object(A.a)({url:P.a.priorityChannelInfo,method:"post",signValidate:!0,data:e})}function C(e){return Object(A.a)({url:P.a.getChannelTpl,method:"post",signValidate:!0,data:e})}function q(e){return Object(A.a)({url:P.a.getParentChannelList,method:"post",signValidate:!0,data:e})}function F(e){return Object(A.a)({url:P.a.getArticleList,method:"post",signValidate:!0,data:e})}function T(e){return Object(A.a)({url:P.a.getArticleInfo,method:"post",signValidate:!0,data:e})}function k(e){return Object(A.a)({url:P.a.addArticleInfo,method:"post",signValidate:!0,data:e})}function x(e){return Object(A.a)({url:P.a.updateArticleInfo,method:"post",signValidate:!0,data:e})}function N(e){return Object(A.a)({url:P.a.deleteArticleInfo,method:"post",signValidate:!0,data:e})}function S(e){return Object(A.a)({url:P.a.getArticleChannelList,method:"post",signValidate:!0,data:e})}t.u=n,t.t=r,t.x=o,t.s=i,t.w=s,t.i=u,t.j=l,t.e=d,t.f=c,t.A=m,t.C=p,t.F=f,t.c=h,t.d=g,t.y=b,t.v=v,t.B=j,t.q=I,t.o=O,t.n=y,t.b=w,t.E=V,t.h=_,t.z=$,t.p=C,t.r=q,t.m=F,t.l=T,t.a=k,t.D=x,t.g=N,t.k=S;var A=a("yYIz"),P=a("z+6n"),R=a("DEjr");a.n(R)},PXjT:function(e,t,a){"use strict";(function(e){var n=a("PHrY");t.a={data:function(){return{loading:!0,count:0,state:!1,resourceInfo:{filename:"",source:""},showFileName:"",rules:{name:[{required:!0,message:"请填写一个主题分类名",trigger:"blur"}],path:[{required:!0,message:"请填写一个路径",trigger:"blur"}],priority:[{required:!0,type:"number",message:"请填写一个数字排序",trigger:"blur"}]}}},methods:{getTemplateInfo:function(e){var t=this;n.w({name:e}).then(function(e){t.loading=!1,t.resourceInfo.source=e.body}).catch(function(e){t.loading=!1,t.$message.error("网络异常")})},updateResourceInfo:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;var a=t.resourceInfo;n.F(a).then(function(e){200==e.code?t.$message.success("修改成功"):t.$message.error(e.message)})})},addTemplateInfo:function(e){var t=this;this.$refs[e].validate(function(e){if(!e)return!1;t.resourceInfo.root=t.resourceInfo.filename,t.resourceInfo.filename=t.showFileName;var a=t.resourceInfo;n.d(a).then(function(e){200==e.code?(t.$message.success("添加成功"),setTimeout(function(){t.back()},1e3)):t.$message.error(e.message)}).catch(function(e){t.$message.error("添加失败")})})},back:function(){this.$router.push({path:"/templatelist",query:{name:this.$route.query.root,noceStr:Math.round(10*Math.random())}})},resetForm:function(e){this.$route.query.id;this.$refs[e].resetFields()},stringReplace:function(e){var t=e.lastIndexOf("/");return e=e.substring(t+1,e.length)}},created:function(){e(window).scrollTop(0);var t=this.$route.query.type,a=this.$route.query.id;this.$route.query.rootId;"add"==t?(this.resourceInfo.filename=a,this.loading=!1):"edit"==t&&(this.resourceInfo.filename=a,this.showFileName=this.stringReplace(a),this.getTemplateInfo(a))},mounted:function(){e("#tmp").slimScroll({height:"100%",width:"100%",color:"#999",opacity:.5}),e(window).scrollTop(0)},watch:{$route:function(t,a){var n=this.$route.query.id;this.showFileName=this.stringReplace(n),this.resourceInfo.filename=n,this.loading=!0,this.getTemplateInfo(n),e(window).scrollTop(0)}}}}).call(t,a("tra3"))},W2dj:function(e,t,a){t=e.exports=a("BkJT")(!1),t.push([e.i,".left-group .el-form-item__label{width:auto;padding-right:6px}",""])},amKY:function(e,t,a){"use strict";function n(e){a("OgkS")}Object.defineProperty(t,"__esModule",{value:!0});var r=a("PXjT"),o=a("MrQv"),i=a("/Xao"),s=n,u=i(r.a,o.a,!1,s,null,null);t.default=u.exports}});