webpackJsonp([87],{"+uvE":function(t,e,a){"use strict";(function(t){var n=a("wc4o");e.a={data:function(){return{loading:!0,ids:"",disabled:!0,items:[],ModelList:[],url:"http://demo4.jeecms.com",params:{pageNo:1,pageSize:10,ctgId:"",productName:"",brandName:"",status:1,isRecommend:"",isSpecial:"",isHotsale:"",isNewProduct:""},pagination:{total:0,currentPage:1,changePageSize:localStorage.getItem("PageSize")},props:{label:"name",children:"zones",isLeaf:"isChild",id:"id"}}},methods:{ansyTree:function(t,e){if(0===t.level)return e([{name:"所有分类",id:0}]);t.level>0&&n.t({pid:t.data.id}).then(function(t){var a=[];for(var n in t.body){var i={};i.id=t.body[n].id,i.isChild=!t.body[n].isChild,i.name=t.body[n].name,a.push(i)}return e(a)})},editProduct:function(t){var e={type:"update",categoryId:t.categoryId,categoryName:t.categoryName,id:t.id,typeId:t.typeId};this.$router.push({path:"/productList/update",query:e})},selectList:function(t,e,a){this.loading=!0;var n=0==t.id?"":t.id;this.params.ctgId=n,this.getItems()},getItems:function(){var t=this;n.G(this.params).then(function(e){t.pagination.total=e.totalCount,t.items=e.body,t.loading=!1})},deleteItems:function(t){var e=this;this.$confirm("确定要删除吗?","警告",{type:"error"}).then(function(a){n.j({ids:t}).then(function(t){200==t.code&&e.successMessage("删除成功"),e.getItems()})}).catch(function(t){})},query:function(){this.loading=!0,this.getItems()},checkIds:function(t){for(var e=[],a=0;a<t.length;a++)e.push(t[a].id);0==e.length?(this.ids="",this.disabled=!0):(this.ids=e.join(","),this.disabled=!1)},reflash:function(){window.location.reload()},creatGroup:function(t){var e=[];for(var a in t){var n={value:"",label:""};n.label=t[a].name,n.value=t[a].id,e.push(n)}return e},getPage:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},getSize:function(t){this.loading=!0,this.params.pageNo=t,this.getItems()},changeSize:function(t){var e=t.target.value;e<1?(localStorage.setItem("PageSize",15),e=15):localStorage.setItem("PageSize",e),this.loading=!0,this.params.pageSize=parseInt(e),this.params.pageNo=1,this.pagination.currentPage=1,this.getItems()},getLocalPage:function(){var t=localStorage.getItem("PageSize");null!=t?this.params.pageSize=parseInt(t):this.changePageSize=20,this.getItems()}},created:function(){this.getLocalPage(),this.$store.dispatch("setCollapse"),t(".aside").toggleClass("aside-collapse"),t("body").toggleClass("mini")}}}).call(e,a("tra3"))},HkUX:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"forum-module"},[t._m(0),t._v(" "),a("div",{staticClass:"tree-layout"},[a("div",{staticClass:"tree"},[a("div",{staticClass:"reflash",on:{click:function(e){t.reflash()}}},[a("span",{staticClass:"el-icon-refresh"}),t._v(" 刷新")]),t._v(" "),a("el-tree",{attrs:{props:t.props,load:t.ansyTree,lazy:"",indent:16,"node-key":"id","default-expanded-keys":[0]},on:{"node-click":t.selectList}})],1),t._v(" "),a("div",{staticClass:"tree-list"},[a("div",{staticClass:"table-top-bar clearfix"},[a("div",{staticClass:"query-inline-box flex-between"},[a("div",[a("el-select",{staticStyle:{width:"85px","margin-right":"11px"},on:{change:t.query},model:{value:t.params.status,callback:function(e){t.$set(t.params,"status",e)},expression:"params.status"}},[a("el-option",{attrs:{label:"在售",value:1}}),t._v(" "),a("el-option",{attrs:{label:"下架",value:2}})],1),t._v(" "),a("el-checkbox-button",{staticClass:"border-left",attrs:{"true-label":"true","false-label":" "},on:{change:t.query},model:{value:t.params.isRecommend,callback:function(e){t.$set(t.params,"isRecommend",e)},expression:"params.isRecommend"}},[t._v("推荐")]),t._v(" "),a("el-checkbox-button",{attrs:{"true-label":"true","false-label":" "},on:{change:t.query},model:{value:t.params.isSpecial,callback:function(e){t.$set(t.params,"isSpecial",e)},expression:"params.isSpecial"}},[t._v("特价")]),t._v(" "),a("el-checkbox-button",{attrs:{"true-label":"true","false-label":" "},on:{change:t.query},model:{value:t.params.isHotsale,callback:function(e){t.$set(t.params,"isHotsale",e)},expression:"params.isHotsale"}},[t._v("热卖")]),t._v(" "),a("el-checkbox-button",{attrs:{"true-label":"true","false-label":" "},on:{change:t.query},model:{value:t.params.isNewProduct,callback:function(e){t.$set(t.params,"isNewProduct",e)},expression:"params.isNewProduct"}},[t._v("新品")])],1),t._v(" "),a("div",[a("div",{staticClass:"query-inline-group",staticStyle:{"margin-left":"55px"}},[a("label",{attrs:{for:""}},[t._v("商品名称:")]),t._v(" "),a("el-input",{staticClass:"w160",nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.query(e)}},model:{value:t.params.productName,callback:function(e){t.$set(t.params,"productName",e)},expression:"params.productName"}})],1),t._v(" "),a("div",{staticClass:"query-inline-group"},[a("label",{attrs:{for:""}},[t._v("品牌:")]),t._v(" "),a("el-input",{staticClass:"w160",nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.query(e)}},model:{value:t.params.brandName,callback:function(e){t.$set(t.params,"brandName",e)},expression:"params.brandName"}}),t._v(" "),a("el-button",{staticClass:"ml16 query",on:{click:function(e){t.query()}}},[t._v("查询")])],1)])])]),t._v(" "),a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}]},[a("el-table",{staticStyle:{width:"100%"},attrs:{data:t.items,stripe:""},on:{"selection-change":t.checkIds}},[a("el-table-column",{attrs:{type:"selection",align:"center",width:68}}),t._v(" "),a("el-table-column",{attrs:{label:"图片",prop:"coverImg",align:"center",width:"80"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("img",{staticClass:"img-50",staticStyle:{"margin-right":"20px"},attrs:{src:t.url+e.row.coverImg}})]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"商品名称",align:"center",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("div",{staticClass:"text-left"},[a("a",{staticClass:"link-color",attrs:{href:t.url+e.row.url,target:"_blank"}},[t._v(t._s(e.row.name))])])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"分类",prop:"categoryName",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"品牌",prop:"brandName",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"价格",prop:"salePrice",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"库存",prop:"stockCount",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("a",{staticClass:"t-edit",attrs:{title:"修改"},on:{click:function(a){t.editProduct(e.row)}}}),t._v(" "),a("a",{staticClass:"t-del",attrs:{title:"删除"},on:{click:function(a){t.deleteItems(e.row.id)}}})]}}])})],1)],1),t._v(" "),a("div",{staticClass:"table-bottom-bar"},[a("div",{staticClass:"pull-left"},[a("el-button",{attrs:{disabled:t.disabled},on:{click:function(e){t.deleteItems(t.ids)}}},[t._v("批量删除")]),t._v(" "),a("span",{staticClass:"ml-48"},[t._v("每页显示\n                    "),a("el-input",{staticClass:"input-sm",attrs:{type:"number",min:"1",max:"50"},on:{blur:t.changeSize},nativeOn:{keyup:function(e){if(!("button"in e)&&t._k(e.keyCode,"enter",13,e.key))return null;t.changeSize(e)}},model:{value:t.pagination.changePageSize,callback:function(e){t.$set(t.pagination,"changePageSize",e)},expression:"pagination.changePageSize"}}),t._v("\n                     条,输入后按回车\n                     ")],1)],1),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-pagination",{attrs:{layout:"total, prev, pager, next",total:t.pagination.total,"page-size":t.params.pageSize,"current-page":t.pagination.currentPage},on:{"update:currentPage":function(e){t.$set(t.pagination,"currentPage",e)},"current-change":t.getPage,"size-change":t.getSize}})],1)])])])])},i=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v("分类列表")])])}],o={render:n,staticRenderFns:i};e.a=o},ULy3:function(t,e,a){var n=a("hQFy");"string"==typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);a("8bSs")("2378f2d8",n,!0)},hQFy:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,".img-50[data-v-76b0cee4]{width:56px;height:56px;vertical-align:middle;margin:6px 0}.tree-list[data-v-76b0cee4]{margin-left:200px}",""])},wc4o:function(t,e,a){"use strict";function n(t){return Object(Y.a)({url:K.a.getProductList,method:"post",data:t,nonceStr:!0,signValidate:!0})}function i(t){return Object(Y.a)({url:K.a.getProductInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function o(t){return Object(Y.a)({url:K.a.getBrandListByCategoryId,method:"post",data:t,nonceStr:!0,signValidate:!0})}function r(t){return Object(Y.a)({url:K.a.addProductList,method:"post",data:t,nonceStr:!0,signValidate:!0})}function s(t){return Object(Y.a)({url:K.a.updateProductList,method:"post",data:t,nonceStr:!0,signValidate:!0})}function l(t){return Object(Y.a)({url:K.a.deleteProductList,method:"post",data:t,nonceStr:!0,signValidate:!0})}function c(t){return Object(Y.a)({url:K.a.getCommodityCategoryTree,method:"post",data:t,signValidate:!0})}function d(t){return Object(Y.a)({url:K.a.getCommodityCategoryList,method:"post",data:t,signValidate:!0})}function u(t){return Object(Y.a)({url:K.a.getCommodityCategoryInfo,method:"post",data:t,signValidate:!0})}function p(t){return Object(Y.a)({url:K.a.getHotList,method:"post",data:t,signValidate:!0})}function g(t){return Object(Y.a)({url:K.a.getSaleChatList,method:"post",data:t,signValidate:!0})}function m(t){return Object(Y.a)({url:K.a.addCommodityCategoryInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function f(t){return Object(Y.a)({url:K.a.updateCommodityCategoryInfo,method:"post",data:t,nonceStr:!0,signValidate:!0})}function h(t){return Object(Y.a)({url:K.a.getCommodityTopCategory,method:"post",data:t,signValidate:!0})}function b(t){return Object(Y.a)({url:K.a.getCommodityTemplate,method:"post",data:t,signValidate:!0})}function v(t){return Object(Y.a)({url:K.a.getCommodityBrand,method:"post",nonceStr:!0,data:t,signValidate:!0})}function y(t){return Object(Y.a)({url:K.a.getCommodityStandardType,method:"post",nonceStr:!0,data:t,signValidate:!0})}function S(t){return Object(Y.a)({url:K.a.getBrandList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function C(t){return Object(Y.a)({url:K.a.getBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function I(t){return Object(Y.a)({url:K.a.getBrandTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function _(t){return Object(Y.a)({url:K.a.addBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function j(t){return Object(Y.a)({url:K.a.updateBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function O(t){return Object(Y.a)({url:K.a.deleteBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function V(t){return Object(Y.a)({url:K.a.priorityBrandInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function k(t){return Object(Y.a)({url:K.a.deleteCommodityCategoryInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function P(t){return Object(Y.a)({url:K.a.priorityCommodityCategoryInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function x(t){return Object(Y.a)({url:K.a.getStandardList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function w(t){return Object(Y.a)({url:K.a.getStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function z(t){return Object(Y.a)({url:K.a.addStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function L(t){return Object(Y.a)({url:K.a.updateStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function N(t){return Object(Y.a)({url:K.a.deleteStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function q(t){return Object(Y.a)({url:K.a.priorityStandardInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function $(t){return Object(Y.a)({url:K.a.getTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function T(t){return Object(Y.a)({url:K.a.addTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function B(t){return Object(Y.a)({url:K.a.updateTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function H(t){return Object(Y.a)({url:K.a.deleteTypeInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function M(t){return Object(Y.a)({url:K.a.getModelList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function R(t){return Object(Y.a)({url:K.a.getModelListAll,method:"post",nonceStr:!0,data:t,signValidate:!0})}function D(t){return Object(Y.a)({url:K.a.getModelInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function E(t){return Object(Y.a)({url:K.a.getConsultList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function U(t){return Object(Y.a)({url:K.a.getConsultInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function F(t){return Object(Y.a)({url:K.a.updateConsultInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function G(t){return Object(Y.a)({url:K.a.deleteConsultInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function J(t){return Object(Y.a)({url:K.a.getDiscussList,method:"post",nonceStr:!0,data:t,signValidate:!0})}function Q(t){return Object(Y.a)({url:K.a.getDiscussInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function A(t){return Object(Y.a)({url:K.a.updateDiscussInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}function X(t){return Object(Y.a)({url:K.a.deleteDiscussInfo,method:"post",nonceStr:!0,data:t,signValidate:!0})}e.G=n,e.F=i,e.o=o,e.c=r,e.S=s,e.j=l,e.t=c,e.s=d,e.r=u,e.B=p,e.H=g,e.b=m,e.P=f,e.w=h,e.v=b,e.q=v,e.u=y,e.n=S,e.m=C,e.p=I,e.a=_,e.O=j,e.f=O,e.L=V,e.g=k,e.M=P,e.J=x,e.I=w,e.d=z,e.T=L,e.k=N,e.N=q,e.K=$,e.e=T,e.U=B,e.l=H,e.D=M,e.E=R,e.C=D,e.y=E,e.x=U,e.Q=F,e.h=G,e.A=J,e.z=Q,e.R=A,e.i=X;var Y=a("yYIz"),K=a("z+6n")},y7P0:function(t,e,a){"use strict";function n(t){a("ULy3")}Object.defineProperty(e,"__esModule",{value:!0});var i=a("+uvE"),o=a("HkUX"),r=a("46Yf"),s=n,l=r(i.a,o.a,!1,s,"data-v-76b0cee4",null);e.default=l.exports}});