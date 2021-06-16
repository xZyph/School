<?php
    $dsn = 'mysql:dbname=uinv19gr27;host=localhost';
    $user = 'uinv19gr27';
    $password = 'uinv19gr27';

    try {
        $db = new PDO($dsn, $user, $password);
    } 
    catch (PDOException $e) {
        echo 'Dang! Stuff happened... Here\'s what: ' . $e->getMessage();
    }
?>