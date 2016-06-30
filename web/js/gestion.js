//TODO hacer el login del manago con el usuario
function borrarMapa() {

    //alert ("borrar");

    OSRM.GUI.resetRouting();

    local_map = OSRM.G.map;  

    if ( typeof vis_rout !== "undefined" ){ 

        local_map.removeLayer(vis_rout);

    }

}
function hacerLogin(){
    user  = document.getElementById("user-input").value;   
    passwd= document.getElementById("passwd-input").value;
    checkcampos= true;
    if (user===""){
        alert('El login de usuario no es un valor valido');
        checkcampos= false;
    }
    if (passwd===""){
        alert('El password de usuario no es un valor valido');
        checkcampos= false;
    }
    if (checkcampos) {
        var url=host+"/login/login";
        var params='{"user":"'+user+'","passwd":"'+passwd+'"}';
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          
          success: function (data) {
                    if ( data === "0" ){
                        window.sessionStorage.setItem("userid", user);
                        window.sessionStorage.setItem("passwd",passwd);
                        window.location = "http://"+ipAdress+"/EditorDeTrayectorias/main.html"; 

                    }else{
                        alert("El usuario no corresponde al passwd "+data);
                        
                    }
                    
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al hacer login!!' + json2.JSON.stringify(errorThrown));
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
    }
}

function getUserInfo(userId){
        var params='{"userId":"'+userId+'"}';
        var url=host+"/getInfo/getInfo";
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          success: function (data) {    
              obj   = JSON.parse(data);
              rutas = obj.routes;
              document.getElementById("nombre-input2-source").value    = obj.nombre;
              document.getElementById("email-input2-source").value     = obj.email;
              document.getElementById("gui-tipo-mensaje-toggle").value = obj.sendEmail;
              sessionStorage.setItem("passwd",obj.passwd); 
              sessionStorage.setItem("email",obj.email);
              sessionStorage.setItem("sendEmail",obj.sendEmail);
              //document.getElementById("passwd-input2-source").value  = obj.passwd;
              nivelIncs = obj.nivelIncs;
              $("#slider").slider('value',nivelIncs);
              if      ( nivelIncs === "0" ) {$("#nivelVal").val("Fluido");$("#nivelVal").css('color', 'green');}
              else if ( nivelIncs === "1" ) {$("#nivelVal").val("Lento");$("#nivelVal").css('color', 'orange');}
              else if ( nivelIncs === "2" ) {$("#nivelVal").val("Atascado");$("#nivelVal").css('color', 'red');}
              else if ( nivelIncs === "3" ) {$("#nivelVal").val("Ninguna");$("#nivelVal").css('color', 'black');}  
              tipoIncs = obj.tipoIncs;
              if ( tipoIncs.charAt(1) === "1" ) $("#accidenteTipo").prop('checked', true); else $("#accidenteTipo").prop('checked', false);
              if ( tipoIncs.charAt(2) === "1" ) $("#retencionTipo").prop('checked', true); else $("#retencionTipo").prop('checked', false);
              if ( tipoIncs.charAt(3) === "1" ) $("#seguridadTipo").prop('checked', true); else $("#seguridadTipo").prop('checked', false);
              if ( tipoIncs.charAt(4) === "1" ) $("#otrasTipo").prop('checked', true); else $("#otrasTipo").prop('checked', false);
              if ( tipoIncs.charAt(5) === "1" ) $("#puertosTipo").prop('checked', true); else $("#puertosTipo").prop('checked', false);
              if ( tipoIncs.charAt(6) === "1" ) $("#vialidadTipo").prop('checked', true); else $("#vialidadTipo").prop('checked', false);
              if ( tipoIncs.charAt(8) === "1" ) $("#obrasTipo").prop('checked', true); else $("#obrasTipo").prop('checked', false);
              $("#tablaTrayectorias").attr('size',rutas.length);
              for(var i=0;i<rutas.length;i++){
                 $("#tablaTrayectorias").append("<option value="+rutas[i].name+">"+rutas[i].name+"</option>");
              }
              sessionStorage.setItem("ss_rutas",JSON.stringify(rutas));
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al obtener la info de Usuario');
              window.location = "http://"+ipAdress+"/EditorDeTrayectorias/index.jsp";
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
}

function setAvisoDia(id, activado){

    if (activado) {
        $(id).removeClass('button-off');
        $(id).addClass('button-on');
    }
    else {
        $(id).removeClass('button-on');
        $(id).addClass('button-off');
    }
      
}
function cargarRuta_noMarkers(){
    
    //borramos la ruta mostrada para que elimine los puntos de inicio y fin
    OSRM.GUI.resetRouting();

    var selRutaIdx = $('#tablaTrayectorias').prop("selectedIndex");
    var rutas = JSON.parse(sessionStorage.getItem("ss_rutas"));

    var ruta = rutas[selRutaIdx].route;

    var iniTime   = rutas[selRutaIdx].iniTime;
    var endTime   = rutas[selRutaIdx].endTime;
    var diasAviso = rutas[selRutaIdx].diasAviso;
    
    $('#slider-range').slider('values',[iniTime, endTime]);
    var out0 ="";
    if ((iniTime-Math.floor(iniTime/60)*60)<10) out0="0";
    var out1 ="";
    if ((endTime-Math.floor(endTime/60)*60)<10) out1="0";
    $( "#minVal" ).val(Math.floor(iniTime/60)+":"+out0+(iniTime-Math.floor(iniTime/60)*60));
    $( "#maxVal" ).val(Math.floor(endTime/60)+":"+out1+(endTime-Math.floor(endTime/60)*60));
    
    setAvisoDia ("#diaL", ((diasAviso & 1)  !== 0));//00000001
    setAvisoDia ("#diaM", ((diasAviso & 2)  !== 0));//00000010
    setAvisoDia ("#diaX", ((diasAviso & 4)  !== 0));//00000100
    setAvisoDia ("#diaJ", ((diasAviso & 8)  !== 0));//00001000
    setAvisoDia ("#diaV", ((diasAviso & 16) !== 0));//00010000
    setAvisoDia ("#diaS", ((diasAviso & 32) !== 0));//00100000
    setAvisoDia ("#diaD", ((diasAviso & 64) !== 0));//01000000
    
    var ruta2 = ruta.replace(/LatLng\(/g,"").replace(/\)/g,"");
    var ruta4 = ruta2.split(",");
    var lats = [];
    var lons = [];
    var LatLons = [];
    for(var i=0;i<ruta4.length;i++){
        j = Math.floor(i/2);
        if ( i % 2 === 0 ) var lat = parseFloat(ruta4[i]);
        else               var lon = parseFloat(ruta4[i]);
        lats[j] = lat;
        lons[j] = lon;
        var LatLon = [];
        LatLon[0] = lat;
        LatLon[1] = lon;
        LatLons[j] = LatLon;
    }
    
/*    local_map = OSRM.G.map;   
    if ( typeof vis_rout !== "undefined" ){  
        local_map.removeLayer(vis_rout);
    }
    //var route_style	= {color:'#0033FF', weight:5, dashArray:""}; 
    var route_style	= {color:'#001155', weight:5, dashArray:""};
    vis_rout = L.polyline(LatLons).setStyle(route_style);
    vis_rout.addTo(local_map);
*/   
    //punto de inicio
    OSRM.Geocoder._onclickResult(OSRM.C.SOURCE_LABEL, lats[0], lons[0]);
    OSRM.Geocoder.updateAddress( OSRM.C.SOURCE_LABEL );
    
    //punto de fin
    OSRM.Geocoder._onclickResult(OSRM.C.TARGET_LABEL, lats[lats.length-1], lons[lons.length -1]);
    OSRM.Geocoder.updateAddress( OSRM.C.TARGET_LABEL );
    		
    //centramos
    var bounds = new L.LatLngBounds(L.polyline(LatLons).getLatLngs());
    OSRM.G.map.fitBoundsUI(bounds);

}

function cargarRuta(){
    
    //borramos la ruta mostrada 
    OSRM.GUI.resetRouting();
    //OSRM.Routing.init();
    
    var selRutaIdx = $('#tablaTrayectorias').prop("selectedIndex");
    var rutas = JSON.parse(sessionStorage.getItem("ss_rutas"));

    var ptos = rutas[selRutaIdx].markers;
    if (ptos == "null" || ptos == null || ptos == "")
    {
        cargarRuta_noMarkers();
        return;
    }
    
    var iniTime   = rutas[selRutaIdx].iniTime;
    var endTime   = rutas[selRutaIdx].endTime;
    var diasAviso = rutas[selRutaIdx].diasAviso;
    
    $('#slider-range').slider('values',[iniTime, endTime]);
    var out0 ="";
    if ((iniTime-Math.floor(iniTime/60)*60)<10) out0="0";
    var out1 ="";
    if ((endTime-Math.floor(endTime/60)*60)<10) out1="0";
    $( "#minVal" ).val(Math.floor(iniTime/60)+":"+out0+(iniTime-Math.floor(iniTime/60)*60));
    $( "#maxVal" ).val(Math.floor(endTime/60)+":"+out1+(endTime-Math.floor(endTime/60)*60));
    
    setAvisoDia ("#diaL", ((diasAviso & 1)  !== 0));//00000001
    setAvisoDia ("#diaM", ((diasAviso & 2)  !== 0));//00000010
    setAvisoDia ("#diaX", ((diasAviso & 4)  !== 0));//00000100
    setAvisoDia ("#diaJ", ((diasAviso & 8)  !== 0));//00001000
    setAvisoDia ("#diaV", ((diasAviso & 16) !== 0));//00010000
    setAvisoDia ("#diaS", ((diasAviso & 32) !== 0));//00100000
    setAvisoDia ("#diaD", ((diasAviso & 64) !== 0));//01000000
    
    var ptos2 = ptos.replace(/LatLng\(/g,"").replace(/\)/g,"");
    var ptos4 = ptos2.split(",");
    var lats = [];
    var lons = [];
    var LatLons = [];
    for(var i=0;i<ptos4.length;i++){
        j = Math.floor(i/2);
        if ( i % 2 === 0 ) var lat = parseFloat(ptos4[i]);
        else               var lon = parseFloat(ptos4[i]);
        lats[j] = lat;
        lons[j] = lon;
        var LatLon = [];
        LatLon[0] = lat;
        LatLon[1] = lon;
        LatLons[j] = LatLon;
    }
    
    //punto de inicio
    OSRM.Geocoder._onclickResult(OSRM.C.SOURCE_LABEL, lats[0], lons[0]);
    OSRM.Geocoder.updateAddress( OSRM.C.SOURCE_LABEL );
    
    //punto de fin
    OSRM.Geocoder._onclickResult(OSRM.C.TARGET_LABEL, lats[lats.length-1], lons[lons.length -1]);
    OSRM.Geocoder.updateAddress( OSRM.C.TARGET_LABEL );
    
    //centramos aquí mejor para evitar un efecto raro que aparece si lo hacemos al final del todo (total, las posiciones no cambian)
    var bounds = new L.LatLngBounds(L.polyline(LatLons).getLatLngs());
    OSRM.G.map.fitBoundsUI(bounds);
        
    //No sé por qué, pero necesitamos hacer un timeout para que coja los marcadores intermedios bien siempre
    setTimeout(function(){
        //Debemos ir indicando los puntos al revés (del último al primero) porque al busacar dónde añadirlo siempre devuelve que al principio --> los coloca al revés
        for (var i = lats.length - 2; i > 0 ; i--)
        //for (var i = 1; i < lats.length - 1; i++)
        {
            var markerPos = new L.LatLng(lats[i], lons[i]);
            var new_via_index = Math.max( 0, OSRM.Via.findViaIndex(markerPos));
            var index = OSRM.G.markers.setVia( new_via_index, markerPos);
            
            //var a = [];
            //a.push(lats[i]);
            //a.push(lons[i]);
            //OSRM.G.response.via_points.push(a);
            
            OSRM.G.markers.route[index].show();
        }
    
    
        OSRM.Routing.getRoute({keepAlternative:true});
        //centramos
        //var bounds = new L.LatLngBounds(L.polyline(LatLons).getLatLngs());
        //OSRM.G.map.fitBoundsUI(bounds);
        
    }, 300);

}
function cargarRuta_noMarkers(){
    
    //borramos la ruta mostrada para que elimine los puntos de inicio y fin
    OSRM.GUI.resetRouting();
    
    var selRutaIdx = $('#tablaTrayectorias').prop("selectedIndex");
    var rutas = JSON.parse(sessionStorage.getItem("ss_rutas"));

    var ruta = rutas[selRutaIdx].route;

    var iniTime   = rutas[selRutaIdx].iniTime;
    var endTime   = rutas[selRutaIdx].endTime;
    var diasAviso = rutas[selRutaIdx].diasAviso;
    
    $('#slider-range').slider('values',[iniTime, endTime]);
    var out0 ="";
    if ((iniTime-Math.floor(iniTime/60)*60)<10) out0="0";
    var out1 ="";
    if ((endTime-Math.floor(endTime/60)*60)<10) out1="0";
    $( "#minVal" ).val(Math.floor(iniTime/60)+":"+out0+(iniTime-Math.floor(iniTime/60)*60));
    $( "#maxVal" ).val(Math.floor(endTime/60)+":"+out1+(endTime-Math.floor(endTime/60)*60));
    
    setAvisoDia ("#diaL", ((diasAviso & 1)  !== 0));//00000001
    setAvisoDia ("#diaM", ((diasAviso & 2)  !== 0));//00000010
    setAvisoDia ("#diaX", ((diasAviso & 4)  !== 0));//00000100
    setAvisoDia ("#diaJ", ((diasAviso & 8)  !== 0));//00001000
    setAvisoDia ("#diaV", ((diasAviso & 16) !== 0));//00010000
    setAvisoDia ("#diaS", ((diasAviso & 32) !== 0));//00100000
    setAvisoDia ("#diaD", ((diasAviso & 64) !== 0));//01000000
    
    var ruta2 = ruta.replace(/LatLng\(/g,"").replace(/\)/g,"");
    var ruta4 = ruta2.split(",");
    var lats = [];
    var lons = [];
    var LatLons = [];
    for(var i=0;i<ruta4.length;i++){
        j = Math.floor(i/2);
        if ( i % 2 === 0 ) var lat = parseFloat(ruta4[i]);
        else               var lon = parseFloat(ruta4[i]);
        lats[j] = lat;
        lons[j] = lon;
        var LatLon = [];
        LatLon[0] = lat;
        LatLon[1] = lon;
        LatLons[j] = LatLon;
    }
    local_map = OSRM.G.map;   
    if ( typeof vis_rout !== "undefined" ){  
        local_map.removeLayer(vis_rout);
    }
    //var route_style	= {color:'#0033FF', weight:5, dashArray:""}; 
    var route_style	= {color:'#001155', weight:5, dashArray:""};
    vis_rout = L.polyline(LatLons).setStyle(route_style);
    vis_rout.addTo(local_map);
    
    //punto de inicio
    OSRM.Geocoder._onclickResult(OSRM.C.SOURCE_LABEL, lats[0], lons[0]);
    OSRM.Geocoder.updateAddress( OSRM.C.SOURCE_LABEL );


    //punto de fin
    OSRM.Geocoder._onclickResult(OSRM.C.TARGET_LABEL, lats[lats.length-1], lons[lons.length -1]);
    OSRM.Geocoder.updateAddress( OSRM.C.TARGET_LABEL );

   /*
    OSRM.Routing.getRoute({recenter:OSRM.G.markers.route.length == 2}  );
    var print_window = OSRM.G.printwindow;
    print_window.OSRM.drawMarkers( OSRM.G.markers.route );
    */
    
    //centramos
    var bounds = new L.LatLngBounds(L.polyline(LatLons).getLatLngs());
    OSRM.G.map.fitBoundsUI(bounds);	  
    
    
}
function getRutaIndex(nombreRuta) {
    var indx = -1;
    var rutaIndx = -1;  
    var nombreMays = nombreRuta.toUpperCase();  
    $("#tablaTrayectorias option").each(function(){
        indx++;
        var name = $(this).text().toUpperCase();
        if (nombreMays === name)
            rutaIndx = rutas[indx].index;
    });   
    return rutaIndx;

}
function borrarRuta() {
    var selRutaIdx = $('#tablaTrayectorias').prop("selectedIndex");
    var rutas = JSON.parse(sessionStorage.getItem("ss_rutas"));
    var rutaIndx = rutas[selRutaIdx].index;
    borrarRutaIndx(rutaIndx);
}

function borrarRutaIndx(rutaIndx){
    var userId = document.getElementById("userId-input2-source").value;
    var params='{"userId":"'+userId+'", "rutaIndx":"'+rutaIndx+'"}';
    var url=host+"/routes/eraseRoute";
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          success: function (data) {
              if (!$("#tablaTrayectorias").is(':empty')){ $("#tablaTrayectorias").empty();}
               getUserInfo(userId);
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al borrar ruta');
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
}


/*
function borrarRuta(){
    var userId = document.getElementById("userId-input2-source").value;
    var selRutaIdx = $('#tablaTrayectorias').prop("selectedIndex");
    var rutas = JSON.parse(sessionStorage.getItem("ss_rutas"));
    var rutaIndx = rutas[selRutaIdx].index;
    var params='{"userId":"'+userId+'", "rutaIndx":"'+rutaIndx+'"}';
    var url=host+"/routes/eraseRoute";
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          success: function (data) { 
              if (!$("#tablaTrayectorias").is(':empty')){ $("#tablaTrayectorias").empty();}
               getUserInfo(userId); 
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al borrar ruta');
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
}
*/
function guardarTipos(){
    var tipoIncidencia = "0";  
    /*
    if ( document.getElementById("meteoTipo").checked )      {tipoIncidencia = tipoIncidencia+"1";}
    else                                                     {tipoIncidencia = tipoIncidencia+"0";}    */
    if ( document.getElementById("accidenteTipo").checked )  {tipoIncidencia = tipoIncidencia+"1";}
    else                                                     {tipoIncidencia = tipoIncidencia+"0";}
    if ( document.getElementById("retencionTipo").checked )  {tipoIncidencia = tipoIncidencia+"1";}
    else                                                     {tipoIncidencia = tipoIncidencia+"0";}    
    if ( document.getElementById("seguridadTipo").checked )  {tipoIncidencia = tipoIncidencia+"1";}
    else                                                     {tipoIncidencia = tipoIncidencia+"0";} 
    if ( document.getElementById("otrasTipo").checked )      {tipoIncidencia = tipoIncidencia+"1";}
    else                                                     {tipoIncidencia = tipoIncidencia+"0";} 
    if ( document.getElementById("puertosTipo").checked )    {tipoIncidencia = tipoIncidencia+"1";}
    else                                                     {tipoIncidencia = tipoIncidencia+"0";} 
    if ( document.getElementById("vialidadTipo").checked )   {tipoIncidencia = tipoIncidencia+"1";}
    else                                                     {tipoIncidencia = tipoIncidencia+"0";} 
    tipoIncidencia = tipoIncidencia+"0";
    /*
    if ( document.getElementById("deporteTipo").checked )      {tipoIncidencia = tipoIncidencia+"1";}
    else                                                     {tipoIncidencia = tipoIncidencia+"0";}    */
    if ( document.getElementById("obrasTipo").checked )      {tipoIncidencia = tipoIncidencia+"1";}
    else                                                     {tipoIncidencia = tipoIncidencia+"0";} 
    var nivelIncidencia = $('#slider').slider("option", "value");
    var userId = document.getElementById("userId-input2-source").value;
    var params='{"userId":"'+userId+'", "tipo":"'+tipoIncidencia+'", "nivel":"'+nivelIncidencia+'"}';
    var url=host+"/routes/saveTypes";
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          success: function (data) { 
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al guardar tipos de incidencias');
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
}


function guardarRuta(userId, nombreRuta, ruta, puntos){
        iniTime = $( "#slider-range" ).slider( "values", 0 );
        endTime = $( "#slider-range" ).slider( "values", 1 );
        
        diasAviso = 0;
        if ($("#diaL").hasClass('button-on')) diasAviso += 1;   //00000001
        if ($("#diaM").hasClass('button-on')) diasAviso += 2;   //00000010
        if ($("#diaX").hasClass('button-on')) diasAviso += 4;   //00000100
        if ($("#diaJ").hasClass('button-on')) diasAviso += 8;   //00001000
        if ($("#diaV").hasClass('button-on')) diasAviso += 16;  //00010000
        if ($("#diaS").hasClass('button-on')) diasAviso += 32;  //00100000
        if ($("#diaD").hasClass('button-on')) diasAviso += 64;  //01000000
        
        
        //alert("iniTime="+iniTime+", endTime="+endTime);
        var params='{"userId":"'+userId+'", "nombreRuta":"'+nombreRuta+'", "ruta":"'+ruta+'", "iniTime":"'+iniTime+'", "endTime":"'+endTime+'", "diasAviso":"' + diasAviso +'", "puntos":"' + puntos +  '"}';
        var url=host+"/routes/saveRoute";
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          success: function (data) {    
               if (!$("#tablaTrayectorias").is(':empty')){$("#tablaTrayectorias").empty();}
               getUserInfo(userId);  
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al guardar ruta');
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
}
function renombrarRuta(userId, nombreRuta){
        var selRutaIdx = $('#tablaTrayectorias').prop("selectedIndex");
        var rutas = JSON.parse(sessionStorage.getItem("ss_rutas"));
        var rutaIndx = rutas[selRutaIdx].index;
        var params='{"userId":"'+userId+'", "rutaIndx":"'+rutaIndx+'", "nombreRuta":"'+nombreRuta+'"}';
        //alert("renombrarRuta :::"+params+" selRutaIdx="+selRutaIdx);
        var url=host+"/routes/renameRoute";
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          success: function (data) {    
               if (!$("#tablaTrayectorias").is(':empty')){$("#tablaTrayectorias").empty();}
               getUserInfo(userId);  
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al renombrar la ruta');
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
}
function cambiarPasswd(userId, newPasswd){
        var params='{"userId":"'+userId+'", "passwd":"'+newPasswd+'"}';
        var url=host+"/getInfo/changePasswd";
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          success: function (data) {    
              alert('Password Cambiado');
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al cambiar Passwd');
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
}
function cambiarEmail(userId, newEmail){
        var params='{"userId":"'+userId+'", "email":"'+newEmail+'"}';
        var url=host+"/getInfo/changeEmail";
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          success: function (data) {    
              alert('Email Cambiado');
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al cambiar Email');
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
}
function cambiarSendEmail(userId, newSendEmail){
        var params='{"userId":"'+userId+'", "sendEmail":"'+newSendEmail+'"}';
        var url=host+"/getInfo/changeSendEmail";
        $.ajax({
          url: url,
          type: 'PUT',
          data: params,
          dataType: 'text',
          success: function (data) {    
              alert('SendEmail Cambiado');
          },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
              alert('Error al cambiar SendEmail');
              console.log(JSON.stringify(XMLHttpRequest));
              console.log(JSON.stringify(textStatus));
              console.log(JSON.stringify(errorThrown));
            }
    });
}
function pruebaLogin(){
    var url=host+"/login/prueba";
    $.ajax({
      url: url,
      type: 'GET',
      dataType: 'text',
      success: function (data) {
          alert(data)
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
          alert('Error en la prueba Login!!');
          console.log(JSON.stringify(XMLHttpRequest));
          console.log(JSON.stringify(textStatus));
          console.log(JSON.stringify(errorThrown));
        }
    });
}
