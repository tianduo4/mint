webpackJsonp([25],{G0Wd:function(t,e,a){"use strict";var n=a("Tw8E");a("vMJZ"),a("cn+c");e.a={data:function(){return{loading:!0,type:this.$route.query.type,id:this.$route.query.id,info:{},typeList:[],pathUrl:"",rules:{}}},methods:{getInfo:function(t){var e=this;n.m({id:t}).then(function(t){e.info=t.body}),this.loading=!1}},created:function(){this.getInfo(this.id)}}},L13w:function(t,e,a){var n=a("pTYL");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a("8bSs")("794886ec",n,!0)},Tw8E:function(t,e,a){"use strict";function n(t){return Object(y.a)({url:_.a.getDictList,method:"post",data:t,signValidate:!0})}function i(t){return Object(y.a)({url:_.a.getDictInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object(y.a)({url:_.a.addDictInfo,method:"post",data:t,signValidate:!0})}function s(t){return Object(y.a)({url:_.a.updateDictInfo,method:"post",data:t,signValidate:!0})}function r(t){return Object(y.a)({url:_.a.deleteDictInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object(y.a)({url:_.a.priorityDictInfo,method:"post",data:t,signValidate:!0})}function u(t){return Object(y.a)({url:_.a.getDictTypeList,method:"post",data:t,signValidate:!0})}function l(t){return Object(y.a)({url:_.a.getDictTypeInfo,method:"post",data:t,signValidate:!0})}function c(t){return Object(y.a)({url:_.a.addDictTypeInfo,method:"post",data:t,signValidate:!0})}function f(t){return Object(y.a)({url:_.a.updateDictTypeInfo,method:"post",data:t,signValidate:!0})}function g(t){return Object(y.a)({url:_.a.deleteDictTypeInfo,method:"post",data:t,signValidate:!0})}function m(t){return Object(y.a)({url:_.a.getDictTypeAll,method:"post",data:t,signValidate:!0})}function p(t){return Object(y.a)({url:_.a.getDictTypeId,method:"post",data:t,signValidate:!0})}function h(t){return Object(y.a)({url:_.a.getMessageList,method:"post",data:t,signValidate:!0})}function b(t){return Object(y.a)({url:_.a.getReceivemessageList,method:"post",data:t,signValidate:!0})}function j(t){return Object(y.a)({url:_.a.getRecycleMessageList,method:"post",data:t,signValidate:!0})}function O(t){return Object(y.a)({url:_.a.getRemoveMessageList,method:"post",data:t,signValidate:!0})}function v(t){return Object(y.a)({url:_.a.addMessageInfo,method:"post",data:t,signValidate:!0})}function V(t){return Object(y.a)({url:_.a.getMessageInfo,method:"post",data:t,signValidate:!0})}function I(t){return Object(y.a)({url:_.a.deleteMessageList,method:"post",data:t,signValidate:!0})}e.h=n,e.g=i,e.a=o,e.s=s,e.d=r,e.r=d,e.l=u,e.k=l,e.b=c,e.t=f,e.e=g,e.i=m,e.j=p,e.n=h,e.o=b,e.p=j,e.q=O,e.c=v,e.m=V,e.f=I;var y=a("yYIz"),_=a("z+6n")},UkOR:function(t,e,a){"use strict";function n(t){a("L13w")}Object.defineProperty(e,"__esModule",{value:!0});var i=a("G0Wd"),o=a("WyqC"),s=a("46Yf"),r=n,d=s(i.a,o.a,!1,r,null,null);e.default=d.exports},WyqC:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v(t._s(t.$route.name))]),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-button",{staticClass:"back",on:{click:function(e){t.routerLink("/messageList","list")}}},[t._v("返回列表")])],1)]),t._v(" "),a("el-form",{ref:"form",staticClass:"table-body",attrs:{"label-width":"160px",model:t.info,rules:t.rules}},[a("el-row",{staticClass:"form-group"},[a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"标题"}},[a("span",[t._v(t._s(t.info.msgTitle))])])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"收件人"}},[a("span",[t._v(t._s(t.info.receiverUserName))])])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"发送时间"}},[a("span",[t._v(t._s(t.info.sendTime))])])],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"内容"}},[a("span",[t._v(t._s(t.info.msgContent))])])],1)],1)],1)],1)},i=[],o={render:n,staticRenderFns:i};e.a=o},"cn+c":function(t,e,a){"use strict";function n(t){return Object(g.a)({url:m.a.getUserList,method:"post",data:t,signValidate:!0})}function i(t){return Object(g.a)({url:m.a.getUserInfo,method:"post",data:t,signValidate:!0})}function o(t){return Object(g.a)({url:m.a.addUserInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function s(t){return Object(g.a)({url:m.a.updateUserInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function r(t){return Object(g.a)({url:m.a.deleteUserInfo,method:"post",data:t,signValidate:!0})}function d(t){return Object(g.a)({url:m.a.getRankList,method:"post",data:t,signValidate:!0})}function u(t){return Object(g.a)({url:m.a.getRankInfo,method:"post",data:t,signValidate:!0})}function l(t){return Object(g.a)({url:m.a.addRankInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object(g.a)({url:m.a.updateRankInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function f(t){return Object(g.a)({url:m.a.deleteRankInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}e.h=n,e.g=i,e.b=o,e.j=s,e.d=r,e.f=d,e.e=u,e.a=l,e.i=c,e.c=f;var g=a("yYIz"),m=a("z+6n")},pTYL:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,"",""])}});