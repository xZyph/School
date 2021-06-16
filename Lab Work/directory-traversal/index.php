<?php
  $tittel = "@xZyph - DT";
  $html = "";
  $side = htmlspecialchars($_GET['fil'] ?? '😈');
  $antallhopp = substr_count($side, '../');
  $sti = "/var/www/datasikkerhet";

  for($i = $antallhopp; $i > 0; $i--) {
    $sti = substr($sti, 0, strrpos( $sti, '/'));
    $side = explode('../', $side);
    array_shift($side);
    $side = implode("../", $side);
  }

  if(!isset($_GET['fil'])) {
    header('Location: ?fil=index.php');
  }
  else if($side != 'etc/passwd')
    $html .= "<p>Error: Finner ikke filen med filsti: " . $sti  . "/" . $side;
  else
    $html .= "<p>Contratz</p>";

?>
<!DOCTYPE html>
<html lang="nb">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title><?php echo $tittel; ?></title>
</head>
<body>
  <?php echo $html; ?>
</body>
</html>