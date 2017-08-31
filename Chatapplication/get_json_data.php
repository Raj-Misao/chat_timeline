<?php 
include 'connection.php';
$userid = $_POST['userid'];
$query = 'select * from users where uid not in("'.$userid.'")';
$result = mysqli_query($conn,$query);
$count = mysqli_num_rows($result);
$response = array();
if($count >0)
{
	while($row=mysqli_fetch_assoc($result))
	{
		$data = $row;
		array_push($response,$data);
	}
}
else
{
	echo "error";
}
echo json_encode(array("data"=>$response));
 ?>