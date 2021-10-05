<HTML>

<HEAD>
    <!--<meta http-equiv="content-type" content="text/plain" charset="utf-8" />-->
<style>
</HEAD>

<body>

<?php
	
    include "connari.php";

    $merkki = "'";
    $sqlDEL = "DELETE FROM talous";

    $result = $con->query($sqlDEL);
    print("Kaikki tuhottu");

    ?>
    <script type="text/javascript">
        window.location = "http://localhost/php-Talous/index.php";
    </script>

    <?php
    $con->close();
?>

</body>

</HTML>