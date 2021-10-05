<html>
<HEAD>
  <!--LINK href="master.css" rel="stylesheet" type="text/css"-->
</HEAD>
<body>

<?php

$un = "root";
$ps = "";
$server = "localhost:3306";
$database = "jrla";
$table = "talous";

// Create connection
$con = new mysqli($server, $un, $ps, $database);
// Check connection
if ($con->connect_error) {
    print "connection error";
    die("Connection failed: " . $con->connect_error);
} 

/*For testing purposes
$sql = "SELECT * FROM Pudotus";
$result = $con->query($sql);

if ($result->num_rows > 0) {*/
    // output data of each row
    //while($row = $result->fetch_assoc()) {
       //echo "Connection file -> id: " . $row["id"] . "<p>";
       /*
    }
} else {*/
 //   echo "0 results";
//}
//$con->close();
//print "connected";
//?>

</body>
</html>
