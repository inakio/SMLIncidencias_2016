
<!DOCTYPE html>
<html>
<!-- head -->
<head>
<script src="js/jquery.min.js" type="text/javascript"></script>

<script>
   function goToEditor(){
       window.sessionStorage.setItem("userid",document.getElementById("user-input").value);
       window.sessionStorage.setItem("passwd",document.getElementById("passwd-input").value);
       //if (window.sessionStorage){
       //    alert("We have sessionStorage+\n"+window.sessionStorage.getItem("userid")+"\n"+window.sessionStorage.getItem("passwd")); 
       //}
       hacerLogin();
}
</script>
<!-- metatags -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>SML Incidencias 2016</title>
<meta name="description" content="Editor de Rutas Habituales"/>
<meta name="author" content="I. Olabarrieta" />

<!-- stylesheets -->
<link rel="stylesheet" href="leaflet/leaflet.css" type="text/css"/>
<!--[if lte IE 8]><link rel="stylesheet" href="leaflet/leaflet.ie.css" type="text/css"/><![endif]-->


<!-- body -->
<body style="color: rgb(0, 0, 0); background-color: rgb(136, 132, 206);" alink="#000099" link="#000099" vlink="#990099">
<div class="box-content-left2">
   <img src="css/images/SML_logo.png" alt="SML Lab Logo" style="width:200px;height:75px;">
</div>
<div class="box-content-right2">
   <img src="css/images/logo_tecnalia_cabecera.png" alt="Tecnalia Logo" style="width:320px;height:78px;">
</div>
    <script>
        //////////////////////
        //Administracion Usuarios - BEGIN
        /////////////////////
        function altaDeUsuario(){
            var url="webresources/adminusers/altadeusuario";           
            var content=document.altaUsuarioForm.content.value;
            console.log("altaDeUsuario:::content="+content);

            $.ajax({
                url: url,
                type: 'PUT',
                data: content,
                contentType: 'application/json',
                dataType: 'json',
                success: function(data) {
                    alert(JSON.stringify(data));
                    console.log(data);
                },
                error: function(data) {
                    console.log(data);   
                }
            });
        }
        function getInfoDeUsuario(){
            var url="webresources/adminusers/getInfo";           
            var content=document.getInfoUsuarioForm.content.value;
            console.log("getInfoDeUsuario:::content="+content);

            $.ajax({
                url: url,
                type: 'PUT',
                data: content,
                contentType: 'application/json',
                dataType: 'json',
                success: function(data) {
                    alert(JSON.stringify(data));
                    console.log(data);
                },
                error: function(data) {
                    console.log(data);   
                }
            });
        }   
        function guardarTiposDeUsuario(){
            var url="webresources/adminusers/saveTypes";           
            var content=document.guardarTiposUsuarioForm.content.value;
            console.log("guardarTiposDeUsuario:::content="+content);

            $.ajax({
                url: url,
                type: 'PUT',
                data: content,
                contentType: 'application/json',
                dataType: 'json',
                success: function(data) {
                    alert(JSON.stringify(data));
                    console.log(data);
                },
                error: function(data) {
                    console.log(data);   
                }
            });
        } 
        function cambiarPasswdDeUsuario(){
            var url="webresources/adminusers/changePasswd";           
            var content=document.cambiarPasswdUsuarioForm.content.value;
            console.log("cambiarPasswdDeUsuario:::content="+content);

            $.ajax({
                url: url,
                type: 'PUT',
                data: content,
                contentType: 'application/json',
                dataType: 'json',
                success: function(data) {
                    alert(JSON.stringify(data));
                    console.log(data);
                },
                error: function(data) {
                    console.log(data);   
                }
            });
        }    
        function cambiarEmailDeUsuario(){
            var url="webresources/adminusers/changeEmail";           
            var content=document.cambiarEmailUsuarioForm.content.value;
            console.log("cambiarEmailDeUsuario:::content="+content);

            $.ajax({
                url: url,
                type: 'PUT',
                data: content,
                contentType: 'application/json',
                dataType: 'json',
                success: function(data) {
                    alert(JSON.stringify(data));
                    console.log(data);
                },
                error: function(data) {
                    console.log(data);   
                }
            });
        }        
        function anadirRuta(){
            var url="webresources/routes/saveRoute";           
            var content=document.anadirRutaForm.content.value;
            console.log("index.jsp.anadirRuta:::content="+content);

            $.ajax({
                url: url,
                type: 'PUT',
                data: content,
                contentType: 'application/json',
                dataType: 'json',
                success: function(data) {
                    alert(JSON.stringify(data));
                    console.log(data);
                },
                error: function(data) {
                    console.log(data);   
                }
            });
        }
        function eliminarRuta(){
            var url="webresources/routes/eraseRoute";           
            var content=document.eliminarRutaForm.content.value;
            console.log("index.jsp.eliminarRuta:::content="+content);

            $.ajax({
                url: url,
                type: 'PUT',
                data: content,
                contentType: 'application/json',
                dataType: 'json',
                success: function(data) {
                    alert(JSON.stringify(data));
                    console.log(data);
                },
                error: function(data) {
                    console.log(data);   
                }
            });
        } 
        function renombrarRuta(){
            var url="webresources/routes/renameRoute";           
            var content=document.renombrarRutaForm.content.value;
            console.log("index.jsp.renombrarRuta:::content="+content);

            $.ajax({
                url: url,
                type: 'PUT',
                data: content,
                contentType: 'application/json',
                dataType: 'json',
                success: function(data) {
                    alert(JSON.stringify(data));
                    console.log(data);
                },
                error: function(data) {
                    console.log(data);   
                }
            });
        } 
    </script> 
    <div>

        <hr>
        <h4>Admistracion usuarios</h4>
