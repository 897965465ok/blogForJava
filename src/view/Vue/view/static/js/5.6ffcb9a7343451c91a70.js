webpackJsonp([5],{"1c5p":function(e,t){},xaIc:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=n("Xxa5"),r=n.n(a),o=n("exGp"),i=n.n(o),s=n("Dd8w"),c=n.n(s),u=(n("mw3O"),n("SJI6")),l=n("MAW0"),d={name:"Login",data:function(){return{form:{username:"",password:"",email:"",childWindow:""},rules:{username:[{required:!0,message:"账号不可为空",trigger:"blur"}],password:[{required:!0,message:"密码不可为空",trigger:"blur"}]}}},mounted:function(){window.removeEventListener("message",this.callbac),window.addEventListener("message",this.callbac)},unmounted:function(){window.removeEventListener("message",this.callbac)},methods:c()({callbac:function(e){var t=this;return i()(r.a.mark(function n(){return r.a.wrap(function(n){for(;;)switch(n.prev=n.next){case 0:if("http://www.mrjiang.work"!==e.origin){n.next=8;break}return e.data.origin="gitee",t.saveOauth(e.data),console.log(e.data),n.next=6,Object(l.b)(e.data);case 6:n.sent,setTimeout(function(){t.childWindow.close()},1e3);case 8:case"end":return n.stop()}},n,t)}))()},onSubmit:function(e){var t,n=this;this.$refs[e].validate((t=i()(r.a.mark(function e(t){var a,o,i,s,c;return r.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(!t){e.next=9;break}return a=n.form,o=a.username,i=a.password,e.next=4,n.$api.post("/v1/login",{account:o,password:i});case 4:s=e.sent,200==(c=s.data).code&&(localStorage.setItem("authorization",c.data.token),n.$router.push("/")),e.next=10;break;case 9:return e.abrupt("return",!1);case 10:case"end":return e.stop()}},e,n)})),function(e){return t.apply(this,arguments)}))},openwindow:function(e,t,n,a){var r=(window.screen.availHeight-30-a)/2,o=(window.screen.availWidth-10-n)/2;return window.open(e,t,"height="+a+",,innerHeight="+a+",width="+n+",innerWidth="+n+",top="+r+",left="+o+",toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no")},oauth:function(e){var t=this,n=void 0;this.$nextTick(function(){switch(e){case"github":n="https://github.com/login/oauth/authorize?client_id=471c5cbd780a0ab68c77&redirect_uri=http://www.mrjiang.work/v1/oauth?origin=github";break;case"gitee":n="https://gitee.com/oauth/authorize?client_id=a480ace6ca2d676d30f1c932936da15c256db9692834a5107ca910586470ccc1&redirect_uri=http://www.mrjiang.work/v1/oauth?origin=gitee&response_type=code"}t.childWindow=t.openwindow(n,"","500","500")})}},Object(u.mapActions)({saveOauth:"saveOauth"}))},m={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"\n      container\n      flex\n      h-screen\n      w-full\n      bg-white\n      justify-center\n      content-center\n      shadow-sm\n    "},[n("el-form",{ref:"loginForm",staticClass:"bg-white w-4/12 h-4/12",attrs:{model:e.form,rules:e.rules}},[n("h3",{staticClass:"login-title"},[e._v("欢迎登录")]),e._v(" "),n("el-form-item",{attrs:{label:"账号",prop:"username"}},[n("el-input",{attrs:{placeholder:"请输入账号",type:"text"},model:{value:e.form.username,callback:function(t){e.$set(e.form,"username",t)},expression:"form.username"}})],1),e._v(" "),n("el-form-item",{attrs:{label:"密码",prop:"password"}},[n("el-input",{attrs:{placeholder:"请输入密码",type:"password"},model:{value:e.form.password,callback:function(t){e.$set(e.form,"password",t)},expression:"form.password"}})],1),e._v(" "),n("el-form-item",{staticClass:"flex justify-between"},[n("el-button",{on:{click:function(t){return e.onSubmit("loginForm")}}},[e._v("登录")]),e._v(" "),n("el-button",{staticClass:"iconfont icon-huaban88",on:{click:function(t){return e.oauth("github")}}}),e._v(" "),n("el-button",{staticClass:"iconfont icon-mayun",on:{click:function(t){return e.oauth("gitee")}}})],1)],1)],1)},staticRenderFns:[]};var h=n("VU/8")(d,m,!1,function(e){n("1c5p")},"data-v-46970730",null);t.default=h.exports}});