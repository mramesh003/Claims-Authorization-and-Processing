$(document).ready(function() {
	document.getElementById("submit").style.color = "white";
	document.getElementById("submit").style.background = "#000";
	document.getElementById("submit").style.opacity = ".4";
	$('#file').change(function() {
		if ($("#file").val() != '') {
			$('#submit').attr('disabled', false);
			document.getElementById("submit").style.color = null;
			document.getElementById("submit").style.background = null;
			document.getElementById("submit").style.opacity = null;
		} else {
			$('#submit').attr('disabled', true);
			document.getElementById("submit").style.color = "white";
			document.getElementById("submit").style.background = "#000";
			document.getElementById("submit").style.opacity = ".4";
		}
	});
});
		