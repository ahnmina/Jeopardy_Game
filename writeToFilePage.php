
<html>
<head>

<title> CS 4640 Assignment 2 </title>
</head>
<body bgcolor="#EEEEEE">
<?php
		$file = fopen('myfile.txt', 'a');
		if(!empty($_POST['sa-question2'])){
			fwrite($file,$_POST['sa-question2']);
			fwrite($file, ":");
			fwrite($file,$_POST['sa-answer2'].PHP_EOL);
		}
		else if(!empty($_POST['mc-question2'])){
			fwrite($file, $_POST['mc-question2']);
			fwrite($file, ":");
			fwrite($file, $_POST['mc-answer2'].PHP_EOL);
		}
		else if(!empty($_POST['tf-question2'])){
			fwrite($file, $_POST['tf-question2']);
			fwrite($file, ":");
			fwrite($file, $_POST['tf-answer2'].PHP_EOL);
		}
		fclose($file);
		?>
		<center><h1> Thank you for submitting your question! </h1>
		Click <a href="./myfile.txt">here</a> to see your file.
		<br><br>
		Click <a href="./home.php">here</a> to submit another question.</center>
</body>
</html>
