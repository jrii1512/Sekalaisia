<html>

<HEAD>
    <!--<LINK href="../masterInput2.css" rel="stylesheet" type="text/css">-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">


<style>
.container {
  width: 400px;
  clear: both;
}

label, textarea, input {
  display: inline-block;
  width: 140px;
  text-align: left;
}​

.el02 { /* Text and background colour, blue on light gray */
color:#00f;
background-color:#ddd;
font-weight:bold;
}

/*
.container {
  width: 50%;
  clear: both;
}
*/
</style>

<script>
// When the user clicks on <div>, open the popup
var showNoteText = 0;


function showNote() {    
    if (showNoteText === 1)
    {
        alert("Tarkista kentät.");
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

$alkupaino = 89.5;
 //http://smart-ip.net/geoip-json

//server stuff
/*
$details = json_decode(file_get_contents("http://ipinfo.io/{$ip}/json"));
$detsu = json_encode($details, true);
$id = $details->id;
$url = $details->url;
?><h3><?php echo $pvm ?></h3><?php
*/

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (isset($_POST['submit'])) {
        include 'connari.php';

        $pvm = $_POST['PVM'];
        /*?><h3><?php echo $pvm ?></h3><?php*/
        $tulot = $_POST['Tulot'];
        $menot = $_POST['Menot'];
        $selite = $_POST['Selite'];
        //$tyyppi = $_POST['Tyyppi'];
        $tyyppi = $_POST['tyypit'];

        //echo "Error (sql) " .  "<br>" . mysqli_error($con);

        $query = "INSERT INTO talous(PVM, Tulot, Menot, Selite, Tyyppi) VALUES ('$pvm','$tulot', '$menot', '$selite','$tyyppi')";
        //$date = date('Y-m-d H:i:s'); //not in use
        
        //echo 'query: '.$query.'<br>';
        $result = mysqli_query($con, $query) or die('inserting values failed '.mysqli_error($con));

        if ($result)
        {
            ?><h3><?php echo $pvm ?></h3><?php
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

    <body>
    <div class="w3-container w3-white">

        <pre>

        </pre>
        <center>
            
            <fieldset style="width:400px">
            <div class = "w3-container w3-gray">
                <legend>
                <?php $d = date('Y-m-d');?>
                    <h3>Talouden Hallinta</h3>
                </legend>

                <!--indexTrain.php-->
            
                <form class="form-style-7" method="post">
                
                <!--<form class="form-style-7" method="post">-->
                    <ul>    
                           
                           <!--
                            <p>
                            <label for="pvm">Pvm</label>
                            <input type="date" name="pvm" min="2020-08-25" max="2020-12-31" value="<?php echo $d;?>">
                            -->
                    
                            <p>
                            <label class = "w3-input" for="PVM">PVM</label>
                            <input class = "w3-input" type="date" name="PVM" value = <?php echo date("Y-m-d");?>>
                            <p>

                            <label class = "w3-input" for="Tulot">Tulot</label>
                            <input class = "w3-input" type="number" step = "0.1" name="Tulot" value = 0>
                            <p>

                            <label class = "w3-input" for="Menot">Menot</label>
                            <input class = "w3-input" type="number" step = "0.1" name="Menot" value = 0>

                            <!--<label for="Tyyppi">Tyyppi</label>
                            <input type="text" name="Tyyppi" value = "Oma">-->

                            <label class = "w3-input" for="Tyyppi">Tyyppi:</label>

                            <select class = "w3-input" name="tyypit" id="tyypit">
                                <option value="Firma">Firma</option>
                                <option value="Ruoka">Ruoka</option>
                                <option value="Bensa">Bensa</option>
                                <option value="Moto">Moottoripyörä</option>
                                <option value="Auto">Auto</option>
                            </select>

                            <p>
                            <label class = "w3-input" for="Selite">Selite</label>
                            <textarea class = "w3-input" cols=20 rows=5 name="Selite"></textarea>
                        <p>
                        <input class = "w3-input" type="submit" name="submit" value="Kantaan">
                    
            </fieldset>
            </div>
            </ul>
            </form>
        </center>
    </body>
</html>