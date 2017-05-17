<!doctype html>
<html>
<head>
	<title> CS 4640 Assignment 2 </title>
	<style>
		table {
			font-family: arial, sans-serif;
			border-collapse: collapse;
			width: 100%;
		}

		td, th {
			border: 1px solid #dddddd;
			text-align: left;
			padding: 8px;
		}

		tr:nth-child(even) {
			background-color: #dddddd;
		}
	</style>
	<h1> <center>Question Maker <center></h1>
	<!--Need to put names on this assignment-->
	<center><span style="color:#f00"><b>"Annette Chun (amc4sq) & Mina Ahn (ma3hd)"</b></span></center>
	<h3> Instructions </h3>
	In order to start creating your question, you must select a question type from the dropdown bar.
	You may choose one of three question types to submit: short answer, multiple choice, or true or false.
	Once you have selected a type, you must fill out both the question and answer inputs and then click on
	the "Submit" button to submit your question. Some question types will require that you select one answer
	out of the many options to be correct. If you fail to fill out all the inputs, your question will not be
	submitted, and the system will remind you to fill out all the fields. If you would like to clear all of
	your data entries on the screen and start from scratch, you may click on the "Clear" button to do so. You
	can always switch between question types when you need to. Your inputs will still exist in the text boxes
	even when moving from one question type to another unless you clear all data inputs for each question. Your
	inputs will also disappear when you submit a question. You may only submit one question at a time. You will
	receive a confirmation alert when you have successfully submitted a question and will then be redirected
	to a simple form handler page.
	
	<h3> Additional Information </h3>
	When typing into one of the text fields, the box will become blue to indicate that you typing within that
	input box. The color will disappear when you click anywhere outside of the box.
	<br>
