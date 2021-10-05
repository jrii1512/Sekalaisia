<!DOCTYPE HTML>
<HTML>

<HEAD>
    <meta http-equiv="refresh" content="60"/>
<style>
#motoTable {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  /*width: 100%;*/
}

#motoTable td {
  border: 1px solid #ddd;
  padding: 8px;
}


#motoTable tr:nth-child(even){background-color: #f2f2f2;}

#motoTable tr:hover {background-color: #ddd;}

#motoTable thead {
  padding-top: 8px;
  padding-left: 8px;
  padding-bottom: 8px;
  text-align: center;
  background-color: #4CAF50;
  color: white;
  display: table-header-row;
}
</style>
    <!--<LINK href="masterMoto.css" rel="stylesheet" type="text/css">-->
    <!--<LINK href="../masterRead.css" rel="stylesheet" type="text/css">-->
</HEAD>

<body>


<?php 
//session_start();
	
include "connariPudotus.php";
require "/opt/lampp/htdocs/phpChart_Lite/conf.php";

$pArvot = array();
$merkki = "'";
$sql = "SELECT * FROM Pudotus ORDER BY id DESC LIMIT 14";
$sql2 = "SELECT paino FROM Pudotus ORDER BY id DESC LIMIT 14";
$sql3 = "SELECT pvm FROM Pudotus ORDER BY id ASC LIMIT 1";
$sql4 = "SELECT pvm FROM Pudotus ORDER BY id DESC LIMIT 1";
$vSQL = "SELECT vyotaro FROM Pudotus ORDER BY id LIMIT 14";
$rintaSql = "SELECT rinta FROM Pudotus ORDER BY id LIMIT 14";

$result = $con->query($sql);
$numRecs = mysqli_num_rows($result);

/*
$sql2 = "SELECT paino FROM Pudotus WHERE id DESC";
$lastPaino = $con->query($sql2);
print("Viimeinen paino " . $lastPaino);
*/

//print("Number of records" + $numRecs);

