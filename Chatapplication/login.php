<?php 
include 'connection.php';

$email = $_POST['email'];
$password = $_POST['password'];
$query = 'select * from users where email="'.$email.'" and password="'.$password.'"';
$result = mysqli_query($conn,$query);
$count = mysqli_num_rows($result);
$data = array();
if($count >0)
{
	$row = mysqli_fetch_array($result);
	$login = $row;
	array_push($data,$row);
	echo json_encode(['user'=>$data]);
}
else
{
	echo "error";
}
 ?>