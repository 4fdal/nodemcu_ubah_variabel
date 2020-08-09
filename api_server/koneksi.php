<?php

$sql = new mysqli('localhost', 'root', '', 'pintu');
if ($sql->connect_error) die("Connection failed: " . $sql->connect_error);