if(mysqli_num_rows($result)==0)
{
    print 'No results';
}
else
{
    print "<h2>Jounin Terveys Seuranta</h2>";
    print "<table>";
    #print "<th>pvm</th>paino</th><th>ylapaine</th><th>alapaine</th><th>pulssi</th><th>peff</th><th>tulos</th>";
    #print "<th>pvm | paino | reisi | vyotaro | rinta | ylapaine | alapaine | pulssi | peff | tulos</th>";
    print "</table>";

    $dateArray=[];
    $res = $con->query($sql3); 
    $res2 = $con->query($sql4); 

    //Mikäli tietoja ei ole annettu niin tulos muuttujaan kirjoitetaan paastomisnote..
    while($pvmRes = mysqli_fetch_assoc($res2)){
        $pvmFromDB = $pvmRes['pvm'];
        $currentHours = Date('H') + 1;

        $pvmStripped = substr($pvmFromDB, 0, 10);
        $pvmDB = strtotime($pvmStripped);
        echo '' . $pvmStripped . ', ' . Date('yy-m-d');
        $today = strtotime(Date('yy-m-d'));
        //print('Latest, ' .$pvmDB. '<br>Today  ' . $today);
        
        if( $currentHours > 8 ){
            if ( strcmp($pvmDB, $today) != 0 ) {
                include 'connariPudotus.php';
                $tulos = "Ei mittauksia, vain normi ilta-aamupaasto";
                $query = "INSERT INTO Pudotus(paino, reisi, vyotaro, rinta, ylaPaine, alaPaine, pulssi, tulos) VALUES ('0','0','0','0','0','0','0','$tulos')";
                $res2 = mysqli_query($con, $query) or die ('inserting values failed '.mysqli_error($con));
                }   
        }
}

    //Paastomisdatan käsittelyä
    while($pvmRes = mysqli_fetch_assoc($res)){
        //print($pvmRes['pvm']);
        $startDateTemp = $pvmRes['pvm'];
        $startDate = new DateTime("27-7-2020");
        $fastPeriodStart = new DateTime("06-10-2020");
        $today = new DateTime(Date('d-m-Y'));
        $diff = $startDate->Diff($today);
        $diffFast = $fastPeriodStart->Diff($today);
        
        $remainingMinutes = 60-Date('i');
        $remaining = $currentHours . ':' . $remainingMinutes;
        $hour = Date('H') + 1;
        $min = Date('i');
        $from = $hour . ":" . $min;
        $start = strtotime($from);
        $stop = strtotime("11:00");
        $diffTime = ($stop - $start)/60;     

        //eating
        $now = $currentHours . ":" . Date('i');
        $eatStart = strtotime($now);
        $eatStop = strtotime("19:00");
        $diffEatTime = ($eatStop -$eatStart)/60 ;

        //late fasting
        //$fastingStarting = "24:00" - $now;
        $startFast = strtotime($now);
        $stopFast = strtotime("24:00");
        $diffFastTime = ($stopFast - $startFast)/60;

        $nextStartFast = strtotime("00:01");
        $nextStopFast = strtotime("11:00");
        $diffFastTime2 = ($nextStopFast-$nextStartFast)/60;
        $finalFastTime = ($diffFastTime + $diffFastTime2);

        //Next day..
        $nextDay = Date('d')+1;
        
        echo '<table>';
        //print("" . $nextDay);
        //print("" . $currentHours . Date('i'));

        echo $diff->Format('<td style=background-color:green; width:100px;text-align:center>Time now:' . $currentHours . ':' . Date('i') . '</td>');
        //Aikaista paastoamista ..
        if ( $currentHours > 1 && $currentHours < 12 ){ 
            echo '<td style=background-color:orange;width:200px;text-align:center> Fasting period, ' . $diffTime . ' minutes left</td><p>';
            }
            
        //syömisikkuna    
        else if ( $currentHours > 11 && $currentHours < 19 && Date('i') < 60 ) {
            echo '<td style=background-color:green; width:200px;text-align:center>Eating ok, ' . $diffEatTime . ' minutes left</td><p>';
        }
        //
        else if ( $currentHours > 18 && Date('i') > 00 )
            //if ( $nextDay ) 
            {
            echo '<td style=background-color:blue; width:200px;text-align:center>Fasting, ' . $finalFastTime . ' minutes left</td><p>';
        }
        
        echo $diff->Format('<td style=background-color:green; width:100px;text-align:center>From start %R%a days</td>');
        echo $diffFast->Format('<td style=background-color:green; width:100px;text-align:center>%R%a fasting days (16:8)</td>');
        array_push($dateArray, $pvmRes['pvm']); 
        echo '</table>';
    }

    $p = array();
    $painoArr = array();
    $vyotaroArr = array();


    //Paino ym mittojen käsittelyä
    while ($db_field = mysqli_fetch_assoc($result)) {
        $pvm = $db_field['pvm'];        
        $paino = $db_field['paino'];
        $reisi = $db_field['reisi'];
        $rinta = $db_field['rinta'];
        $vyotaro = $db_field['vyotaro'];
        $peff = $db_field['peff'];
        $ylaP = $db_field['ylaPaine'];
        $alaP = $db_field['alaPaine'];
        $pulssi = $db_field['pulssi'];
        $tulos = $db_field['tulos'];
        $tulosWrapped = wordwrap($tulos, 200, "<br />\n");

        if ( $paino > 80 || $paino == 0 )
            {
                $painoRes = "<td style=background-color:red;width:100px;text-align:center>" . $db_field['paino'];
            }
        else{ 
                $painoRes = "<td style=background-color:green;width:100px;text-align:center>" . $db_field['paino'];
            }

        if ( $reisi > 60 || $reisi == 0 )
            {
                $reisiRes = "<td style=background-color:red;width:100px;text-align:center>" . $db_field['reisi'];
            }
        else{ 
                    $reisiRes = "<td style=background-color:green;width:100px;text-align:center>" . $db_field['reisi'];
            }

        if ( $rinta > 105 || $rinta == 0 )
            {
                $rintaRes = "<td style=background-color:red;width:100px;text-align:center>" . $db_field['rinta'];
            }
        else{ 
                $rintaRes = "<td style=background-color:green;width:100px;text-align:center>" . $db_field['rinta'];
            }


        if ( $vyotaro > 95  || $vyotaro == 0 )
            {
                $vyotaroRes = "<td style=background-color:red;width:100px;text-align:center>" . $db_field['vyotaro'];
            }
        else{ 
                $vyotaroRes = "<td style=background-color:green;width:100px;text-align:center>" . $db_field['vyotaro'];
            }

        if ( $ylaP > 139   || $ylaP == 0 )
            {
                $ylaPaineRes = "<td style=background-color:red;width:100px;text-align:center>" . $db_field['ylaPaine'];
            }
        else{ 
                $ylaPaineRes = "<td style=background-color:green;width:100px;text-align:center>" . $db_field['ylaPaine'];
            }

        if ( $alaP > 79   || $alaP == 0 )
            {
                $alaPaineRes = "<td style=background-color:red;width:100px;text-align:center>" . $db_field['alaPaine'];
            }
        else{ 
                $alaPaineRes = "<td style=background-color:green;width:100px;text-align:center>" . $db_field['alaPaine'];
            }

  if ( $pulssi > 51 || $pulssi == 0 )
            {
                $pulssiRes = "<td style=background-color:red;width:100px;text-align:center>" . $db_field['pulssi'];
            }
        else{ 
                $pulssiRes = "<td style=background-color:green;width:100px;text-align:center>" . $db_field['pulssi'];
            }

        if ( $peff < 610  || $peff == 0 )
            {
                $peffRes = "<td style=background-color:red;width:100px;text-align:center>" . $db_field['peff'];
            }
        else{ 
                $peffRes = "<td style=background-color:green;width:100px;text-align:center>" . $db_field['peff'];
        }

        //if waist, tigh or chess have not been given..
        /*
        if ($reisi == 0 || $vyotaro == 0 || $rinta == 0 ){

            //<td style='width:20px'>" . $db_field['id'] . "</td>
            print("<p>");
            print "<table >";
            print "<tr><td style='width:100px'>" . $db_field['pvm'] . "</td>"  . $painoRes . " kg</td>" . $ylaPaineRes . " yläp.</td>" . $alaPaineRes . " alap.</td>" . $pulssiRes . " pulssi</td>" . $peffRes . " peff</td><td style='width:200px'>" . $tulosWrapped . "</td></tr>";         
            print "</table>";
            print "<p>";
            array_push($painoArr, round($db_field['paino'],1));
        }
        else{*/
            print("<p>");
            //<td style='width:20px'>" . $db_field['id'] . "</td>
            print "<table >";
            print "<tr><td style='width:100px'>" . $db_field['pvm'] . "</td>" . $painoRes . " kg</td>" . $reisiRes . " cm (reisi)</td>" . $vyotaroRes .  " cm (vyötärö)</td>" . $rintaRes . " cm (rinta)</td>" . $ylaPaineRes . " yläp.</td>" . $alaPaineRes . " alap.</td>" . $pulssiRes . " pulssi</td>" . $peffRes . " peff</td><td style='width:350px'>" . $tulosWrapped . "</td></tr>";         
            print "</table>";
            print "<p>";
            //array_push($painoArr, round($db_field['paino'],1));
            //array_push($vyotaroArr, round($db_field['vyotaro'],1));    
            //}
        //}

    //print_r($paino);
    //end($painoArr);
    //$lastP=key($painoArr);
    //end($vyotaroArr);
    //$lastV=key($vyotaroArr);
    //print "Last weight" + $lastP;
    //for($h=0; $h<count($paino);$h++){
    //$pc = new C_PhpChartX(array(array($painoArr[$lastP],$painoArr[5], $painoArr[4], $painoArr[3], $painoArr[2], $painoArr[1], $painoArr[0])), 'Paino chart');
    //$pcV = new C_PhpChartX(array(array($vyotaroArr[0],$vyotaroArr[1], $vyotaroArr[2], $vyotaroArr[3], $vyotaroArr[4], $vyotaroArr[5], $painoArr[$lastP])), 'Vyotärö chart');
    //$pcV = new C_PhpChartX(array(array($vyotaroArr[0], $vyotaroArr[$lastV])), 'Vyotärö chart');
    //$pc = new C_P    //$pc = new C_PhpChartX(array(array($painoArr[0],$painoArr[1], $painoArr[2], $painoArr[3], $painoArr[4], $painoArr[5], $painoArr[$lastP])), 'Paino chart');
hpChartX(array($paino[0],$paino[1], $paino[2], $paino[3], $paino[4], $paino[5], $paino[$lastP]), 'Paino chart');
    //$pc = new C_PhpChartX(array(array($paino[$h])),'Paino chart');
    //}
    //print "<p>";
    //$pc->set_title(array('text'=>'Fat Chart'));
    //$pc->draw();   

    //print "<p>";
    
    //$pcV->set_title(array('text'=>'Waist Chart'));
   // $pcV->draw();
         

}
}

$con->close();
?>

</body>

</HTML>