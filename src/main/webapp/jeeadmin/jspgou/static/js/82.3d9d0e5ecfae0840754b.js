webpackJsonp([82],{E1xb:function(t,e,a){var n=a("a0g5");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a("8bSs")("74c18d30",n,!0)},H5YY:function(t,e,a){"use strict";var n=a("wc4o");e.a={data:function(){return{loading:!0,items:[],params:{pageNo:1,pageSize:10},disabled:!0,pagination:{total:0,currentPage:1,changePageSize:localStorage.getItem("PageSize")}}},methods:{getItems:function(){var t=this;n.D(this.params).then(function(e){t.pagination.total=e.totalCount,t.items=e.body,t.loading=!1})},deleteItems:function(t){var e=this;this.$confirm("确定要删除吗?","警告",{type:"error"}).then(function(a){n.l({ids:t}).then(function(t){200==t.code&&e.successMessage("删除成功"),e.getItems()})}).catch(function(t){})},checkIds:function(t){for(var e=[],a=0;a<t.length;a++)e.push(t[a].id);0==e.length?(this.ids="",this.disabled=!0):(this.ids=e.join(","),this.disabled=!1)},modelList:function(t,e){this.$router.push({path:"/typeList/modelList",query:{typeId:t,isCategory:e}})},query:function(){this.getItems()},getPage:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},getSize:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},changeSize:function(t){var e=t.target.value;e<1?(localStorage.setItem("PageSize",15),e=15):localStorage.setItem("PageSize",e),this.loading=!0,this.params.pageSize=parseInt(e),this.params.pageNo=1,this.pagination.currentPage=1,this.getItems()},getLocalPage:function(){var t=localStorage.getItem("PageSize");null!=t?this.params.pageSize=parseInt(t):this.changePageSize=20,this.getItems()}},created:function(){this.getLocalPage()}}},XRCK:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[t._m(0),t._v(" "),a("div",{staticClass:"table-top-bar clearfix"},[a("div",{staticClass:"query-inline-box flex-between"},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.routerLink("/typeList/save","save",0)}}},[t._v("添加类型")])],1)]),t._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.items,stripe:""},on:{"selection-change":t.checkIds}},[a("el-table-column",{attrs:{type:"selection",align:"center",width:68}}),t._v(" "),a("el-table-column",{attrs:{label:"id",prop:"id",align:"center",width:68}}),t._v(" "),a("el-table-column",{attrs:{label:"规格名称",prop:"name",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"商品分类模板前缀",prop:"channelPrefix",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"商品内容模板前缀",prop:"contentPrefix",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"属性列表",prop:"priority",align:"center",width:"300"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("a",{staticClass:"link-color",attrs:{href:"javascript:void(0)"}},[t._v("[分类字段]")]),t._v(" "),a("a",{staticClass:"link-color",attrs:{href:"javascript:void(0)"}},[t._v("[商品字段]")])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"操作",align:"center",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("a",{staticClass:"t-edit",attrs:{title:"修改"},on:{click:function(a){t.routerLink("/typeList/update","update",e.row.id)}}}),t._v(" "),a("a",{staticClass:"t-del",attrs:{title:"删除"},on:{click:function(a){t.deleteItems(e.row.id)}}})]}}])})],1),t._v(" "),a("div",{staticClass:"table-bottom-bar"},[a("div",{staticClass:"pull-left"},[a("el-button",{attrs:{disabled:t.disabled},on:{click:function(e){t.deleteItems(t.ids)}}},[t._v("批量删除")]),t._v(" "),a("span",{staticClass:"ml-48"},[t._v("每页显示\n                  "),a("el-input",{staticClass:"input-sm",attrs:{type:"number",min:"1",max:"50"},on:{blur:t.changeSize},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.changeSize(e)}},model:{value:t.pagination.changePageSize,callback:function(e){t.$set(t.pagination,"changePageSize",e)},expression:"pagination.changePageSize"}}),t._v("\n                   条,输入后按回车\n                   ")],1)],1),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-pagination",{attrs:{layout:"total, prev, pager, next",total:t.pagination.total,"page-size":t.params.pageSize,"current-page":t.pagination.currentPage},on:{"update:currentPage":function(e){t.$set(t.pagination,"currentPage",e)},"current-change":t.getPage,"size-change":t.getSize}})],1)])],1)},o=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v("类型列表")])])}],i={render:n,staticRenderFns:o};e.a=i},"a/xb":function(t,e,a){"use strict";function n(t){a("E1xb")}Object.defineProperty(e,"__esModule",{value:!0});var o=a("H5YY"),i=a("XRCK"),r=a("46Yf"),s=n,d=r(o.a,i.a,!1,s,null,null);e.default=d.exports},a0g5:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,"",""])},wc4o:function(t,e,a){"use strict";function n(t){return Object(Q.a)({url:U.a.getProductList,method:"post",data:t,nonceStr:!0,signValidate:!0})}function o(t){return Object(Q.a)({url:U.a.getProductInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function i(t){return Object(Q.a)({url:U.a.getBrandListByCategoryId,method:"post",data:t,nonceStr:!0,signValidate:!0})}function r(t){return Object(Q.a)({url:U.a.addProductList,method:"post",data:t,nonceStr:!0,signValidate:!0})}function s(t){return Object(Q.a)({url:U.a.updateProductList,method:"post",data:t,nonceStr:!0,signValidate:!0})}function d(t){return Object(Q.a)({url:U.a.deleteProductList,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object(Q.a)({url:U.a.getCommodityCategoryTree,method:"post",data:t,signValidate:!0})}function l(t){return Object(Q.a)({url:U.a.getCommodityCategoryList,method:"post",data:t,signValidate:!0})}function u(t){return Object(Q.a)({url:U.a.getCommodityCategoryInfo,method:"post",data:t,signValidate:!0})}function g(t){return Object(Q.a)({url:U.a.getHotList,method:"post",data:t,signValidate:!0})}function p(t){return Object(Q.a)({url:U.a.getSaleChatList,method:"post",data:t,signValidate:!0})}function f(t){return Object(Q.a)({url:U.a.addCommodityCategoryInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function m(t){return Object(Q.a)({url:U.a.updateCommodityCategoryInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function h(t){return Object(Q.a)({url:U.a.getCommodityTopCategory,method:"post",data:t,signValidate:!0})}function b(t){return Object(Q.a)({url:U.a.getCommodityTemplate,method:"post",data:t,signValidate:!0})}function S(t){return Object(Q.a)({url:U.a.getCommodityBrand,method:"post",nonceStr:!0,data:t,signValidate:!0})}function y(t){return Object(Q.a)({url:U.a.getCommodityStandardType,method:"post",nonceStr:!0,data:t,signValidate:!0})}function v(t){return Object(Q.a)({url:U.a.getBrandList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function j(t){return Object(Q.a)({url:U.a.getBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function I(t){return Object(Q.a)({url:U.a.getBrandTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function O(t){return Object(Q.a)({url:U.a.addBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function V(t){return Object(Q.a)({url:U.a.updateBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function C(t){return Object(Q.a)({url:U.a.deleteBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function _(t){return Object(Q.a)({url:U.a.priorityBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function P(t){return Object(Q.a)({url:U.a.deleteCommodityCategoryInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function L(t){return Object(Q.a)({url:U.a.priorityCommodityCategoryInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function z(t){return Object(Q.a)({url:U.a.getStandardList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function k(t){return Object(Q.a)({url:U.a.getStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function x(t){return Object(Q.a)({url:U.a.addStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function w(t){return Object(Q.a)({url:U.a.updateStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function B(t){return Object(Q.a)({url:U.a.deleteStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function T(t){return Object(Q.a)({url:U.a.priorityStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function D(t){return Object(Q.a)({url:U.a.getTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function M(t){return Object(Q.a)({url:U.a.addTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function N(t){return Object(Q.a)({url:U.a.updateTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function Y(t){return Object(Q.a)({url:U.a.deleteTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function $(t){return Object(Q.a)({url:U.a.getModelList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function E(t){return Object(Q.a)({url:U.a.getModelListAll,method:"post",nonceStr:!0,data:t,signValidate:!0})}function q(t){return Object(Q.a)({url:U.a.getModelInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function H(t){return Object(Q.a)({url:U.a.getConsultList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function R(t){return Object(Q.a)({url:U.a.getConsultInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function J(t){return Object(Q.a)({url:U.a.updateConsultInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function K(t){return Object(Q.a)({url:U.a.deleteConsultInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function A(t){return Object(Q.a)({url:U.a.getDiscussList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function F(t){return Object(Q.a)({url:U.a.getDiscussInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function X(t){return Object(Q.a)({url:U.a.updateDiscussInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function G(t){return Object(Q.a)({url:U.a.deleteDiscussInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}e.G=n,e.F=o,e.o=i,e.c=r,e.S=s,e.j=d,e.t=c,e.s=l,e.r=u,e.B=g,e.H=p,e.b=f,e.P=m,e.w=h,e.v=b,e.q=S,e.u=y,e.n=v,e.m=j,e.p=I,e.a=O,e.O=V,e.f=C,e.L=_,e.g=P,e.M=L,e.J=z,e.I=k,e.d=x,e.T=w,e.k=B,e.N=T,e.K=D,e.e=M,e.U=N,e.l=Y,e.D=$,e.E=E,e.C=q,e.y=H,e.x=R,e.Q=J,e.h=K,e.A=A,e.z=F,e.R=X,e.i=G;var Q=a("yYIz"),U=a("z+6n")}});