<?php 
include 'connection.php';
$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];
$birthday = $_POST['birthday'];
$gender = $_POST['gender'];
$image_name = $_POST['image_name'];
$encoded_string = $_POST['encoded_string'];
$decoded_string = base64_decode($encoded_string);
$path = 'images/profile/'.$image_name;
$file = fopen($path, "wb");
$is_written = fwrite($file,$decoded_string);
fclose($file);


$query = 'insert into users values("","'.$name.'","'.$email.'","'.$password.'","'.$birthday.'","'.$gender.'","'.$path.'")';
$result = mysqli_query($conn,$query);
 ?>