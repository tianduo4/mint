webpackJsonp([6],{"/UuH":function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"gou-upload",staticStyle:{position:"relative"}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{action:this.$store.state.sys.upLoadUrl,name:"uploadFile",data:t.upload.data,"show-file-list":!1,"on-success":t.handleAvatarSuccess,"on-progress":t.handleAvatarProgress}},[t.upload.uploading?a("div",{staticClass:"uploading"},[a("el-progress",{attrs:{type:"circle",percentage:t.upload.percent}})],1):t._e(),t._v(" "),t.upload.imageUrl?a("div",{staticClass:"upload-hover-box"},[a("img",{staticClass:"avatar",attrs:{src:t.upload.imageUrl}}),t._v(" "),t.upload.imageUrl?a("span",{staticClass:"upload-cover"},[t._v("点击修改图片")]):t._e()]):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"})]),t._v(" "),t.upload.imageUrl?a("i",{staticClass:"iconfont bbs-delete pos-del",attrs:{title:"删除"},on:{click:function(e){e.preventDefault(),t.delImg(e)}}}):t._e()],1)},n=[],o={render:i,staticRenderFns:n};e.a=o},"9W04":function(t,e,a){var i=a("VEko");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a("8bSs")("a37a6bf4",i,!0)},NLbN:function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement;return(t._self._c||e)("script",{attrs:{id:t.randomId,name:"content",type:"text/plain"}})},n=[],o={render:i,staticRenderFns:n};e.a=o},PHrY:function(t,e,a){"use strict";function i(t){return Object(P.a)({url:"/api/admin/resource/tree",method:"post",data:t})}function n(t){return Object(P.a)({url:"/api/admin/resource/list",method:"post",data:t})}function o(t){return Object(P.a)({url:"/api/admin/template/list",method:"post",data:t})}function s(t){return Object(P.a)({url:"/api/admin/resource/get",method:"post",data:t})}function r(t){return Object(P.a)({url:"/api/admin/template/get",method:"post",data:t})}function d(t){return Object(P.a)({url:"/api/admin/resource/delete",method:"post",signValidate:!0,data:t})}function l(t){return Object(P.a)({url:"/api/admin/template/delete",method:"post",signValidate:!0,data:t})}function u(t){return Object(P.a)({url:"api/admin/resource/dir_save",method:"post",signValidate:!0,data:t})}function c(t){return Object(P.a)({url:"api/admin/template/dir_save",method:"post",signValidate:!0,data:t})}function p(t){return Object(P.a)({url:"/api/admin/resource/rename",method:"post",signValidate:!0,data:t})}function f(t){return Object(P.a)({url:"/api/admin/template/rename",method:"post",signValidate:!0,data:t})}function m(t){return Object(P.a)({url:"/api/admin/template/update",method:"post",signValidate:!0,data:t})}function h(t){return Object(P.a)({url:"/api/admin/resource/save",method:"post",signValidate:!0,data:t})}function g(t){return Object(P.a)({url:"/api/admin/template/save",method:"post",signValidate:!0,data:t})}function v(t){return Object(P.a)({url:"/api/admin/template/tree",method:"post",data:t})}function b(t){return Object(P.a)({url:"/api/admin/template/getSolutions",method:"post",data:t})}function y(t){return Object(P.a)({url:"/api/admin/template/solutionupdate",method:"post",signValidate:!0,data:t})}function I(t){return Object(P.a)({url:A.a.getChannelTree,method:"post",signValidate:!0,data:t})}function _(t){return Object(P.a)({url:A.a.getChannelList,method:"post",signValidate:!0,data:t})}function j(t){return Object(P.a)({url:A.a.getChannelInfo,method:"post",signValidate:!0,data:t})}function C(t){return Object(P.a)({url:A.a.addChannelInfo,method:"post",signValidate:!0,data:t})}function x(t){return Object(P.a)({url:A.a.updateChannelInfo,method:"post",signValidate:!0,data:t})}function E(t){return Object(P.a)({url:A.a.deleteChannelInfo,method:"post",signValidate:!0,data:t})}function U(t){return Object(P.a)({url:A.a.priorityChannelInfo,method:"post",signValidate:!0,data:t})}function w(t){return Object(P.a)({url:A.a.getChannelTpl,method:"post",signValidate:!0,data:t})}function O(t){return Object(P.a)({url:A.a.getParentChannelList,method:"post",signValidate:!0,data:t})}function V(t){return Object(P.a)({url:A.a.getArticleList,method:"post",signValidate:!0,data:t})}function S(t){return Object(P.a)({url:A.a.getArticleInfo,method:"post",signValidate:!0,data:t})}function $(t){return Object(P.a)({url:A.a.addArticleInfo,method:"post",signValidate:!0,data:t})}function k(t){return Object(P.a)({url:A.a.updateArticleInfo,method:"post",signValidate:!0,data:t})}function L(t){return Object(P.a)({url:A.a.deleteArticleInfo,method:"post",signValidate:!0,data:t})}function T(t){return Object(P.a)({url:A.a.getArticleChannelList,method:"post",signValidate:!0,data:t})}e.u=i,e.t=n,e.x=o,e.s=s,e.w=r,e.i=d,e.j=l,e.e=u,e.f=c,e.A=p,e.C=f,e.F=m,e.c=h,e.d=g,e.y=v,e.v=b,e.B=y,e.q=I,e.o=_,e.n=j,e.b=C,e.E=x,e.h=E,e.z=U,e.p=w,e.r=O,e.m=V,e.l=S,e.a=$,e.D=k,e.g=L,e.k=T;var P=a("yYIz"),A=a("z+6n"),M=a("DEjr");a.n(M)},QCMo:function(t,e,a){"use strict";e.a={name:"imageUpload",props:{path:{type:String,default:""},index:{type:Number,default:0}},data:function(){return{upload:{url:"http://demo4.jeecms.com",imageUrl:"",uploading:!1,percent:0,data:{type:"image",appId:this.$store.state.sys.appId,sessionKey:localStorage.getItem("sessionKey")}}}},methods:{handleAvatarSuccess:function(t,e){this.upload.uploading=!1,this.upload.percent=0,200==t.code?(this.upload.imageUrl=URL.createObjectURL(e.raw),this.$emit("getPath",t.body.url,this.index)):209==t.code?(this.errorMessage("无上传权限"),this.$emit("getPath","",this.index)):(this.errorMessage("上传失败，请检查文件类型"),this.$emit("getPath","",this.index))},handleAvatarProgress:function(t,e,a){this.upload.uploading=!0,this.upload.percent=parseInt(t.percent)},delImg:function(){this.upload.imageUrl="",this.$emit("getPath","",this.index)}},created:function(){""==this.path||null==this.path?this.upload.imageUrl="":this.path.indexOf("http://")>=0?this.upload.imageUrl=this.path:this.upload.imageUrl=this.upload.url+this.path},watch:{path:function(t,e){""==this.path||null==this.path?this.upload.imageUrl="":this.upload.imageUrl=this.upload.url+this.path}}}},RuDe:function(t,e,a){"use strict";function i(t){a("9W04")}Object.defineProperty(e,"__esModule",{value:!0});var n=a("TeJ4"),o=a("gMDH"),s=a("46Yf"),r=i,d=s(n.a,o.a,!1,r,null,null);e.default=d.exports},TeJ4:function(t,e,a){"use strict";var i=a("b1Wg"),n=a("Uwrw"),o=a("PHrY");e.a={components:{VueUEditor:i.a,imageUpload:n.a},data:function(){return{loading:!0,type:this.$route.query.type,id:this.$route.query.id,info:{},categoryIdList:[],UE:"",imagePath:"",topList:[],rules:{title:[{required:!0,message:"此项必填",trigger:"blur"}],channelId:[{required:!0,type:"number",message:"此项必填",trigger:"change"}],priority:[{required:!0,message:"请输入排序",type:"number",trigger:"blur"}]}}},methods:{getInfo:function(t){var e=this;"save"==this.type&&(t=0),o.l({id:t}).then(function(t){e.info=t.body,e.info.channelId=e.id,e.loading=!1,e.UE.setContent(e.info.content),o.k().then(function(t){e.topList=t.body})})},updateForm:function(){var t=this;this.$refs.form.validate(function(e){e&&(t.loading=!0,t.info.content=t.UE.getContent(),o.D(t.info).then(function(e){t.loading=!1,200==e.code&&(t.successMessage("修改成功"),setTimeout(function(){t.routerLink("/articleList","list")},1e3))}))})},saveForm:function(t){var e=this;this.$refs.form.validate(function(a){a&&(e.loading=!0,e.info.content=e.UE.getContent(),0==e.info.channelId&&(e.info.channelId=""),o.a(e.info).then(function(a){e.loading=!1,200==a.code&&(e.successMessage("添加成功"),t?(e.imagePath="",e.getInfo(e.id)):setTimeout(function(){e.routerLink("/articleList","list")},1e3))}))})},restForm:function(){this.$refs.form.resetFields(),"save"==this.type?this.getInfo(0):this.getInfo(this.id)},getPath:function(t){this.info.logoPath=t},editorReady:function(t){this.UE=t}},created:function(){this.getInfo(this.id)}}},Uwrw:function(t,e,a){"use strict";function i(t){a("iPum")}var n=a("QCMo"),o=a("/UuH"),s=a("46Yf"),r=i,d=s(n.a,o.a,!1,r,"data-v-1161f364",null);e.a=d.exports},VEko:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,"",""])},b1Wg:function(t,e,a){"use strict";var i=a("lr5Q"),n=a("NLbN"),o=a("46Yf"),s=o(i.a,n.a,!1,null,null,null);e.a=s.exports},gMDH:function(t,e,a){"use strict";var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"forum-module"},[a("div",{staticClass:"forum-header"},[a("span",{staticClass:"forum-name"},[t._v(t._s(t.$route.name))]),t._v(" "),a("div",{staticClass:"pull-right"},[a("el-button",{staticClass:"back",on:{click:function(e){t.routerLink("/articleList","list")}}},[t._v("返回列表")])],1)]),t._v(" "),a("el-form",{ref:"form",staticClass:"table-body",attrs:{"label-width":"160px",model:t.info,rules:t.rules}},[a("el-row",{staticClass:"form-group"},[a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"栏目",prop:"channelId"}},[a("el-select",{staticClass:"w192",attrs:{filterable:"",placeholder:"请选择"},model:{value:t.info.channelId,callback:function(e){t.$set(t.info,"channelId",e)},expression:"info.channelId"}},[a("el-option",{attrs:{label:"顶级分类",value:0}}),t._v(" "),t._l(t.topList,function(e){return a("el-option",{key:e.id,attrs:{label:e.name,value:e.id}},[e.leval>1?a("span",{style:{paddingLeft:10*e.leval+"px"}},[t._v(">"+t._s(e.name))]):a("span",{style:{paddingLeft:10*e.leval+"px"}},[t._v(t._s(e.name))])])})],2)],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"标题",prop:"title"}},[a("el-input",{staticClass:"w192",model:{value:t.info.title,callback:function(e){t.$set(t.info,"title",e)},expression:"info.title"}})],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:12,lg:12}},[a("el-form-item",{attrs:{label:"是否禁用"}},[a("el-radio",{attrs:{label:!0},model:{value:t.info.disabled,callback:function(e){t.$set(t.info,"disabled",e)},expression:"info.disabled"}},[t._v("是")]),t._v(" "),a("el-radio",{attrs:{label:!1},model:{value:t.info.disabled,callback:function(e){t.$set(t.info,"disabled",e)},expression:"info.disabled"}},[t._v("否")])],1)],1),t._v(" "),a("el-col",{attrs:{xs:24,sm:24,md:24,lg:24}},[a("el-form-item",{attrs:{label:"内容"}},[a("VueUEditor",{on:{ready:t.editorReady}})],1)],1)],1)],1),t._v(" "),a("div",{staticClass:"form-bottom-bar"},["save"==t.type?a("el-button",{attrs:{type:"warning"},on:{click:function(e){t.saveForm(!0)}}},[t._v("提交并继续添加")]):t._e(),t._v(" "),"save"==t.type?a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.saveForm(!1)}}},[t._v("添加")]):t._e(),t._v(" "),"update"==t.type?a("el-button",{attrs:{type:"primary"},on:{click:function(e){t.updateForm()}}},[t._v("修改")]):t._e(),t._v(" "),a("el-button",{attrs:{type:"info"},on:{click:function(e){t.restForm()}}},[t._v("重置")]),t._v(" "),"save"==t.type?a("div",{staticClass:"help-info"},[t._v("选择“提交并继续添加”按钮会停留在添加页面；选择“添加”按钮后将跳转到对应的列表页。")]):t._e()],1)],1)},n=[],o={render:i,staticRenderFns:n};e.a=o},iPum:function(t,e,a){var i=a("uJq0");"string"==typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);a("8bSs")("99408900",i,!0)},lr5Q:function(t,e,a){"use strict";e.a={name:"VueUEditor",props:{ueditorPath:{type:String,default:"/static/ueditor/"},defaultMsg:{type:String,default:""},ueditorConfig:{type:Object,default:function(){return{}}},index:{default:0}},data:function(){return{randomId:"editor_"+1e4*Math.random(),instance:null,scriptTagStatus:0,appId:this.$store.state.sys.appId}},created:function(){void 0!==window.UE?(this.scriptTagStatus=2,this.initEditor()):this.insertScriptTag()},mounted:function(){},beforeDestroy:function(){null!==this.instance&&this.instance.destroy&&this.instance.destroy()},watch:{defaultMsg:function(t,e){this.instance=window.UE.getEditor(this.randomId,this.ueditorConfig),null!==t&&this.instance.ready(function(){this.setContent(t)})}},methods:{insertScriptTag:function(){var t=this,e=document.getElementById("editorScriptTag"),a=document.getElementById("configScriptTag");if(null===e){a=document.createElement("script"),a.type="text/javascript",a.src=this.ueditorPath+"ueditor.config.js",a.id="configScriptTag",e=document.createElement("script"),e.type="text/javascript",e.src=this.ueditorPath+"ueditor.all.js",e.id="editorScriptTag";var i=document.getElementsByTagName("head")[0];i.appendChild(a),i.appendChild(e)}a.loaded?this.scriptTagStatus++:a.addEventListener("load",function(){t.scriptTagStatus++,a.loaded=!0,t.initEditor()}),e.loaded?this.scriptTagStatus++:e.addEventListener("load",function(){t.scriptTagStatus++,e.loaded=!0,t.initEditor()}),this.initEditor()},getUEContent:function(){return this.instance.getContent()},initEditor:function(){var t=this,e=this;2===this.scriptTagStatus&&null===this.instance&&this.$nextTick(function(){t.instance=window.UE.getEditor(t.randomId,t.ueditorConfig),t.instance.addListener("ready",function(){window.UE.Editor.prototype._bkGetActionUrl=window.UE.Editor.prototype.getActionUrl,window.UE.Editor.prototype.getActionUrl=function(t){return"uploadimage"==t?"http://192.168.0.173:8080/jeecmsv9/api/admin/ueditor/upload?sessionKey=41e913c000de6b32c2bfb9ab98d67ab5f2377b7c2b605d9c599a688f3bfdd189454b68b238d570cf5b0ad835c5710337&appId=1580387213331704":"uploadvideo"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/upload?sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"uploadfile"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/upload?sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"catchimage"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/getRemoteImage?sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"uploadscrawl"==t?"http://192.168.0.199:12000/newjspgou/api/admin//ueditor/scrawlImage?Type=Image&sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"listimage"==t||"listfile"==t?"http://192.168.0.199:12000/newjspgou/api/admin/ueditor/imageManager?picNum=50&insite=false&sessionKey="+localStorage.getItem("sessionKey")+"&appId="+e.$store.state.sys.appId:"config"==t?"/static/ueditor/config.json":void 0},t.$emit("ready",t.instance,t.index),t.instance.setContent(t.defaultMsg)})})}}}},uJq0:function(t,e,a){e=t.exports=a("BkJT")(!1),e.push([t.i,".gou-upload[data-v-1161f364]{width:178px}.pos-del[data-v-1161f364]{position:absolute;width:45px;height:45px;color:#fff;cursor:pointer;top:0;right:0;font-size:25px;display:none;z-index:15;text-align:center}.gou-upload:hover .pos-del[data-v-1161f364],.gou-upload:hover .upload-cover[data-v-1161f364]{display:block}",""])}});