</head>
<body>
	<br><br>
	<h3> CREATE YOUR QUESTION HERE </h3>
	<h3> Choose question type: </h3>
	<form>
		<?php
		if($_SERVER['REQUEST_METHOD'] === 'POST' && $_POST['type'] === 'mcxxx'){
			echo '<select id="questionType" onchange="showDataInputBox()">';
			echo '<option value="sa">Short Answer</option>';
			echo '<option selected value="mc">Multiple Choice</option>';
			echo '<option value="tf">True or False</option>';
			echo '</select>';
		}
		else if($_SERVER['REQUEST_METHOD'] === 'POST' && $_POST['type'] === 'tfxx'){
			echo '<select id="questionType" onchange="showDataInputBox()">';
			echo '<option value="sa">Short Answer</option>';
			echo '<option value="mc">Multiple Choice</option>';
			echo '<option selected value="tf">True or False</option>';
			echo '</select>';
		}
		else{
			echo '<select id="questionType" onchange="showDataInputBox()">';
			echo '<option value="sa">Short Answer</option>';
			echo '<option value="mc">Multiple Choice</option>';
			echo '<option value="tf">True or False</option>';
			echo '</select>';
		}
		?>
	</form>

	<div id="shortanswer" style="display:block">
		<form method="post" action="formHandler.php" name="sa-info" onSubmit="return (validateInputs())">
			<table>
				<h3 name="instruction">Enter short answer question:</h3>
				<span id="sa-msg" style="color:red; font-style:italic; font-size:80%"></span>
				<tr>
					<th>Question: </th>
					<?php
					if ($_SERVER['REQUEST_METHOD'] === 'POST' && $_POST['type'] === 'sa'){
						echo '<td><input type="text" name="sa-question" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)" value="'.$_POST['sa-question2'].'"></td>';
					}																																									
					else{
						echo '<td><input type="text" name="sa-question" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)"></td>';
					}
					?>
				</tr>
				<tr>
					<th>Answer: </th>
					<?php
					if ($_SERVER['REQUEST_METHOD'] === 'POST' && $_POST['type'] === 'sa'){
						echo '<td><textarea name="sa-answer" style="width:250px;height:150px;" onfocus="changeColor(this)" onblur="defaultColor(this)">'.$_POST['sa-answer2'].'</textarea></td>';
					}																																									
					else{
						echo '<td><textarea name="sa-answer" style="width:250px;height:150px;" onfocus="changeColor(this)" onblur="defaultColor(this)" value="hello"></textarea></td>';
					}
					?>
				</tr>
			</table>
			<center><input type="submit" name="submit" value="Submit">
				<input type="reset" value="Clear"></center>
		</form>
	</div>

	<div id="multiplechoice" style="display:none">
		<form method="post" action="formHandler.php" name="mc-info" onSubmit="return (validateInputs())">
			<table>
				<h3 name="instruction">Enter multiple choice question:</h3>
				<span id="mc-msg" style="color:red; font-style:italic; font-size:80%" onfocus="changeColor(this)" onblur="defaultColor(this)"></span>
				<tr>
					<th>Question: </th>
					<?php
					if ($_SERVER['REQUEST_METHOD'] === 'POST' && $_POST['type'] === 'mc'){
						echo '<td><input type="text" name="mc-question" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)" value="'.$_POST['mc-question2'].'"></td>';
					}																																									
					else{
						echo '<td><input type="text" name="mc-question" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)"></td>';
				
					}
					?>
				</tr>
				<tr>
					<th>Answer: </th>
					<?php
					if ($_SERVER['REQUEST_METHOD'] === 'POST' && $_POST['type'] === 'mc'){
						echo '<td><input type="radio" name="mc-radio" value="mc-radio1"><input type="text" name="mc-answer1" style="width:250px"onfocus="changeColor(this)" onblur="defaultColor(this)" value="'.$_POST['mc-answer1x'].'"></td>';
						echo '</tr>';
						echo '<tr><td><td><input type="radio" name="mc-radio" value="mc-radio2"><input type="text" name="mc-answer2" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)" value="'.$_POST['mc-answer2x'].'"></td></td></tr>';
						echo '<tr><td><td><input type="radio" name="mc-radio" value="mc-radio3"><input type="text" name="mc-answer3" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)" value="'.$_POST['mc-answer3x'].'"></td></td></tr>';
						echo '<tr><td><td><input type="radio" name="mc-radio" value="mc-radio4"><input type="text" name="mc-answer4" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)" value="'.$_POST['mc-answer4x'].'"></td></td>';	
					}																																									
					else{
						echo '<td><input type="radio" name="mc-radio" value="mc-radio1"><input type="text" name="mc-answer1" style="width:250px"onfocus="changeColor(this)" onblur="defaultColor(this)"></td>';
						echo '</tr>';
						echo '<tr><td><td><input type="radio" name="mc-radio" value="mc-radio2"><input type="text" name="mc-answer2" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)"></td></td></tr>';
						echo '<tr><td><td><input type="radio" name="mc-radio" value="mc-radio3"><input type="text" name="mc-answer3" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)"></td></td></tr>';
						echo '<tr><td><td><input type="radio" name="mc-radio" value="mc-radio4"><input type="text" name="mc-answer4" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)"></td></td>';	
					}
					?>				
			</table>

			<center><td><input type="submit" name="submit" value="Submit">
				<input type="reset" value="Clear"></td></center>
		</form>
	</div>

	<div id="truefalse" style="display:none">
		<form method="post" action="formHandler.php" name="tf-info" onSubmit="return (validateInputs())">
			<table>
				<h3 name="instruction">Enter true or false question:</h3>
				<span id="tf-msg" style="color:red; font-style:italic; font-size:80%"></span>
				<tr>
					<th>Question: </th>
					<?php
					if ($_SERVER['REQUEST_METHOD'] === 'POST' && $_POST['type'] === 'tf'){
						echo '<td><input type="text" name="tf-question" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)" value="'.$_POST['tf-question2'].'"></td>';
					}																																									
					else{
						echo '<td><input type="text" name="tf-question" style="width:250px" onfocus="changeColor(this)" onblur="defaultColor(this)"></td>';
					}
					?>
				</tr>
				<tr>
					<th>Answer: </th>
					<?php
					if ($_SERVER['REQUEST_METHOD'] === 'POST' && $_POST['type'] === 'tf'){
						echo '<td><input type="radio" name="tf-answer" value="true">True</td>';
						echo '</tr>';
						echo '<tr><td><td><input type="radio" name="tf-answer" value="false">False</td></td></tr>';
					}																																									
					else{
						echo '<td><input type="radio" name="tf-answer" value="true">True</td>';
						echo '</tr>';
						echo '<tr><td><td><input type="radio" name="tf-answer" value="false">False</td></td></tr>';
					}
					?>
			</table>
			<center><td><input type="submit" name="submit" value="Submit">
				<input type="reset" value="Clear"></td></center>
		</form>
	</div>
