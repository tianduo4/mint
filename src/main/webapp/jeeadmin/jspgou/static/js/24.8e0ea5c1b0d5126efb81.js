webpackJsonp([24],{"5+K1":function(e,t,a){var s=a("j0lS");"string"==typeof s&&(s=[[e.i,s,""]]),s.locals&&(e.exports=s.locals);a("8bSs")("985b3e80",s,!0)},"7p6Q":function(e,t,a){"use strict";(function(e){var s=a("2sCs"),o=a.n(s),l=a("cn+c"),i=a("FVK1"),r=a("Tw8E");t.a={data:function(){var e=this,t=function(e,t,a){if(""===t)a();else{/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(t)?a():a(new Error("请输入正确的邮箱地址"))}},a=function(t,a,s){""===a?s():(""!==e.info.checkPass&&e.$refs.form.validateField("checkPass"),s())},s=function(t,a,s){""===a?s():a!==e.info.password?s(new Error("两次输入密码不一致!")):s()};return{loading:!0,moreInfo:!1,type:this.$route.query.type,id:this.$route.query.id,info:{},rankList:[],userDegreeList:[],degreeIdList:[],incomeDescIdList:[],workSeniorityIdList:[],familyMembersIdList:[],upload:{url:this.$store.state.sys.baseUrl,imageUrl:"",uploading:!1,percent:0,data:{type:"attach",appId:this.$store.state.sys.appId,sessionKey:localStorage.getItem("sessionKey")}},rules:{username:[{required:!0,message:"请输入用户名",trigger:"blur"}],email:[{validator:t,trigger:"blur"},{required:!0,message:"请输入邮箱地址",trigger:"blur"}],password:[{validator:a,trigger:"blur"},{required:!0,message:"请输入密码",trigger:"blur"}],checkPass:[{validator:s,trigger:"blur"},{required:!0,message:"请再次输入密码",trigger:"blur"}]}}},methods:{getInfo:function(e){var t=this;o.a.all([l.g({id:e}),l.f(),Object(r.j)({typeId:1}),Object(r.j)({typeId:5}),Object(r.j)({typeId:3}),Object(r.j)({typeId:4}),Object(r.j)({typeId:2})]).then(o.a.spread(function(e,a,s,o,l,i,r){t.info=e.body,""!=t.upload.imageUrl&&(t.upload.imageUrl=t.upload.url+e.body.avatar),t.rankList=t.creatGroup(a.body),t.userDegreeList=t.creatGroup(s.body),t.degreeIdList=t.creatGroup(o.body),t.incomeDescIdList=t.creatGroup(l.body),t.workSeniorityIdList=t.creatGroup(i.body),t.familyMembersIdList=t.creatGroup(r.body),t.info.password="",t.info.checkPass="",t.loading=!1}))},saveForm:function(e){var t=this;this.$refs.form.validate(function(a){a&&(t.loading=!0,t.aesPwd(),l.b(t.info).then(function(a){t.loading=!1,200==a.code?(t.successMessage("添加成功"),e?(t.moreInfo=!1,t.getInfo(0)):setTimeout(function(){t.routerLink("/userList","list")},1e3)):(t.info.password="",t.info.checkPass="")}).catch(function(e){t.errorMessage("执行异常"),t.loading=!1}))})},handleAvatarSuccess:function(e,t){this.upload.uploading=!1,this.upload.percent=0,200==e.code?(this.upload.imageUrl=URL.createObjectURL(t.raw),this.info.avatar=e.body.url):this.errorMessage("上传失败，请检查文件类型")},handleAvatarProgress:function(e,t,a){this.upload.uploading=!0,this.upload.percent=parseInt(e.percent)},restForm:function(){this.$refs.form.resetFields(),this.getInfo(this.id)},toggleMore:function(){1==this.moreInfo?(this.moreInfo=!1,e(".more-info").find("i").removeClass("el-icon-caret-top").addClass("el-icon-caret-bottom")):(e(".more-info").find("i").removeClass("el-icon-caret-bottom").addClass("el-icon-caret-top"),this.moreInfo=!0)},aesPwd:function(){this.info.password=i.a(this.info.password),this.info.checkPass=i.a(this.info.checkPass)},creatGroup:function(e){var t=[];for(var a in e){var s={value:"",label:""};s.label=e[a].name,s.value=e[a].id,t.push(s)}return t}},watch:{},created:function(){this.getInfo(this.id)}}}).call(t,a("tra3"))},"8jo0":function(e,t,a){"use strict";function s(e){a("5+K1")}Object.defineProperty(t,"__esModule",{value:!0});var o=a("7p6Q"),l=a("xNoO"),i=a("/Xao"),r=s,n=i(o.a,l.a,!1,r,null,null);t.default=n.exports},Tw8E:function(e,t,a){"use strict";function s(e){return Object(_.a)({url:w.a.getDictList,method:"post",data:e,signValidate:!0})}function o(e){return Object(_.a)({url:w.a.getDictInfo,method:"post",data:e,signValidate:!0})}function l(e){return Object(_.a)({url:w.a.addDictInfo,method:"post",data:e,signValidate:!0})}function i(e){return Object(_.a)({url:w.a.updateDictInfo,method:"post",data:e,signValidate:!0})}function r(e){return Object(_.a)({url:w.a.deleteDictInfo,method:"post",data:e,signValidate:!0})}function n(e){return Object(_.a)({url:w.a.priorityDictInfo,method:"post",data:e,signValidate:!0})}function c(e){return Object(_.a)({url:w.a.getDictTypeList,method:"post",data:e,signValidate:!0})}function d(e){return Object(_.a)({url:w.a.getDictTypeInfo,method:"post",data:e,signValidate:!0})}function u(e){return Object(_.a)({url:w.a.addDictTypeInfo,method:"post",data:e,signValidate:!0})}function f(e){return Object(_.a)({url:w.a.updateDictTypeInfo,method:"post",data:e,signValidate:!0})}function m(e){return Object(_.a)({url:w.a.deleteDictTypeInfo,method:"post",data:e,signValidate:!0})}function p(e){return Object(_.a)({url:w.a.getDictTypeAll,method:"post",data:e,signValidate:!0})}function g(e){return Object(_.a)({url:w.a.getDictTypeId,method:"post",data:e,signValidate:!0})}function v(e){return Object(_.a)({url:w.a.getMessageList,method:"post",data:e,signValidate:!0})}function b(e){return Object(_.a)({url:w.a.getReceivemessageList,method:"post",data:e,signValidate:!0})}function h(e){return Object(_.a)({url:w.a.getRecycleMessageList,method:"post",data:e,signValidate:!0})}function y(e){return Object(_.a)({url:w.a.getRemoveMessageList,method:"post",data:e,signValidate:!0})}function x(e){return Object(_.a)({url:w.a.addMessageInfo,method:"post",data:e,signValidate:!0})}function I(e){return Object(_.a)({url:w.a.getMessageInfo,method:"post",data:e,signValidate:!0})}function k(e){return Object(_.a)({url:w.a.deleteMessageList,method:"post",data:e,signValidate:!0})}t.h=s,t.g=o,t.a=l,t.s=i,t.d=r,t.r=n,t.l=c,t.k=d,t.b=u,t.t=f,t.e=m,t.i=p,t.j=g,t.n=v,t.o=b,t.p=h,t.q=y,t.c=x,t.m=I,t.f=k;var _=a("yYIz"),w=a("z+6n")},"cn+c":function(e,t,a){"use strict";function s(e){return Object(m.a)({url:p.a.getUserList,method:"post",data:e,signValidate:!0})}function o(e){return Object(m.a)({url:p.a.getUserInfo,method:"post",data:e,signValidate:!0})}function l(e){return Object(m.a)({url:p.a.addUserInfo,method:"post",data:e,nonceStr:!0,signValidate:!0})}function i(e){return Object(m.a)({url:p.a.updateUserInfo,method:"post",data:e,nonceStr:!0,signValidate:!0})}function r(e){return Object(m.a)({url:p.a.deleteUserInfo,method:"post",data:e,signValidate:!0})}function n(e){return Object(m.a)({url:p.a.getRankList,method:"post",data:e,signValidate:!0})}function c(e){return Object(m.a)({url:p.a.getRankInfo,method:"post",data:e,signValidate:!0})}function d(e){return Object(m.a)({url:p.a.addRankInfo,method:"post",data:e,nonceStr:!0,signValidate:!0})}function u(e){return Object(m.a)({url:p.a.updateRankInfo,method:"post",data:e,nonceStr:!0,signValidate:!0})}function f(e){return Object(m.a)({url:p.a.deleteRankInfo,method:"post",data:e,nonceStr:!0,signValidate:!0})}t.h=s,t.g=o,t.b=l,t.j=i,t.d=r,t.f=n,t.e=c,t.a=d,t.i=u,t.c=f;var m=a("yYIz"),p=a("z+6n")},j0lS:function(e,t,a){t=e.exports=a("BkJT")(!1),t.push([e.i,"",""])},xNoO:function(e,t,a){"use strict";var s=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"forum-module"},[a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[e._v(e._s(e.$route.name))])]),e._v(" "),a("el-form",{ref:"form",staticClass:"table-body",attrs:{"label-width":"160px",model:e.info,rules:e.rules}},[a("el-row",{staticClass:"form-group"},[a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"会员名",prop:"username"}},[a("el-input",{staticClass:"w192",model:{value:e.info.username,callback:function(t){e.$set(e.info,"username",t)},expression:"info.username"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"邮箱",prop:"email"}},[a("el-input",{staticClass:"w192",model:{value:e.info.email,callback:function(t){e.$set(e.info,"email",t)},expression:"info.email"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"密码",prop:"password"}},[a("el-input",{staticClass:"w192",attrs:{type:"password"},model:{value:e.info.password,callback:function(t){e.$set(e.info,"password",t)},expression:"info.password"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"确认密码",prop:"checkPass"}},[a("el-input",{staticClass:"w192",attrs:{type:"password"},model:{value:e.info.checkPass,callback:function(t){e.$set(e.info,"checkPass",t)},expression:"info.checkPass"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"会员组",required:!0}},[a("el-select",{staticClass:"w192",attrs:{placeholder:"请选择"},model:{value:e.info.groupId,callback:function(t){e.$set(e.info,"groupId",t)},expression:"info.groupId"}},e._l(e.rankList,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"积分",required:!0}},[a("el-input",{staticClass:"w192",model:{value:e.info.score,callback:function(t){e.$set(e.info,"score",t)},expression:"info.score"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"是否禁用",required:!0}},[a("el-switch",{attrs:{"active-text":"是","inactive-text":"否"},model:{value:e.info.disabled,callback:function(t){e.$set(e.info,"disabled",t)},expression:"info.disabled"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("div",{staticClass:"more-info",on:{click:function(t){e.toggleMore()}}},[e._v("更多信息"),a("i",{staticClass:"el-icon-caret-bottom"})])])],1),e._v(" "),a("el-row",{directives:[{name:"show",rawName:"v-show",value:e.moreInfo,expression:"moreInfo"}],staticClass:"form-group"},[a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"真实姓名",prop:"realName"}},[a("el-input",{staticClass:"w192",model:{value:e.info.realName,callback:function(t){e.$set(e.info,"realName",t)},expression:"info.realName"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"性别",prop:"gender"}},[a("el-radio",{attrs:{label:!0},model:{value:e.info.gender,callback:function(t){e.$set(e.info,"gender",t)},expression:"info.gender"}},[e._v("男")]),e._v(" "),a("el-radio",{attrs:{label:!1},model:{value:e.info.gender,callback:function(t){e.$set(e.info,"gender",t)},expression:"info.gender"}},[e._v("女")])],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"生日",prop:"birthday"}},[a("el-date-picker",{staticClass:"w192",attrs:{type:"date",placeholder:"选择日期",format:"yyyy 年 MM 月 dd 日","value-format":"yyyy-MM-dd"},model:{value:e.info.birthday,callback:function(t){e.$set(e.info,"birthday",t)},expression:"info.birthday"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"婚姻状况",prop:"marriage"}},[a("el-radio",{attrs:{label:!0},model:{value:e.info.marriage,callback:function(t){e.$set(e.info,"marriage",t)},expression:"info.marriage"}},[e._v("已婚")]),e._v(" "),a("el-radio",{attrs:{label:!1},model:{value:e.info.marriage,callback:function(t){e.$set(e.info,"marriage",t)},expression:"info.marriage"}},[e._v("未婚")])],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"手机"}},[a("el-input",{staticClass:"w192",model:{value:e.info.mobile,callback:function(t){e.$set(e.info,"mobile",t)},expression:"info.mobile"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"电话"}},[a("el-input",{staticClass:"w192",model:{value:e.info.tel,callback:function(t){e.$set(e.info,"tel",t)},expression:"info.tel"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"毕业院校"}},[a("el-input",{staticClass:"w192",model:{value:e.info.schoolTag,callback:function(t){e.$set(e.info,"schoolTag",t)},expression:"info.schoolTag"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"毕业时间"}},[a("el-date-picker",{staticClass:"w192",attrs:{type:"date",placeholder:"选择日期",format:"yyyy 年 MM 月 dd 日","value-format":"yyyy-MM-dd"},model:{value:e.info.schoolTagDate,callback:function(t){e.$set(e.info,"schoolTagDate",t)},expression:"info.schoolTagDate"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"是否有车",required:!0}},[a("el-radio",{attrs:{label:!0},model:{value:e.info.isCar,callback:function(t){e.$set(e.info,"isCar",t)},expression:"info.isCar"}},[e._v("是")]),e._v(" "),a("el-radio",{attrs:{label:!1},model:{value:e.info.isCar,callback:function(t){e.$set(e.info,"isCar",t)},expression:"info.isCar"}},[e._v("否")])],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"职位"}},[a("el-input",{staticClass:"w192",model:{value:e.info.position,callback:function(t){e.$set(e.info,"position",t)},expression:"info.position"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"身份类型",required:!0}},[a("el-select",{staticClass:"w192",attrs:{placeholder:"请选择"},model:{value:e.info.userDegreeId,callback:function(t){e.$set(e.info,"userDegreeId",t)},expression:"info.userDegreeId"}},e._l(e.userDegreeList,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"教育程度类型",required:!0}},[a("el-select",{staticClass:"w192",attrs:{placeholder:"请选择"},model:{value:e.info.degreeId,callback:function(t){e.$set(e.info,"degreeId",t)},expression:"info.degreeId"}},e._l(e.degreeIdList,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"收入状况类型",required:!0}},[a("el-select",{staticClass:"w192",attrs:{placeholder:"请选择"},model:{value:e.info.incomeDescId,callback:function(t){e.$set(e.info,"incomeDescId",t)},expression:"info.incomeDescId"}},e._l(e.incomeDescIdList,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"工作年限类型",required:!0}},[a("el-select",{staticClass:"w192",attrs:{placeholder:"请选择"},model:{value:e.info.workSeniorityId,callback:function(t){e.$set(e.info,"workSeniorityId",t)},expression:"info.workSeniorityId"}},e._l(e.workSeniorityIdList,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"家庭成员类型",required:!0}},[a("el-select",{staticClass:"w192",attrs:{placeholder:"请选择"},model:{value:e.info.familyMembersId,callback:function(t){e.$set(e.info,"familyMembersId",t)},expression:"info.familyMembersId"}},e._l(e.familyMembersIdList,function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"公司名称"}},[a("el-input",{staticClass:"w192",model:{value:e.info.company,callback:function(t){e.$set(e.info,"company",t)},expression:"info.company"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"头像"}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{action:this.$store.state.sys.upLoadUrl,name:"uploadFile",data:e.upload.data,"show-file-list":!1,"on-success":e.handleAvatarSuccess,"on-progress":e.handleAvatarProgress}},[e.upload.uploading?a("div",{staticClass:"uploading"},[a("el-progress",{attrs:{type:"circle",percentage:e.upload.percent}})],1):e._e(),e._v(" "),e.upload.imageUrl?a("div",{staticClass:"upload-hover-box"},[a("img",{staticClass:"avatar",attrs:{src:e.upload.imageUrl}}),e._v(" "),a("span",{staticClass:"upload-cover"},[e._v("点击修改头像")])]):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"})])],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"住址"}},[a("el-input",{staticClass:"w192",model:{value:e.info.address,callback:function(t){e.$set(e.info,"address",t)},expression:"info.address"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"最喜欢的品牌"}},[a("el-input",{staticClass:"w192",attrs:{type:"textarea"},model:{value:e.info.favoriteBrand,callback:function(t){e.$set(e.info,"favoriteBrand",t)},expression:"info.favoriteBrand"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"最喜欢的明星"}},[a("el-input",{staticClass:"w192",attrs:{type:"textarea"},model:{value:e.info.favoriteStar,callback:function(t){e.$set(e.info,"favoriteStar",t)},expression:"info.favoriteStar"}})],1)],1),e._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"最喜欢的电影"}},[a("el-input",{staticClass:"w192",attrs:{type:"textarea"},model:{value:e.info.favoriteMovie,callback:function(t){e.$set(e.info,"favoriteMovie",t)},expression:"info.favoriteMovie"}})],1)],1)],1)],1),e._v(" "),a("div",{staticClass:"form-bottom-bar"},["save"==e.type?a("el-button",{attrs:{type:"warning"},on:{click:function(t){e.saveForm(!0)}}},[e._v("提交并继续添加")]):e._e(),e._v(" "),"save"==e.type?a("el-button",{attrs:{type:"primary"},on:{click:function(t){e.saveForm(!1)}}},[e._v("添加")]):e._e(),e._v(" "),a("el-button",{attrs:{type:"info"},on:{click:function(t){e.restForm()}}},[e._v("重置")]),e._v(" "),"save"==e.type?a("div",{staticClass:"help-info"},[e._v("选择“提交并继续添加”按钮会停留在添加页面；选择“添加”按钮后将跳转到对应的列表页。")]):e._e()],1)],1)},o=[],l={render:s,staticRenderFns:o};t.a=l}});