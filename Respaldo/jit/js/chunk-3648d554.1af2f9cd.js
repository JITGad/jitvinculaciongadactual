(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3648d554"],{"4baa":function(e,t,n){"use strict";n.r(t);n("b0c0");var c=n("7a23"),a={class:"table table-bordered table-striped table-hover table-light"},o=Object(c["g"])("thead",{class:"thead-light"},[Object(c["g"])("tr",null,[Object(c["g"])("th",{scope:"col"},"Id"),Object(c["g"])("th",{scope:"col"},"Texto"),Object(c["g"])("th",{scope:"col"},"Nombre corto"),Object(c["g"])("th",{scope:"col"},"Imagen"),Object(c["g"])("th",{scope:"col"},"Instrucciones"),Object(c["g"])("th",{scope:"col"},"Audio de instrucciones"),Object(c["g"])("th",{scope:"col"},"Video de instrucciones"),Object(c["g"])("th",{scope:"col"},"Estado"),Object(c["g"])("th",{scope:"col"},"Editar")])],-1),r={scope:"row"},i={align:"center"},u=["onUpdate:modelValue"],s={align:"center"},l={align:"center"},d={align:"center"};function g(e,t,n,g,b,p){var j=Object(c["C"])("my-prev-file"),m=Object(c["C"])("my-link-table");return Object(c["w"])(),Object(c["f"])("table",a,[o,Object(c["g"])("tbody",null,[(Object(c["w"])(!0),Object(c["f"])(c["a"],null,Object(c["A"])(g.TipoJuegos,(function(e,t){return Object(c["w"])(),Object(c["f"])("tr",{key:t},[Object(c["g"])("th",r,Object(c["E"])(e.idgametype),1),Object(c["g"])("td",null,Object(c["E"])(e.name),1),Object(c["g"])("td",null,Object(c["E"])(e.shortname),1),Object(c["g"])("td",i,[Object(c["j"])(j,{type:"image",modelValue:e.image,"onUpdate:modelValue":function(t){return e.image=t}},null,8,["modelValue","onUpdate:modelValue"])]),Object(c["g"])("td",null,[Object(c["M"])(Object(c["g"])("textarea",{class:"form-control",rows:"3",readonly:"","onUpdate:modelValue":function(t){return e.text_instructions=t}},"\r\n          ",8,u),[[c["I"],e.text_instructions]])]),Object(c["g"])("td",s,[Object(c["j"])(j,{type:"audio",modelValue:e.audio_instructions,"onUpdate:modelValue":function(t){return e.audio_instructions=t}},null,8,["modelValue","onUpdate:modelValue"])]),Object(c["g"])("td",l,[Object(c["j"])(j,{type:"video",modelValue:e.video_instructions,"onUpdate:modelValue":function(t){return e.video_instructions=t}},null,8,["modelValue","onUpdate:modelValue"])]),Object(c["g"])("td",null,Object(c["E"])(e.state?"Activo":"Inactivo"),1),Object(c["g"])("td",d,[Object(c["j"])(m,{object:e,icon:"fas fa-pen",onClick:g.EditarTipoJuego},null,8,["object","onClick"])])])})),128))])])}var b=n("1da1"),p=(n("96cf"),n("6c02")),j=n("6362"),m=n("0cd5"),O={name:"TipoJuegos",setup:function(e,t){var n=Object(c["l"])(),a=Object(c["z"])([]),o=Object(p["d"])(),r=Object(c["n"])("layout-list");Object(c["u"])(Object(b["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return r.bind({FetchData:i,uid:n.uid,title:"Mis Tipos de juegos",url_nuevo:""}),e.next=3,i();case 3:case"end":return e.stop()}}),e)})))),Object(c["s"])((function(){r.unbind(n.uid)}));var i=function(){var e=Object(b["a"])(regeneratorRuntime.mark((function e(){var t,n,c=arguments;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return t=c.length>0&&void 0!==c[0]?c[0]:1,r.setPageActual(t),e.next=4,j["a"].getTipoJuegosAdministrador(t);case 4:n=e.sent,n.status.error?Object(m["b"])(n.status.message):(r.changeData(n.conteo,n.totalPaginas),a.value=n.data);case 6:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),u=function(e){o.push({name:"EditarTipoJuego",params:{id:e["idgametype"]}})},s=function(e){Object(m["a"])("Confirmación","Esta seguro de eliminar la TipoJuego ".concat(e.name),Object(b["a"])(regeneratorRuntime.mark((function t(){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,j["a"].deleteTipoJuego(e.idgametype);case 2:n=t.sent,n.status.error?Object(m["b"])(n.status.message):Object(m["c"])("Registro eliminado correctamente",Object(b["a"])(regeneratorRuntime.mark((function e(){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,i();case 2:case"end":return e.stop()}}),e)}))));case 4:case"end":return t.stop()}}),t)}))),(function(){}))};return{TipoJuegos:a,EditarTipoJuego:u,EliminarTipoJuego:s}}},f=n("6b0d"),v=n.n(f);const h=v()(O,[["render",g]]);t["default"]=h},6362:function(e,t,n){"use strict";var c=n("1da1"),a=n("b85c"),o=n("d4ec"),r=n("bee2"),i=n("d5e4"),u=n("5364"),s=(n("96cf"),n("d3b7"),n("99af"),n("3ca3"),n("10d1"),n("ddb0"),n("70f8")),l=n("05b6"),d=n("c2f9"),g=new WeakMap,b=function(){function e(){Object(o["a"])(this,e),Object(i["a"])(this,g,{writable:!0,value:"webresources/gametype"})}return Object(r["a"])(e,[{key:"getTipoJuegoAdminstrador",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;return new Promise((function(n){s["a"].get("".concat(Object(u["a"])(e,g),"/getgametypebyid").concat(Object(l["c"])({idgametype:t})),(function(e){return n(e)}),!0)}))}},{key:"getTipoJuego",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;return new Promise((function(n){s["a"].get("".concat(Object(u["a"])(e,g),"/getgametypebyidsk").concat(Object(l["c"])({idgametype:t})),(function(e){return n(e)}),!0)}))}},{key:"getTipoJuegoPorActividad",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;return new Promise((function(n){s["a"].get("".concat(Object(u["a"])(e,g),"/getgametypewithgames").concat(Object(l["c"])({idactivitiestype:t})),(function(e){return n(e)}),!0)}))}},{key:"getTipoJuegosSelectMenu",value:function(){var e=this;return new Promise((function(t){s["a"].get("".concat(Object(u["a"])(e,g),"/getgametypecv"),(function(e){var n=[];if(Array.isArray(e.data)){var c,o=Object(a["a"])(e.data);try{for(o.s();!(c=o.n()).done;){var r=c.value;n.push(new d["a"](r.id,r.text,r.value))}}catch(i){o.e(i)}finally{o.f()}}t(n)}),!0,!1)}))}},{key:"getTipoJuegosAdministrador",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1;return new Promise((function(n){s["a"].get("".concat(Object(u["a"])(e,g),"/getgametypeAdmin").concat(Object(l["c"])({page:t})),(function(e){return n(e)}),!0,!0)}))}},{key:"postTipoJuego",value:function(e){var t=this;return new Promise((function(n){s["a"].post("".concat(Object(u["a"])(t,g),"/Postgametype"),e,(function(e){return n(e)}),s["a"].JSONENCODE,!0)}))}},{key:"putTipoJuego",value:function(e){var t=this;return new Promise((function(n){s["a"].put("".concat(Object(u["a"])(t,g),"/Putgametype"),e,(function(e){return n(e)}),s["a"].JSONENCODE,!0)}))}},{key:"deleteTipoJuego",value:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:0;return new Promise(function(){var n=Object(c["a"])(regeneratorRuntime.mark((function n(c){return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.next=2,s["a"].delete("".concat(Object(u["a"])(e,g),"/Deletegametype"),{idgametype:t},(function(e){return c(e)}));case 2:case"end":return n.stop()}}),n)})));return function(e){return n.apply(this,arguments)}}())}}]),e}();t["a"]=new b}}]);
//# sourceMappingURL=chunk-3648d554.1af2f9cd.js.map