</body>
</html>

<script type="text/javascript">
	function showDataInputBox(){
		var change = document.forms[0].questionType.value;
		if (change==="sa"){
			document.getElementById("multiplechoice").style.display="none";
			document.getElementById("shortanswer").style.display="block";
			document.getElementById("truefalse").style.display="none";
			document.getElementById("sa-msg").innerHTML="";
			document.getElementById("mc-msg").innerHTML="";
			document.getElementById("tf-msg").innerHTML="";
		}
		else if (change==="mc"){
			document.getElementById("multiplechoice").style.display="block";
			document.getElementById("shortanswer").style.display="none";
			document.getElementById("truefalse").style.display="none";
			document.getElementById("sa-msg").innerHTML="";
			document.getElementById("mc-msg").innerHTML="";
			document.getElementById("tf-msg").innerHTML="";
		}else{
			document.getElementById("multiplechoice").style.display="none";
			document.getElementById("shortanswer").style.display="none";
			document.getElementById("truefalse").style.display="block";
			document.getElementById("sa-msg").innerHTML="";
			document.getElementById("mc-msg").innerHTML="";
			document.getElementById("tf-msg").innerHTML="";
		}
	}
	
	function validateInputs(){
		var qType = document.forms[0].questionType.value;
        if (qType=="sa"){
			var q = document.forms["sa-info"]["sa-question"].value;
			var a = document.forms["sa-info"]["sa-answer"].value;
			//checks if both the question and answer inputs are filled out
			if (q===null || q==="" || a===null || a===""){
				document.getElementById("sa-msg").innerHTML = "Please fill out all input fields.";
				return(false);
			}
		}else if (qType==="mc"){
			var q = document.forms["mc-info"]["mc-question"].value;
			var r = document.getElementsByName("mc-radio");
			var a1 = document.forms["mc-info"]["mc-answer1"].value;
			var a2 = document.forms["mc-info"]["mc-answer2"].value;
			var a3 = document.forms["mc-info"]["mc-answer3"].value;
			var a4 = document.forms["mc-info"]["mc-answer4"].value;
			//checks if all text inputs are filled
			if (q===null || q==="" || a1===null || a1==="" || a2===null || a2===""|| a3===null || a3===""|| a4===null || a4===""){
				document.getElementById("mc-msg").innerHTML = "Please fill out all input fields.";
				return(false);
			}
			//checks if at least one radio button is checked
			var bool = false;
			for(var i=0; i<r.length; i++){
				if (r[i].checked){
					bool = true;
				}
			}
			if (bool===false){
				document.getElementById("mc-msg").innerHTML = "Please select at least one answer as correct.";
				return(false);
			}
		}else{
			var q = document.forms["tf-info"]["tf-question"].value;
			var a = document.getElementsByName("tf-answer");
			//checks if question input is filled
			if (q===null || q===""){
				document.getElementById("tf-msg").innerHTML = "Please fill out the question field.";
				return(false);
			}
			//checks if at least one radio button is checked
			var bool = false;
			for(var i=0; i<a.length; i++){
				if (a[i].checked){
					bool = true;
				}
			}
			if (bool===false){
				document.getElementById("tf-msg").innerHTML = "Please select either true or false.";
				return(false);
			}
		}
		confirmation();
     }
	 
	 function confirmation() {
		alert("Thank you for submitting a question! You will be redirected to a simple form handler page.");
	 }
	 
	 function changeColor(x){
		x.style.background="paleturquoise";
	 }
	 
	 function defaultColor(x){
		x.style.background="white";
	 }
	 
	 function dothis(){
		 document.write("Hello world!");
	 }
	 
	 function dothat(){
		 document.write("I HATE THIS GAME");
	 }
</script>