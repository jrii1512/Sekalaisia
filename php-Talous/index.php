<HTML>

<HEAD>
    <!--<meta http-equiv="content-type" content="text/plain" charset="utf-8" />-->
<style>
#talousTable {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#talousTable td, #motoTable th {
  border: 1px solid #ddd;
  padding: 8px;
}

#talousTable tr:nth-child(even){background-color: #f2f2f2;}

#talousTable tr:hover {background-color: #ddd;}

#talousTable th {
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
	
include "connari.php";

$merkki = "'";
$sql = "SELECT * FROM talous";


$result = $con->query($sql);

$numOfRows = mysqli_num_rows($result);

if($numOfRows==0)
{
    echo 'No results';
}
else
{
    print "<h2>Talouskirja</h2>";
    print "<table id='talousTable'>";
        print "<thead>";
            print"<tr>";
                print "<th style='width:20px'>ID </th>";
                print "<th style='width:200px'>PVM </th>";
                print "<th style='width:200px'>Tulot</th>";
                print "<th style='width:100px'>Menot</th>";
                print "<th style='width:100px'>Selite</th>";
                print "<th style='width:200px'>Tyyppi </th>";
            print "</tr>";
        print "</thead>";
    print "</table>";   

    $firmanTulo = 0;
    $firmanMeno = 0;
    $ruokaMeno = 0;
    $motoMeno = 0;
    $autoMeno = 0;
    $bensaMeno = 0;
    $total = 0;
    $firmanMenot = [];
    $ruokaMenot = [];
    $motoMenot = [];
    $autoMenot = [];
    $bensaMenot = [];
    $bensaMeno = 0;
    $firmanTulot = [];
    $fType = "";
    $bType = "";
    $aType = "";
    $mType = "";
    $rType = "";

    while ($db_field = mysqli_fetch_assoc($result)) {    
        if ($db_field['Tyyppi'] == "Firma"){
            $fType = "Firma";
            $firmanTulot[] = $db_field["Tulot"];
            $firmanMenot[] = $db_field["Menot"];    
            foreach($firmanMenot as $fM){
                $firmanTulo = $firmanMeno + $fM;       
            }

            foreach($firmanTulot as $fT){
                $firmanTulo = $firmanTulo + $fT;       
            }

        }
        else if ($db_field['Tyyppi'] == "Ruoka"){
            $rType = "Ruoka";
            $ruokaMenot[] = $db_field["Menot"];    
            foreach($ruokaMenot as $rM){
                $ruokaMeno = $ruokaMeno + $rM;       
            }

        }

        else if ($db_field['Tyyppi'] == "Bensa"){
            $bType = "Bensa";
            $bensaMenot[] = $db_field["Menot"];    
            foreach($bensaMenot as $bM){
                $bensaMeno = $bensaMeno + $bM;       
            }
        }

        else if ($db_field['Tyyppi'] == "Moto"){
            $mType = "Moto";
            $motoMenot[] = $db_field["Menot"];    
            foreach($motoMenot as $mM){
                $motoMeno = $motoMeno + $mM;       
            }

        }
       
        else if ($db_field['Tyyppi'] == "Auto"){
            $aType = "Auto";
            $autoMenot[] = $db_field["Menot"];    
            foreach($autoMenot as $aM){
                $autoMeno = $autoMeno + $aM;       
            }
        }


        print "<table id ='talousTable'>";
        print "<tr><td style='width:20px'>" . $db_field['ID'] . "<td style='width:200px'>" . $db_field['PVM'] . "</td></td><td style='width:200px'>" . $db_field['Tulot'] . "</td><td style='width:100px'>" . $db_field['Menot'] . "</td><td style='width:200px'>" . wordwrap($db_field['Selite'], 100, "<br>\n")  . "<td style='width:100px'> " . $db_field['Tyyppi'] . "</td></td></tr>";         
        
        print "</table>";
        
    }


    //kaikki menot
    $kaikkiMenot = $firmanMeno + $bensaMeno + $autoMeno + $motoMeno + $ruokaMeno;

    print "<h3>Yhteenveto (Tulot - Menot)</h3>";
    print "<p>Firman" . " tulot: " . $firmanTulo . " euroa, Menot: " . $firmanMeno . " euroa.<br>";
    print "Polttoaine menot" . ": " . $bensaMeno . " euroa.<br>";
    print "Autoilun menot" . ": " . $autoMeno . " euroa.<br>";
    print "Moottoripyörän menot" . ": " . $motoMeno . " euroa.<br>";
    print "Ruokamenot" . ": " . $ruokaMeno . " euroa.<br>";
    print "<p>Yhteensä tuloja: " . $firmanTulo . ", menoja " . $kaikkiMenot; 
}


$con->close();
?>

</body>

</HTML>