<?php
include 'conexion.php';
$correo= $_GET['correo'];

$consulta= "SELECT id_usuario FROM Usuario WHERE correo = '$correo'";
$resultado = $conexion ->query($consulta);

while ($fila = $resultado->fetch_array()){
         $info_usuario[]= array_map('utf8_encode',$fila);     
}
echo json_encode($info_usuario);
$resultado -> close();
?>