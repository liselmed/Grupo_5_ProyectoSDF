<?php
include 'conexion.php';
$correo=$_POST['usuario'];
$contraseña=$_POST['password'];


$sentencia=$conexion->prepare("SELECT * FROM Usuario WHERE correo=? AND contraseña=?");
$sentencia->bind_param('ss',$correo,$contraseña);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
         echo json_encode($fila,JSON_UNESCAPED_UNICODE);     
}
$sentencia->close();
$conexion->close();
?>