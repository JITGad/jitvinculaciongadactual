(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-e58025d8"],{"0cbf":function(e,t,n){"use strict";n.r(t);var r=n("7a23");function o(e,t,n,o,a,i){var u=Object(r["C"])("form-tipo-juego");return Object(r["w"])(),Object(r["d"])(u,{onSubmit:o.handleSubmit},null,8,["onSubmit"])}var a=n("1da1"),i=(n("96cf"),n("ca92")),u=n("6362"),c={name:"CrearTipoJuego",components:{FormTipoJuego:i["a"]},setup:function(e,t){var n=function(){var e=Object(a["a"])(regeneratorRuntime.mark((function e(t,n){var r;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,u["a"].postTipoJuego(t);case 2:r=e.sent,n(r);case 4:case"end":return e.stop()}}),e)})));return function(t,n){return e.apply(this,arguments)}}();return{handleSubmit:n}}},l=n("6b0d"),d=n.n(l);const s=d()(c,[["render",o]]);t["default"]=s},"4de4":function(e,t,n){"use strict";var r=n("23e7"),o=n("b727").filter,a=n("1dde"),i=a("filter");r({target:"Array",proto:!0,forced:!i},{filter:function(e){return o(this,e,arguments.length>1?arguments[1]:void 0)}})},5530:function(e,t,n){"use strict";n.d(t,"a",(function(){return a}));n("b64b"),n("a4d3"),n("4de4"),n("d3b7"),n("e439"),n("159b"),n("dbb4");var r=n("ade3");function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function a(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){Object(r["a"])(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}},6362:function(e,t,n){"use strict";var r=n("1da1"),o=n("b85c"),a=n("d4ec"),i=n("bee2"),u=n("d5e4"),c=n("5364"),l=(n("96cf"),n("d3b7"),n("99af"),n("3ca3"),n("10d1"),n("ddb0"),n("70f8")),d=n("05b6"),s=n("c2f9"),b=new WeakMap,m=function(){function e(){Object(a["a"])(this,e),Object(u["a"])(this,b,{writable:!0,value:"webresources/gametype"})}return Object(i["a"])(e,[{key:"getTipoJuegoAdminstrador",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;return new Promise((function(n){l["a"].get("".concat(Object(c["a"])(e,b),"/getgametypebyid").concat(Object(d["c"])({idgametype:t})),(function(e){return n(e)}),!0)}))}},{key:"getTipoJuego",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;return new Promise((function(n){l["a"].get("".concat(Object(c["a"])(e,b),"/getgametypebyidsk").concat(Object(d["c"])({idgametype:t})),(function(e){return n(e)}),!0)}))}},{key:"getTipoJuegoPorActividad",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;return new Promise((function(n){l["a"].get("".concat(Object(c["a"])(e,b),"/getgametypewithgames").concat(Object(d["c"])({idactivitiestype:t})),(function(e){return n(e)}),!0)}))}},{key:"getTipoJuegosSelectMenu",value:function(){var e=this;return new Promise((function(t){l["a"].get("".concat(Object(c["a"])(e,b),"/getgametypecv"),(function(e){var n=[];if(Array.isArray(e.data)){var r,a=Object(o["a"])(e.data);try{for(a.s();!(r=a.n()).done;){var i=r.value;n.push(new s["a"](i.id,i.text,i.value))}}catch(u){a.e(u)}finally{a.f()}}t(n)}),!0,!1)}))}},{key:"getTipoJuegosAdministrador",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1;return new Promise((function(n){l["a"].get("".concat(Object(c["a"])(e,b),"/getgametypeAdmin").concat(Object(d["c"])({page:t})),(function(e){return n(e)}),!0,!0)}))}},{key:"postTipoJuego",value:function(e){var t=this;return new Promise((function(n){l["a"].post("".concat(Object(c["a"])(t,b),"/Postgametype"),e,(function(e){return n(e)}),l["a"].JSONENCODE,!0)}))}},{key:"putTipoJuego",value:function(e){var t=this;return new Promise((function(n){l["a"].put("".concat(Object(c["a"])(t,b),"/Putgametype"),e,(function(e){return n(e)}),l["a"].JSONENCODE,!0)}))}},{key:"deleteTipoJuego",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;return new Promise(function(){var n=Object(r["a"])(regeneratorRuntime.mark((function n(r){return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.next=2,l["a"].delete("".concat(Object(c["a"])(e,b),"/Deletegametype"),{idgametype:t},(function(e){return r(e)}));case 2:case"end":return n.stop()}}),n)})));return function(e){return n.apply(this,arguments)}}())}}]),e}();t["a"]=new m},ca92:function(e,t,n){"use strict";n("b0c0");var r=n("7a23"),o={key:0},a={key:1};function i(e,t,n,i,u,c){var l=Object(r["C"])("my-input"),d=Object(r["C"])("my-input-file"),s=Object(r["C"])("my-select-boolean");return i.Loading?(Object(r["w"])(),Object(r["f"])("div",o,"Cargando...")):(Object(r["w"])(),Object(r["f"])("div",a,[Object(r["j"])(l,{modelValue:i.model.name,"onUpdate:modelValue":t[0]||(t[0]=function(e){return i.model.name=e}),type:"text",label:"Nombre Largo",placeholder:"Escriba el nombre de la actividad",validations:"requerido"},null,8,["modelValue"]),Object(r["j"])(l,{modelValue:i.model.shortname,"onUpdate:modelValue":t[1]||(t[1]=function(e){return i.model.shortname=e}),type:"text",label:"Nombre corto",help:i.HelpShortName,disabled:n.edit,placeholder:"Escriba el nombre de la actividad",validations:"requerido"},null,8,["modelValue","help","disabled"]),Object(r["j"])(d,{label:"Imagen",modelValue:i.model.image,"onUpdate:modelValue":t[2]||(t[2]=function(e){return i.model.image=e}),type:"image"},null,8,["modelValue"]),Object(r["j"])(l,{modelValue:i.model.text_instructions,"onUpdate:modelValue":t[3]||(t[3]=function(e){return i.model.text_instructions=e}),type:"text",multiple:!0,label:"Instrucciones",placeholder:"Escriba la instrucciones del juego"},null,8,["modelValue"]),Object(r["j"])(d,{label:"Audio de instrucciones",modelValue:i.model.audio_instructions,"onUpdate:modelValue":t[4]||(t[4]=function(e){return i.model.audio_instructions=e}),type:"audio"},null,8,["modelValue"]),Object(r["j"])(d,{label:"Video de instrucciones",modelValue:i.model.video_instructions,"onUpdate:modelValue":t[5]||(t[5]=function(e){return i.model.video_instructions=e}),type:"video"},null,8,["modelValue"]),Object(r["j"])(s,{label:"Estado",modelValue:i.model.state,"onUpdate:modelValue":t[6]||(t[6]=function(e){return i.model.state=e})},null,8,["modelValue"])]))}var u=n("1da1"),c=n("5530"),l=(n("96cf"),n("a9e3"),n("d3b7"),n("6362")),d=n("0cd5"),s={name:"FormTipoJuego",emits:["submit"],props:{idgametype:{type:Number,default:0},title:{type:String,default:"Crear tipo de juego"},edit:{type:Boolean,default:!1}},setup:function(e,t){var n={idgametype:0,name:"",shortname:"",image:null,audio_instructions:null,text_instructions:"",video_instructions:null,state:!0},o=Object(r["b"])((function(){return e.edit?"Este campo no es editable":"Ese campo no se podra editar una vez se guarde"})),a=Object(r["z"])(!1),i=Object(r["n"])("layout"),s=Object(r["l"])(),b=Object(r["y"])(Object(c["a"])({},n));function m(){return new Promise((function(e){t.emit("submit",b,(function(t){return e(t)}))}))}function p(e){a.value=e,i.loading(e)}function f(){Object.assign(b,n)}return Object(r["u"])(Object(u["a"])(regeneratorRuntime.mark((function t(){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(i.bind({submit:m,clear:f,uid:s.uid,title:e.title,"url-next":"/list/tipo-juegos","is-edit":e.edit}),!(e.idgametype>0)){t.next=7;break}return p(!0),t.next=5,l["a"].getTipoJuegoAdminstrador(e.idgametype);case 5:n=t.sent,n.status.error?Object(d["b"])(n.status.message):(Object.assign(b,n.data),p(!1));case 7:case"end":return t.stop()}}),t)})))),Object(r["s"])((function(){i.unbind(s.uid)})),{model:b,Loading:a,HelpShortName:o}}},b=n("6b0d"),m=n.n(b);const p=m()(s,[["render",i]]);t["a"]=p},dbb4:function(e,t,n){var r=n("23e7"),o=n("83ab"),a=n("56ef"),i=n("fc6a"),u=n("06cf"),c=n("8418");r({target:"Object",stat:!0,sham:!o},{getOwnPropertyDescriptors:function(e){var t,n,r=i(e),o=u.f,l=a(r),d={},s=0;while(l.length>s)n=o(r,t=l[s++]),void 0!==n&&c(d,t,n);return d}})},e439:function(e,t,n){var r=n("23e7"),o=n("d039"),a=n("fc6a"),i=n("06cf").f,u=n("83ab"),c=o((function(){i(1)})),l=!u||c;r({target:"Object",stat:!0,forced:l,sham:!u},{getOwnPropertyDescriptor:function(e,t){return i(a(e),t)}})}}]);
//# sourceMappingURL=chunk-e58025d8.c46e7b82.js.map