<!-- Añadir Usuario  -->        
        <hr>
        <form name="altaUsuarioForm" id="altaUsuarioForm" accept-charset="UTF-8">
        <b>URL: </b>webresources/adminusers/altadeusuario<br>
        <b>method: </b>PUT<br>
        <b>params:</b><br>
        <textarea style="width:90%;height:120px" id="content" accept-charset="UTF-8">
        {"name":"inakio", 
        "passwd":"mypasswd",
        "email":"ignacio.olabarrieta@tecnalia.com",
        "host":"samu",
        "gcm":"ASKLDJ()AS&)D&/ASGDJHA"
        }
        </textarea>
        <br><br>
        <button type="button" onclick="altaDeUsuario();return false;">Test alta De Usuario</button>
        </form>
<!--  get Info Usuario -->
        <hr>
        <form name="getInfoUsuarioForm" id="getInfoUsuarioForm" accept-charset="UTF-8">
        <b>URL: </b>webresources/adminusers/getInfo<br>
        <b>method: </b>PUT<br>
        <b>params:</b><br>
        <textarea style="width:90%;height:50px" id="content" accept-charset="UTF-8">
        {
         "name":"inakio"
        }
        </textarea>
        <br><br>
        <button type="button" onclick="getInfoDeUsuario();return false;">Test get Info De Usuario</button>
        </form>
<!--  save Types Usuario -->
        <hr>
        <form name="guardarTiposUsuarioForm" id="guardarTiposUsuarioForm" accept-charset="UTF-8">
        <b>URL: </b>webresources/adminusers/saveTypes<br>
        <b>method: </b>PUT<br>
        <b>params:</b><br>
        <textarea style="width:90%;height:100px" id="content" accept-charset="UTF-8">
        {
         "name":"inakio",
         "types":"ACD",
         "levels":"3"
        }
        </textarea>
        <br><br>
        <button type="button" onclick="guardarTiposDeUsuario();return false;">Test guardar tipos De Usuario</button>
        </form>
<!--  change Password Usuario -->
        <hr>
        <form name="cambiarPasswdUsuarioForm" id="cambiarPasswdUsuarioForm" accept-charset="UTF-8">
        <b>URL: </b>webresources/adminusers/changePasswd<br>
        <b>method: </b>PUT<br>
        <b>params:</b><br>
        <textarea style="width:90%;height:70px" id="content" accept-charset="UTF-8">
        {
         "name":"inakio",
         "passwd":"my_very_secret_password2"
        }
        </textarea>
        <br><br>
        <button type="button" onclick="cambiarPasswdDeUsuario();return false;">Test cambiar Password De Usuario</button>
        </form>     
<!--  change Email Usuario -->
        <hr>
        <form name="cambiarEmailUsuarioForm" id="cambiarEmailUsuarioForm" accept-charset="UTF-8">
        <b>URL: </b>webresources/adminusers/changeEmail<br>
        <b>method: </b>PUT<br>
        <b>params:</b><br>
        <textarea style="width:90%;height:70px" id="content" accept-charset="UTF-8">
        {
         "name":"inakio",
         "email":"inakio@gmail.com"
        }
        </textarea>
        <br><br>
        <button type="button" onclick="cambiarEmailDeUsuario();return false;">Test cambiar Email De Usuario</button>
        </form>           
<!--  Añadir Ruta   -->
        <hr>
        <form name="anadirRutaForm" id="anadirRutaForm" accept-charset="UTF-8">
        <b>URL: </b>webresources/routes/saveRoute<br>
        <b>method: </b>PUT<br>
        <b>params:</b><br>
        <textarea style="width:90%;height:180px" id="content" accept-charset="UTF-8">
        {
        "name":"inakio",
        "route_name":"mi_ruta", 
        "path":"_p~iF~ps|U_ulLnnqC_mqNvxq`@",  
        "ini_time":390, 
        "end_time":1230,
        "days":31,  
        "conf":"45B",  
        "distance":"2"
        }
        </textarea>
        <br><br>
        <button type="button" onclick="anadirRuta();return false;">Test añadir Ruta</button>
        </form>
        <hr>   
<!--  Eliminar Ruta   -->
        <hr>
        <form name="eliminarRutaForm" id="eliminarRutaForm" accept-charset="UTF-8">
        <b>URL: </b>webresources/routes/eraseRoute<br>
        <b>method: </b>PUT<br>
        <b>params:</b><br>
        <textarea style="width:90%;height:60px" id="content" accept-charset="UTF-8">
        {
        "name":"inakio",
        "trajectory_id":2
        }
        </textarea>
        <br><br>
        <button type="button" onclick="eliminarRuta();return false;">Test eliminar Ruta</button>
        </form>
        <hr>   
<!--  Renombrar Ruta   -->
        <hr>
        <form name="renombrarRutaForm" id="renombrarRutaForm" accept-charset="UTF-8">
        <b>URL: </b>webresources/routes/renameRoute<br>
        <b>method: </b>PUT<br>
        <b>params:</b><br>
        <textarea style="width:90%;height:100px" id="content" accept-charset="UTF-8">
        {
        "name":"inakio",
        "trajectory_id":2,
        "route_name":"my_new_route"
        }
        </textarea>
        <br><br>
        <button type="button" onclick="renombrarRuta();return false;">Test reNombrar Ruta</button>
        </form>
        <hr>            
    </div>

</body>
</html>