$(document).ready(function() {
	
	document.getElementById("submit").style.color = "red";
	//document.getElementById("submit").style.background = "#000";
	document.getElementById("submit").style.background = null;
	document.getElementById("submit").style.opacity = ".5";
	$('#file').change(function() {
		if ($("#file").val() != '') {
			$('#submit').attr('disabled', false);
			document.getElementById("submit").style.color = "green";
			document.getElementById("submit").style.background = null;
			document.getElementById("submit").style.opacity = null;
		} else {
			$('#submit').attr('disabled', true);
			document.getElementById("submit").style.color = "black";
			//document.getElementById("submit").style.background = "#000";
			document.getElementById("submit").style.background = null;
			//document.getElementById("submit").style.opacity = ".4";
		}
	});
});
		