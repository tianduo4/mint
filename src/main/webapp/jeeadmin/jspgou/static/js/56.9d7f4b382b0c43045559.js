webpackJsonp([56],{"2Bgo":function(t,e,a){"use strict";var n=a("FWz8");e.a={data:function(){return{centerDialogVisible:!1,centerDialogVisible1:!1,loading:!0,type:this.$route.query.type,id:this.$route.query.id,info:{},saveOrder:{orderId:0,id:"",waybill:"",comment:""},payOrderInfo:{orderId:0,bank:"",accounts:"",money:"",drawee:"",comment:""},shipmentsList:[],orderInfoState:!0,userInfoState:!0,orderReturnState:!0,typeList:[],count:[],countPrice:"",productList:[],url:"http://demo4.jeecms.com",pathUrl:"",rules:{name:[{required:!0,message:"此项必填",trigger:"blur"}]}}},methods:{getInfo:function(t){var e=this;n.n({id:t}).then(function(t){e.info=t.body,e.productList=t.body.product,e.loading=!1})}},created:function(){this.getInfo(this.id)}}},"6OcI":function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,".border-all[data-v-cea086ba]{border:1px solid #eee}.w280[data-v-cea086ba]{width:320px;margin:0 auto}.img-w50[data-v-cea086ba]{display:block;width:50px;height:50px;margin:7px 10px 7px 0;float:left}.img-title[data-v-cea086ba]{float:left;line-height:20px;text-align:left;color:#1276c3;width:260px;margin-top:7px;text-overflow:ellipsis}.pro-table[data-v-cea086ba]{width:100%;margin-top:24px}.w-80[data-v-cea086ba]{width:80px}.pro-table th[data-v-cea086ba]{border-top:1px solid #eee;border-bottom:2px solid #eee;height:50px;font-size:14px;color:#8a8e98;text-align:center}.pro-table td[data-v-cea086ba]{border-bottom:1px solid #eee;font-size:14px;color:#353535;text-align:center;padding:0 12px}.more-info[data-v-cea086ba]{padding-left:0;margin-top:22px;margin-bottom:0}",""])},FWz8:function(t,e,a){"use strict";function n(t){return Object(C.a)({url:L.a.getOrderList,method:"post",data:t,signValidate:!0})}function r(t){return Object(C.a)({url:L.a.getOrderInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object(C.a)({url:L.a.updateOrderInfo,method:"post",data:t,signValidate:!0})}function i(t){return Object(C.a)({url:L.a.deleteOrderInfo,method:"post",data:t,signValidate:!0})}function s(t){return Object(C.a)({url:L.a.enterOrderInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(C.a)({url:L.a.doneOrderInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object(C.a)({url:L.a.saveOrderInfo,method:"post",data:t,signValidate:!0})}function c(t){return Object(C.a)({url:L.a.payOrderInfo,method:"post",data:t,signValidate:!0})}function u(t){return Object(C.a)({url:L.a.cannelOrderInfo,method:"post",data:t,signValidate:!0})}function m(t){return Object(C.a)({url:L.a.getOrderReturnList,method:"post",data:t,signValidate:!0})}function f(t){return Object(C.a)({url:L.a.getOrderReturnInfo,method:"post",data:t,signValidate:!0})}function p(t){return Object(C.a)({url:L.a.deleteOrderReturnInfo,method:"post",data:t,signValidate:!0})}function b(t){return Object(C.a)({url:L.a.affirmOrderReturnInfo,method:"post",data:t,signValidate:!0})}function g(t){return Object(C.a)({url:L.a.sendbackOrderReturnInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(C.a)({url:L.a.receiveOrderReturnInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(C.a)({url:L.a.refundOrderReturnInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object(C.a)({url:L.a.getShopShipmentsList,method:"post",data:t,signValidate:!0})}function _(t){return Object(C.a)({url:L.a.getShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function x(t){return Object(C.a)({url:L.a.addShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(C.a)({url:L.a.updateShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function j(t){return Object(C.a)({url:L.a.deleteShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object(C.a)({url:L.a.getShipmentsList,method:"post",data:t,signValidate:!0})}function y(t){return Object(C.a)({url:L.a.getShipmentsInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(C.a)({url:L.a.isPrintShipmentsInfo,method:"post",data:t,signValidate:!0})}function w(t){return Object(C.a)({url:L.a.deleteShipmentsInfo,method:"post",data:t,signValidate:!0})}e.k=n,e.j=r,e.x=o,e.d=i,e.i=s,e.h=l,e.v=d,e.s=c,e.c=u,e.m=m,e.l=f,e.e=p,e.b=b,e.w=g,e.t=h,e.u=v,e.q=O,e.p=_,e.a=x,e.y=I,e.g=j,e.o=V,e.n=y,e.r=S,e.f=w;var C=a("yYIz"),L=a("z+6n")},YWoC:function(t,e,a){var n=a("6OcI");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a("8bSs")("b78db290",n,!0)},doId:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v(t._s(t.$route.name))]),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-button",{staticClass:"back",on:{click:function(e){t.routerLink("/shipmentsList","list")}}},[t._v("返回列表")])],1)]),t._v(" "),a("el-form",{ref:"form",staticClass:"table-body",attrs:{"label-width":"160px",model:t.info,rules:t.rules}},[a("div",{staticClass:"more-info  more-info1"},[t._v("订单信息")]),t._v(" "),a("el-row",{staticClass:"form-group"},[a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"订单编号"}},[t._v("\n             "+t._s(t.info.code)+"\n        ")])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"支付状态"}},[1==t.info.paymentStatus?a("span",[t._v("未支付")]):t._e(),t._v(" "),2==t.info.paymentStatus?a("span",[t._v("已支付")]):t._e()])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"收货人姓名"}},[t._v("\n             "+t._s(t.info.receiveName)+"\n        ")])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"收货人手机号"}},[t._v("\n             "+t._s(t.info.receiveMobile)+"\n        ")])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"收货人固定电话"}},[t._v("\n             "+t._s(t.info.receivePhone)+"\n        ")])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"收货人邮编"}},[t._v("\n             "+t._s(t.info.receiveZip)+"\n        ")])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"收货人地址"}},[t._v("\n             "+t._s(t.info.receiveAddress)+"\n        ")])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"物流单号"}},[t._v("\n             "+t._s(t.info.waybill)+"\n        ")])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"金额"}},[t._v("\n             "+t._s(t.info.money)+"\n        ")])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"操作员"}},[t._v("\n             "+t._s(t.info.username)+"\n        ")])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"备注"}},[t._v("\n             "+t._s(t.info.comment)+"\n        ")])],1)],1)],1)],1)},r=[],o={render:n,staticRenderFns:r};e.a=o},y6pP:function(t,e,a){"use strict";function n(t){a("YWoC")}Object.defineProperty(e,"__esModule",{value:!0});var r=a("2Bgo"),o=a("doId"),i=a("46Yf"),s=n,l=i(r.a,o.a,!1,s,"data-v-cea086ba",null);e.default=l.exports}});