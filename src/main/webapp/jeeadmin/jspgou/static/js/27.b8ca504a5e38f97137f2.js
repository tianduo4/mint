webpackJsonp([27],{"0CTF":function(t,e,a){"use strict";function n(t){a("9v/S")}Object.defineProperty(e,"__esModule",{value:!0});var i=a("rovp"),o=a("2m/g"),s=a("46Yf"),r=n,u=s(i.a,o.a,!1,r,"data-v-ed30d592",null);e.default=u.exports},"2m/g":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[t._m(0),t._v(" "),a("div",{staticClass:"table-top-bar clearfix"},[a("div",{staticClass:"query-inline-box flex-between"},[a("div",{staticClass:"query-inline-group"},[a("el-select",{on:{change:t.logisticsIds},model:{value:t.params.status,callback:function(e){t.$set(t.params,"status",e)},expression:"params.status"}},[a("el-option",{attrs:{label:"选择快递单模版",value:""}}),t._v(" "),t._l(t.LogisticsList,function(t,e){return a("el-option",{key:e,attrs:{label:t.name,value:t.id}})})],2)],1),t._v(" "),a("div",{staticClass:"query-inline-group"},[a("el-select",{on:{change:t.query},model:{value:t.params.isPrint,callback:function(e){t.$set(t.params,"isPrint",e)},expression:"params.isPrint"}},[a("el-option",{attrs:{label:"全部订单",value:""}}),t._v(" "),a("el-option",{attrs:{label:"已打印",value:!0}}),t._v(" "),a("el-option",{attrs:{label:"未打印",value:!1}})],1)],1)])]),t._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.items,stripe:""},on:{"selection-change":t.checkIds}},[a("el-table-column",{attrs:{type:"selection",align:"center",width:68}}),t._v(" "),a("el-table-column",{attrs:{label:"id",prop:"id",align:"center",width:68}}),t._v(" "),a("el-table-column",{attrs:{label:"订单编号",prop:"code",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"物流编号",prop:"waybill",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"是否已打印快递面单",prop:"isPrint",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.isPrint?a("span",[t._v("已打印")]):a("span",[t._v("未打印")])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"操作员",prop:"username",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",align:"center",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("a",{staticClass:"t-order",attrs:{title:"查看"},on:{click:function(a){t.routerLink("/shipmentsList/update","update",e.row.id)}}}),t._v(" "),a("a",{staticClass:"t-del",attrs:{title:"删除"},on:{click:function(a){t.deleteItems(e.row.id)}}})]}}])})],1),t._v(" "),a("div",{staticClass:"table-bottom-bar"},[a("div",{staticClass:"pull-left"},[a("el-button",{on:{click:function(e){t.isPrintShipmentsInfo("true")}}},[t._v("标记为已打印")]),t._v(" "),a("el-button",{on:{click:function(e){t.isPrintShipmentsInfo("false")}}},[t._v("标记为未打印")]),t._v(" "),a("el-button",{attrs:{disabled:t.disabled},on:{click:function(e){t.deleteItems(t.ids)}}},[t._v("批量删除")]),t._v(" "),a("span",{staticClass:"ml-48"},[t._v("每页显示\n                  "),a("el-input",{staticClass:"input-sm",attrs:{type:"number",min:"1",max:"50"},on:{blur:t.changeSize},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.changeSize(e)}},model:{value:t.pagination.changePageSize,callback:function(e){t.$set(t.pagination,"changePageSize",e)},expression:"pagination.changePageSize"}}),t._v("\n                   条,输入后按回车\n                   ")],1)],1),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-pagination",{attrs:{layout:"total, prev, pager, next",total:t.pagination.total,"page-size":t.params.pageSize,"current-page":t.pagination.currentPage},on:{"update:currentPage":function(e){t.$set(t.pagination,"currentPage",e)},"current-change":t.getPage,"size-change":t.getSize}})],1)])],1)},i=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v("订单列表")])])}],o={render:n,staticRenderFns:i};e.a=o},"9v/S":function(t,e,a){var n=a("S9jC");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a("8bSs")("57b42408",n,!0)},Cgi1:function(t,e,a){"use strict";function n(t){return Object(st.a)({url:rt.a.getApiInfoList,method:"post",data:t,signValidate:!0})}function i(t){return Object(st.a)({url:rt.a.deleteApiInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object(st.a)({url:rt.a.updateApiInfo,method:"post",data:t,signValidate:!0})}function s(t){return Object(st.a)({url:rt.a.addApiInfo,method:"post",data:t,signValidate:!0})}function r(t){return Object(st.a)({url:rt.a.getApiInfo,method:"post",data:t,signValidate:!0})}function u(t){return Object(st.a)({url:rt.a.getApiAccountList,method:"post",data:t,signValidate:!0})}function d(t){return Object(st.a)({url:rt.a.getApiAccountInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(st.a)({url:rt.a.addApiAccountInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object(st.a)({url:rt.a.updateApiPassword,method:"post",data:t,signValidate:!0})}function g(t){return Object(st.a)({url:rt.a.checkApiPassword,method:"post",data:t,nonceStr:!0,signValidate:!0})}function p(t){return Object(st.a)({url:rt.a.getApiRecordList,method:"post",data:t,signValidate:!0})}function f(t){return Object(st.a)({url:rt.a.deleteApiRecordInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(st.a)({url:rt.a.getFtpList,method:"post",data:t,signValidate:!0})}function m(t){return Object(st.a)({url:rt.a.getFtpInfo,method:"post",data:t,signValidate:!0})}function b(t){return Object(st.a)({url:rt.a.addFtpInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object(st.a)({url:rt.a.updateFtpInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(st.a)({url:rt.a.deleteFtpInfo,method:"post",data:t,signValidate:!0})}function j(t){return Object(st.a)({url:rt.a.getGlobalInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object(st.a)({url:rt.a.updateGlobalInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(st.a)({url:rt.a.getBasicInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(st.a)({url:rt.a.updateBasicInfo,method:"post",data:t,signValidate:!0})}function _(t){return Object(st.a)({url:rt.a.getEmailInfo,method:"post",data:t,signValidate:!0})}function P(t){return Object(st.a)({url:rt.a.updateEmailInfo,method:"post",data:t,signValidate:!0})}function y(t){return Object(st.a)({url:rt.a.getShipList,method:"post",data:t,signValidate:!0})}function L(t){return Object(st.a)({url:rt.a.getShipInfo,method:"post",data:t,signValidate:!0})}function z(t){return Object(st.a)({url:rt.a.addShipInfo,method:"post",data:t,signValidate:!0})}function k(t){return Object(st.a)({url:rt.a.updateShipInfo,method:"post",data:t,signValidate:!0})}function C(t){return Object(st.a)({url:rt.a.priorityShipInfo,method:"post",data:t,signValidate:!0})}function A(t){return Object(st.a)({url:rt.a.deleteShipInfo,method:"post",data:t,signValidate:!0})}function w(t){return Object(st.a)({url:rt.a.getLogisticsList,method:"post",data:t,signValidate:!0})}function W(t){return Object(st.a)({url:rt.a.getLogisticsInfo,method:"post",data:t,signValidate:!0})}function x(t){return Object(st.a)({url:rt.a.addLogisticsInfo,method:"post",data:t,signValidate:!0})}function M(t){return Object(st.a)({url:rt.a.updateLogisticsInfo,method:"post",data:t,signValidate:!0})}function X(t){return Object(st.a)({url:rt.a.deleteLogisticsInfo,method:"post",data:t,signValidate:!0})}function R(t){return Object(st.a)({url:rt.a.priorityLogisticsInfo,method:"post",data:t,signValidate:!0})}function F(t){return Object(st.a)({url:rt.a.getSsoInfo,method:"post",data:t,signValidate:!0})}function $(t){return Object(st.a)({url:rt.a.updateSsoInfo,method:"post",data:t,signValidate:!0})}function q(t){return Object(st.a)({url:rt.a.getThirdApiInfo,method:"post",data:t,signValidate:!0})}function N(t){return Object(st.a)({url:rt.a.updateThirdApiInfo,method:"post",data:t,signValidate:!0})}function B(t){return Object(st.a)({url:rt.a.getPaymentPluginsList,method:"post",data:t,signValidate:!0})}function E(t){return Object(st.a)({url:rt.a.getPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function T(t){return Object(st.a)({url:rt.a.updatePaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function Y(t){return Object(st.a)({url:rt.a.priorityPaymentPluginsInfo,method:"post",data:t,signValidate:!0})}function D(t){return Object(st.a)({url:rt.a.getWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function G(t){return Object(st.a)({url:rt.a.updateWeiXinSetInfo,method:"post",data:t,signValidate:!0})}function J(t){return Object(st.a)({url:rt.a.getWeiXinMenuList,method:"post",data:t,signValidate:!0})}function H(t){return Object(st.a)({url:rt.a.getWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function K(t){return Object(st.a)({url:rt.a.addWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Q(t){return Object(st.a)({url:rt.a.updateWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function U(t){return Object(st.a)({url:rt.a.deleteWeiXinMenuInfo,method:"post",data:t,signValidate:!0})}function Z(t){return Object(st.a)({url:rt.a.getWeiXinMessageList,method:"post",data:t,signValidate:!0})}function tt(t){return Object(st.a)({url:rt.a.addWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function et(t){return Object(st.a)({url:rt.a.getWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function at(t){return Object(st.a)({url:rt.a.updateWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function nt(t){return Object(st.a)({url:rt.a.deleteWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function it(t){return Object(st.a)({url:rt.a.getDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}function ot(t){return Object(st.a)({url:rt.a.updateDefaultWeiXinMessageInfo,method:"post",data:t,signValidate:!0})}e.s=n,e.i=i,e.Q=o,e.b=s,e.r=r,e.q=u,e.p=d,e.a=l,e.R=c,e.h=g,e.t=p,e.j=f,e.y=h,e.x=m,e.c=b,e.V=O,e.k=I,e.z=j,e.W=V,e.u=v,e.S=S,e.w=_,e.U=P,e.F=y,e.E=L,e.e=z,e.Z=k,e.P=C,e.m=A,e.B=w,e.A=W,e.d=x,e.X=M,e.l=X,e.N=R,e.G=F,e._0=$,e.H=q,e._1=N,e.D=B,e.C=E,e.Y=T,e.O=Y,e.M=D,e._4=G,e.J=J,e.I=H,e.f=K,e._2=Q,e.n=U,e.L=Z,e.g=tt,e.K=et,e._3=at,e.o=nt,e.v=it,e.T=ot;var st=a("yYIz"),rt=a("z+6n")},FWz8:function(t,e,a){"use strict";function n(t){return Object(z.a)({url:k.a.getOrderList,method:"post",data:t,signValidate:!0})}function i(t){return Object(z.a)({url:k.a.getOrderInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object(z.a)({url:k.a.updateOrderInfo,method:"post",data:t,signValidate:!0})}function s(t){return Object(z.a)({url:k.a.deleteOrderInfo,method:"post",data:t,signValidate:!0})}function r(t){return Object(z.a)({url:k.a.enterOrderInfo,method:"post",data:t,signValidate:!0})}function u(t){return Object(z.a)({url:k.a.doneOrderInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object(z.a)({url:k.a.saveOrderInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(z.a)({url:k.a.payOrderInfo,method:"post",data:t,signValidate:!0})}function c(t){return Object(z.a)({url:k.a.cannelOrderInfo,method:"post",data:t,signValidate:!0})}function g(t){return Object(z.a)({url:k.a.getOrderReturnList,method:"post",data:t,signValidate:!0})}function p(t){return Object(z.a)({url:k.a.getOrderReturnInfo,method:"post",data:t,signValidate:!0})}function f(t){return Object(z.a)({url:k.a.deleteOrderReturnInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(z.a)({url:k.a.affirmOrderReturnInfo,method:"post",data:t,signValidate:!0})}function m(t){return Object(z.a)({url:k.a.sendbackOrderReturnInfo,method:"post",data:t,signValidate:!0})}function b(t){return Object(z.a)({url:k.a.receiveOrderReturnInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object(z.a)({url:k.a.refundOrderReturnInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(z.a)({url:k.a.getShopShipmentsList,method:"post",data:t,signValidate:!0})}function j(t){return Object(z.a)({url:k.a.getShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object(z.a)({url:k.a.addShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(z.a)({url:k.a.updateShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(z.a)({url:k.a.deleteShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function _(t){return Object(z.a)({url:k.a.getShipmentsList,method:"post",data:t,signValidate:!0})}function P(t){return Object(z.a)({url:k.a.getShipmentsInfo,method:"post",data:t,signValidate:!0})}function y(t){return Object(z.a)({url:k.a.isPrintShipmentsInfo,method:"post",data:t,signValidate:!0})}function L(t){return Object(z.a)({url:k.a.deleteShipmentsInfo,method:"post",data:t,signValidate:!0})}e.k=n,e.j=i,e.x=o,e.d=s,e.i=r,e.h=u,e.v=d,e.s=l,e.c=c,e.m=g,e.l=p,e.e=f,e.b=h,e.w=m,e.t=b,e.u=O,e.q=I,e.p=j,e.a=V,e.y=v,e.g=S,e.o=_,e.n=P,e.r=y,e.f=L;var z=a("yYIz"),k=a("z+6n")},S9jC:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,".w160[data-v-ed30d592]{width:128px;margin-right:10px}",""])},rovp:function(t,e,a){"use strict";var n=a("FWz8"),i=a("Cgi1");e.a={data:function(){return{loading:!0,items:[],ids:"",params:{pageNo:1,pageSize:10,isPrint:""},LogisticsList:[],disabled:!0,pagination:{total:0,currentPage:1,changePageSize:localStorage.getItem("PageSize")}}},methods:{getItems:function(){var t=this;n.o(this.params).then(function(e){t.pagination.total=e.totalCount,t.items=e.body,t.loading=!1})},getLogisticsId:function(){var t=this;i.B().then(function(e){t.LogisticsList=e.body})},deleteItems:function(t){var e=this;this.$confirm("确定要删除吗?","警告",{type:"error"}).then(function(a){n.f({ids:t}).then(function(t){200==t.code&&e.successMessage("删除成功"),e.getItems()})}).catch(function(t){})},isPrintShipmentsInfo:function(t){var e=this,a=t?"是否标记为已打印":"是否标记为未打印";this.$confirm(a,"提示",{type:"error"}).then(function(a){n.r({status:t,ids:e.ids}).then(function(t){200==t.code&&(e.successMessage("修改成功"),e.getItems())})}).catch(function(t){console.log(t)})},logisticsIds:function(t){},checkIds:function(t){for(var e=[],a=0;a<t.length;a++)e.push(t[a].id);0==e.length?(this.ids="",this.disabled=!0):(this.ids=e.join(","),this.disabled=!1)},query:function(){this.loading=!0,this.getItems()},getPage:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},getSize:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},changeSize:function(t){var e=t.target.value;e<1?(localStorage.setItem("PageSize",15),e=15):localStorage.setItem("PageSize",e),this.loading=!0,this.params.pageSize=parseInt(e),this.params.pageNo=1,this.pagination.currentPage=1,this.getItems()},getLocalPage:function(){var t=localStorage.getItem("PageSize");null!=t?this.params.pageSize=parseInt(t):this.pagination.changePageSize=20,this.getItems()}},created:function(){this.getLocalPage(),this.getLogisticsId()}}}});