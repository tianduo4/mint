webpackJsonp([58],{FWz8:function(t,e,a){"use strict";function n(t){return Object(V.a)({url:z.a.getOrderList,method:"post",data:t,signValidate:!0})}function s(t){return Object(V.a)({url:z.a.getOrderInfo,method:"post",data:t,signValidate:!0})}function i(t){return Object(V.a)({url:z.a.updateOrderInfo,method:"post",data:t,signValidate:!0})}function r(t){return Object(V.a)({url:z.a.deleteOrderInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(V.a)({url:z.a.enterOrderInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object(V.a)({url:z.a.doneOrderInfo,method:"post",data:t,signValidate:!0})}function u(t){return Object(V.a)({url:z.a.saveOrderInfo,method:"post",data:t,signValidate:!0})}function c(t){return Object(V.a)({url:z.a.payOrderInfo,method:"post",data:t,signValidate:!0})}function p(t){return Object(V.a)({url:z.a.cannelOrderInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object(V.a)({url:z.a.getOrderReturnList,method:"post",data:t,signValidate:!0})}function m(t){return Object(V.a)({url:z.a.getOrderReturnInfo,method:"post",data:t,signValidate:!0})}function g(t){return Object(V.a)({url:z.a.deleteOrderReturnInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(V.a)({url:z.a.affirmOrderReturnInfo,method:"post",data:t,signValidate:!0})}function f(t){return Object(V.a)({url:z.a.sendbackOrderReturnInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(V.a)({url:z.a.receiveOrderReturnInfo,method:"post",data:t,signValidate:!0})}function b(t){return Object(V.a)({url:z.a.refundOrderReturnInfo,method:"post",data:t,signValidate:!0})}function _(t){return Object(V.a)({url:z.a.getShopShipmentsList,method:"post",data:t,signValidate:!0})}function y(t){return Object(V.a)({url:z.a.getShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(V.a)({url:z.a.addShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(V.a)({url:z.a.updateShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object(V.a)({url:z.a.deleteShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function k(t){return Object(V.a)({url:z.a.getShipmentsList,method:"post",data:t,signValidate:!0})}function w(t){return Object(V.a)({url:z.a.getShipmentsInfo,method:"post",data:t,signValidate:!0})}function C(t){return Object(V.a)({url:z.a.isPrintShipmentsInfo,method:"post",data:t,signValidate:!0})}function j(t){return Object(V.a)({url:z.a.deleteShipmentsInfo,method:"post",data:t,signValidate:!0})}e.k=n,e.j=s,e.x=i,e.d=r,e.i=l,e.h=o,e.v=u,e.s=c,e.c=p,e.m=d,e.l=m,e.e=g,e.b=v,e.w=f,e.t=h,e.u=b,e.q=_,e.p=y,e.a=S,e.y=I,e.g=O,e.o=k,e.n=w,e.r=C,e.f=j;var V=a("yYIz"),z=a("z+6n")},"H/O4":function(t,e,a){"use strict";var n=a("FWz8");e.a={data:function(){return{loading:!0,items:[],ids:"",params:{pageNo:1,pageSize:10,userName:"",code1:"",status:"",paymentStatus:"",shippingStatus:"",paymentId:"",shoppingId:"",startTime:"",endTime:""},disabled:!0,pagination:{total:0,currentPage:1,changePageSize:localStorage.getItem("PageSize")}}},methods:{getItems:function(){var t=this;n.k(this.params).then(function(e){t.pagination.total=e.totalCount,t.items=e.body,t.loading=!1})},deleteItems:function(t){var e=this;this.$confirm("确定要删除吗?","警告",{type:"error"}).then(function(a){n.d({ids:t}).then(function(t){200==t.code&&e.successMessage("删除成功"),e.getItems()})}).catch(function(t){})},checkIds:function(t){for(var e=[],a=0;a<t.length;a++)e.push(t[a].id);0==e.length?(this.ids="",this.disabled=!0):(this.ids=e.join(","),this.disabled=!1)},query:function(){this.loading=!0,this.getItems()},getPage:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},getSize:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},changeSize:function(t){var e=t.target.value;e<1?(localStorage.setItem("PageSize",15),e=15):localStorage.setItem("PageSize",e),this.loading=!0,this.params.pageSize=parseInt(e),this.params.pageNo=1,this.pagination.currentPage=1,this.getItems()},getLocalPage:function(){var t=localStorage.getItem("PageSize");null!=t?this.params.pageSize=parseInt(t):this.pagination.changePageSize=20,this.getItems()}},created:function(){var t=this.$route.query.type,e=this.$route.query.id;"shippingStatus"==t?(this.params.shippingStatus=e,this.getLocalPage()):"paymentStatus"==t?(this.params.paymentStatus=e,this.getLocalPage()):this.getLocalPage()}}},f7nU:function(t,e,a){var n=a("vScG");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a("8bSs")("a5f6e570",n,!0)},jg3M:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[t._m(0,!1,!1),t._v(" "),a("div",{staticClass:"table-top-bar clearfix"},[a("div",{staticClass:"query-inline-box "},[a("div",[a("div",{staticClass:"query-inline-group"},[a("el-select",{staticClass:"w160",on:{change:t.query},model:{value:t.params.status,callback:function(e){t.$set(t.params,"status",e)},expression:"params.status"}},[a("el-option",{attrs:{label:"订单状态",value:""}}),t._v(" "),a("el-option",{attrs:{label:"未确认",value:1}}),t._v(" "),a("el-option",{attrs:{label:"已确认",value:2}}),t._v(" "),a("el-option",{attrs:{label:"已取消",value:3}}),t._v(" "),a("el-option",{attrs:{label:"已完成",value:4}})],1),t._v(" "),a("el-select",{staticClass:"w160",on:{change:t.query},model:{value:t.params.paymentStatus,callback:function(e){t.$set(t.params,"paymentStatus",e)},expression:"params.paymentStatus"}},[a("el-option",{attrs:{label:"支付状态",value:""}}),t._v(" "),a("el-option",{attrs:{label:"未支付",value:1}}),t._v(" "),a("el-option",{attrs:{label:"已支付",value:2}}),t._v(" "),a("el-option",{attrs:{label:"已退款",value:3}})],1),t._v(" "),a("el-select",{staticClass:"w160",on:{change:t.query},model:{value:t.params.shippingStatus,callback:function(e){t.$set(t.params,"shippingStatus",e)},expression:"params.shippingStatus"}},[a("el-option",{attrs:{label:"配送状态",value:""}}),t._v(" "),a("el-option",{attrs:{label:"未发货",value:1}}),t._v(" "),a("el-option",{attrs:{label:"已发货",value:2}}),t._v(" "),a("el-option",{attrs:{label:"已退货",value:3}})],1),t._v(" "),a("el-select",{staticClass:"w160",on:{change:t.query},model:{value:t.params.paymentId,callback:function(e){t.$set(t.params,"paymentId",e)},expression:"params.paymentId"}},[a("el-option",{attrs:{label:"支付方式",value:""}}),t._v(" "),a("el-option",{attrs:{label:"网上支付",value:1}}),t._v(" "),a("el-option",{attrs:{label:"银行汇款",value:2}}),t._v(" "),a("el-option",{attrs:{label:"货到付款",value:3}})],1),t._v(" "),a("el-select",{staticClass:"w160",on:{change:t.query},model:{value:t.params.shoppingId,callback:function(e){t.$set(t.params,"shoppingId",e)},expression:"params.shoppingId"}},[a("el-option",{attrs:{label:"配送方式",value:""}}),t._v(" "),a("el-option",{attrs:{label:"普通快递",value:1}}),t._v(" "),a("el-option",{attrs:{label:"顺丰快递",value:2}})],1)],1),t._v(" "),a("div",{staticClass:"clearfix",staticStyle:{"margin-top":"48px"}},[a("div",{staticClass:"query-inline-group"},[a("label",{attrs:{for:""}},[t._v("订单编号:")]),t._v(" "),a("el-input",{staticClass:"w160",nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.query()}},model:{value:t.params.code1,callback:function(e){t.$set(t.params,"code1",e)},expression:"params.code1"}})],1),t._v(" "),a("div",{staticClass:"query-inline-group"},[a("label",[t._v("下单时间:")]),t._v(" "),a("el-date-picker",{staticClass:"w128-sm",attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss"},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.query(e)}},model:{value:t.params.startTime,callback:function(e){t.$set(t.params,"startTime",e)},expression:"params.startTime"}}),t._v(" "),a("span",{staticClass:"time-slot"},[t._v("-")]),t._v(" "),a("el-date-picker",{staticClass:"w128-sm",attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss"},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.query(e)}},model:{value:t.params.endTime,callback:function(e){t.$set(t.params,"endTime",e)},expression:"params.endTime"}})],1),t._v(" "),a("div",{staticClass:"query-inline-group"},[a("el-button",{on:{click:t.query}},[t._v("查询")])],1)])])])]),t._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.items,stripe:""},on:{"selection-change":t.checkIds}},[a("el-table-column",{attrs:{type:"selection",align:"center",width:68}}),t._v(" "),a("el-table-column",{attrs:{label:"id",prop:"id",align:"center",width:68}}),t._v(" "),a("el-table-column",{attrs:{label:"订单编号",prop:"code",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"买家",prop:"receiveName",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"订单金额(元)",prop:"score",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(e.row.total))]),t._v(" "),a("div",{staticStyle:{color:"#d8dce5"}},[t._v("(含运费"+t._s(e.row.freight)+")")])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"下单日期",prop:"createTime",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"订单状态",prop:"orderStatus",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[1==e.row.orderStatus?a("span",[t._v("未确认")]):t._e(),t._v(" "),2==e.row.orderStatus?a("span",[t._v("已确认")]):t._e(),t._v(" "),3==e.row.orderStatus?a("span",[t._v("已取消")]):t._e(),t._v(" "),4==e.row.orderStatus?a("span",[t._v("已完成")]):t._e()]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"支付状态",prop:"paymentStatus",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[1==e.row.paymentStatus?a("span",[t._v("未支付")]):t._e(),t._v(" "),2==e.row.paymentStatus?a("span",[t._v("已支付")]):t._e(),t._v(" "),3==e.row.paymentStatus?a("span",[t._v("已退款")]):t._e()]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"支付方式",prop:"paymentName",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"配送状态",prop:"shippingStatus",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[1==e.row.shippingStatus?a("span",[t._v("未发货")]):t._e(),t._v(" "),2==e.row.shippingStatus?a("span",[t._v("已发货")]):t._e(),t._v(" "),3==e.row.shippingStatus?a("span",[t._v("已退货")]):t._e()]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"配送方式",prop:"shippingName",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",align:"center",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("a",{staticClass:"t-order",attrs:{title:"订单管理"},on:{click:function(a){t.routerLink("/order/mange","update",e.row.id)}}}),t._v(" "),a("a",{staticClass:"t-edit",attrs:{title:"修改"},on:{click:function(a){t.routerLink("/order/update","update",e.row.id)}}}),t._v(" "),a("a",{staticClass:"t-del",attrs:{title:"删除"},on:{click:function(a){t.deleteItems(e.row.id)}}})]}}])})],1),t._v(" "),a("div",{staticClass:"table-bottom-bar"},[a("div",{staticClass:"pull-left"},[a("el-button",{attrs:{disabled:t.disabled},on:{click:function(e){t.deleteItems(t.ids)}}},[t._v("批量删除")]),t._v(" "),a("span",{staticClass:"ml-48"},[t._v("每页显示\n                  "),a("el-input",{staticClass:"input-sm",attrs:{type:"number",min:"1",max:"50"},on:{blur:t.changeSize},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.changeSize(e)}},model:{value:t.pagination.changePageSize,callback:function(e){t.$set(t.pagination,"changePageSize",e)},expression:"pagination.changePageSize"}}),t._v("\n                   条,输入后按回车\n                   ")],1)],1),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-pagination",{attrs:{layout:"total, prev, pager, next",total:t.pagination.total,"page-size":t.params.pageSize,"current-page":t.pagination.currentPage},on:{"update:currentPage":function(e){t.$set(t.pagination,"currentPage",e)},"current-change":t.getPage,"size-change":t.getSize}})],1)])],1)},s=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v("订单列表")])])}],i={render:n,staticRenderFns:s};e.a=i},vScG:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,".w160[data-v-e911c6e0]{width:128px;margin-right:10px}",""])},waSk:function(t,e,a){"use strict";function n(t){a("f7nU")}Object.defineProperty(e,"__esModule",{value:!0});var s=a("H/O4"),i=a("jg3M"),r=a("/Xao"),l=n,o=r(s.a,i.a,!1,l,"data-v-e911c6e0",null);e.default=o.exports}});