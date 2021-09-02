<?php
include 'conexion.php';
$fecha= $_GET['fecha'];

$consulta= "SELECT id_fecha FROM Fecha WHERE fecha = '$fecha'";
$resultado = $conexion ->query($consulta);

while ($fila = $resultado->fetch_array()){
         $info_fecha[]= array_map('utf8_encode',$fila);     
}
echo json_encode($info_fecha);
$resultado -> close();
?>