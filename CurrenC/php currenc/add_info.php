<?php
require "init.php";

$username = $_POST["username"];
$password = $_POST["password"];
$email = $_POST["email"];
$contact = $_POST["contact"];


$dob = $_POST["dob"];
$gender = $_POST["gender"];

$password = $_POST["password"];
$sql = "insert into users values('$username','$password','$email','$contact','$dob','$gender');";
if(mysqli_query($con,$sql))
{
	echo"<br><h3>One Row Inserted..</h3>";

}
else{
	echo"Error in Insertion.....".mysqli_error($con);
}


?>