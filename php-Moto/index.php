<HTML>

<HEAD>
    <!--<meta http-equiv="content-type" content="text/plain" charset="utf-8" />-->
<style>
#motoTable {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#motoTable td, #motoTable th {
  border: 1px solid #ddd;
  padding: 8px;
}

#motoTable tr:nth-child(even){background-color: #f2f2f2;}

#motoTable tr:hover {background-color: #ddd;}

#motoTable th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
  color: white;
}
</style>
    <!--<LINK href="masterMoto.css" rel="stylesheet" type="text/css">-->
    <!--<LINK href="../masterRead.css" rel="stylesheet" type="text/css">-->
</HEAD>

<body>


    <?php
session_start();
	
include "connariMoto.php";

$merkki = "'";
//$sql = "SELECT * FROM moto ORDER BY id DESC LIMIT 1";
$sql = "SELECT * FROM moto ORDER BY Kertyma";


$result = $con->query($sql);


if(mysqli_num_rows($result)==0)
{
    echo 'No results';
}
else
{
    print "<h2>Moto (Indian Scout (XGM-63) 2021 Päiväkirja</h2>";
    print "<table id='motoTable'>";
        print "<thead>";
            print"<tr>";
                print "<th style='width:20px'>ID </th>";
                print "<th style='width:200px'>Tapahtuma </th>";
                print "<th style='width:100px'>Km </th>";
                print "<th style='width:100px'>Kirjaus Pvm </th>";
                print "<th style='width:200px'>Selite </th>";
                print "<th style='width:200px'>Kertymä </th>";
            print "</tr>";
        print "</thead>";
    print "</table>";   
    

    while ($db_field = mysqli_fetch_assoc($result)) {        
        print "<table id='motoTable'>";
        print "<tr><td style='width:20px'>" . $db_field['ID'] . "</td><td style='width:200px'>" . $db_field['Tapahtuma'] . "</td><td style='width:100px'>" . $db_field['KM'] . " km</td><td style='width:100px'> " . $db_field['PVM'] . "</td><td style='width:200px'>" . wordwrap($db_field['Selite'], 100, "<br>\n")  .  "</td><td style='width:200px'>" . $db_field['Kertyma'] . "</td></tr>";         
        print "</table>";    
    }

if (isset($_POST['submitform']))
    {         
  
    ?>
    <script type="text/javascript">
        window.location = "http://moto.jiju.fi/index.php";
    </script>
    <?php
    }

    /*
    $pCounter = $pCounter + 1;
	if ( $pCounter == 5){
		
		?>
    <p style='page-break-before:always;'><?php
		$pCounter = 0;
    }
    */
}
$con->close();
?>

</body>

</HTML>