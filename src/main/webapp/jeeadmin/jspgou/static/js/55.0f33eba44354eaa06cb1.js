webpackJsonp([55],{"5FjW":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v(t._s(t.$route.name))]),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-button",{staticClass:"back",on:{click:function(e){t.routerLink("/shopShipments","list")}}},[t._v("返回列表")])],1)]),t._v(" "),a("el-form",{ref:"form",staticClass:"table-body",attrs:{"label-width":"160px",model:t.info,rules:t.rules}},[a("el-row",{staticClass:"form-group"},[a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-col",{staticClass:"border-none",attrs:{xs:24,sm:24,md:12,lg:12}}),t._v(" "),a("el-form-item",{attrs:{label:"发货人:",prop:"name"}},[a("el-input",{staticClass:"w192",model:{value:t.info.name,callback:function(e){t.$set(t.info,"name",e)},expression:"info.name"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-col",{staticClass:"border-none",attrs:{xs:24,sm:24,md:12,lg:12}}),t._v(" "),a("el-form-item",{attrs:{label:"发货人联系方式:",prop:"name"}},[a("el-input",{staticClass:"w192",model:{value:t.info.mobile,callback:function(e){t.$set(t.info,"mobile",e)},expression:"info.mobile"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-col",{staticClass:"border-none",attrs:{xs:24,sm:24,md:12,lg:12}}),t._v(" "),a("el-form-item",{attrs:{label:"发货地址:",prop:"name"}},[a("el-input",{staticClass:"w192",model:{value:t.info.address,callback:function(e){t.$set(t.info,"address",e)},expression:"info.address"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"是否默认:",required:!0}},[a("el-radio",{attrs:{label:!0},model:{value:t.info.isDefault,callback:function(e){t.$set(t.info,"isDefault",e)},expression:"info.isDefault"}},[t._v("是")]),t._v(" "),a("el-radio",{attrs:{label:!1},model:{value:t.info.isDefault,callback:function(e){t.$set(t.info,"isDefault",e)},expression:"info.isDefault"}},[t._v("否")])],1)],1)],1)],1),t._v(" "),a("div",{staticClass:"form-bottom-bar"},["save"==t.type?a("el-button",{attrs:{type:"warning"},on:{click:function(e){t.saveForm(!0)}}},[t._v("提交并继续添加")]):t._e(),t._v(" "),"save"==t.type?a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.saveForm(!1)}}},[t._v("添加")]):t._e(),t._v(" "),"update"==t.type?a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.updateForm()}}},[t._v("修改")]):t._e(),t._v(" "),a("el-button",{attrs:{type:"info"},on:{click:function(e){t.restForm()}}},[t._v("重置")]),t._v(" "),"save"==t.type?a("div",{staticClass:"help-info"},[t._v("选择“提交并继续添加”按钮会停留在添加页面；选择“添加”按钮后将跳转到对应的列表页。")]):t._e()],1)],1)},o=[],i={render:n,staticRenderFns:o};e.a=i},DsPF:function(t,e,a){var n=a("joYa");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a("8bSs")("48f716da",n,!0)},FWz8:function(t,e,a){"use strict";function n(t){return Object(C.a)({url:F.a.getOrderList,method:"post",data:t,signValidate:!0})}function o(t){return Object(C.a)({url:F.a.getOrderInfo,method:"post",data:t,signValidate:!0})}function i(t){return Object(C.a)({url:F.a.updateOrderInfo,method:"post",data:t,signValidate:!0})}function s(t){return Object(C.a)({url:F.a.deleteOrderInfo,method:"post",data:t,signValidate:!0})}function r(t){return Object(C.a)({url:F.a.enterOrderInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(C.a)({url:F.a.doneOrderInfo,method:"post",data:t,signValidate:!0})}function u(t){return Object(C.a)({url:F.a.saveOrderInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object(C.a)({url:F.a.payOrderInfo,method:"post",data:t,signValidate:!0})}function c(t){return Object(C.a)({url:F.a.cannelOrderInfo,method:"post",data:t,signValidate:!0})}function f(t){return Object(C.a)({url:F.a.getOrderReturnList,method:"post",data:t,signValidate:!0})}function m(t){return Object(C.a)({url:F.a.getOrderReturnInfo,method:"post",data:t,signValidate:!0})}function p(t){return Object(C.a)({url:F.a.deleteOrderReturnInfo,method:"post",data:t,signValidate:!0})}function h(t){return Object(C.a)({url:F.a.affirmOrderReturnInfo,method:"post",data:t,signValidate:!0})}function g(t){return Object(C.a)({url:F.a.sendbackOrderReturnInfo,method:"post",data:t,signValidate:!0})}function b(t){return Object(C.a)({url:F.a.receiveOrderReturnInfo,method:"post",data:t,signValidate:!0})}function v(t){return Object(C.a)({url:F.a.refundOrderReturnInfo,method:"post",data:t,signValidate:!0})}function O(t){return Object(C.a)({url:F.a.getShopShipmentsList,method:"post",data:t,signValidate:!0})}function j(t){return Object(C.a)({url:F.a.getShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function _(t){return Object(C.a)({url:F.a.addShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(C.a)({url:F.a.updateShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object(C.a)({url:F.a.deleteShopShipmentsInfo,method:"post",data:t,signValidate:!0})}function y(t){return Object(C.a)({url:F.a.getShipmentsList,method:"post",data:t,signValidate:!0})}function x(t){return Object(C.a)({url:F.a.getShipmentsInfo,method:"post",data:t,signValidate:!0})}function k(t){return Object(C.a)({url:F.a.isPrintShipmentsInfo,method:"post",data:t,signValidate:!0})}function S(t){return Object(C.a)({url:F.a.deleteShipmentsInfo,method:"post",data:t,signValidate:!0})}e.k=n,e.j=o,e.x=i,e.d=s,e.i=r,e.h=l,e.v=u,e.s=d,e.c=c,e.m=f,e.l=m,e.e=p,e.b=h,e.w=g,e.t=b,e.u=v,e.q=O,e.p=j,e.a=_,e.y=I,e.g=V,e.o=y,e.n=x,e.r=k,e.f=S;var C=a("yYIz"),F=a("z+6n")},QHNj:function(t,e,a){"use strict";var n=a("FWz8");e.a={data:function(){return{loading:!0,type:this.$route.query.type,id:this.$route.query.id,info:{},typeList:[],pathUrl:"",rules:{name:[{required:!0,message:"此项必填",trigger:"blur"}]}}},methods:{getInfo:function(t){var e=this;n.p({id:t}).then(function(t){e.info=t.body,e.loading=!1})},updateForm:function(){var t=this;this.$refs.form.validate(function(e){e&&(t.loading=!0,n.y(t.info).then(function(e){t.loading=!1,200==e.code&&(t.successMessage("修改成功"),setTimeout(function(){t.routerLink("/shopShipments","list")},1e3))}))})},saveForm:function(t){var e=this;this.$refs.form.validate(function(a){a&&(e.loading=!0,n.a(e.info).then(function(a){e.loading=!1,200==a.code&&(e.successMessage("添加成功"),t?(e.getInfo(e.id),e.pathUrl=""):setTimeout(function(){e.routerLink("/shopShipments","list")},1e3))}))})},restForm:function(){this.$refs.form.resetFields(),this.getInfo(this.id),this.pathUrl=""}},created:function(){this.getInfo(this.id)}}},etgx:function(t,e,a){"use strict";function n(t){a("DsPF")}Object.defineProperty(e,"__esModule",{value:!0});var o=a("QHNj"),i=a("5FjW"),s=a("46Yf"),r=n,l=s(o.a,i.a,!1,r,null,null);e.default=l.exports},joYa:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,"",""])}});