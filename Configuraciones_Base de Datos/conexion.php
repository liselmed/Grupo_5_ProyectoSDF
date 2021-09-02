<?php
 $servername = "localhost";
 $username = "id17421150_sistemasembebidos";
 $password = "XbF96TejPsx9qAl#";
 $dbname = "id17421150_sdf_data";
 
	 
$conexion=new mysqli($servername,$username,$password,$dbname);
if($conexion->connect_errno){
    echo "El sitio web está experimentado problemas";
}
?>