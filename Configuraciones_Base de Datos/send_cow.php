<?php
include 'conexion.php';
$id_usuario= $_GET['id_usuario'];

$consulta= "SELECT * FROM Vaca WHERE id_usuario = '$id_usuario'";
$resultado = $conexion ->query($consulta);

while ($fila = $resultado->fetch_array()){
         $info_vaca[]= array_map('utf8_encode',$fila);     
}
echo json_encode($info_vaca);
$resultado -> close();
?>