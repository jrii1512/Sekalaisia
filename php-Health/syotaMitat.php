<html>

<HEAD>
    <!--<LINK href="../masterInput2.css" rel="stylesheet" type="text/css">-->
<style>
.container {
  width: 500px;
  clear: both;
}

.container input {
  width: 50%;
  clear: both;
}
</style>

<script>
// When the user clicks on <div>, open the popup
var showNoteText = 0;


function showNote() {    
    if (showNoteText === 1)
    {
        alert("Tarkista treenien kestot.");
    }
}
</script>


</HEAD>
<body>

    <?php

//define variables and set to empty values
$ErrorErr = '';
$name = $story = '';

$ip = $_SERVER['REMOTE_ADDR'];
 //http://smart-ip.net/geoip-json

$details = json_decode(file_get_contents("http://ipinfo.io/{$ip}/json"));
$detsu = json_encode($details, true);

$id = $details->id;
$url = $details->url;

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (isset($_POST['submit'])) {
        include 'connariMoto.php';

        $reisi = $_POST['reisi'];
        $vyotaro = $_POST['vyotaro'];
        $rinta = $_POST['rinta'];

        $query = "INSERT INTO Pudotus VALUES ('$pvm', '$paikka', '$km','$lisatietoja')";
        print "" . $reisi . ", " . $vyotaro. ", " . $paikka . ", " . $lisatietoja . ", " . $km; 

        //$date = date('Y-m-d H:i:s'); //not in use

        
        //echo 'query: '.$query.'<br>';
        $result = mysqli_query($con, $query) or die('inserting values failed '.mysqli_error($con));

        if ($result)
        {
            ?>
            <script>
              window.location.href = "index.php";
            </script>
            <?php
        }
        }
    }

function test_input($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);

    return $data;
}
//$con->close();
?>

    <!--Label { display: inline-block; width: 100px; text-align: right; }-->

    <body id="main_bgc">
        <pre>

</pre>
        <center>
            <div class = "container">
            <fieldset style="width:900px">
                <legend>
                    <h3>Painon syöttö</h3>
                </legend>

                <!--indexTrain.php-->
                <form class="form-style-7" method="post">
                <!--<form class="form-style-7" method="post">-->
                    <ul>                        
                            <label for="reisi">Reisi</label>
                            <input type="number" name="reisi">
                            <p>

                            <label for="vyotaro">Vyötärö</label>
                            <input type="number" name="vyotaro">
                            <p>
                            <label for="rinta">Rinta</label>
                            <input type="number" name="rinta">

                        <p>
                        <input type="submit" name="submit" value="Send" class=btn-spacing>

            </fieldset>
            </div>
            </ul>
            </form>
        </center>
    </body>
</html>