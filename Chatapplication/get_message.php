<?php 
	include 'connection.php';
	
$senderid = $_POST["sender"];
$receiverid = $_POST["receiver"];
$query = 'select * from messages where receiverid in("'.$senderid.'","'.$receiverid.'") and senderid in("'.$senderid.'","'.$receiverid.'")';
$result = mysqli_query($conn,$query);
$count = mysqli_num_rows($result);
$response = array();
if($count >0)
{
	while($row=mysqli_fetch_assoc($result))
	{
		$data = $row;
		$senderid = $row['senderid'];
		$query = "select image_path from users where uid = '".$senderid."'";
		$result1 = mysqli_query($conn,$query);
		$userdata = mysqli_fetch_assoc($result1);
		$image_path  =  $userdata['image_path'];
		$data['image_path'] = $image_path;
		array_push($response,$data);
	}
}
else
{
	echo "error";
}
echo json_encode(array("message"=>$response));

 ?>
