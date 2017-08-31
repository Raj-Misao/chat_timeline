<?php 
include 'connection.php';

$query = "select * from posts order by id desc";
$result = mysqli_query($conn, $query);
$count = 0;
while ($row = mysqli_fetch_assoc($result)) {
	$userid = $row['userid'];
	$data[]= $row;

	$query = "select username from users where uid='".$userid."'";
	$result1 = mysqli_query($conn,$query);
	while($row1 = mysqli_fetch_assoc($result1)){
		$data[$count]['username'] = $row1['username'];
	}
	$count++;
}

echo json_encode(array("postdata"=>$data));
 ?>