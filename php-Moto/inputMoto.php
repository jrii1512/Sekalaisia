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
//include '../sshtunnel/enable_ssh.php';
//define variables and set to empty values
$ErrorErr = '';
$name = $story = '';

$ip = $_SERVER['REMOTE_ADDR'];
 //http://smart-ip.net/geoip-json

$details = json_decode(file_get_contents("http://ipinfo.io/{$ip}/json"));
$detsu = json_encode($details, true);
/*
$id = $details->id;
$url = $details->url;
*/
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (isset($_POST['submit'])) {
        include 'connariMoto.php';

        $scope = $_POST['scope'];
        $pvm = $_POST['pvm'];
        $paikka = $_POST['paikka'];
        $lisatietoja = $_POST['lisatietoja'];
        $km = $_POST['km'];

        $query = "INSERT INTO moto (scope, pvm, paikka, km, lisatietoja) VALUES ('$scope','$pvm', '$paikka', '$km','$lisatietoja')";
        //print "" . $scope . ", " . $pvm . ", " . $paikka . ", " . $lisatietoja . ", " . $km; 

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
                    <h3>Indian Scout Journal (XGM-63)</h3>
                </legend>

                <!--indexTrain.php-->
                <form class="form-style-7" method="post">
                <!--<form class="form-style-7" method="post">-->
                    <ul>                        
                            <label for="type">Scope: </label>
                            <select style="margin-left:10px" name="scope">                                
                                <option value="Huolto">Huolto</option>
                                <option value="Tripit">Tripit</option>
                                <option value="Muuta">Muuta</option>
                            </select>    
                            <p>
                            <label for="pvm">Pvm</label>
                            <input type="date" name="pvm">
                            <p>

                            <label for="km">Km</label>
                            <input type="number" name="km">
                            <p>

                            <label for="paikka">Paikka</label>
                            <input type="string" name="paikka">
                        
                            <p>
                            <label for="lisatietoja">Lisätietoja</label>
                            <br>
                            <textarea cols=30 rows=5 name="lisatietoja"></textarea>

                        <p>
                        <input type="submit" name="submit" value="Send" class=btn-spacing>

            </fieldset>
            </div>
            </ul>
            </form>
        </center>
    </body>
</html>