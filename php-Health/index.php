<!DOCTYPE HTML>
<HTML>

<HEAD>
    <meta http-equiv="refresh" content="10" />
    <LINK href="css/health.css" rel="stylesheet" type="text/css">
    <title>Jounin Terveys Seuranta</title>
</HEAD>

<body>
    <?php
    include "connariPudotus.php";

    $pArvot = array();
    $merkki = "'";
    $sql = "SELECT * FROM Pudotus ORDER BY id DESC LIMIT 14";
    $result = $con->query($sql);
    $numRecs = mysqli_num_rows($result);

    if (mysqli_num_rows($result) == 0) {
        print 'No results';
    } else {

        print "<table class = 'center'>";
            print "<thead>";
            print "<tr class = 'healthTable tr'>";
                print "<th id = 'th0' style='width:80px'>PVM</th>";
                print "<th id='th1' style='width:60px'>Paino</th>";
                print "<th id='th2' style='width:60px'>Reisi</th>";
                print "<th id='th3' style='width:60px'>Vyötärö</th>";
                print "<th id='th4' style='width:60px'>Rinta</th>";
                print "<th id='th5' style='width:60px'>Yläpaine</th>";
                print "<th id='th6' style='width:60px'>Alapaine</th>";
                print "<th id='th7' style='width:60px'>Pulssi</th>";
                print "<th id='th8' style='width:60px'>Peff</th>";
                print "<th id='th9' style='width:300px; text-align:left'>Tulos</th>";
        print "</tr>";
        print "</thead>";
        print "</table>";


        $dateArray = [];
        $currentHours = Date('H') + 1;

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

            if ($paino > 80 || $paino == 0) {
                $painoRes = "<td id = 'th1' style=background-color:red;width:60px>" . $db_field['paino'];
            } else {
                $painoRes = "<td id = 'th1' style=background-color:green;width:60px>" . $db_field['paino'];
            }

            if ($reisi > 60 || $reisi == 0) {
                $reisiRes = "<td id = 'th2' style=background-color:red;width:60px>" . $db_field['reisi'];
            } else {
                $reisiRes = "<td id = 'th2' style=background-color:green;width:60px>" . $db_field['reisi'];
            }

            if ($rinta > 105 || $rinta == 0) {
                $rintaRes = "<td id = 'th3' style=background-color:red;width:60px>" . $db_field['rinta'];
            } else {
                $rintaRes = "<td id = 'th3' style=background-color:green;width:60px>" . $db_field['rinta'];
            }

            if ($vyotaro > 95  || $vyotaro == 0) {
                $vyotaroRes = "<td id = 'th4' style=background-color:red;width:60px>" . $db_field['vyotaro'];
            } else {
                $vyotaroRes = "<td id = 'th4' style=background-color:green;width:60px>" . $db_field['vyotaro'];
            }

            if ($ylaP > 139   || $ylaP == 0) {
                $ylaPaineRes = "<td id = 'th5' style=background-color:red;width:60px>" . $db_field['ylaPaine'];
            } else {
                $ylaPaineRes = "<td id = 'th5' style=background-color:green;width:60px>" . $db_field['ylaPaine'];
            }

            if ($alaP > 79   || $alaP == 0) {
                $alaPaineRes = "<td id = 'th6' style=background-color:red;width:60px>" . $db_field['alaPaine'];
            } else {
                $alaPaineRes = "<td id = 'th6' style=background-color:green;width:60px>" . $db_field['alaPaine'];
            }

            if ($pulssi > 51 || $pulssi == 0) {
                $pulssiRes = "<td id = 'th7' style=background-color:red;width:60px>" . $db_field['pulssi'];
            } else {
                $pulssiRes = "<td id = 'th7' style=background-color:green;width:60px>" . $db_field['pulssi'];
            }

            if ($peff < 610  || $peff == 0) {
                $peffRes = "<td id = 'th8' style=background-color:red;width:60px>" . $db_field['peff'];
            } else {
                $peffRes = "<td id = 'th8' style=background-color:green;width:60px>" . $db_field['peff'];
            }

            $showMe = "<tr><td id = 'th0' style='width:80px'>" . $db_field['pvm'] . "</td>" . $painoRes . "</td>" . $reisiRes . "</td><td " . $vyotaroRes .  "</td>" . $rintaRes . "</td>" . $ylaPaineRes . "</td>" . $alaPaineRes . "</td>" . $pulssiRes . "</td>" . $peffRes . "</td><td id = 'th9' style='width:300px'>" . $tulosWrapped . "</td></tr>";
            
            print "<table style = 'margin-left:500px'>";
                print "$showMe";
            print "</table>";

        
        }
    }

    $con->close();
    ?>

</body>

</HTML>