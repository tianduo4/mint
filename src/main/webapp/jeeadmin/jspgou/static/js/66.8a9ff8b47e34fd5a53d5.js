webpackJsonp([66],{"7qbo":function(t,e,a){"use strict";var n=a("simw");e.a={data:function(){return{loading:!0,items:[],ids:"",params:{pageNo:1,pageSize:10},disabled:!0,pagination:{total:0,currentPage:1,changePageSize:localStorage.getItem("PageSize")}}},methods:{getItems:function(){var t=this;n.s(this.params).then(function(e){t.pagination.total=e.totalCount,t.items=e.body,t.loading=!1})},deleteItems:function(t){var e=this;this.$confirm("确定要删除吗?","警告",{type:"error"}).then(function(a){n.i({ids:t}).then(function(t){200==t.code&&e.successMessage("删除成功"),e.getItems()})}).catch(function(t){})},checkIds:function(t){for(var e=[],a=0;a<t.length;a++)e.push(t[a].id);0==e.length?(this.ids="",this.disabled=!0):(this.ids=e.join(","),this.disabled=!1)},query:function(){this.getItems()},getPage:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},getSize:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},changeSize:function(t){var e=t.target.value;e<1?(localStorage.setItem("PageSize",15),e=15):localStorage.setItem("PageSize",e),this.loading=!0,this.params.pageSize=parseInt(e),this.params.pageNo=1,this.pagination.currentPage=1,this.getItems()},getLocalPage:function(){var t=localStorage.getItem("PageSize");null!=t?this.params.pageSize=parseInt(t):this.changePageSize=20,this.getItems()}},created:function(){this.getLocalPage()}}},SsL1:function(t,e,a){var n=a("VRUu");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a("8bSs")("f1c47772",n,!0)},VRUu:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,"",""])},c9Cs:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[t._m(0),t._v(" "),a("div",{staticClass:"table-top-bar clearfix"},[a("div",{staticClass:"query-inline-box flex-between"},[a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.routerLink("/advertise/save","save",0)}}},[t._v("添加广告")])],1)]),t._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.items,stripe:""},on:{"selection-change":t.checkIds}},[a("el-table-column",{attrs:{type:"selection",align:"center",width:68}}),t._v(" "),a("el-table-column",{attrs:{label:"id",prop:"id",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"广告名称",prop:"name",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"广告权重",prop:"weight",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"点击次数/展现次数",prop:"priority",align:"center",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(e.row.clickCount)+" / "+t._s(e.row.displayCount))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"是否启用",prop:"enabled",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.enabled?a("span",[t._v("否")]):a("span",{staticClass:"red"},[t._v("是")])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"操作",align:"center",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("a",{staticClass:"t-edit",attrs:{title:"修改"},on:{click:function(a){t.routerLink("/advertise/update","update",e.row.id)}}}),t._v(" "),a("a",{staticClass:"t-del",attrs:{title:"删除"},on:{click:function(a){t.deleteItems(e.row.id)}}})]}}])})],1),t._v(" "),a("div",{staticClass:"table-bottom-bar"},[a("div",{staticClass:"pull-left"},[a("el-button",{attrs:{disabled:t.disabled},on:{click:function(e){t.deleteItems(t.ids)}}},[t._v("批量删除")]),t._v(" "),a("span",{staticClass:"ml-48"},[t._v("每页显示\n                  "),a("el-input",{staticClass:"input-sm",attrs:{type:"number",min:"1",max:"50"},on:{blur:t.changeSize},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.changeSize(e)}},model:{value:t.pagination.changePageSize,callback:function(e){t.$set(t.pagination,"changePageSize",e)},expression:"pagination.changePageSize"}}),t._v("\n                   条,输入后按回车\n                   ")],1)],1),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-pagination",{attrs:{layout:"total, prev, pager, next",total:t.pagination.total,"page-size":t.params.pageSize,"current-page":t.pagination.currentPage},on:{"update:currentPage":function(e){t.$set(t.pagination,"currentPage",e)},"current-change":t.getPage,"size-change":t.getSize}})],1)])],1)},i=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v("广告列表")])])}],o={render:n,staticRenderFns:i};e.a=o},simw:function(t,e,a){"use strict";function n(t){return Object($.a)({url:B.a.getAdminList,method:"post",data:t,signValidate:!0})}function i(t){return Object($.a)({url:B.a.getAdminInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object($.a)({url:B.a.addAdminInfo,method:"post",data:t,signValidate:!0,nonceStr:!0})}function s(t){return Object($.a)({url:B.a.deleteAdminInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function r(t){return Object($.a)({url:B.a.updateAdminInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object($.a)({url:B.a.getRoleList,method:"post",data:t,signValidate:!0})}function u(t){return Object($.a)({url:B.a.getRoleInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object($.a)({url:B.a.addRoleInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object($.a)({url:B.a.updateRoleInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function g(t){return Object($.a)({url:B.a.deleteRoleInfo,method:"post",data:t,signValidate:!0})}function p(t){return Object($.a)({url:B.a.getCouponList,method:"post",data:t,signValidate:!0})}function f(t){return Object($.a)({url:B.a.getCouponInfo,method:"post",data:t,signValidate:!0})}function m(t){return Object($.a)({url:B.a.addCouponInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object($.a)({url:B.a.deleteCouponInfo,method:"post",data:t,signValidate:!0})}function b(t){return Object($.a)({url:B.a.disabledCouponInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function v(t){return Object($.a)({url:B.a.getCustomerServiceList,method:"post",data:t,signValidate:!0})}function I(t){return Object($.a)({url:B.a.getCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object($.a)({url:B.a.addCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function j(t){return Object($.a)({url:B.a.updateCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object($.a)({url:B.a.deleteCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object($.a)({url:B.a.priorityCustomerServiceInfo,method:"post",data:t,signValidate:!0})}function _(t){return Object($.a)({url:B.a.getPosterList,method:"post",data:t,signValidate:!0})}function C(t){return Object($.a)({url:B.a.updatePosterInfo,method:"post",data:t,signValidate:!0})}function z(t){return Object($.a)({url:B.a.getLogList,method:"post",data:t,signValidate:!0})}function y(t){return Object($.a)({url:B.a.getAdspaceList,method:"post",data:t,signValidate:!0})}function P(t){return Object($.a)({url:B.a.getAdspaceInfo,method:"post",data:t,signValidate:!0})}function k(t){return Object($.a)({url:B.a.addAdspaceInfo,method:"post",data:t,signValidate:!0})}function w(t){return Object($.a)({url:B.a.updateAdspaceInfo,method:"post",data:t,signValidate:!0})}function A(t){return Object($.a)({url:B.a.deleteAdspaceInfo,method:"post",data:t,signValidate:!0})}function L(t){return Object($.a)({url:B.a.getAdvertiseList,method:"post",data:t,signValidate:!0})}function x(t){return Object($.a)({url:B.a.getAdvertiseInfo,method:"post",data:t,signValidate:!0})}function R(t){return Object($.a)({url:B.a.addAdvertiseInfo,method:"post",data:t,signValidate:!0})}function q(t){return Object($.a)({url:B.a.updateAdvertiseInfo,method:"post",data:t,signValidate:!0})}function N(t){return Object($.a)({url:B.a.deleteAdvertiseInfo,method:"post",data:t,signValidate:!0})}e.o=n,e.n=i,e.a=o,e.g=s,e.C=r,e.A=l,e.z=u,e.f=d,e.H=c,e.l=g,e.u=p,e.t=f,e.d=m,e.j=h,e.m=b,e.w=v,e.v=I,e.e=S,e.F=j,e.k=O,e.B=V,e.y=_,e.G=C,e.x=z,e.q=y,e.p=P,e.b=k,e.D=w,e.h=A,e.s=L,e.r=x,e.c=R,e.E=q,e.i=N;var $=a("yYIz"),B=a("z+6n")},zBtF:function(t,e,a){"use strict";function n(t){a("SsL1")}Object.defineProperty(e,"__esModule",{value:!0});var i=a("7qbo"),o=a("c9Cs"),s=a("46Yf"),r=n,l=s(i.a,o.a,!1,r,null,null);e.default=l.exports}});