<HTML>

<HEAD>
    <!--<meta http-equiv="content-type" content="text/plain" charset="utf-8" />-->
    <style>

    </style>
    <LINK href="masterMoto2.css" rel="stylesheet" type="text/css">
    <!--<LINK href="../masterRead.css" rel="stylesheet" type="text/css">-->
</HEAD>

<body>


    <?php
session_start();
	
include "connariMoto.php";

$merkki = "'";
//$sql = "SELECT * FROM moto ORDER BY id DESC LIMIT 1";
$sql = "SELECT * FROM moto ORDER BY id";


$result = $con->query($sql);


if($results->num_rows === 0)
{
    echo 'No results';
}
else
{
    
    print "<h2>Moto details</h2>";
    print "<table>";
            print"<tr>";
                print "<th>ID </th>";
                print "<th>Scope </th>";
                print "<th>Km </th>";
                print "<th>Pvm </th>";
                print "<th>Paikka </th>";
                print "<th>Lisätietoja </th>";
            print "</tr>";
    print "</table>";   

    while ($db_field = mysqli_fetch_assoc($result)) {
        print "<table align='center'>";
        print "<tr><td>" . $db_field['id'] . "</td><td>" . $db_field['scope'] . "</td><td>" . $db_field['km'] . " km</td><td> " . $db_field['pvm'] . "</td><td>" . $db_field['paikka'] .  "</td><td>" . wordwrap($db_field['lisatietoja'], 100, "<br>\n") . "</td></tr>";         
        print "</table>";    
    }
    print "<p>";

if (isset($_POST['submitform']))
    {         
  
    ?>
    <script type="text/javascript">
        window.location = "http://moto.jiju.fi/indexMoto.php";
    </script>
    <?php
    }

    $pCounter = $pCounter + 1;
	if ( $pCounter == 5){
		
		?>
    <p style='page-break-before:always;'><?php
		$pCounter = 0;
	}
}
$con->close();
?>

</body>

</HTML>