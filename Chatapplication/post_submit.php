<?php 
include 'connection.php';
$postmessage = $_POST['message'];
$userid = $_POST['userid'];
$encoded_string = $_POST['encoded_string'];
$image_name = $_POST['image_name'];
$decoded_string = base64_decode($encoded_string);
$path = 'images/'.$image_name;
$file = fopen($path, "wb");
$is_written = fwrite($file, $decoded_string);
fclose($file);
$query = "insert into posts values('','".$userid."','".$postmessage."','".$image_name."','".$path."')";
$result = mysqli_query($conn,$query);

echo $postmessage." and ".$userid;
 ?>