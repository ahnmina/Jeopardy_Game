<html>
<head>
  <title>Submitted Question</title>
</head>

<body bgcolor="#EEEEEE">
  <center><h2>Submitted Question</h2></center>

  <?php
	if(!empty($_POST['sa-question'])){
		echo "Question: ".$_POST['sa-question']."<br><br>";
		echo "Answer: ".$_POST['sa-answer'];
		echo '<form action="home.php" method="post">';
		echo '<input type="hidden" name="sa-question2" value="'.$_POST['sa-question'].'">';
		echo '<input type="hidden" name="sa-answer2" value="'.$_POST['sa-answer'].'">';
		echo '<input type="hidden" name="type" value="sa">';
		echo '<center><input type="submit" name="edit_q" value="Edit Question"></center>';
		echo '</form>';
		echo '<form name="confirm" method = "post" action="writeToFilePage.php">';
		echo '<input type="hidden" name="sa-question2" value="'.$_POST['sa-question'].'">';
		echo '<input type="hidden" name="sa-answer2" value="'.$_POST['sa-answer'].'">';
		echo '<input type="hidden" name="type" value="sa">';
		echo '<center><input type="submit" name="confirm" value="Confirm"></center>';
		echo '</form>';
	}

	else if(!empty($_POST['mc-question'])){
		echo "Question: ".$_POST['mc-question']."<br><br>";
		$radio = $_POST['mc-radio'];
		$answer = "";
		if ($radio == 'mc-radio1'){
			$answer = $_POST['mc-answer1'];
			echo "Answer: ".$_POST['mc-answer1'];
		}
		else if ($radio == 'mc-radio2'){
			$answer = $_POST['mc-answer2'];
			echo "Answer: ".$_POST['mc-answer2'];
		}
		else if ($radio == 'mc-radio3'){
			$answer = $_POST['mc-answer3'];
			echo "Answer: ".$_POST['mc-answer3'];
		}
		else{
			$answer = $_POST['mc-answer4'];
			echo "Answer: ".$_POST['mc-answer4'];
		}
		echo '<form action="home.php" method="post">';
		echo '<input type="hidden" name="mc-question2" value="'.$_POST['mc-question'].'">';
		echo '<input type="hidden" name="mc-answer1x" value="'.$_POST['mc-answer1'].'">';
		echo '<input type="hidden" name="mc-answer2x" value="'.$_POST['mc-answer2'].'">';
		echo '<input type="hidden" name="mc-answer3x" value="'.$_POST['mc-answer3'].'">';
		echo '<input type="hidden" name="mc-answer4x" value="'.$_POST['mc-answer4'].'">';
		echo '<input type="hidden" name="type" value="mc">';
		echo '<center><input type="submit" name="edit_q" value="Edit Question"></center>';
		echo '</form>';
		echo '<form name="confirm" method = "post" action="writeToFilePage.php">';
		echo '<input type="hidden" name="mc-question2" value="'.$_POST['mc-question'].'">';
		echo '<input type="hidden" name="mc-answer2" value="'.$answer.'">';
		echo '<input type="hidden" name="type" value="mc">';
		echo '<center><input type="submit" name="confirm" value="Confirm"></center>';
		echo '</form>';
	}

	else if(!empty($_POST['tf-question'])){
		echo "Question: ".$_POST['tf-question']."<br><br>";
		$radio = $_POST['tf-answer'];
		$answer = "";
		if ($radio == 'true'){
			$answer = "True";
			echo "Answer: True";
		}
		else{
			$answer = "False";
			echo "Answer: False";
		}
		echo '<form action="home.php" method="post">';
		echo '<input type="hidden" name="tf-question2" value="'.$_POST['tf-question'].'">';
		echo '<input type="hidden" name="type" value="tf">';
		echo '<center><input type="submit" name="edit_q" value="Edit Question"></center>';
		echo '</form>';
		echo '<form name="confirm" method ="post" action="writeToFilePage.php">';
		echo '<input type="hidden" name="tf-question2" value="'.$_POST['tf-question'].'">';
		echo '<input type="hidden" name="tf-answer2" value="'.$answer.'">';
		echo '<input type="hidden" name="type" value="tf">';
		echo '<center><input type="submit" name="confirm" value="Confirm"></center>';
		echo '</form>';
	}

	//in the case that someone accesses the formHandler page directly without submitting a question
	else{
		echo '<form action="home.php">';
		echo '<center><td><input type="submit" name="submit" value="Go to Main Page">';
		echo '</form>';
	}
	?>

</form>
</body>
</html>
