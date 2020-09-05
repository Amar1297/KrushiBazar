<?php

require "connect.php";

$pname = $_POST["pname"];
$pdescription = $_POST["pdescription"];
$pprice = $_POST["pprice"];
$status = $_POST["pstatus"];

$sql = "insert into products(name,description,price,status) values ('$pname','$pdescription','$pprice','$pstatus');";

if(mysqli_query($conn, $sql))
	echo "Product added successfully";
else
	echo "Something went wrong";		

mysqli_close($conn);

?>