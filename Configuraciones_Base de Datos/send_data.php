	<?php
	 $servername = "localhost";
	 $username = "id17421150_sistemasembebidos";
	 $password = "XbF96TejPsx9qAl#";
	 $dbname = "id17421150_sdf_data";
	 
	$api_key_value = "tPmAT5Ab3j7F9";
	 
	if ($_SERVER["REQUEST_METHOD"] == "POST") {
	        //Obtener la clave enviada por el modulo wifi
	    $api_key = test_input($_POST["api_key"]);
	    if($api_key == $api_key_value) { // si son iguales entonces se lee los datos enviados
	        $id_usuario = test_input($_POST["id_usuario"]);
	        $codigo_vaca = test_input($_POST["codigo_vaca"]);   //Se almacenan los datos en variables
	        $temperatura = test_input($_POST["temperatura"]);
	        $ph = test_input($_POST["ph"]);
	        $datetime = test_input($_POST["datetime"]);
	        // Crear la conexión
	        $conn = new mysqli($servername, $username, $password, $dbname);
	        $temp= intval($temperatura);
	        $ph1=intval($ph);
	        if (($temp<38 or $temp>42) or($ph1<5.6 or $ph1>7)){
	         mail("pedro10farinango@gmail.com","Advertencia","Su vaca se encuentra en peligro.");
	        }
	        
	        if ($conn->connect_error) {
	            die("Connection failed: " . $conn->connect_error);
	        }
	       
	                // Se crea una variable de como se enviarán los datos para el query
	        $sql = "INSERT INTO DatoSensor (id_usuario, codigo_vaca, temperatura, ph, datetime)
	       VALUES ('".$id_usuario."', '".$codigo_vaca."' , '".$temperatura."', '".$ph."', '".$datetime."' )";
	 
	                // Se insertan los datos en una tabla general que tendrá los triggers para que se llenen las tablas
	                // del diagrama entidad relación
	               
	        if ($conn->query($sql) === TRUE) {
	            echo "New record created successfully";
	        }
	        else {
	            echo "Error: " . $sql . "<br>" . $conn->error;
	        }
	   
	        $conn->close();
	    }
	    else {
	        echo "Wrong API Key provided.";
	    }
	 
	}
	else {
	    echo "No data posted with HTTP POST.";
	}
	 
	function test_input($data) {
	    $data = trim($data);
	    $data = stripslashes($data);
	    $data = htmlspecialchars($data);
	    return $data;
	}
	 
	?>
