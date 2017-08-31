<?php 
include 'connection.php';

$senderid = $_POST['sid'];
$receiverid = $_POST['rid'];
$message = $_POST['message'];

$query = 'insert into messages values("","'.$senderid.'","'.$receiverid.'","'.$message.'")';
$result = mysqli_query($conn,$query);
 ?>