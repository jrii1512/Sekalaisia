<html>
<HEAD>
  <!--LINK href="master.css" rel="stylesheet" type="text/css"-->
</HEAD>
<body>

<?php
/*
$un = "gwyokupv_visitor";
$ps = "visitor07";
$server = "mysql02.domainhotelli.fi:3306";
$database = "gwyokupv_Jiju";
$table = "juji";

//Check connection
$con = mysqli_connect($server, $un, $ps, $database);
if (mysqli_connect_errno())
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		mysqli_close(true);
	}
		
else
{
$connection_status = "database connected"; 
//$mysqli_db = mysqli_select_db($con, $database) or die("couldn't connect...".mysqli_error($con));;

}
*/
$un = "gwyokupv_visitor";
$ps = "visitor_07";
$server = "mysql02.domainhotelli.fi:3306";
$database = "gwyokupv_terveys";
$table = "Pudotus";

// Create connection
$con = new mysqli($server, $un, $ps, $database);
// Check connection
if ($con->connect_error) {
    print "connection error";
    die("Connection failed: " . $con->connect_error);
} 

//For testing purposes
$sql = "SELECT * FROM Pudotus";
$result = $con->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
       //echo "Connection file -> id: " . $row["id"] . "<p>";
    }
} else {
 //   echo "0 results";
}
//$con->close();
//print "connected";
//?>

</body>
</html>
