<?php
include 'conexion.php';
$id_vaca= $_GET['id_vaca'];
$id_fecha= $_GET['id_fecha'];

$consulta= "SELECT * FROM Datos WHERE id_vaca = '$id_vaca' and id_fecha = '$id_fecha'";
$resultado = $conexion ->query($consulta);

while ($fila = $resultado->fetch_array()){
         $info_data[]= array_map('utf8_encode',$fila);     
}
echo json_encode($info_data);
$resultado -> close();
?>